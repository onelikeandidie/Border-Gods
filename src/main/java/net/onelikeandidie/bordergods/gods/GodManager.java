package net.onelikeandidie.bordergods.gods;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.onelikeandidie.bordergods.events.DroppedInLavaCallback;
import net.onelikeandidie.bordergods.events.TimeToMoveBorderCallback;
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
        TimeToMoveBorderCallback.EVENT.register(() -> {
            GodManager.resetSatisfactions();
            return ActionResult.PASS;
        });
        logger.info("Now listening for God Offerings");
    }

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        // Listen for reset commands
        dispatcher.register(CommandManager.literal("bordergods")
                .then(CommandManager.literal("reset").executes(GodManager::resetCommand)));
    }

    private static int resetCommand(CommandContext<ServerCommandSource> context) {
        resetSatisfactions();
        return 1;
    }

    private static ActionResult offeringGiven(PlayerEntity playerEntity, BlockEntity blockEntity, ItemStack itemStack) {
        for (IGod god : gods) {
            var result = god.evaluateOffering(playerEntity, blockEntity, itemStack);
        }
        return ActionResult.PASS;
    }

    public static void resetSatisfactions() {
        for (IGod god: gods) {
            god.resetSatisfaction();
        }
    }

    public static Text toList() {
        MutableText text = Text.of("").copy();
        for (IGod god : gods) {
            text.append(god.getName());
            text.append(": ");
            text.append(String.format("%.2f", god.getSatisfaction()));
            text.append("\n");
        }
        return text;
    }
}
