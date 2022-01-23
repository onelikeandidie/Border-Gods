package net.onelikeandidie.bordergods.util.config;

import net.minecraft.util.Pair;

import java.util.*;

public class ConfigUtil {

    public static List<Integer> parseIntList(String list) {
        var list_split = list.split(",");
        return Arrays.stream(list_split).map((v) -> {
            try {
                return Integer.parseInt(v);
            } catch (NumberFormatException err) {
                return null;
            }
        }).filter(Objects::nonNull).toList();
    }

    public static List<String> parseStringList(String list) {
        var list_split = list.split(",");
        return Arrays.stream(list_split).filter(Objects::nonNull).toList();
    }

    public static Map<String, Double> parseStringIntMap(String string_map) {
        var list_split = string_map.split(",");
        var values_split = Arrays.stream(list_split).map((v) -> {
            // Split into pairs
            return v.split(":");
        }).toList();
        var map = new HashMap<String, Double>();
        for (String[] entries : values_split) {
            if (entries.length == 2) {
                try {
                    var number = Double.parseDouble(entries[1]);
                    map.put(entries[0], number);
                } catch (NumberFormatException ignored) {}
            }
        }
        return map;
    }

    /**
     * For parsing Key=Value pairs
     */
    public static Pair<String, Integer> parsePairStrInt(String string) {
        var entry = string.split("=");
        Pair<String, Integer> pair = null;
        if (entry.length == 2) {
            var key = entry[0];
            if (entry[1].endsWith(";")) {
                entry[1] = entry[1].substring(0, entry[1].length() - 1);
            }
            int value;
            try {
                value = Integer.parseInt(entry[1]);
            } catch (NumberFormatException ignored) {
                return null;
            }
            return new Pair<>(key, value);
        }
        return null;
    }
}
