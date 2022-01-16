package net.onelikeandidie.bordergods.mixins;

import net.minecraft.fluid.LavaFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(LavaFluid.class)
public class DroppedInLavaMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid"))
}
