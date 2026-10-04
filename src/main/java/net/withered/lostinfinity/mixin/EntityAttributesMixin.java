package net.withered.lostinfinity.mixin;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClampedEntityAttribute.class)
public class EntityAttributesMixin {
    @Inject(method = "getMaxValue", at = @At("HEAD"), cancellable = true)
    private void modifyMaxHealth(CallbackInfoReturnable cir) {
        if (((ClampedEntityAttribute) (Object) this).getTranslationKey().equals("attribute.name.generic.max_health")) {
            cir.setReturnValue(1000000.0);
        }
    }

    @Inject(method = "clamp", at = @At("HEAD"), cancellable = true)
    private void modifyClamp(double value, CallbackInfoReturnable cir) {
        if (((ClampedEntityAttribute) (Object) this).getTranslationKey().equals("attribute.name.generic.max_health")) {
            double clampedValue = Math.max(1.0, Math.min(value, 1000000.0));
            cir.setReturnValue(clampedValue);
        }
    }
}
