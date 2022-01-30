package net.onelikeandidie.bordergods.util.config;

import net.onelikeandidie.bordergods.util.config.external.simpleconfig.SimpleConfig;
import net.onelikeandidie.bordergods.util.config.gods.*;

public class ConfigManager {
    public static BorderGodsLoader CONFIG;
    public static EnormaLoader CONFIG_ENORMA;
    public static AnullisLoader CONFIG_ANULLIS;
    public static MerchetLoader CONFIG_MERCHET;

    public static void loadConfig() {
        BorderGodsLoader.loadConfig("bordergods");
        EnormaLoader.loadConfig("bordergods/enorma.yml");
        AnullisLoader.loadConfig("bordergods/anullis.yml");
        MerchetLoader.loadConfig("bordergods/merchet.yml");
    }

    public static void reloadConfigs() {
        BorderGodsLoader.reloadConfig();
        EnormaLoader.reloadConfig();
        AnullisLoader.reloadConfig();
        MerchetLoader.reloadConfig();
    }
}