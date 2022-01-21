package net.onelikeandidie.bordergods.util;

import net.minecraft.world.World;

public class Border {
    public static void add(World world, double number, long time) {
        var currentBorder = world.getWorldBorder().getSize();
        var finalBorderSize = currentBorder + number;
        world.getWorldBorder().interpolateSize(currentBorder, finalBorderSize, time);
    }

    public static double get(World world) {
        return world.getWorldBorder().getSize();
    }
}
