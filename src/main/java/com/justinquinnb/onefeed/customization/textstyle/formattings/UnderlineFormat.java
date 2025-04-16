package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.text.ParseException;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Marker for underline text formatting. Obtain an instance through {@link #getInstance()}.
 */
public class UnderlineFormat extends TextFormatting implements Html {
    private static volatile UnderlineFormat instance = null;

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
     */
    public static FormattingMarkedText extractFromHtml(MarkedUpText text) {
        // Don't bother trying to parse the text if it doesn't employ any HTML
        if (text.getMarkupLanguages().contains(Html.class)) {
            return MarkupLanguage.EXTRACTOR_FALLBACK_PROCESS.apply(text);
        }
        
        // Attempt to parse out the content
        try {
            return Html.extractContentFromElement(text, UnderlineFormat.getInstance(), "u");
        } catch (ParseException e) {
            return MarkupLanguage.EXTRACTOR_FALLBACK_PROCESS.apply(text);
        }
    }

    // OVERRIDES
    @Override
    public MarkedUpText applyAsHtmlTo(String text) {
        return new MarkedUpText("<u>" + text + "</u>", Html.class);
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