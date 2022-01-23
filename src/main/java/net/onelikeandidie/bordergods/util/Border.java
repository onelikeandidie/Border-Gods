package net.onelikeandidie.bordergods.util;

import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.onelikeandidie.bordergods.events.SomeoneSetWorldBorder;
import net.onelikeandidie.bordergods.util.config.ConfigManager;

public class Border {
    public static double finalBorderSize = -1;

    public static void listenToSet() {
        SomeoneSetWorldBorder.EVENT.register((world) -> {
            get(world, true);
            return ActionResult.PASS;
        });
    }

    public static void add(World world, double number, long time) {
        var currentBorder = world.getWorldBorder().getSize();
        var bufferedBorder = Border.get(world);
        finalBorderSize = bufferedBorder + number;
        if (finalBorderSize < 1) {
            finalBorderSize = 1;
        }
        world.getWorldBorder().interpolateSize(currentBorder, finalBorderSize, time);
    }

    public static double get(World world) {
        return get(world, false);
    }

    private static double get(World world, boolean refresh) {
        if (refresh || finalBorderSize == -1) {
            finalBorderSize = world.getWorldBorder().getSize();
        }
        return finalBorderSize;
    }
}
