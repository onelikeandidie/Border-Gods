package net.onelikeandidie.bordergods.util.config;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class YAMLLoader {
    public static YamlMapping loadConfig(String filename, String defaultProvider) {
        var configDir = FabricLoader.getInstance().getConfigDir();
        var configPath = configDir.resolve(filename);
        var configFile = new File(configPath.toString());
        try {
            // Parse the config file
            return Yaml.createYamlInput(configFile).readYamlMapping();
        } catch (IOException e) {
            return createNewFile(defaultProvider, configFile);
        }
    }

    private static YamlMapping createNewFile(String defaultProvider, File configFile) {
        var configYaml = defaultProvider;
        if (!(configFile.exists() || configFile.isFile())) {
            configFile.getParentFile().mkdirs();
            try {
                configFile.createNewFile();
            } catch (IOException ignored) {}
        }
        try {
            FileWriter writer = new FileWriter(configFile);
            writer.write(configYaml);
            writer.close();
        } catch (IOException ignored) {}
        YamlMapping yaml;
        try {
            yaml = Yaml.createYamlInput(configYaml).readYamlMapping();
        } catch (IOException e) {
            yaml = Yaml.createYamlMappingBuilder().build();
        }
        return yaml;
    }
}
