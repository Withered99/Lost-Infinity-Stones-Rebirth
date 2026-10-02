package net.withered.lostinfinity.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.withered.lostinfinity.entity.custom.DeviantShulkerEntity;
import net.withered.lostinfinity.util.ModDamageUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBulletEntity.class)
public abstract class ShulkerBulletEntityMixin {

    @Inject(method = "onEntityHit", at = @At("HEAD"), cancellable = true)
    private void onHitDeviantTarget(EntityHitResult entityHitResult, CallbackInfo ci) {
        ShulkerBulletEntity bullet = (ShulkerBulletEntity) (Object) this;
        Entity owner = bullet.getOwner();

        if (owner instanceof DeviantShulkerEntity) {
            Entity target = entityHitResult.getEntity();

            if (!bullet.getWorld().isClient() && target instanceof LivingEntity livingTarget) {
                ServerWorld serverWorld = (ServerWorld) bullet.getWorld();

                DamageSource damageSource = bullet.getDamageSources().mobAttack((LivingEntity) owner);

                ModDamageUtils.dealMaxHealthDamage(serverWorld, livingTarget, damageSource, 0.15f);
            }

            bullet.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0F, 1.0F);
            bullet.discard();

            ci.cancel();
        }
    }
}