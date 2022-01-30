package net.onelikeandidie.bordergods.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.onelikeandidie.bordergods.events.MinutePassedCallback;
import net.onelikeandidie.bordergods.gods.GodManager;
import net.onelikeandidie.bordergods.util.config.ConfigManager;

public class GodManagerCommands {
    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        // Listen for reset commands
        dispatcher.register(CommandManager.literal("bordergods")
                .then(CommandManager.literal("reset").executes(GodManagerCommands::resetCommand)));
        dispatcher.register(CommandManager.literal("bordergods")
                .then(CommandManager.literal("get").executes(GodManagerCommands::getCommand)));
        dispatcher.register(CommandManager.literal("bordergods")
                .then(CommandManager.literal("reload").executes(GodManagerCommands::reloadCommand)));
        dispatcher.register(CommandManager.literal("bordergods")
                .then(CommandManager.literal("randomtick").executes(GodManagerCommands::randomCommand)));
    }

    private static int resetCommand(CommandContext<ServerCommandSource> context) {
        if (context.getSource().hasPermissionLevel(4)) {
            GodManager.resetSatisfactions();
            context.getSource().sendFeedback(Text.of("The border gods were reset"), true);
            return 1;
        }
        context.getSource().sendFeedback(Text.of("You need to be OP to run this command"), false);
        return -1;
    }

    private static int getCommand(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(GodManager.toList(), false);
        return 1;
    }

    private static int reloadCommand(CommandContext<ServerCommandSource> context) {
        if (context.getSource().hasPermissionLevel(4)) {
            ConfigManager.reloadConfigs();
            context.getSource().sendFeedback(Text.of("Reload Complete"), true);
            return 1;
        }
        context.getSource().sendFeedback(Text.of("You need to be OP to run this command"), false);
        return -1;
    }

    private static int randomCommand(CommandContext<ServerCommandSource> context) {
        if (context.getSource().hasPermissionLevel(4)) {
            MinutePassedCallback.EVENT.invoker().interact(0);
            context.getSource().sendFeedback(Text.of("Random ticked"), true);
            return 1;
        }
        context.getSource().sendFeedback(Text.of("You need to be OP to run this command"), false);
        return -1;
    }
}
