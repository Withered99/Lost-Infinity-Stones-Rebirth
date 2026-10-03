package net.withered.lostinfinity.item.custom.weapons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class DualityBladesItem extends SwordItem {
    private static final String MODE_KEY = "blade";

    public DualityBladesItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    private Text getDualityGradient() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.world == null) {
            return Text.literal("Duality");
        }

        long time = client.world.getTime();
        String word = "Duality";
        MutableText duality = Text.empty();

        int colorA = 0x0000FF;
        int colorB = 0xFF0000;

        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);

            float ratio = (float) ((Math.sin((time * 0.1f) + (i * 0.15f)) + 1.0) / 2.0);
            int rgb = blendColors(colorA, colorB, ratio);

            duality.append(Text.literal(String.valueOf(character)).setStyle(Style.EMPTY.withColor(rgb)));
        }

        return duality;
    }

    private int blendColors(int c1, int c2, float ratio) {
        int r = (int) (((c1 >> 16) & 0xFF) + ratio * (((c2 >> 16) & 0xFF) - ((c1 >> 16) & 0xFF)));
        int g = (int) (((c1 >> 8) & 0xFF) + ratio * (((c2 >> 8) & 0xFF) - ((c1 >> 8) & 0xFF)));
        int b = (int) ((c1 & 0xFF) + ratio * ((c2 & 0xFF) - (c1 & 0xFF)));
        return (r << 16) | (g << 8) | b;
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey(stack), getDualityGradient());
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List tooltip, TooltipType type) {
        tooltip.add(Text.literal("A weapon that switches sides when you attack.").formatted(Formatting.GOLD));
        tooltip.add(Text.literal("Blade of Wind: Sends you and the target flying into the sky.").formatted(Formatting.AQUA));
        tooltip.add(Text.literal("Blade of Treachery: Kills targets over 100 height.").formatted(Formatting.RED));

        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = attacker.getWorld();

        if (this.isMode(stack)) {
            if (target.getY() > 100.0) {
                if (attacker instanceof PlayerEntity player) {
                    target.damage(world.getDamageSources().playerAttack(player), Float.MAX_VALUE);
                } else {
                    target.damage(world.getDamageSources().generic(), Float.MAX_VALUE);
                }

                if (target instanceof PlayerEntity && !world.isClient()) {
                    attacker.sendMessage(
                            Text.literal("Your blade has consumed " + target.getName().getString())
                                    .formatted(Formatting.GREEN)
                    );
                }
            }
        } else if (attacker instanceof ServerPlayerEntity serverAttacker) {
            Vec3d launchVelocity = new Vec3d(0.0, 4.0, 0.0);

            target.setVelocity(launchVelocity);
            target.velocityModified = true;

            attacker.setVelocity(launchVelocity);
            attacker.velocityModified = true;
        }

        this.toggleMode(stack);
        return true;
    }

    private void toggleMode(ItemStack stack) {
        this.setMode(stack, !this.isMode(stack));
    }

    private void setMode(ItemStack stack, boolean mode) {
        NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        NbtCompound nbt = nbtComponent.copyNbt();
        nbt.putBoolean(MODE_KEY, mode);
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
    }

    private boolean isMode(ItemStack stack) {
        NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        return nbtComponent.copyNbt().getBoolean(MODE_KEY);
    }
}