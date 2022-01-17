package net.onelikeandidie.bordergods.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;
import net.onelikeandidie.bordergods.events.DroppedInLavaCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class DroppedInLavaMixin {

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;discard()V"))
    private void onDamagedItemDiscard(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source == DamageSource.LAVA) {
            var self = (ItemEntity) (Object) this;
            var player_uuid = self.getOwner();
            var world = self.getWorld();
            var player = world.getPlayerByUuid(player_uuid);
            var item_stack = self.getStack();
            ActionResult result = DroppedInLavaCallback.EVENT.invoker().interact(player, item_stack);

            if (result == ActionResult.FAIL) {
                cir.cancel();
            }
        }
    }

}
