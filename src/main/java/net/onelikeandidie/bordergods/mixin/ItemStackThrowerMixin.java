package net.onelikeandidie.bordergods.mixin;

import net.minecraft.item.ItemStack;
import net.onelikeandidie.bordergods.util.IItemStackThrower;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nullable;
import java.util.UUID;

@Mixin(ItemStack.class)
public abstract class ItemStackThrowerMixin implements IItemStackThrower {
    @Nullable
    private UUID thrownByUUID;

    @Nullable
    @Override
    public UUID getThrownByUUID() {
        return this.thrownByUUID;
    }

    public void setThrownByUUID(@Nullable UUID thrownByUUID) {
        this.thrownByUUID = thrownByUUID;
    }
}
