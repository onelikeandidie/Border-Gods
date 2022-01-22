package net.onelikeandidie.bordergods.util;

import net.onelikeandidie.bordergods.listeners.DropInLavaListener;
import net.onelikeandidie.bordergods.listeners.TimeToMoveBorderListener;

public class ListenerRegister {
    public static void registerListeners() {
        var lavaListener = new DropInLavaListener();
        lavaListener.register();
        var borderListener = new TimeToMoveBorderListener();
        borderListener.register();
        Border.listenToSet();
    }
}
