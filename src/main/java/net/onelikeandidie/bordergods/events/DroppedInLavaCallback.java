package net.onelikeandidie.bordergods.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface DroppedInLavaCallback {
    Event<DroppedInLavaCallback> EVENT = EventFactory.createArrayBacked(DroppedInLavaCallback.class,
            (listeners) -> (player, block, item) -> {
                for (DroppedInLavaCallback listener : listeners) {
                    ActionResult result = listener.interact(player, block, item);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(PlayerEntity player, BlockEntity block, ItemStack item);
}
