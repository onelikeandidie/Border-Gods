package net.onelikeandidie.bordergods;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bordergods implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("bordergods");
    @Override
    public void onInitialize() {
        LOGGER.info("Border Gods Mod Initializing...");
        LOGGER.info("Border Gods Mod Initialized!!!.");
    }
}
