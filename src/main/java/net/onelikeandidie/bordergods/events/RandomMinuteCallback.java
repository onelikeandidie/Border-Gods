package net.onelikeandidie.bordergods.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface RandomMinuteCallback {
    Event<RandomMinuteCallback> EVENT = EventFactory.createArrayBacked(RandomMinuteCallback.class,
            (listeners) -> () -> {
                for (RandomMinuteCallback listener : listeners) {
                    ActionResult result = listener.interact();

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact();
}
