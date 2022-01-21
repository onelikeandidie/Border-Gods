package net.onelikeandidie.bordergods;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.onelikeandidie.bordergods.extensions.GodPlaceholderAPI;
import net.onelikeandidie.bordergods.gods.GodManager;
import net.onelikeandidie.bordergods.listeners.DropInLavaListener;
import net.onelikeandidie.bordergods.listeners.TimeToMoveBorderListener;
import net.onelikeandidie.bordergods.util.ServerTimeLoop;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Bordergods implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("bordergods");
    @Override
    public void onInitialize() {
        LOGGER.info("Border Gods Mod Initializing...");
        // Initialize time check to check the time every hour lol
        ServerTimeLoop.init();
        // Register event listeners
        var lavaListener = new DropInLavaListener();
        lavaListener.register();
        var borderListener = new TimeToMoveBorderListener();
        borderListener.register();
        // If the instance is a server then check for optional dependencies
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            var spl = FabricLoader.getInstance().isModLoaded("styledplayerlist");
            // Register placeholder stuff for messages and player list
            if (spl) {
                GodPlaceholderAPI.register();
            }
        }
        // Initialize the god manager
        GodManager.initialize();
        LOGGER.info("Border Gods Mod Initialized!!!.");
    }
}
