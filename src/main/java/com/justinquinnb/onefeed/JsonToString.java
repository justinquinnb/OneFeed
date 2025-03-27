package com.justinquinnb.onefeed;

import java.lang.reflect.Field;

/**
 * Provides an easy way of converting an instance into the JSON-like toString representation used across OneFeed.
 */
public final class JsonToString {
    /**
     * Generate a JSON-like {@code String} representation of the passed {@code obj}, the standard representation used
     * by OneFeed types.
     *
     * @param obj the instance to represent in a JSON-like {@code String}
     * @return {@code obj} represented by a JSON-like {@code String}
     */
    public static String of(Object obj) {
        String str = obj.getClass().getTypeName() + "@" + obj.hashCode() + "{";
        Field[] fields = obj.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                if (i > 0 && i < fields.length - 1) {
                    str += ", ";
                }
                str += fields[i].getName() + fields[i].get(obj);
            } catch (IllegalAccessException e) {
                // do nothing
            }
        }

        return str + "}";
    }
}