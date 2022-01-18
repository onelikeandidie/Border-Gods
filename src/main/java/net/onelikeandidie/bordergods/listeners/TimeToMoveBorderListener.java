package net.onelikeandidie.bordergods.listeners;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
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

        return ActionResult.PASS;
    }
}
