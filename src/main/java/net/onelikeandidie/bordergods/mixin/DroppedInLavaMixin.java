package net.onelikeandidie.bordergods.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;
import net.onelikeandidie.bordergods.events.DroppedInLavaCallback;
import net.onelikeandidie.bordergods.util.IItemStackThrower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class DroppedInLavaMixin {

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;discard()V"))
    private void onDamagedItemDiscard(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source == DamageSource.LAVA) {
            var self = (ItemEntity) (Object) this;
            var item_stack = self.getStack();
            var player_uuid = ((IItemStackThrower) (Object) item_stack).getThrownByUUID();
            if (player_uuid == null) {
                return;
            }
            var world = self.getWorld();
            var player = world.getPlayerByUuid(player_uuid);
            if (player == null) {
                return;
            }
            ActionResult result = DroppedInLavaCallback.EVENT.invoker().interact(player, item_stack);

            if (result == ActionResult.FAIL) {
                cir.cancel();
            }
        }
    }

}
