package net.onelikeandidie.bordergods.util.config;

import org.apache.commons.lang3.NotImplementedException;

public interface ReloadableLoader extends SingleLoader {
    public static void reload() {}
    public static void reset() {}
}
