package net.onelikeandidie.bordergods.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface MinutePassedCallback {
    Event<MinutePassedCallback> EVENT = EventFactory.createArrayBacked(MinutePassedCallback.class,
            (listeners) -> (newMinute) -> {
                for (MinutePassedCallback listener : listeners) {
                    ActionResult result = listener.interact(newMinute);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(int newMinute);
}
