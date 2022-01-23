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
import net.onelikeandidie.bordergods.util.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GodManager {
    static List<IGod> gods;

    public static void initialize() {
        Logger logger = LogManager.getLogger("bordergods");
        // Load not disabled gods and their multipliers
        loadGodsFromConfig();
        logger.info("Initialized God Manager with {} gods", gods.size());
        // Listen for god offerings
        DroppedInLavaCallback.EVENT.register(GodManager::offeringGiven);
        TimeToMoveBorderCallback.EVENT.register((newHour) -> {
            var config = ConfigManager.getConfig();
            if (config.GOD_RESET_TIME.contains(newHour)) {
                GodManager.resetSatisfactions();
            }
            return ActionResult.PASS;
        });
        logger.info("Now listening for God Offerings");
    }

    private static void loadGodsFromConfig() {
        var config = ConfigManager.getConfig();
        List<IGod> god_list = new ArrayList<>();
        if (!config.GOD_DISABLED.contains("Enorma")) {
            god_list.add(new Enorma(config.GOD_MULTIPLIERS.getOrDefault("Enorma", 3.0)));
        }
        gods = god_list;
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
            text.append(god.getFormattedName());
            text.append(": ");
            text.append(String.format("%.2f", god.getSatisfaction()));
            text.append("\n");
        }
        return text;
    }
}
