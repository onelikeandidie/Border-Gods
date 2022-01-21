package net.onelikeandidie.bordergods.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;

public class ServerUtils {
    public static MinecraftServer getServer() {
        MinecraftServer server;
        EnvType environmentType = FabricLoader.getInstance().getEnvironmentType();
        if (environmentType == EnvType.CLIENT) {
            server = getIntegratedServer();
        } else {
            server = (MinecraftServer) FabricLoader.getInstance().getGameInstance();
        }
        return server;
    }

    @Environment(EnvType.CLIENT)
    public static MinecraftServer getIntegratedServer() {
        return MinecraftClient.getInstance().getServer();
    }
}
