package com.justinquinnb.onefeed.customization.textstyle.markup;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMismatchException;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.MarkupLangMismatchException;

import java.text.ParseException;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Marker for strikethrough text formatting. Obtain an instance through {@link #getInstance()}.
 */
public class StrikethroughFormat extends TextFormatting implements Html, ExtendedMarkdown {
    private static volatile StrikethroughFormat instance = null;

    // Register the format as Html, Markdown, and Extended Markdown compatible
    static {
        TextFormattingRegistry.registerForLanguage(
                StrikethroughFormat.class, Html.class, getHtmlPattern(),
                StrikethroughFormat::extractFromHtml, StrikethroughFormat::applyAsHtml);
        TextFormattingRegistry.registerForLanguage(
                StrikethroughFormat.class, ExtendedMarkdown.class, getExtdMdPattern(),
                StrikethroughFormat::extractFromExtdMd, StrikethroughFormat::applyAsExtdMd);
    }
    
    /**
     * Creates an instance of strikethrough formatting.
     */
    private StrikethroughFormat() {
        super("Strikethrough");
    }

    /**
     * Gets the single instance of {@code StrikethroughFormat}. Multiple instances aren't necessary as this format
     * requires no complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code StrikethroughFormat}
     */
    public static StrikethroughFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (StrikethroughFormat.class) {
                if (instance == null) {
                    instance = new StrikethroughFormat();
                }
            }
        }
        return instance;
    }

    // PATTERNS
    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code StrikethroughFormat} appears in text marked up in
     * HTML.
     *
     * @return a {@code Pattern} capable of identifying strikethrough formatting as it manifests in HTML
     */
    public static Pattern getHtmlPattern() {
        return Pattern.compile("<s(.*)>(.*)</s\\s>", Pattern.DOTALL);
    }

    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code StrikethroughFormat} appears in text marked up in
     * Extended Markdown.
     *
     * @return a {@code Pattern} capable of identifying strikethrough formatting as it manifests in Extended Markdown
     */
    public static Pattern getExtdMdPattern() {
        return Pattern.compile("~~(.*)~~", Pattern.DOTALL);
    }

    // EXTRACTORS
    /**
     * Extracts a {@code StrikethroughFormat} object replicating the formatting of {@code text} as is specified by its
     * HTML markup and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs HTML and contains a valid instance of strikethrough formatting, a
     * {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup coupled with an
     * instance of {@code StrikethroughFormat} representing the formatting that markup called for.
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ HTML
     * @throws ParseException if a strikethrough in HTML could not be parsed from the {@code text}
     */
    public static FormattingMarkedText extractFromHtml(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        // Don't bother trying to parse the text if it doesn't employ any HTML
        MarkupLanguage.preventMarkupLangMismatch(text, "strikethrough", Html.class);
        
        // Attempt to parse out the content
        return Html.extractContentFromElement(text, StrikethroughFormat.getInstance(), "s");
    }

    /**
     * Extracts a {@code StrikethroughFormat} object replicating the formatting of {@code text} as is specified by its
     * Extended Markdown and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs Extended Markdown and contains a valid instance of strikethrough
     * formatting, a {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup
     * coupled with an instance of {@code StrikethroughFormat} representing the formatting that markup called for.
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ Extended Markdown
     * @throws ParseException if strikethrough in Extended Markdown could not be parsed from the {@code text}
     */
    public static FormattingMarkedText extractFromExtdMd(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        // Don't bother trying to parse the text if it doesn't employ any MD
        MarkupLanguage.preventMarkupLangMismatch(text, "strikethrough", ExtendedMarkdown.class);

        // Find the outermost strikethrough bounds in order to determine that outermost element's content
        Pattern startTag = Pattern.compile("^(~~)");
        Pattern endTag = Pattern.compile("(~~)$");

        return MarkupLanguage.parseFmtBetweenBounds(text, startTag, endTag, StrikethroughFormat.getInstance());
    }
    
    // APPLIERS
    /**
     * Marks up the provided {@code text}'s raw text in HTML.
     *
     * @param text the {@code FormattingMarkedText} to apply strikethrough formatting to in HTML
     *
     * @return the raw text of {@code text} marked up as to indicate a strikethrough in HTML
     * @throws FormattingMismatchException if the {@code text} does not call for strikethrough formatting
     */
    public static MarkedUpText applyAsHtml(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, StrikethroughFormat.class);

        return new MarkedUpText("<s>" + text + "</s>", Html.class);
    }

    /**
     * Marks up the provided {@code text}'s raw text in Extended Markdown.
     *
     * @param text the {@code FormattingMarkedText} to apply strikethrough formatting to in Extended Markdown
     *
     * @return the raw text of {@code text} marked up as to indicate a strikethrough in Extended Markdown
     * @throws FormattingMismatchException if the {@code text} does not call for strikethrough formatting
     */
    public static MarkedUpText applyAsExtdMd(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, StrikethroughFormat.class);

        return new MarkedUpText("~~" + text + "~~", ExtendedMarkdown.class);
    }

    // OVERRIDES
    @Override
    public MarkedUpText applyAsHtmlTo(String text) {
        try {
            return applyAsHtml(new FormattingMarkedText(text, StrikethroughFormat.getInstance()));
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
            return applyAsExtdMd(new FormattingMarkedText(text, StrikethroughFormat.getInstance()));
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
        } else if (markupLang.equals(ExtendedMarkdown.class)) {
            return this::applyAsExtdMdTo;
        } else {
            return null;
        }
    }

    public String toString() {
        return "StrikethroughFormat@" + this.hashCode() + "{}";
    }
}