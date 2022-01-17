package net.onelikeandidie.bordergods.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface DroppedInLavaCallback {
    Event<DroppedInLavaCallback> EVENT = EventFactory.createArrayBacked(DroppedInLavaCallback.class,
            (listeners) -> (player, item) -> {
        for (DroppedInLavaCallback listener : listeners) {
            ActionResult result = listener.interact(player, item);

            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult interact(PlayerEntity player, ItemStack item);
}
