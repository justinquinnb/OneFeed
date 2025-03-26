package com.justinquinnb.onefeed.customization.textstyle.defaults;

/**
 * Marker for italic text formatting.
 */
public non-sealed class ItalicFormat extends BasicFormatting {
    private static volatile ItalicFormat instance = null;

    /**
     * Creates an instance of italic formatting.
     */
    private ItalicFormat() {
        super("Italic");
    }

    /**
     * Gets the single instance of {@code ItalicFormat}. Multiple instances aren't necessary as this format requires no
     * complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code ItalicFormatting}
     */
    public static ItalicFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (ItalicFormat.class) {
                if (instance == null) {
                    instance = new ItalicFormat();
                }
            }
        }
        return instance;
    }
}