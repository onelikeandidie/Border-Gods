package net.onelikeandidie.bordergods.gods;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.onelikeandidie.bordergods.events.DroppedInLavaCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GodManager {
    static IGod[] gods;

    public static void initialize() {
        Logger logger = LogManager.getLogger("bordergods");
        gods = new IGod[]{
                new Enorma()
            };
        logger.info("Initialize God Manager with {} gods", gods.length);
        // Listen for god offerings
        DroppedInLavaCallback.EVENT.register(GodManager::offeringGiven);
        logger.info("Now listening for God Offerings");
    }

    private static ActionResult offeringGiven(PlayerEntity playerEntity, BlockEntity blockEntity, ItemStack itemStack) {
        for (IGod god : gods) {
            var result = god.evaluateOffering(playerEntity, blockEntity, itemStack);
        }
        return ActionResult.PASS;
    }

    public static Text toList() {
        MutableText text = Text.of("").copy();
        for (IGod god : gods) {
            text.append(god.getName());
            text.append(": ");
            text.append(Float.toString(god.getSatisfaction()));
            text.append("\n");
        }
        return text;
    }
}
