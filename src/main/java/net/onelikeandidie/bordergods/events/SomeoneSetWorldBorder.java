package net.onelikeandidie.bordergods.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public interface SomeoneSetWorldBorder {
    Event<SomeoneSetWorldBorder> EVENT = EventFactory.createArrayBacked(SomeoneSetWorldBorder.class,
            (listeners) -> (World world) -> {
                for (SomeoneSetWorldBorder listener : listeners) {
                    ActionResult result = listener.interact(world);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(World world);
}
