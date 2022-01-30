package net.onelikeandidie.bordergods.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.ActionResult;

public interface VillagerLeveledUpCallback {
    Event<VillagerLeveledUpCallback> EVENT = EventFactory.createArrayBacked(VillagerLeveledUpCallback.class,
            (listeners) -> (villager) -> {
                for (VillagerLeveledUpCallback listener : listeners) {
                    ActionResult result = listener.interact(villager);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(VillagerEntity villager);
}
