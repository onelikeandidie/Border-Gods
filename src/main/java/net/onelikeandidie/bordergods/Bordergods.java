package net.onelikeandidie.bordergods;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.onelikeandidie.bordergods.extensions.GodPlaceholderAPI;
import net.onelikeandidie.bordergods.gods.GodManager;
import net.onelikeandidie.bordergods.listeners.DropInLavaListener;
import net.onelikeandidie.bordergods.listeners.TimeToMoveBorderListener;
import net.onelikeandidie.bordergods.util.CommandRegister;
import net.onelikeandidie.bordergods.util.ListenerRegister;
import net.onelikeandidie.bordergods.util.ServerTimeLoop;
import net.onelikeandidie.bordergods.util.config.ConfigManager;
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
        // Load the config
        ConfigManager.loadConfig();
        // Initialize time check to check the time every hour lol
        ServerTimeLoop.init();
        // Register event listeners
        ListenerRegister.registerListeners();
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
        // Register commands
        CommandRegister.registerCommands();
        LOGGER.info("Border Gods Mod Initialized!!!.");
    }
}
