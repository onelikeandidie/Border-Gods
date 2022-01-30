package net.onelikeandidie.bordergods.util.config.gods;

import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlNode;
import net.onelikeandidie.bordergods.util.ServerUtils;
import net.onelikeandidie.bordergods.util.config.ConfigUtil;
import net.onelikeandidie.bordergods.util.config.ReloadableLoader;
import net.onelikeandidie.bordergods.util.config.YAMLLoader;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.HashMap;

public class AnullisLoader implements ReloadableLoader {
    static AnullisConfig config;
    static String path;

    public static void loadConfig(String pathname) {
        AnullisLoader.loadConfig(pathname, AnullisLoader.defaultProvider());
    }

    public static void loadConfig(String pathname, String provider) {
        path = pathname;
        var configYaml = YAMLLoader.loadConfig(path, provider);
        AnullisLoader.parseConfig(configYaml);
    }

    protected static String defaultProvider() {
        return """
                # Value of entities sacrificed
                values:
                  # ANULLIS LIKES MEAT
                  - entity.minecraft.sheep=8
                  - entity.minecraft.cow=8
                  - entity.minecraft.rabbit=16
                  # ANULLIS DOESN'T LIKE PORK
                  - entity.minecraft.pig=-64
                """;
    }

    protected static void parseConfig(YamlMapping CONFIG_FILE) {
        config = new AnullisConfig();
        var values = CONFIG_FILE.yamlSequence("values").values();
        config.valueSet = new HashMap<>();
        for (YamlNode value : values) {
            var pair = ConfigUtil.parsePairStrInt(value.asScalar().value());
            if (pair != null) {
                config.valueSet.put(pair.getLeft(), pair.getRight());
            }
        }
    }

    public static AnullisConfig getConfig() {
        return config;
    }

    public static void reloadConfig() {
        AnullisLoader.loadConfig(AnullisLoader.getPath());
    }

    public static String getPath() {
        return path;
    }
}
