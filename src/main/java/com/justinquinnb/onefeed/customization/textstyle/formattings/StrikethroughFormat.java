package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.text.ParseException;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Marker for strikethrough text formatting. Obtain an instance through {@link #getInstance()}.
 */
public class StrikethroughFormat extends TextFormatting implements Html, ExtendedMarkdown {
    private static volatile StrikethroughFormat instance = null;

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
     */
    public static FormattingMarkedText extractFromHtml(MarkedUpText text) {
        // Don't bother trying to parse the text if it doesn't employ any HTML
        if (text.getMarkupLanguages().contains(Html.class)) {
            return MarkupLanguage.EXTRACTOR_FALLBACK_PROCESS.apply(text);
        }
        
        // Attempt to parse out the content
        try {
            return Html.extractContentFromElement(text, StrikethroughFormat.getInstance(), "s");
        } catch (ParseException e) {
            return MarkupLanguage.EXTRACTOR_FALLBACK_PROCESS.apply(text);
        }
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
     */
    public static FormattingMarkedText extractFromExtdMd(MarkedUpText text) {
        // Don't bother trying to parse the text if it doesn't employ any MD
        if (text.getMarkupLanguages().contains(ExtendedMarkdown.class)) {
            return MarkupLanguage.EXTRACTOR_FALLBACK_PROCESS.apply(text);
        }
        
        try {
            // Find the outermost strikethrough bounds in order to determine that outermost element's content
            Pattern startTag = Pattern.compile("^(~~)");
            Pattern endTag = Pattern.compile("(~~)$");

            return MarkupLanguage.parseFmtBetweenBounds(text, startTag, endTag, StrikethroughFormat.getInstance());
        } catch (ParseException e) {
            return MarkupLanguage.EXTRACTOR_FALLBACK_PROCESS.apply(text);
        }
    }

    // OVERRIDES
    @Override
    public MarkedUpText applyAsHtmlTo(String text) {
        return new MarkedUpText("<s>" + text + "</s>", Html.class);
    }

    @Override
    public MarkedUpText applyAsExtdMdTo(String text) {
        return new MarkedUpText("~~" + text + "~~", Markdown.class);
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