package net.onelikeandidie.bordergods.listeners;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.onelikeandidie.bordergods.events.DroppedInLavaCallback;
import org.apache.logging.log4j.LogManager;

public class DropInLavaListener {
    public void register() {
        DroppedInLavaCallback.EVENT.register(this::droppedInLava);
        var logger = LogManager.getLogger("bordergods");
        logger.info("Now listening for items dropped in Lava");
    }

    public ActionResult droppedInLava(PlayerEntity player, BlockEntity block, ItemStack item) {
        var logger = LogManager.getLogger("bordergods");
        return ActionResult.PASS;
    }
}
