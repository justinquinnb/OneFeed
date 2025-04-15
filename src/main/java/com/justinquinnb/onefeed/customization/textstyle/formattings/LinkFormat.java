package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.function.Function;
import java.util.regex.Pattern;

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
    public Pattern getHtmlPattern() {
        return Pattern.compile("(<a(.*)(href=\")(.*)(\">)(.*))(.*)(</a\\s>)", Pattern.DOTALL);
    }

    @Override
    public FormattingMarkedText extractFromHtml(MarkedUpText text) {
        try {
            // Parse out the link destination
            String destination = Html.extractAttributeFromElement(text, "href", "a");

            // Parse out the tooltip (if one exists)
            boolean hasTooltip = false;
            String tooltip = "";
            try {
                tooltip = Html.extractAttributeFromElement(text, "title", "a");
            } catch (IllegalStateException e) {
                // Do nothing as a tooltip isn't required
            }

            // Parse out the link text
            Pattern anchorOpenTag = Pattern.compile("<a(.*)>");
            Pattern anchorCloseTag = Pattern.compile("</a\\s>");
            String linkText = MarkupLanguage.parseTextBetweenBounds(text.getText(), anchorOpenTag, anchorCloseTag);

            // Generate the formatting
            LinkFormat extractedFormatting;
            if (hasTooltip) {
                extractedFormatting = new LinkFormat(url, tooltip);
            } else {
                extractedFormatting = new LinkFormat(url);
            }

            return new FormattingMarkedText(linkText, extractedFormatting);
        } catch (IllegalStateException e) {
            return new FormattingMarkedText(text.getText(), DefaultFormat.getInstance());
        }
    }

    @Override
    public MarkedUpText applyMd(String text) {
        return new MarkedUpText("[" + text + "](" + url + ")", Markdown.class);
    }

    @Override
    public Pattern getMdPattern() {
        return Pattern.compile("\\[(.*)\\]\\((.*)\\)", Pattern.DOTALL);
    }

    @Override
    public FormattingMarkedText extractFromMd(MarkedUpText text) {
        try {
            // Parse out the link text
            Pattern linkTextStartBound = Pattern.compile("\\[");
            Pattern linkTextEndBound = Pattern.compile("\\]");
            String linkText =
                    MarkupLanguage.parseTextBetweenBounds(text.getText(), linkTextStartBound, linkTextEndBound);

            // Parse out the link destination
            Pattern destinationStartBound = Pattern.compile("\\(");
            Pattern destinationEndBound = Pattern.compile("\\)");
            String linkDestination = MarkupLanguage.parseTextBetweenBounds(text.getText(), destinationStartBound,
                    destinationEndBound);

            return new FormattingMarkedText(linkText, new LinkFormat(linkDestination));
        } catch (IllegalStateException e) {
            return new FormattingMarkedText(text.getText(), DefaultFormat.getInstance());
        }
    }

    @Override
    public MarkedUpText applyExtdMd(String text) {
        return new MarkedUpText("[" + text + "](" + url + ")", ExtendedMarkdown.class);
    }

    @Override
    public Pattern getExtdMdPattern() {
        return this.getMdPattern();
    }

    @Override
    public FormattingMarkedText extractFromExtdMd(MarkedUpText text) {
        return this.extractFromMd(text);
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

    @Override
    public Pattern getMarkupPatternFor(Class<? extends MarkupLanguage> markupLang) {
        if (markupLang.equals(Html.class)) {
            return this.getHtmlPattern();
        } else if (markupLang.equals(Markdown.class)) {
            return this.getMdPattern();
        } else if (markupLang.equals(ExtendedMarkdown.class)) {
            return this.getExtdMdPattern();
        } else {
            return null;
        }
    }

    @Override
    public Function<MarkedUpText, FormattingMarkedText> getFormattingExtractorFor(
            Class<? extends MarkupLanguage> markupLang) {
        if (markupLang.equals(Html.class)) {
            return this::extractFromHtml;
        } else if (markupLang.equals(Markdown.class)) {
            return this::extractFromMd;
        } else if (markupLang.equals(ExtendedMarkdown.class)) {
            return this::extractFromExtdMd;
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