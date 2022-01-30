package net.onelikeandidie.bordergods.util.config.gods;

import com.amihaiemil.eoyaml.YamlMapping;
import net.onelikeandidie.bordergods.util.config.BorderGodsLoader;
import net.onelikeandidie.bordergods.util.config.ReloadableLoader;
import net.onelikeandidie.bordergods.util.config.YAMLLoader;

public class MerchetLoader implements ReloadableLoader {
    static MerchetConfig config;
    static String path;

    public static void loadConfig(String pathname) {
        MerchetLoader.loadConfig(pathname, MerchetLoader.defaultProvider());
    }

    public static void loadConfig(String pathname, String provider) {
        path = pathname;
        var configYaml = YAMLLoader.loadConfig(path, provider);
        MerchetLoader.parseConfig(configYaml);
    }

    protected static String defaultProvider() {
        return """
                # Base increase per villager levelup
                base.increase: 1
                """;
    }

    protected static void parseConfig(YamlMapping CONFIG_FILE) {
        config = new MerchetConfig();
        var value = CONFIG_FILE.value("base.increase");
        if (value != null && !value.isEmpty()) {
            config.baseIncrease = CONFIG_FILE.doubleNumber("base.increase");
        }
    }

    public static MerchetConfig getConfig() {
        return config;
    }

    public static void reloadConfig() {
        MerchetLoader.loadConfig(MerchetLoader.getPath());
    }

    public static String getPath() {
        return path;
    }
}
