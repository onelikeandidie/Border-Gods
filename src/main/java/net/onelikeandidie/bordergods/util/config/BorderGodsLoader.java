package net.onelikeandidie.bordergods.util.config;

import net.onelikeandidie.bordergods.util.config.external.simpleconfig.SimpleConfig;
import net.onelikeandidie.bordergods.util.config.gods.MerchetConfig;

public class BorderGodsLoader implements ReloadableLoader {
    static BorderGodsConfig config;
    static String path;

    public static void loadConfig(String filename) {
        // Parse the config file
        path = filename;
        parseConfig(SimpleConfig.of(filename).provider(BorderGodsLoader::defaultProvider).request());
    }

    private static String defaultProvider(String filename) {
        return """
                # Default config file
                # List of times to increase the border normally, 24-hour format, separate by ','
                border.base.increase.time=17
                border.base.increase=6
                # Chance every minute to decrease the god's satisfaction
                god.random.decay.chance=0.05
                # List of gods disabled, separate by ','
                god.disabled=
                # List of god multipliers if the god supports adjustment
                # Key:Value pairs, separate by ','
                god.multipliers=Enorma:3.0,Anullis:1.0,Merchet:1.0
                """;
    }

    private static void parseConfig(SimpleConfig CONFIG_FILE) {
        config = new BorderGodsConfig();
        var bbit = CONFIG_FILE.getOrDefault("border.base.increase.time", "17");
        config.BORDER_BASE_INCREASE_TIME = ConfigUtil.parseIntList(bbit);
        var bbi = CONFIG_FILE.getOrDefault("border.base.increase", 6);
        config.BORDER_BASE_INCREASE = bbi;
        var grcc = CONFIG_FILE.getOrDefault("god.random.decay.chance", 0.05);
        config.GOD_RANDOM_DECAY_CHANCE = grcc;
        var gd = CONFIG_FILE.getOrDefault("god.disabled", "");
        config.GOD_DISABLED = ConfigUtil.parseStringList(gd);
        var gm = CONFIG_FILE.getOrDefault("god.multipliers", "Enorma:3.0,Anullis:1.0,Merchet:1.0");
        config.GOD_MULTIPLIERS = ConfigUtil.parseStringIntMap(gm);
    }

    public static BorderGodsConfig getConfig() {
        return config;
    }

    public static void reloadConfig() {
        BorderGodsLoader.loadConfig(BorderGodsLoader.getPath());
    }

    public static String getPath() {
        return path;
    }
}
