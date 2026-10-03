package net.withered.lostinfinity.item.custom.weapons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ConvergenceBladesItem extends SwordItem {
    private static final String MODE_KEY = "blade";

    public ConvergenceBladesItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    private Text getConvergenceGradient() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.world == null) {
            return Text.literal("Convergence");
        }

        long time = client.world.getTime();
        String word = "Convergence";
        MutableText convergence = Text.empty();

        int colorA = 0x55FF55;
        int colorB = 0xFF5555;

        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);

            float ratio = (float) ((Math.sin((time * 0.1f) + (i * 0.15f)) + 1.0) / 2.0);
            int rgb = blendColors(colorA, colorB, ratio);

            convergence.append(Text.literal(String.valueOf(character)).setStyle(Style.EMPTY.withColor(rgb)));
        }

        return convergence;
    }

    private int blendColors(int c1, int c2, float ratio) {
        int r = (int) (((c1 >> 16) & 0xFF) + ratio * (((c2 >> 16) & 0xFF) - ((c1 >> 16) & 0xFF)));
        int g = (int) (((c1 >> 8) & 0xFF) + ratio * (((c2 >> 8) & 0xFF) - ((c1 >> 8) & 0xFF)));
        int b = (int) ((c1 & 0xFF) + ratio * ((c2 & 0xFF) - (c1 & 0xFF)));
        return (r << 16) | (g << 8) | b;
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey(stack), getConvergenceGradient());
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List tooltip, TooltipType type) {
        tooltip.add(Text.literal("A weapon that switches sides when you attack.").formatted(Formatting.GOLD));
        tooltip.add(Text.literal("Blade of Destiny: All nearby entities are pulled into the air with you.").formatted(Formatting.GREEN));
        tooltip.add(Text.literal("Blade of Ultimatum: Kills all nearby targets over 100 height.").formatted(Formatting.RED));

        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = attacker.getWorld();
        Box box = attacker.getBoundingBox().expand(8.0);

        List nearbyEntities = world.getEntitiesByClass(LivingEntity.class, box, e -> true);

        if (this.isMode(stack)) {
            for (Object obj : nearbyEntities) {
                if (!(obj instanceof LivingEntity e)) {
                    continue;
                }

                if (e == attacker || !e.isAlive()) {
                    continue;
                }

                if (e.getY() > 100.0) {
                    if (attacker instanceof PlayerEntity player) {
                        e.damage(world.getDamageSources().playerAttack(player), Float.MAX_VALUE);
                    } else {
                        e.damage(world.getDamageSources().generic(), Float.MAX_VALUE);
                    }

                    if (e instanceof PlayerEntity && !world.isClient()) {
                        if (attacker instanceof PlayerEntity playerAttacker) {
                            playerAttacker.sendMessage(
                                    Text.literal("Your blade has consumed " + e.getName().getString())
                                            .formatted(Formatting.GREEN)
                            );
                        }
                    }
                }
            }
        } else if (attacker instanceof ServerPlayerEntity serverAttacker) {
            Vec3d launchVelocity = new Vec3d(0.0, 4.0, 0.0);

            attacker.setVelocity(launchVelocity);
            attacker.velocityModified = true;
            serverAttacker.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(serverAttacker));

            for (Object obj : nearbyEntities) {
                if (!(obj instanceof LivingEntity e)) {
                    continue;
                }

                if (e == attacker || !e.isAlive()) {
                    continue;
                }

                e.setVelocity(launchVelocity);
                e.velocityModified = true;

                if (e instanceof ServerPlayerEntity serverTarget) {
                    serverTarget.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(serverTarget));
                }
            }
        }

        this.toggleMode(stack);
        return true;
    }

    private void toggleMode(ItemStack stack) {
        this.setMode(stack, !this.isMode(stack));
    }

    private void setMode(ItemStack stack, boolean blade) {
        NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        NbtCompound nbt = nbtComponent.copyNbt();
        nbt.putBoolean(MODE_KEY, blade);
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
    }

    private boolean isMode(ItemStack stack) {
        NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        return nbtComponent.copyNbt().getBoolean(MODE_KEY);
    }
}