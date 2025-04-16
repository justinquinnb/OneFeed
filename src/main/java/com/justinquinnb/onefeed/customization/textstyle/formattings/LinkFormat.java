package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.text.ParseException;
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

    // PATTERNS
    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code LinkFormat} appears in text marked up in HTML.
     *
     * @return a {@code Pattern} capable of identifying link formatting as it manifests in HTML
     */
    public static Pattern getHtmlPattern() {
        return Pattern.compile("(<a(.*)(href=\")(.*)(\">)(.*))(.*)(</a\\s>)", Pattern.DOTALL);
    }

    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code LinkFormat} appears in text marked up in
     * Markdown.
     *
     * @return a {@code Pattern} capable of identifying link formatting as it manifests in Markdown
     */
    public static Pattern getMdPattern() {
        return Pattern.compile("\\[(.*)\\]\\((.*)\\)", Pattern.DOTALL);
    }

    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code LinkFormat} appears in text marked up in
     * Extended Markdown.
     *
     * @return a {@code Pattern} capable of identifying link formatting as it manifests in Extended Markdown
     *
     * @see #getMdPattern()
     */
    public static Pattern getExtdMdPattern() {
        return getMdPattern();
    }

    // EXTRACTORS
    /**
     * Extracts a {@code LinkFormat} object replicating the formatting of {@code text} as is specified by its
     * HTML markup and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs HTML and contains a valid instance of link formatting, a
     * {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup coupled with an
     * instance of {@code LinkFormat} representing the formatting that markup called for.
     */
    public static FormattingMarkedText extractFromHtml(MarkedUpText text) {
        // Don't bother trying to parse the text if it doesn't employ any HTML
        if (text.getMarkupLanguages().contains(Html.class)) {
            return MarkupLanguage.EXTRACTOR_FALLBACK_PROCESS.apply(text);
        }
        
        try {
            // Parse out the link destination
            String destination = Html.extractAttributeFromElement(text, "href", "a");

            // Parse out the tooltip (if one exists)
            boolean hasTooltip = false;
            String tooltip = "";
            try {
                tooltip = Html.extractAttributeFromElement(text, "title", "a");
                hasTooltip = true;
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
                extractedFormatting = new LinkFormat(destination, tooltip);
            } else {
                extractedFormatting = new LinkFormat(destination);
            }

            return new FormattingMarkedText(linkText, extractedFormatting);
        } catch (ParseException e) {
            return MarkupLanguage.EXTRACTOR_FALLBACK_PROCESS.apply(text);
        }
    }

    /**
     * Extracts a {@code LinkFormat} object replicating the formatting of {@code text} as is specified by its
     * Markdown and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs Markdown and contains a valid instance of link formatting, a
     * {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup coupled with an
     * instance of {@code LinkFormat} representing the formatting that markup called for.
     */
    public static FormattingMarkedText extractFromMd(MarkedUpText text) {
        // Don't bother trying to parse the text if it doesn't employ any MD
        if (text.getMarkupLanguages().contains(Markdown.class) ||
                text.getMarkupLanguages().contains(ExtendedMarkdown.class)
        ) {
            return MarkupLanguage.EXTRACTOR_FALLBACK_PROCESS.apply(text);
        }
        
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
        } catch (ParseException e) {
            return MarkupLanguage.EXTRACTOR_FALLBACK_PROCESS.apply(text);
        }
    }

    /**
     * Extracts a {@code LinkFormat} object replicating the formatting of {@code text} as is specified by its
     * Extended Markdown and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs Extended Markdown and contains a valid instance of link
     * formatting, a {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup
     * coupled with an instance of {@code LinkFormat} representing the formatting that markup called for.
     *
     * @see #extractFromMd
     */
    public static FormattingMarkedText extractFromExtdMd(MarkedUpText text) {
        return extractFromMd(text);
    }

    // OVERRIDES
    @Override
    public MarkedUpText applyAsHtmlTo(String text) {
        String htmlLink = "<a href=\"" + url + "\"";

        // Add the tooltip if one was provided at instantiation
        if (tooltip != null) {
            htmlLink += " title=\"" + tooltip + "\"";
        }

        htmlLink += ">" + text + "</a>";

        return new MarkedUpText(htmlLink, Html.class);
    }

    @Override
    public MarkedUpText applyAsMdTo(String text) {
        return new MarkedUpText("[" + text + "](" + url + ")", Markdown.class);
    }

    @Override
    public MarkedUpText applyAsExtdMdTo(String text) {
        return new MarkedUpText("[" + text + "](" + url + ")", ExtendedMarkdown.class);
    }

    @Override
    public Function<String, MarkedUpText> getMarkupLangApplierFor(Class<? extends MarkupLanguage> markupLang) {
        if (markupLang.equals(Html.class)) {
            return this::applyAsHtmlTo;
        } else if (markupLang.equals(Markdown.class)) {
            return this::applyAsMdTo;
        } else if (markupLang.equals(ExtendedMarkdown.class)) {
            return this::applyAsExtdMdTo;
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