package net.onelikeandidie.bordergods.util.config;

import net.onelikeandidie.bordergods.util.config.external.simpleconfig.SimpleConfig;
import net.onelikeandidie.bordergods.util.config.gods.EnormaConfig;
import net.onelikeandidie.bordergods.util.config.gods.EnormaLoader;

public class ConfigManager {
    public static BorderGodsLoader CONFIG;
    public static EnormaLoader CONFIG_ENORMA;

    public static void loadConfig() {
        BorderGodsLoader.loadConfig();
        EnormaLoader.loadConfig();
    }
}