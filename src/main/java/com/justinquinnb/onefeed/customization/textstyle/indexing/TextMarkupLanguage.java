package com.justinquinnb.onefeed.customization.textstyle.indexing;

/**
 * Specifies some text markup language like HTML, Markdown, or a custom Platform-specific one (e.g. Instagram #hashtags
 * or @usernames).
 */
public class TextMarkupLanguage {
    /**
     * The name of the
     */
    private final String name;

    /**
     *
     * @param name
     */
    public TextMarkupLanguage(String name) {
        this.name = name.toUpperCase().strip();
    }

    public String getName() {
        return this.name;
    }
}