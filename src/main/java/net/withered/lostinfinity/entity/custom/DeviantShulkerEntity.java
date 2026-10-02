package net.withered.lostinfinity.entity.custom;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.withered.lostinfinity.entity.custom.base.EntityMultipleLives;

public class DeviantShulkerEntity extends ShulkerEntity implements EntityMultipleLives {
    private static final TrackedData REMAINING_LIVES =
            DataTracker.registerData(DeviantShulkerEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public DeviantShulkerEntity(EntityType entityType, World world) {
        super(entityType, world);
        this.setRemainingLives(this.numberOfLives());
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return ShulkerEntity.createShulkerAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 500)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) {
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = 40;
                this.idleAnimationState.start(this.age);
            } else {
                --this.idleAnimationTimeout;
            }
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

        this.targetSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
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