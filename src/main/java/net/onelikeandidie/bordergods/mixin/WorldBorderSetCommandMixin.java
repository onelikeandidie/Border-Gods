package net.onelikeandidie.bordergods.mixin;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.WorldBorderCommand;
import net.onelikeandidie.bordergods.events.SomeoneSetWorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldBorderCommand.class)
public class WorldBorderSetCommandMixin {

    @Inject(method = "executeSet", at = @At("HEAD"))
    private static void executeSetEventThrower(ServerCommandSource source, double distance, long time, CallbackInfoReturnable<Integer> cir) {
        SomeoneSetWorldBorder.EVENT.invoker().interact(source.getServer().getOverworld());
    }
}
