package com.justinquinnb.onefeed.customization.textstyle.markup;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMismatchException;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.MarkupLangMismatchException;

import java.text.ParseException;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Marker for underline text formatting. Obtain an instance through {@link #getInstance()}.
 */
public class UnderlineFormat extends TextFormatting implements Html {
    private static volatile UnderlineFormat instance = null;

    // Register the format as Html, Markdown, and Extended Markdown compatible
    static {
        TextFormattingRegistry.registerForLanguage(
                UnderlineFormat.class, Html.class, getHtmlPattern(),
                UnderlineFormat::extractFromHtml, UnderlineFormat::applyAsHtml);
    }

    /**
     * Creates an instance of underline formatting.
     */
    private UnderlineFormat() {
        super("Underline");
    }

    /**
     * Gets the single instance of {@code UnderlineFormat}. Multiple instances aren't necessary as this format requires
     * no complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code UnderlineFormat}
     */
    public static UnderlineFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (UnderlineFormat.class) {
                if (instance == null) {
                    instance = new UnderlineFormat();
                }
            }
        }
        return instance;
    }

    // PATTERNS
    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code UnderlineFormat} appears in text marked up in HTML.
     *
     * @return a {@code Pattern} capable of identifying underline formatting as it manifests in HTML
     */
    public static Pattern getHtmlPattern() {
        return Pattern.compile("<u(.*)>(.*)</u\\s>", Pattern.DOTALL);
    }

    // EXTRACTORS
    /**
     * Extracts a {@code UnderlineFormat} object replicating the formatting of {@code text} as is specified by its
     * HTML markup and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs HTML and contains a valid instance of underline formatting, a
     * {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup coupled with an
     * instance of {@code UnderlineFormat} representing the formatting that markup called for.
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ HTML
     * @throws ParseException if underlining in HTML could not be parsed from the {@code text}
     */
    public static FormattingMarkedText extractFromHtml(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        // Don't bother trying to parse the text if it doesn't employ any HTML
        MarkupLanguage.preventMarkupLangMismatch(text, "underlining", Html.class);
        
        // Attempt to parse out the content
        return Html.extractContentFromElement(text, UnderlineFormat.getInstance(), "u");
    }
    
    // APPLIERS
    /**
     * Marks up the provided {@code text}'s raw text in HTML.
     *
     * @param text the {@code FormattingMarkedText} to apply underline formatting to in HTML
     *
     * @return the raw text of {@code text} marked up as to indicate a underline in HTML
     * @throws FormattingMismatchException if the {@code text} does not call for underline formatting
     */
    public static MarkedUpText applyAsHtml(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, UnderlineFormat.class);

        return new MarkedUpText("<u>" + text + "</u>", Html.class);
    }

    // OVERRIDES
    @Override
    public MarkedUpText applyAsHtmlTo(String text) {
        try {
            return applyAsHtml(new FormattingMarkedText(text, UnderlineFormat.getInstance()));
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
        } else {
            return null;
        }
    }

    public String toString() {
        return "UnderlineFormat@" + this.hashCode() + "{}";
    }
}