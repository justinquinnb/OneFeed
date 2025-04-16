package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.text.ParseException;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Marker for inline code text formatting. Obtain an instance through {@link #getInstance()}.
 */
public class InlineCodeFormat extends TextFormatting implements Html, Markdown, ExtendedMarkdown {
    private static volatile InlineCodeFormat instance = null;

    /**
     * Creates an instance of code block formatting.
     */
    private InlineCodeFormat() {
        super("InlineCodeFormat");
    }

    /**
     * Gets the single instance of {@code InlineCodeFormat}. Multiple instances aren't necessary as this format requires no
     * complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code InlineCodeFormat}
     */
    public static InlineCodeFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (InlineCodeFormat.class) {
                if (instance == null) {
                    instance = new InlineCodeFormat();
                }
            }
        }
        return instance;
    }

    // PATTERNS
    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code InlineCodeFormat} appears in text marked up in HTML.
     *
     * @return a {@code Pattern} capable of identifying inline code formatting as it manifests in HTML
     */
    public static Pattern getHtmlPattern() {
        return Pattern.compile("(<code(.*)>)(.*)(</code\\s>)", Pattern.DOTALL);
    }

    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code InlineCodeFormat} appears in text marked up in
     * Markdown.
     *
     * @return a {@code Pattern} capable of identifying inline code formatting as it manifests in Markdown
     */
    public static Pattern getMdPattern() {
        return Pattern.compile("`(.*)`", Pattern.DOTALL);
    }

    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code InlineCodeFormat} appears in text marked up in
     * Extended Markdown.
     *
     * @return a {@code Pattern} capable of identifying inline code formatting as it manifests in Extended Markdown
     *
     * @see #getMdPattern()
     */
    public static Pattern getExtdMdPattern() {
        return getMdPattern();
    }

    // EXTRACTORS
    /**
     * Extracts a {@code InlineCodeFormat} object replicating the formatting of {@code text} as is specified by its
     * HTML markup and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs HTML and contains a valid instance of inline code formatting, a
     * {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup coupled with an
     * instance of {@code InlineCodeFormat} representing the formatting that markup called for.
     */
    public static FormattingMarkedText extractFromHtml(MarkedUpText text) {
        // Don't bother trying to parse the text if it doesn't employ any HTML
        if (text.getMarkupLanguages().contains(Html.class)) {
            return MarkupLanguage.EXTRACTOR_FALLBACK_PROCESS.apply(text);
        }

        // Attempt to parse out the content
        try {
            return Html.extractContentFromElement(text, InlineCodeFormat.getInstance(), "code");
        } catch (ParseException e) {
            return MarkupLanguage.EXTRACTOR_FALLBACK_PROCESS.apply(text);
        }
    }

    /**
     * Extracts a {@code InlineCodeFormat} object replicating the formatting of {@code text} as is specified by its
     * Markdown and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs Markdown and contains a valid instance of inline code formatting, a
     * {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup coupled with an
     * instance of {@code InlineCodeFormat} representing the formatting that markup called for.
     */
    public static FormattingMarkedText extractFromMd(MarkedUpText text) {
        // Don't bother trying to parse the text if it doesn't employ any MD
        if (text.getMarkupLanguages().contains(Markdown.class) ||
                text.getMarkupLanguages().contains(ExtendedMarkdown.class)
        ) {
            return MarkupLanguage.EXTRACTOR_FALLBACK_PROCESS.apply(text);
        }

        try {
            Pattern startBound = Pattern.compile("^`");
            Pattern endBound = Pattern.compile("`$");
            return MarkupLanguage.parseFmtBetweenBounds(text, startBound, endBound, InlineCodeFormat.getInstance());
        } catch (ParseException e) {
            return MarkupLanguage.EXTRACTOR_FALLBACK_PROCESS.apply(text);
        }
    }

    /**
     * Extracts a {@code InlineCodeFormat} object replicating the formatting of {@code text} as is specified by its
     * Extended Markdown and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs Extended Markdown and contains a valid instance of inline code
     * formatting, a {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup
     * coupled with an instance of {@code InlineCodeFormat} representing the formatting that markup called for.
     *
     * @see #extractFromMd
     */
    public static FormattingMarkedText extractFromExtdMd(MarkedUpText text) {
        return extractFromMd(text);
    }

    // OVERRIDES
    @Override
    public MarkedUpText applyAsHtmlTo(String text) {
        return new MarkedUpText("<code>" + text + "</code>", Html.class);
    }

    @Override
    public MarkedUpText applyAsMdTo(String text) {
        return new MarkedUpText("`" + text + "`", Markdown.class);
    }

    @Override
    public MarkedUpText applyAsExtdMdTo(String text) {
        return new MarkedUpText("`" + text + "`", ExtendedMarkdown.class);
    }

    @Override
    public Function<String, MarkedUpText> getMarkupLangApplierFor(
            Class<? extends MarkupLanguage> markupLang) {
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
        return "InlineCodeFormat@" + this.hashCode() + "{}";
    }
}