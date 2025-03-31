package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.TextFormatting;

import java.util.function.Function;

/**
 * A basic, in-text link with optional tooltip.
 */
public class LinkFormat extends TextFormatting implements Html, Markdown, ExtendedMarkdown {
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

    /**
     * Gets the URL {@code this} {@code LinkFormat} points to.
     *
     * @return the URL {@code this} {@code LinkFormat} points to
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Gets the tooltip affiliated with this {@code this} {@code LinkFormat}.
     *
     * @return the tooltip affiliated with this {@code this} {@code LinkFormat}
     */
    public String getTooltip() {
        return this.tooltip;
    }

    @Override
    public MarkedUpText applyHtml(String text) {
        String htmlLink = "<a href=\"" + url + "\"";

        // Add the tooltip if one was provided at instantiation
        if (tooltip != null) {
            htmlLink += " title=\"" + tooltip + "\"";
        }

        htmlLink += ">" + text + "</a>";

        return new MarkedUpText(htmlLink, Html.class);
    }

    @Override
    public MarkedUpText applyMd(String text) {
        return new MarkedUpText("[" + text + "](" + url + ")", Markdown.class);
    }

    @Override
    public MarkedUpText applyExtdMd(String text) {
        return new MarkedUpText("[" + text + "](" + url + ")", ExtendedMarkdown.class);
    }

    @Override
    public Function<String, MarkedUpText> getMarkupLangApplierFor(Class<? extends MarkupLanguage> markupLang) {
        if (markupLang.equals(Html.class)) {
            return this::applyHtml;
        } else if (markupLang.equals(Markdown.class)) {
            return this::applyMd;
        } else if (markupLang.equals(ExtendedMarkdown.class)) {
            return this::applyExtdMd;
        } else {
            return null;
        }
    }

    public String toString() {
        return "LinkFormat@" + this.hashCode() +
                "{url=" + this.url +
                ", tooltip=" + this.tooltip + "}";
    }
}