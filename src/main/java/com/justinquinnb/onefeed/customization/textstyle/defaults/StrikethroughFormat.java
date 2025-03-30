package com.justinquinnb.onefeed.customization.textstyle.defaults;

import com.justinquinnb.onefeed.customization.textstyle.TextFormatting;

/**
 * Marker for strikethrough text formatting.
 */
public class StrikethroughFormat extends TextFormatting {
    private static volatile StrikethroughFormat instance = null;

    /**
     * Creates an instance of strikethrough formatting.
     */
    private StrikethroughFormat() {
        super("Strikethrough");
    }

    /**
     * Gets the single instance of {@code StrikethroughFormat}. Multiple instances aren't necessary as this format
     * requires no complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code StrikethroughFormatting}
     */
    public static StrikethroughFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (StrikethroughFormat.class) {
                if (instance == null) {
                    instance = new StrikethroughFormat();
                }
            }
        }
        return instance;
    }

    public String toString() {
        return "StrikethroughFormat@" + this.hashCode() + "{}";
    }
}