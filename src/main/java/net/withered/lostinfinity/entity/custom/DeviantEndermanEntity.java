package net.withered.lostinfinity.entity.custom;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.withered.lostinfinity.entity.custom.base.EntityMultipleLives;
import net.withered.lostinfinity.util.ModDamageUtils;

public class DeviantEndermanEntity extends EndermanEntity implements EntityMultipleLives {
    private static final TrackedData REMAINING_LIVES =
            DataTracker.registerData(DeviantEndermanEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

    public DeviantEndermanEntity(EntityType entityType, World world) {
        super(entityType, world);
        this.setRemainingLives(this.numberOfLives());
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return EndermanEntity.createEndermanAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 500)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.attackAnimationTimeout > 0) {
            --this.attackAnimationTimeout;
        } else {
            this.attackAnimationState.stop();
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 4) {
            this.attackAnimationTimeout = 25;
            this.attackAnimationState.start(this.age);
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(REMAINING_LIVES, 2);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean didAttack = super.tryAttack(target);

        if (didAttack) {
            this.getWorld().sendEntityStatus(this, (byte) 4);

            if (!this.getWorld().isClient() && target instanceof LivingEntity livingTarget) {
                ServerWorld serverWorld = (ServerWorld) this.getWorld();
                ModDamageUtils.dealMaxHealthDamage(
                        serverWorld,
                        livingTarget,
                        this.getDamageSources().mobAttack(this),
                        0.15f
                );
            }
        }

        return didAttack;
    }

    @Override
    public int getRemainingLives() {
        return (Integer) this.dataTracker.get(REMAINING_LIVES);
    }

    @Override
    public void setRemainingLives(int lives) {
        this.dataTracker.set(REMAINING_LIVES, lives);
    }

    @Override
    public int numberOfLives() {
        return 3;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        handleMultiLifeDeath(this, damageSource, () -> super.onDeath(damageSource));
    }

    @Override
    public void trueDeathAction() {
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        writeLivesToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        readLivesFromNbt(nbt);
    }
}