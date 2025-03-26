package com.justinquinnb.onefeed.customization.textstyle.defaults;

import com.justinquinnb.onefeed.customization.textstyle.TextFormatting;

/**
 * A basic, in-text link. Belongs to the language of {@link BasicFormatting}s.
 *
 * @see TextFormatting
 */
public non-sealed class LinkFormat extends BasicFormatting{
    /**
     * The URL to link to.
     */
    private String url;

    /**
     * The link's optional tooltip.
     */
    private String tooltip = "";

    /**
     * Creates an instance of link formatting with just the URL to apply to some text (which will serve as the link's
     * label).
     *
     * @param url the URL that some text, should {@code this} {@link LinkFormat} be applied to it, links to
     */
    public LinkFormat(String url) {
        super("Link");
        this.url = url;
    }

    /**
     * Creates an instance of link formatting with a URL and tooltip to apply to some text (which will serve as the
     * link's label).
     *
     * @param url the URL that some text, should {@code this} {@link LinkFormat} be applied to it, links to
     * @param tooltip the tooltip/description of the URL
     */
    public LinkFormat(String url,String tooltip) {
        super("Link");
        this.url = url;
        this.tooltip = tooltip;
    }
}