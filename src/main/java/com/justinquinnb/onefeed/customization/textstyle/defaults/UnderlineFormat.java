package com.justinquinnb.onefeed.customization.textstyle.defaults;

import com.justinquinnb.onefeed.customization.textstyle.TextFormatting;

/**
 * Marker for underline text formatting.
 */
public class UnderlineFormat extends TextFormatting {
    private static volatile UnderlineFormat instance = null;

    /**
     * Creates an instance of underline formatting.
     */
    private UnderlineFormat() {
        super("Underline");
    }

    /**
     * Gets the single instance of {@code UnderlineFormat}. Multiple instances aren't necessary as this format requires
     * no complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code UnderlineFormatting}
     */
    public static UnderlineFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (UnderlineFormat.class) {
                if (instance == null) {
                    instance = new UnderlineFormat();
                }
            }
        }
        return instance;
    }

    public String toString() {
        return "UnderlineFormat@" + this.hashCode() + "{}";
    }
}