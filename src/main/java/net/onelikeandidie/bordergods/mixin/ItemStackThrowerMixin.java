package net.onelikeandidie.bordergods.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.onelikeandidie.bordergods.util.IItemEntityThrower;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStack.class)
public abstract class ItemStackThrowerMixin implements IItemEntityThrower {
    private NbtCompound thrownByUUID;

    @Override
    public NbtCompound getThrownByUUID() {
        if (this.thrownByUUID == null) {
            this.thrownByUUID = new NbtCompound();
        }
        return this.thrownByUUID;
    }
}
