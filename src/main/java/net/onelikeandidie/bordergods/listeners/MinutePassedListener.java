package net.onelikeandidie.bordergods.listeners;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.onelikeandidie.bordergods.events.DroppedInLavaCallback;
import net.onelikeandidie.bordergods.events.MinutePassedCallback;
import net.onelikeandidie.bordergods.events.RandomMinuteCallback;
import net.onelikeandidie.bordergods.util.config.BorderGodsLoader;
import net.onelikeandidie.bordergods.util.config.ConfigManager;
import org.apache.logging.log4j.LogManager;

public class MinutePassedListener {
    public void register() {
        MinutePassedCallback.EVENT.register(this::minutePassed);
    }

    public ActionResult minutePassed(int newMinute) {
        var config = BorderGodsLoader.getConfig();
        var random = Math.random();
        if (random < config.GOD_RANDOM_DECAY_CHANCE) {
            RandomMinuteCallback.EVENT.invoker().interact();
        }
        return ActionResult.PASS;
    }
}
