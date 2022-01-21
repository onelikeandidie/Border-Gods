package net.onelikeandidie.bordergods.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.onelikeandidie.bordergods.util.IItemStackThrower;
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
        IItemStackThrower stack_cast = (IItemStackThrower) (Object) stack;
        if (stack_cast == null) {
            return;
        }
        stack_cast.setThrownByUUID(self.getUuid());
    }
}
