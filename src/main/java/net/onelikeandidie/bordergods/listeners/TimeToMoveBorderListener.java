package net.onelikeandidie.bordergods.listeners;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.onelikeandidie.bordergods.events.TimeToMoveBorderCallback;
import org.apache.logging.log4j.LogManager;

import static net.onelikeandidie.bordergods.util.Border.add;
import static net.onelikeandidie.bordergods.util.ServerUtils.getServer;

public class TimeToMoveBorderListener {
    public void register() {
        TimeToMoveBorderCallback.EVENT.register(this::timeToMoveBorder);
        var logger = LogManager.getLogger("bordergods");
        logger.info("Now listening for new move border events");
    }

    public ActionResult timeToMoveBorder() {
        var logger = LogManager.getLogger("bordergods");
        logger.info("Border supposedly moved");
        MinecraftServer server = getServer();
        var worlds = server.getWorlds();
        worlds.forEach(serverWorld -> {
            add(serverWorld, 6, 1000);
        });
        return ActionResult.PASS;
    }
}
