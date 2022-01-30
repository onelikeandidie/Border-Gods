package net.onelikeandidie.bordergods.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Pair;
import net.onelikeandidie.bordergods.events.MinutePassedCallback;
import net.onelikeandidie.bordergods.events.TimeToMoveBorderCallback;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ServerTimeLoop {
    static int lastHour;
    static int lastMinute;
    static final int MILLISECONDS_IN_AN_HOUR = 60*60*1000;
    static final int MILLISECONDS_IN_AN_MINUTE = 60*1000;
    static List<Pair<TimerTask, Timer>> timers;

    public static void init() {
        timers = new ArrayList<>();

        lastHour = -1;
        lastMinute = -1;
        startHourTimer();
        startMinuteTimer();

        ServerLifecycleEvents.SERVER_STOPPING.register(ServerTimeLoop::stopTimers);
    }

    public static void stopTimers(MinecraftServer server) {
        for (Pair<TimerTask, Timer> timer : timers) {
            timer.getLeft().cancel();
            timer.getRight().cancel();
        }
    }

    private static void startHourTimer() {
        var timer = new Timer();
        var timerTask = new TimerTask() {
            @Override
            public void run() {
                var cal = Calendar.getInstance();
                // This hour is in 24-hour format
                var currentHour = cal.get(Calendar.HOUR_OF_DAY);
                if (currentHour != lastHour) {
                    // This means the hour has changed
                    lastHour = currentHour;
                    ServerTimeLoop.hourPassed(currentHour);
                }
            }
        };
        var cal = Calendar.getInstance();
        var millisecondsUntilNextHour = millisecondsTillTheHour(cal);
        timer.schedule(timerTask, millisecondsUntilNextHour, MILLISECONDS_IN_AN_HOUR);
        timers.add(new Pair(timerTask, timer));
    }

    private static void startMinuteTimer() {
        var timer = new Timer();
        var timerTask = new TimerTask() {
            @Override
            public void run() {
                var cal = Calendar.getInstance();
                // This hour is in 24-hour format
                var currentMinute = cal.get(Calendar.MINUTE);
                if (currentMinute != lastMinute) {
                    // This means the hour has changed
                    lastMinute = currentMinute;
                    ServerTimeLoop.minutePassed(currentMinute);
                }
            }
        };
        var cal = Calendar.getInstance();
        var millisecondsUntilNextMinute = millisecondsTillTheMinute(cal);
        timer.schedule(timerTask, millisecondsUntilNextMinute, MILLISECONDS_IN_AN_MINUTE);
        timers.add(new Pair(timerTask, timer));
    }

    static void hourPassed(int newHour) {
        TimeToMoveBorderCallback.EVENT.invoker().interact(newHour);
    }

    static void minutePassed(int newMinute) {
        MinutePassedCallback.EVENT.invoker().interact(newMinute);
    }

    static int millisecondsTillTheHour(@NotNull Calendar cal) {
        var currentMilliseconds = cal.get(Calendar.MINUTE) * 60 * 1000 + cal.get(Calendar.SECOND) * 1000 + cal.get(Calendar.MILLISECOND);
        return MILLISECONDS_IN_AN_HOUR - currentMilliseconds;
    }

    static int millisecondsTillTheMinute(@NotNull Calendar cal) {
        var currentMilliseconds = cal.get(Calendar.SECOND) * 1000 + cal.get(Calendar.MILLISECOND);
        return MILLISECONDS_IN_AN_MINUTE - currentMilliseconds;
    }
}


