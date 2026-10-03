package net.withered.lostinfinity.item.custom.weapons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class SkyvergeItem extends Item {
    public SkyvergeItem(Settings settings) {
        super(settings);
    }

    private Text getSkyvergeGradient() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.world == null) {
            return Text.literal("Skyverge");
        }

        long time = client.world.getTime();
        String word = "Skyverge";
        MutableText skyverge = Text.empty();

        int colorA = 0x00AAAA;
        int colorB = 0x0000FF;

        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);

            float ratio = (float) ((Math.sin((time * 0.1f) + (i * 0.15f)) + 1.0) / 2.0);
            int rgb = blendColors(colorA, colorB, ratio);

            skyverge.append(Text.literal(String.valueOf(character)).setStyle(Style.EMPTY.withColor(rgb)));
        }

        return skyverge;
    }

    private int blendColors(int c1, int c2, float ratio) {
        int r = (int) (((c1 >> 16) & 0xFF) + ratio * (((c2 >> 16) & 0xFF) - ((c1 >> 16) & 0xFF)));
        int g = (int) (((c1 >> 8) & 0xFF) + ratio * (((c2 >> 8) & 0xFF) - ((c1 >> 8) & 0xFF)));
        int b = (int) ((c1 & 0xFF) + ratio * ((c2 & 0xFF) - (c1 & 0xFF)));
        return (r << 16) | (g << 8) | b;
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey(stack), getSkyvergeGradient());
    }

    @Override
    public TypedActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient()) {
            Box searchBox = user.getBoundingBox().expand(30.0);

            Iterable nearbyEntities = world.getEntitiesByClass(LivingEntity.class, searchBox, e -> true);

            for (Object obj : nearbyEntities) {
                if (!(obj instanceof LivingEntity entity)) {
                    continue;
                }

                if (!entity.isAlive() || entity.getUuid().equals(user.getUuid())) {
                    continue;
                }

                double distanceSq = user.squaredDistanceTo(entity);
                if (entity instanceof PlayerEntity player) {
                    double dx = Math.signum(user.getX() - player.getX()) * 0.7;
                    double dy = 0.5 + Math.signum(user.getY() - entity.getY()) * 0.5;
                    double dz = Math.signum(user.getZ() - player.getZ()) * 0.7;
                    player.addVelocity(dx, dy, dz);
                    player.velocityModified = true;
                    player.damage(world.getDamageSources().playerAttack(user), 8.0F);
                } else {
                    if (distanceSq <= 15.0 * 15.0) {
                        double dx = Math.signum(user.getX() - entity.getX()) * -2.5;
                        double dy = 0.5;
                        double dz = Math.signum(user.getZ() - entity.getZ()) * -2.5;
                        entity.addVelocity(dx, dy, dz);
                        entity.velocityModified = true;
                    }
                }
            }
        }

        return TypedActionResult.success(stack, false);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List tooltip, TooltipType type) {
        tooltip.add(Text.literal("When activated, creates 2 gusts of wind:").formatted(Formatting.AQUA));
            tooltip.add(Text.literal("A gust that forces creatures away.").formatted(Formatting.RED));
            tooltip.add(Text.literal("A gust that damages and carries nearby players towards you.").formatted(Formatting.BLUE));

        super.appendTooltip(stack, context, tooltip, type);
    }
}
