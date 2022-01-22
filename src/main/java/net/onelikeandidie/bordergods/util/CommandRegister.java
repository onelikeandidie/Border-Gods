package net.onelikeandidie.bordergods.util;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.onelikeandidie.bordergods.commands.GodManagerCommands;
import net.onelikeandidie.bordergods.gods.GodManager;

public class CommandRegister {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(GodManagerCommands::registerCommands);
    }
}
