package com.justinquinnb.onefeed.customization.textstyle.markup;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMismatchException;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.MarkupLangMismatchException;

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

    // Register the format as Html, Markdown, and Extended Markdown compatible
    static {
        TextFormattingRegistry.registerForLanguage(
                LinkFormat.class, Html.class, getHtmlPattern(),
                LinkFormat::extractFromHtml, LinkFormat::applyAsHtml);
        TextFormattingRegistry.registerForLanguage(
                LinkFormat.class, Markdown.class, getMdPattern(),
                LinkFormat::extractFromMd, LinkFormat::applyAsMd);
        TextFormattingRegistry.registerForLanguage(
                LinkFormat.class, ExtendedMarkdown.class, getMdPattern(),
                LinkFormat::extractFromMd, LinkFormat::applyAsMd);
    }

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
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ HTML
     * @throws ParseException if an HTML link could not be parsed from the {@code text}
     */
    public static FormattingMarkedText extractFromHtml(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        // Don't bother trying to parse the text if it doesn't employ any HTML
        MarkupLanguage.preventMarkupLangMismatch(text, "a link", Html.class);

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
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ Markdown / Extended Markdown
     * @throws ParseException if a Markdown link could not be parsed from the {@code text}
     */
    public static FormattingMarkedText extractFromMd(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        // Don't bother trying to parse the text if it doesn't employ any MD
        MarkupLanguage.preventMarkupLangMismatch(text, "a link", Markdown.class,
                ExtendedMarkdown.class);

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
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ Markdown / Extended Markdown
     * @throws ParseException if a Markdown link could not be parsed from the {@code text}
     *
     * @see #extractFromMd
     */
    public static FormattingMarkedText extractFromExtdMd(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        return extractFromMd(text);
    }
    
    // APPLIERS
    /**
     * Marks up the provided {@code text}'s raw text in HTML.
     *
     * @param text the {@code FormattingMarkedText} to apply link formatting to in HTML
     *
     * @return the raw text of {@code text} marked up as to indicate a link in HTML
     * @throws FormattingMismatchException if the {@code text} does not call for link formatting
     */
    public static MarkedUpText applyAsHtml(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, LinkFormat.class);

        LinkFormat linkFormat = (LinkFormat)text.getFormatting();
        String url = linkFormat.getUrl();
        String tooltip = linkFormat.getTooltip();
        String htmlLink = "<a href=\"" + url + "\"";

        // Add the tooltip if one was provided at instantiation
        if (tooltip != null) {
            htmlLink += " title=\"" + tooltip + "\"";
        }

        htmlLink += ">" + text + "</a>";

        return new MarkedUpText(htmlLink, Html.class);
    }

    /**
     * Marks up the provided {@code text}'s raw text in Markdown / Extended Markdown.
     *
     * @param text the {@code FormattingMarkedText} to apply link formatting to in Markdown / Extended Markdown
     *
     * @return the raw text of {@code text} marked up as to indicate a link in Markdown / Extended Markdown
     * @throws FormattingMismatchException if the {@code text} does not call for link formatting
     */
    public static MarkedUpText applyAsMd(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, LinkFormat.class);

        LinkFormat linkFormat = (LinkFormat)text.getFormatting();
        String url = linkFormat.getUrl();
        String tooltip = linkFormat.getTooltip();
        return new MarkedUpText("[" + text + "](" + url + ")", Markdown.class);
    }

    // OVERRIDES
    @Override
    public MarkedUpText applyAsHtmlTo(String text) {
        try {
            return applyAsHtml(new FormattingMarkedText(text, new LinkFormat(this.url, this.tooltip)));
        } catch (FormattingMismatchException e) {
            /* So long as the static function employs the correct definition of a FormattingMismatchException (as would
            be the case with it using TextFormatting's preventFormattingMismatch() function), this clause should NEVER
            be executed.
             */
            throw new RuntimeException(e);
        }
    }

    @Override
    public MarkedUpText applyAsMdTo(String text) {
        try {
            return applyAsMd(new FormattingMarkedText(text, new LinkFormat(this.url, this.tooltip)));
        } catch (FormattingMismatchException e) {
            /* So long as the static function employs the correct definition of a FormattingMismatchException (as would
            be the case with it using TextFormatting's preventFormattingMismatch() function), this clause should NEVER
            be executed.
             */
            throw new RuntimeException(e);
        }
    }

    @Override
    public MarkedUpText applyAsExtdMdTo(String text) {
        try {
            return applyAsMd(new FormattingMarkedText(text, new LinkFormat(this.url, this.tooltip)));
        } catch (FormattingMismatchException e) {
            /* So long as the static function employs the correct definition of a FormattingMismatchException (as would
            be the case with it using TextFormatting's preventFormattingMismatch() function), this clause should NEVER
            be executed.
             */
            throw new RuntimeException(e);
        }
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