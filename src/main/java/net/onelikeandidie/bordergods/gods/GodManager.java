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
import net.onelikeandidie.bordergods.events.RandomMinuteCallback;
import net.onelikeandidie.bordergods.events.TimeToMoveBorderCallback;
import net.onelikeandidie.bordergods.util.ServerUtils;
import net.onelikeandidie.bordergods.util.config.BorderGodsLoader;
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
        // Register decay listener
        RandomMinuteCallback.EVENT.register(GodManager::decaySatisfactions);
        logger.info("Initialized God Manager with {} gods", gods.size());
        logger.info("Now listening for God Offerings");
    }

    private static void loadGodsFromConfig() {
        var config = BorderGodsLoader.getConfig();
        List<IGod> god_list = new ArrayList<>();
        if (!config.GOD_DISABLED.contains("Enorma")) {
            var enorma = new Enorma(config.GOD_MULTIPLIERS.getOrDefault("Enorma", 3.0));
            god_list.add(enorma);
        }
        if (!config.GOD_DISABLED.contains("Anullis")) {
            var anullis = new Anullis(config.GOD_MULTIPLIERS.getOrDefault("Anullis", 1.0));
            god_list.add(anullis);
        }
        if (!config.GOD_DISABLED.contains("Merchet")) {
            var merchet = new Merchet(config.GOD_MULTIPLIERS.getOrDefault("Merchet", 3.0));
            god_list.add(merchet);
        }
        gods = god_list;
    }

    public static void resetSatisfactions() {
        for (IGod god: gods) {
            god.resetSatisfaction();
        }
    }

    public static ActionResult decaySatisfactions() {
        for (IGod god: gods) {
            god.decaySatisfaction();
        }
        return ActionResult.PASS;
    }

    public static void sendOfferMessage(IGod god, boolean good) {
        MutableText message = (MutableText) Text.of("");
        message.append(god.getFormattedName());
        message.append(" ");
        if (good) {
            message.append("grows satisfied");
        } else {
            message = Text.of("grows ever unsatisfied").copy();
        }
        var players = ServerUtils.getServer().getPlayerManager().getPlayerList();
        for (PlayerEntity player : players) {
             player.sendMessage(message, true);
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
