package net.onelikeandidie.bordergods.listeners;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.onelikeandidie.bordergods.events.TimeToMoveBorderCallback;
import org.apache.logging.log4j.LogManager;

public class TimeToMoveBorderListener {
    public void register() {
        TimeToMoveBorderCallback.EVENT.register(this::timeToMoveBorder);
        var logger = LogManager.getLogger("bordergods");
        logger.info("Now listening for new move border events");
    }

    public ActionResult timeToMoveBorder() {
        var logger = LogManager.getLogger("bordergods");
        logger.info("Border supposedly moved");
        MinecraftServer server = null;
        EnvType environmentType = FabricLoader.getInstance().getEnvironmentType();
        if (environmentType == EnvType.SERVER) {
            server = (MinecraftServer) FabricLoader.getInstance().getGameInstance();
        } else if (environmentType == EnvType.CLIENT){
            server = getIntegratedServer();
        }
        if (server == null) {
            return ActionResult.PASS;
        }
        var worlds = server.getWorlds();
        worlds.forEach(serverWorld -> {
            var currentBorder = serverWorld.getWorldBorder().getSize();
            var finalBorderSize = currentBorder + 6;
            serverWorld.getWorldBorder().setSize(finalBorderSize);
        });
        return ActionResult.PASS;
    }

    @Environment(EnvType.CLIENT)
    public static MinecraftServer getIntegratedServer() {
        return MinecraftClient.getInstance().getServer();
    }
}
