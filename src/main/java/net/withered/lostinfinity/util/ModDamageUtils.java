package net.withered.lostinfinity.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;

public class ModDamageUtils {
    public static void dealMaxHealthDamage(ServerWorld world, LivingEntity target, DamageSource source, float percentage) {
        if (target == null || world.isClient()) {
            return;
        }

        float maxHealth = target.getMaxHealth();

        float damageAmount = maxHealth * percentage;

        target.damage(source, damageAmount);
    }
    public static void dealTrueDamage(ServerWorld world, LivingEntity target, DamageSource source, float percentage) {
        if (target == null || world.isClient() || target.isInvulnerableTo(source)) {
            return;
        }

        float maxHealth = target.getMaxHealth();
        float damageAmount = maxHealth * percentage;
        float initialHealth = target.getHealth();
        boolean hurt = target.damage(source, damageAmount);
        if (hurt) {
            float expectedHealth = initialHealth - damageAmount;
                if (target.getHealth() > expectedHealth) {
                    target.setHealth(Math.max(0.0f, expectedHealth));
                }
        }
    }
}