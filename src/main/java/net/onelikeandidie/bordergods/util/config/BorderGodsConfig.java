package net.onelikeandidie.bordergods.util.config;

import java.util.*;
import java.util.stream.Collectors;

public class BorderGodsConfig {
    // Base Border Related
    public List<Integer> BORDER_BASE_INCREASE_TIME;
    public int BORDER_BASE_INCREASE;
    // God Related
    public List<Integer> GOD_RESET_TIME;
    public List<String> GOD_DISABLED;
    public Map<String, Double> GOD_MULTIPLIERS;

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
}
