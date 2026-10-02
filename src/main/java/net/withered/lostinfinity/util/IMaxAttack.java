package net.withered.lostinfinity.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public interface IMaxAttack {
    record MaxAttackResult(boolean targetKilled) {
        public boolean wasTargetKilled() {
            return targetKilled;
        }
    }

    static MaxAttackResult dealMaxHealth(LivingEntity attacker, LivingEntity target, int multiplier) {
        DamageSource source = attacker instanceof PlayerEntity player
                ? attacker.getWorld().getDamageSources().playerAttack(player)
                : attacker.getWorld().getDamageSources().generic();

        float damage = target.getMaxHealth() * multiplier;
        target.damage(source, damage);

        if (target.getHealth() <= 0.0F) {
            return new MaxAttackResult(true);
        }
        return new MaxAttackResult(false);
    }
}