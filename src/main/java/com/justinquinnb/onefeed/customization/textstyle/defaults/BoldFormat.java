package com.justinquinnb.onefeed.customization.textstyle.defaults;

/**
 * Marker for bold/boldface text formatting.
 */
public non-sealed class BoldFormat extends BasicFormatting {
    private static volatile BoldFormat instance = null;

    /**
     * Creates an instance of bold formatting.
     */
    private BoldFormat() {
        super("Bold");
    }

    /**
     * Gets the single instance of {@code BoldFormat}. Multiple instances aren't necessary as this format requires no
     * complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code BasicFormatting}
     */
    public static BoldFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (BoldFormat.class) {
                if (instance == null) {
                    instance = new BoldFormat();
                }
            }
        }
        return instance;
    }
}