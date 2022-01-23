package net.onelikeandidie.bordergods.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.onelikeandidie.bordergods.events.TimeToMoveBorderCallback;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ServerTimeLoop {
    static int lastHour;
    static final int MILLISECONDS_IN_AN_HOUR = 60*60*1000;
    static TimerTask timerTask;
    static Timer timer;

    public static void init() {
        var logger = LogManager.getLogger("bordergods");
        lastHour = -1;
        timer = new Timer();
        timerTask = new TimerTask() {
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
        logger.info("Next task will execute in: {}ms", millisecondsUntilNextHour);
        ServerLifecycleEvents.SERVER_STOPPING.register((server) -> {
            ServerTimeLoop.timerTask.cancel();
            ServerTimeLoop.timer.cancel();
        });
    }

    static void hourPassed(int newHour) {
        TimeToMoveBorderCallback.EVENT.invoker().interact(newHour);
    }

    static int millisecondsTillTheHour(@NotNull Calendar cal) {
        var currentMilliseconds = cal.get(Calendar.MINUTE) * 60 * 1000 + cal.get(Calendar.SECOND) * 1000 + cal.get(Calendar.MILLISECOND);
        return MILLISECONDS_IN_AN_HOUR - currentMilliseconds;
    }
}


