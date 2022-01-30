package net.onelikeandidie.bordergods.util.multiblock;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.onelikeandidie.bordergods.util.ServerUtils;
import org.spongepowered.include.com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

public class MultiblockManager {
    static List<Multiblock> LoadedStructures;

    public static void register() {
        var server = ServerUtils.getServer();
    }

    public static void reloadStructures() {

    }
}
