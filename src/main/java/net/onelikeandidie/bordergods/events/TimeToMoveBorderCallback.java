package net.onelikeandidie.bordergods.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface TimeToMoveBorderCallback {
    Event<TimeToMoveBorderCallback> EVENT = EventFactory.createArrayBacked(TimeToMoveBorderCallback.class,
            (listeners) -> (int newHour) -> {
                for (TimeToMoveBorderCallback listener : listeners) {
                    ActionResult result = listener.interact(newHour);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(int newHour);
}
