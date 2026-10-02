package net.withered.lostinfinity.entity.custom.base;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public interface EntityMultipleLives {

    int getRemainingLives();

    void setRemainingLives(int lives);

    int numberOfLives();

    void trueDeathAction();

    default boolean handleMultiLifeDeath(LivingEntity entity, DamageSource damageSource, Runnable superOnDeath) {
        int lives = getRemainingLives();
        if (lives > 1) {
            setRemainingLives(lives - 1);
            entity.setHealth(entity.getMaxHealth());
            entity.clearStatusEffects();
            if (entity.getWorld() != null && !entity.getWorld().isClient()) {
                entity.getWorld().playSound(
                        null, entity.getBlockPos(),
                        SoundEvents.ITEM_TOTEM_USE,
                        SoundCategory.HOSTILE, 1.0F, 1.0F
                );
            }
            return true;
        }

        trueDeathAction();
        superOnDeath.run();
        return false;
    }

    default void writeLivesToNbt(NbtCompound nbt) {
        nbt.putInt("Lives", getRemainingLives());
    }

    default void readLivesFromNbt(NbtCompound nbt) {
        if (nbt.contains("Lives")) {
            setRemainingLives(nbt.getInt("Lives"));
        }
    }
}