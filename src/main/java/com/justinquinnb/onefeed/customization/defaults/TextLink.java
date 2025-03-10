package com.justinquinnb.onefeed.customization.defaults;

/**
 *
 */
public class TextLink extends TextFormatting {
    /**
     *
     */
    private String url = null;

    /**
     *
     */
    private String text = null;

    /**
     *
     */
    private String tooltip = null;

    public TextLink() {
        super("Link");
    }

    // consider a builder for this?
    /**
     *
     * @param url
     */
    public TextLink(String url) {
        super("Link");
        this.url = url;
    }

    /**
     *
     * @param url
     * @param tooltip
     */
    public TextLink(String url, String tooltip) {
        super("Link");
        this.url = url;
        this.tooltip = tooltip;
    }
}
