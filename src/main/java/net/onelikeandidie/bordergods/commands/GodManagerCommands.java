package net.onelikeandidie.bordergods.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.onelikeandidie.bordergods.gods.GodManager;

public class GodManagerCommands {
    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        // Listen for reset commands
        dispatcher.register(CommandManager.literal("bordergods")
                .then(CommandManager.literal("reset").executes(GodManagerCommands::resetCommand)));
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
}
