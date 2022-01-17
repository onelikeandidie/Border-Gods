package net.onelikeandidie.bordergods.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.onelikeandidie.bordergods.util.IItemEntityThrower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class AddWhoDroppedItemMixin {

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At(value = "TAIL"))
    public void dropItemAddWhoDroppedItLol(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity self = (PlayerEntity) (Object) this;
        IItemEntityThrower stack_cast = (IItemEntityThrower) (Object) stack;
        if (stack_cast == null) {
            return;
        }
        stack_cast.getThrownByUUID().putUuid("thrownByUUID", self.getUuid());
        Logger logger = LogManager.getLogger("bordergods");
        logger.info(stack_cast.getThrownByUUID());
        stack.setNbt(stack_cast.getThrownByUUID());
    }
}
