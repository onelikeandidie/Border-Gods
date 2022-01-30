package net.onelikeandidie.bordergods.util.config;

import com.amihaiemil.eoyaml.YamlMapping;
import net.onelikeandidie.bordergods.util.config.gods.MerchetConfig;
import org.apache.commons.lang3.NotImplementedException;

public interface SingleLoader {

    static void loadConfig(String pathname) {}

    static void loadConfig(String pathname, String provider) {}

    static String defaultProvider() {
        return "";
    }

    static void parseConfig(YamlMapping CONFIG_FILE) {
        throw new NotImplementedException();
    }

    static Config getConfig() {
        return null;
    }

    static String getPath() {
        return null;
    }
}
