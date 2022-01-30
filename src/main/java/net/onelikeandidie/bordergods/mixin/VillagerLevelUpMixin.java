package net.onelikeandidie.bordergods.mixin;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.ActionResult;
import net.onelikeandidie.bordergods.events.VillagerLeveledUpCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public class VillagerLevelUpMixin {
    @Inject(method = "levelUp", at = @At("TAIL"), cancellable = true)
    private void onLevelUp(CallbackInfo ci) {
        ActionResult result = VillagerLeveledUpCallback.EVENT.invoker().interact((VillagerEntity) (Object) this);

        if(result == ActionResult.FAIL) {
            ci.cancel();
        }
    }
}
