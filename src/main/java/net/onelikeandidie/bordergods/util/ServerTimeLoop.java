package net.onelikeandidie.bordergods.util;

import net.fabricmc.fabric.api.event.server.ServerTickCallback;

import java.util.Calendar;

public class ServerTimeLoop {
    static int lastHour;

    static void init() {
        // this is bad, not this pls
        //while (true) {
        //    var cal = Calendar.getInstance();
        //    // This hour is in 24-hour format
        //    var currentHour = cal.get(Calendar.HOUR_OF_DAY);
        //    if (currentHour != lastHour) {
        //        // This means the hour has changed
        //        lastHour = currentHour;
        //        ServerTimeLoop.hourPassed(currentHour);
        //    }
        //}
    }

    static void hourPassed(int newHour) {

    }
}
