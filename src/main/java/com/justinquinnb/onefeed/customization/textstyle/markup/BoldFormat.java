package com.justinquinnb.onefeed.customization.textstyle.markup;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMismatchException;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.MarkupLangMismatchException;

import java.text.ParseException;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Marker for bold/boldface text formatting. Obtain an instance through {@link #getInstance()}.
 */
public class BoldFormat extends TextFormatting implements Html, Markdown, ExtendedMarkdown {
    private static volatile BoldFormat instance = null;

    // Register the format as Html, Markdown, and Extended Markdown compatible
    static {
        TextFormattingRegistry.registerForLanguage(
                BoldFormat.class, Html.class, getHtmlPattern(),
                BoldFormat::extractFromHtml, BoldFormat::applyAsHtml);
        TextFormattingRegistry.registerForLanguage(
                BoldFormat.class, Markdown.class, getMdPattern(),
                BoldFormat::extractFromMd, BoldFormat::applyAsMd);
        TextFormattingRegistry.registerForLanguage(
                BoldFormat.class, ExtendedMarkdown.class, getMdPattern(),
                BoldFormat::extractFromMd, BoldFormat::applyAsMd);
    }

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
     * @return the singleton instance of {@code BoldFormat}
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

    // PATTERNS
    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code BoldFormat} appears in text marked up in HTML.
     *
     * @return a {@code Pattern} capable of identifying bold formatting as it manifests in HTML
     */
    public static Pattern getHtmlPattern() {
        return Pattern.compile("((<b\\s(.*)>)(.*)(</b\\s>))|((<strong\\s(.*)>)(.*)(</strong\\s>))");
    }

    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code BoldFormat} appears in text marked up in Markdown.
     *
     * @return a {@code Pattern} capable of identifying bold formatting as it manifests in Markdown
     */
    public static Pattern getMdPattern() {
        // Only considers the outermost **s or __s when a boldface/italics combo is used
        return Pattern.compile("([^\\*]\\*\\*(.*)\\*\\*[^\\*])|([^_]__(.*)__[^_])", Pattern.DOTALL);
    }

    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code BoldFormat} appears in text marked up in
     * Extended Markdown.
     *
     * @return a {@code Pattern} capable of identifying bold formatting as it manifests in Extended Markdown
     *
     * @see #getMdPattern()
     */
    public static Pattern getExtdMdPattern() {
        return getMdPattern();
    }

    // EXTRACTORS
    /**
     * Extracts a {@code BoldFormat} object replicating the formatting of {@code text} as is specified by its
     * HTML markup and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs HTML and contains a valid instance of bold formatting, a
     * {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup coupled with an
     * instance of {@code BoldFormat} representing the formatting that markup called for.
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ HTML
     * @throws ParseException if HTML boldface could not be parsed from the {@code text}
     */
    public static FormattingMarkedText extractFromHtml(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        // Don't bother trying to parse the text if it doesn't employ any HTML
        MarkupLanguage.preventMarkupLangMismatch(text, "boldface", Html.class);

        // Attempt to parse out the content
        return Html.extractContentFromElement(text, BoldFormat.getInstance(), "b", "strong");
    }

    /**
     * Extracts a {@code BoldFormat} object replicating the formatting of {@code text} as is specified by its
     * Markdown and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs Markdown and contains a valid instance of bold formatting, a
     * {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup coupled with an
     * instance of {@code BoldFormat} representing the formatting that markup called for.
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ Markdown
     * @throws ParseException if boldface Markdown could not be parsed from the {@code text}
     */
    public static FormattingMarkedText extractFromMd(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        // Don't bother trying to parse the text if it doesn't employ any MD
        MarkupLanguage.preventMarkupLangMismatch(text, "boldface", Markdown.class,
                ExtendedMarkdown.class);

        // Find the outermost boldface bounds in order to determine that outermost element's content
        Pattern startBound = Pattern.compile("^((\\*\\*[^\\*])|(__[^_]))");
        Pattern endBound = Pattern.compile("(([^_]__)|([^\\*]\\*\\*))$");
        return MarkupLanguage.parseFmtBetweenBounds(text, startBound, endBound, BoldFormat.getInstance());
    }

    /**
     * Extracts a {@code BoldFormat} object replicating the formatting of {@code text} as is specified by its
     * Extended Markdown and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs Extended Markdown and contains a valid instance of bold
     * formatting, a {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup
     * coupled with an instance of {@code BoldFormat} representing the formatting that markup called for.
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ Markdown/Extended Markdown
     * @throws ParseException if boldface Markdown could not be parsed from the {@code text}
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
     * @param text the {@code FormattingMarkedText} to apply boldface formatting to in HTML
     *
     * @return the raw text of {@code text} marked up as to indicate boldface in HTML
     * @throws FormattingMismatchException if the {@code text} does not call for boldface formatting
     */
    public static MarkedUpText applyAsHtml(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, BoldFormat.class);

        return new MarkedUpText("<b>" + text.getText() + "</b>", Html.class);
    }

    /**
     * Marks up the provided {@code text}'s raw text in Markdown / Extended Markdown.
     *
     * @param text the {@code FormattingMarkedText} to apply boldface formatting to in Markdown / Extended Markdown
     *
     * @return the raw text of {@code text} marked up as to indicate boldface in Markdown / Extended Markdown
     * @throws FormattingMismatchException if the {@code text} does not call for boldface formatting
     */
    public static MarkedUpText applyAsMd(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, BoldFormat.class);

        return new MarkedUpText("**" + text.getText() + "**", Markdown.class);
    }

    // OVERRIDES
    @Override
    public MarkedUpText applyAsHtmlTo(String text) {
        try {
            return applyAsHtml(new FormattingMarkedText(text, BoldFormat.getInstance()));
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
            return applyAsMd(new FormattingMarkedText(text, BoldFormat.getInstance()));
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
            return applyAsMd(new FormattingMarkedText(text, BoldFormat.getInstance()));
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
        return "BoldFormat@" + this.hashCode() + "{}";
    }
}