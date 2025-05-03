package com.justinquinnb.onefeed.customization.textstyle.markup;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMismatchException;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.MarkupLangMismatchException;

import java.text.ParseException;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Marker for code block text formatting. Obtain an instance through {@link #getInstance()}.
 */
public class CodeBlockFormat extends TextFormatting implements Html {
    /**
     * The singleton instance of {@code CodeBlockFormat}. Only one instance is needed because the type is effectively
     * memberless--the formatting requires no extra data.
     */
    private static volatile CodeBlockFormat instance = null;

    // Register the format as Html compatible
    static {
        TextFormattingRegistry.registerForLanguage(
                CodeBlockFormat.class, Html.class, getHtmlPattern(),
                CodeBlockFormat::extractFromHtml, CodeBlockFormat::applyAsHtml);
    }

    /**
     * Creates an instance of code block formatting.
     */
    private CodeBlockFormat() {
        super("CodeBlockFormat");
    }

    /**
     * Gets the single instance of {@code CodeBlockFormat}. Multiple instances aren't necessary as this format requires no
     * complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code CodeBlockFormat}
     */
    public static CodeBlockFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (CodeBlockFormat.class) {
                if (instance == null) {
                    instance = new CodeBlockFormat();
                }
            }
        }
        return instance;
    }

    // PATTERNS
    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code CodeBlockFormat} appears in text marked up in HTML.
     *
     * @return a {@code Pattern} capable of identifying code block formatting as it manifests in HTML
     */
    public static Pattern getHtmlPattern() {
        return Pattern.compile("((<pre(.*)>)\\s(<code(.*)>)(.*)(</code\\s>)\\s(</pre\\s>))", Pattern.DOTALL);
    }

    // EXTRACTORS
    /**
     * Extracts a {@code CodeBlockFormat} object replicating the formatting of {@code text} as is specified by its
     * HTML markup and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs HTML and contains a valid instance of code block formatting, a
     * {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup coupled with an
     * instance of {@code CodeBlockFormat} representing the formatting that markup called for.
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ HTML
     * @throws ParseException if an HTML code block could not be parsed from the {@code text}
     */
    public static FormattingMarkedText extractFromHtml(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        // Don't bother trying to parse the text if it doesn't employ any HTML
        MarkupLanguage.preventMarkupLangMismatch(text, "a code block", Html.class);

        // Find the outermost element tags in order to determine that outermost element's content
        Pattern startTag = Pattern.compile("^((<pre\\s(.*)>)\\s(<code\\s(.*)>))");
        Pattern endTag = Pattern.compile("((</code\\s>)\\s(</pre\\s))$");
        return MarkupLanguage.parseFmtBetweenBounds(text, startTag, endTag, CodeBlockFormat.getInstance());
    }

    // APPLIERS
    /**
     * Marks up the provided {@code text}'s raw text in HTML.
     *
     * @param text the {@code FormattingMarkedText} to apply code block formatting to in HTML
     *
     * @return the raw text of {@code text} marked up as to indicate a code block in HTML
     * @throws FormattingMismatchException if the {@code text} does not call for code block formatting
     */
    public static MarkedUpText applyAsHtml(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, CodeBlockFormat.class);

        return new MarkedUpText("<pre><code>" + text + "</code></pre>", Html.class);
    }

    // OVERRIDES
    @Override
    public MarkedUpText applyAsHtmlTo(String text) {
        try {
            return applyAsHtml(new FormattingMarkedText(text, CodeBlockFormat.getInstance()));
        } catch (FormattingMismatchException e) {
            /* So long as the static function employs the correct definition of a FormattingMismatchException (as would
            be the case with it using TextFormatting's preventFormattingMismatch() function), this clause should NEVER
            be executed.
             */
            throw new RuntimeException(e);
        }
    }

    @Override
    public Function<String, MarkedUpText> getMarkupLangApplierFor(
            Class<? extends MarkupLanguage> markupLang) {
        if (markupLang.equals(Html.class)) {
            return this::applyAsHtmlTo;
        } else {
            return null;
        }
    }

    public String toString() {
        return "CodeBlockFormat@" + this.hashCode() + "{}";
    }
}