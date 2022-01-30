package net.onelikeandidie.bordergods.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.onelikeandidie.bordergods.events.KilledByLavaCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class KilledByLavaMixin {

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void onKilledByLava(DamageSource source, CallbackInfo ci) {
        if (source == DamageSource.LAVA) {
            KilledByLavaCallback.EVENT.invoker().interact((LivingEntity) (Object) this);
        }
    }
}
