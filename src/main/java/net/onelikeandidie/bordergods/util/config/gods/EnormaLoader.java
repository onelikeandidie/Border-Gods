package net.onelikeandidie.bordergods.util.config.gods;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlNode;
import net.fabricmc.loader.api.FabricLoader;
import net.onelikeandidie.bordergods.util.ServerUtils;
import net.onelikeandidie.bordergods.util.config.ConfigUtil;
import net.onelikeandidie.bordergods.util.config.ReloadableLoader;
import net.onelikeandidie.bordergods.util.config.YAMLLoader;
import net.onelikeandidie.bordergods.util.config.external.simpleconfig.SimpleConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class EnormaLoader implements ReloadableLoader {
    static EnormaConfig config;
    static String path;

    public static void loadConfig(String pathname) {
        loadConfig(pathname, defaultProvider());
    }

    public static void loadConfig(String pathname, String provider) {
        path = pathname;
        var configYaml = YAMLLoader.loadConfig(path, provider);
        parseConfig(configYaml);
    }

    protected static String defaultProvider() {
        return """
                # Value of items sacrificed
                values:
                  # ENORMA LIKES SHINY
                  - item.minecraft.diamond=128
                  - item.minecraft.gold_ingot=32
                  - item.minecraft.emerald=64
                  - item.minecraft.amethyst_shard=8
                  - block.minecraft.amethyst_block=32
                  # HAHA YOU CAN'T BURN THIS ONE IN LAVA
                  - item.minecraft.netherite_ingot=256
                  # ENORMA DOESN'T LIKE PLANTS
                  - block.minecraft.lily_pad=-64
                  - block.minecraft.sugar_cane=-64
                """;
    }

    protected static void parseConfig(YamlMapping CONFIG_FILE) {
        config = new EnormaConfig();
        var values = CONFIG_FILE.yamlSequence("values").values();
        config.valueSet = new HashMap<>();
        for (YamlNode value : values) {
            var pair = ConfigUtil.parsePairStrInt(value.asScalar().value());
            if (pair != null) config.valueSet.put(pair.getLeft(), pair.getRight());
        }
    }

    public static EnormaConfig getConfig() {
        return config;
    }

    public static void reloadConfig() {
        loadConfig(getPath());
    }

    public static String getPath() {
        return path;
    }
}
