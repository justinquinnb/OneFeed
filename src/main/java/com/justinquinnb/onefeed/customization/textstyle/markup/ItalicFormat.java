package com.justinquinnb.onefeed.customization.textstyle.markup;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMismatchException;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.MarkupLangMismatchException;

import java.text.ParseException;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Marker for italic text formatting. Obtain an instance through {@link #getInstance()}.
 */
public class ItalicFormat extends TextFormatting implements Html, Markdown, ExtendedMarkdown {
    private static volatile ItalicFormat instance = null;

    // Register the format as Html, Markdown, and Extended Markdown compatible
    static {
        TextFormattingRegistry.registerForLanguage(
                ItalicFormat.class, Html.class, getHtmlPattern(),
                ItalicFormat::extractFromHtml, ItalicFormat::applyAsHtml);
        TextFormattingRegistry.registerForLanguage(
                ItalicFormat.class, Markdown.class, getMdPattern(),
                ItalicFormat::extractFromMd, ItalicFormat::applyAsMd);
        TextFormattingRegistry.registerForLanguage(
                ItalicFormat.class, ExtendedMarkdown.class, getMdPattern(),
                ItalicFormat::extractFromMd, ItalicFormat::applyAsMd);
    }
    
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
     * @return the singleton instance of {@code ItalicFormat}
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

    // PATTERNS
    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code ItalicFormat} appears in text marked up in HTML.
     *
     * @return a {@code Pattern} capable of identifying italic formatting as it manifests in HTML
     */
    public static Pattern getHtmlPattern() {
        return Pattern.compile("(<i(.*)>(.*)</i\\s>)|(<em(.*)>(.*)</em\s>)", Pattern.DOTALL);
    }

    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code ItalicFormat} appears in text marked up in
     * Markdown.
     *
     * @return a {@code Pattern} capable of identifying italic formatting as it manifests in Markdown
     */
    public static Pattern getMdPattern() {
        return Pattern.compile("(\\*([^\\*][^\\*])(.*)([^\\*][^\\*])\\*)|(_([^__][^_])(.*)_([^_][^_]))",
                Pattern.DOTALL);
    }

    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code ItalicFormat} appears in text marked up in
     * Extended Markdown.
     *
     * @return a {@code Pattern} capable of identifying italic formatting as it manifests in Extended Markdown
     *
     * @see #getMdPattern()
     */
    public static Pattern getExtdMdPattern() {
        return getMdPattern();
    }

    // EXTRACTORS
    /**
     * Extracts a {@code ItalicFormat} object replicating the formatting of {@code text} as is specified by its
     * HTML markup and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs HTML and contains a valid instance of italic formatting, a
     * {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup coupled with an
     * instance of {@code ItalicFormat} representing the formatting that markup called for.\
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ HTML
     * @throws ParseException if HTML italics could not be parsed from the {@code text}
     */
    public static FormattingMarkedText extractFromHtml(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        // Don't bother trying to parse the text if it doesn't employ any HTML
        MarkupLanguage.preventMarkupLangMismatch(text, "italics", Html.class);
        
        // Attempt to parse out the content
        return Html.extractContentFromElement(text, ItalicFormat.getInstance(), "i", "em");
    }

    /**
     * Extracts a {@code ItalicFormat} object replicating the formatting of {@code text} as is specified by its
     * Markdown and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs Markdown and contains a valid instance of italic formatting, a
     * {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup coupled with an
     * instance of {@code ItalicFormat} representing the formatting that markup called for.
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ Markdown / Extended Markdown
     * @throws ParseException if Markdown italics could not be parsed from the {@code text}
     */
    public static FormattingMarkedText extractFromMd(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        // Don't bother trying to parse the text if it doesn't employ any MD
        MarkupLanguage.preventMarkupLangMismatch(text, "italics", Markdown.class,
                ExtendedMarkdown.class);

        // Find the outermost italic bounds in order to determine that outermost element's content
        Pattern startTag = Pattern.compile("^((\\*([^\\*][^\\*]))|(_([^__][^__])))");
        Pattern endTag = Pattern.compile("((([^\\*][^\\*])\\*)|(([^_][^_])_))$");

        return MarkupLanguage.parseFmtBetweenBounds(text, startTag, endTag, ItalicFormat.getInstance());
    }

    /**
     * Extracts a {@code ItalicFormat} object replicating the formatting of {@code text} as is specified by its
     * Extended Markdown and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs Extended Markdown and contains a valid instance of italic
     * formatting, a {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup
     * coupled with an instance of {@code ItalicFormat} representing the formatting that markup called for.
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ Markdown / Extended
     * @throws ParseException if Markdown italics could not be parsed from the {@code text}
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
     * @param text the {@code FormattingMarkedText} to apply italic formatting to in HTML
     *
     * @return the raw text of {@code text} marked up as to indicate italics in HTML
     * @throws FormattingMismatchException if the {@code text} does not call for italic formatting
     */
    public static MarkedUpText applyAsHtml(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, ItalicFormat.class);

        return new MarkedUpText("<i>" + text + "</i>", Html.class);
    }

    /**
     * Marks up the provided {@code text}'s raw text in Markdown / Extended Markdown.
     *
     * @param text the {@code FormattingMarkedText} to apply italic formatting to in Markdown / Extended Markdown
     *
     * @return the raw text of {@code text} marked up as to indicate italics in Markdown / Extended Markdown
     * @throws FormattingMismatchException if the {@code text} does not call for italic formatting
     */
    public static MarkedUpText applyAsMd(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, ItalicFormat.class);

        return new MarkedUpText("*" + text + "*", Markdown.class);
    }

    // OVERRIDES
    @Override
    public MarkedUpText applyAsHtmlTo(String text) {
        try {
            return applyAsHtml(new FormattingMarkedText(text, ItalicFormat.getInstance()));
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
            return applyAsMd(new FormattingMarkedText(text, ItalicFormat.getInstance()));
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
            return applyAsMd(new FormattingMarkedText(text, ItalicFormat.getInstance()));
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
        return "ItalicFormat@" + this.hashCode() + "{}";
    }
}