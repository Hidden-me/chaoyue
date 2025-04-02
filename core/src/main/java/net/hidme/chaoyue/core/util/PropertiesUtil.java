package net.hidme.chaoyue.core.util;

import java.util.Properties;

/**
 * Utilities for <code>Properties</code> objects.
 */
public abstract class PropertiesUtil {

    public static boolean getBooleanPropertyOrDefault(Properties properties, String key, boolean defaultValue) {
        final String value = properties.getProperty(key);
        if (value == null) return defaultValue;
        return Boolean.parseBoolean(value);
    }

    public static int getIntPropertyOrDefault(Properties properties, String key, int defaultValue) {
        final String value = properties.getProperty(key);
        if (value == null) return defaultValue;
        return Integer.parseInt(value);
    }

    public static long getLongProperty(Properties properties, String key) {
        final String value = properties.getProperty(key);
        if (value == null) throw new IllegalArgumentException("missing key " + key);
        return Long.parseLong(value);
    }

    public static long getLongPropertyOrDefault(Properties properties, String key, long defaultValue) {
        final String value = properties.getProperty(key);
        if (value == null) return defaultValue;
        return Long.parseLong(value);
    }

}
