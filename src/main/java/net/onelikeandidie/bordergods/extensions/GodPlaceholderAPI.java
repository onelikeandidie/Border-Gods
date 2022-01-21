package net.onelikeandidie.bordergods.extensions;

import eu.pb4.placeholders.PlaceholderAPI;
import eu.pb4.placeholders.PlaceholderResult;
import net.minecraft.util.Identifier;
import net.onelikeandidie.bordergods.gods.GodManager;

public class GodPlaceholderAPI {
    public static void register() {
        PlaceholderAPI.register(new Identifier("border_gods", "god_list"), context -> {
            return PlaceholderResult.value(GodManager.toList());
        });
    }
}
