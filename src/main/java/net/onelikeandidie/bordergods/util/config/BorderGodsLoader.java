package net.onelikeandidie.bordergods.util.config;

import net.onelikeandidie.bordergods.util.config.external.simpleconfig.SimpleConfig;

public class BorderGodsLoader {
    static BorderGodsConfig CONFIG;

    public static void loadConfig() {
        loadConfig("bordergods");
    }

    public static void loadConfig(String filename) {
        // Parse the config file
        parseConfig(SimpleConfig.of(filename).provider(BorderGodsLoader::defaultProvider).request());
    }

    private static String defaultProvider(String filename) {
        return """
                # Default config file
                # List of times to increase the border normally, 24-hour format, separate by ','
                border.base.increase.time=17
                border.base.increase=6
                # List of times to reset the god satisfaction, 24-hour format, separate by ','
                god.reset.time=17
                # List of gods disabled, separate by ','
                god.disabled=
                # List of god multipliers if the god supports adjustment
                # Key:Value pairs, separate by ','
                god.multipliers=Enorma:3.0
                """;
    }

    private static void parseConfig(SimpleConfig CONFIG_FILE) {
        CONFIG = new BorderGodsConfig();
        var bbit = CONFIG_FILE.getOrDefault("border.base.increase.time", "17");
        CONFIG.BORDER_BASE_INCREASE_TIME = ConfigUtil.parseIntList(bbit);
        var bbi = CONFIG_FILE.getOrDefault("border.base.increase", 6);
        CONFIG.BORDER_BASE_INCREASE = bbi;
        var gri = CONFIG_FILE.getOrDefault("god.reset.time", "17");
        CONFIG.GOD_RESET_TIME = ConfigUtil.parseIntList(gri);
        var gd = CONFIG_FILE.getOrDefault("god.disabled", "");
        CONFIG.GOD_DISABLED = ConfigUtil.parseStringList(gd);
        var gm = CONFIG_FILE.getOrDefault("god.multipliers", "Enorma:3.0");
        CONFIG.GOD_MULTIPLIERS = ConfigUtil.parseStringIntMap(gm);
    }

    public static BorderGodsConfig getConfig() {
        return CONFIG;
    }
}
