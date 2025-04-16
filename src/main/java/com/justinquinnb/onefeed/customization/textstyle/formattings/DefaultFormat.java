package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Marker for default/unformatted text. Obtain an instance through {@link #getInstance()}.
 */
public class DefaultFormat extends TextFormatting implements Html, Markdown, ExtendedMarkdown {
    private static volatile DefaultFormat instance = null;

    /**
     * Creates an instance of bold formatting.
     */
    private DefaultFormat() {
        super("DefaultFormat");
    }

    /**
     * Gets the single instance of {@code DefaultFormat}. Multiple instances aren't necessary as this format requires no
     * complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code DefaultFormat}
     */
    public static DefaultFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (DefaultFormat.class) {
                if (instance == null) {
                    instance = new DefaultFormat();
                }
            }
        }
        return instance;
    }

    // PATTERNS
    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code DefaultFormat} appears in text marked up in HTML.
     *
     * @return a {@code Pattern} capable of identifying default formatting as it manifests in HTML
     */
    public static Pattern getHtmlPattern() {
        return Pattern.compile(".*", Pattern.DOTALL);
    }

    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code DefaultFormat} appears in text marked up in
     * Markdown.
     *
     * @return a {@code Pattern} capable of identifying default formatting as it manifests in Markdown
     */
    public static Pattern getMdPattern() {
        return Pattern.compile(".*", Pattern.DOTALL);
    }

    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code DefaultFormat} appears in text marked up in
     * Extended Markdown.
     *
     * @return a {@code Pattern} capable of identifying default formatting as it manifests in Extended Markdown
     *
     * @see #getMdPattern()
     */
    public static Pattern getExtdMdPattern() {
        return getMdPattern();
    }

    // EXTRACTORS
    /**
     * Extracts a {@code DefaultFormat} object replicating the formatting of {@code text} as is specified by its
     * HTML markup and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs HTML and contains a valid instance of default formatting, a
     * {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup coupled with an
     * instance of {@code DefaultFormat} representing the formatting that markup called for.
     */
    public static FormattingMarkedText extractFromHtml(MarkedUpText text) {
        return new FormattingMarkedText(text.getText(), DefaultFormat.getInstance());
    }

    /**
     * Extracts a {@code DefaultFormat} object replicating the formatting of {@code text} as is specified by its
     * Markdown and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs Markdown and contains a valid instance of default formatting, a
     * {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup coupled with an
     * instance of {@code DefaultFormat} representing the formatting that markup called for.
     */
    public static FormattingMarkedText extractFromMd(MarkedUpText text) {
        return new FormattingMarkedText(text.getText(), DefaultFormat.getInstance());
    }

    /**
     * Extracts a {@code DefaultFormat} object replicating the formatting of {@code text} as is specified by its
     * Extended Markdown and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs Extended Markdown and contains a valid instance of default
     * formatting, a {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup
     * coupled with an instance of {@code DefaultFormat} representing the formatting that markup called for.
     *
     * @see #extractFromMd
     */
    public static FormattingMarkedText extractFromExtdMd(MarkedUpText text) {
        return extractFromMd(text);
    }

    // OVERRIDES
    @Override
    public MarkedUpText applyAsHtmlTo(String text) {
        return new MarkedUpText(text, Html.class);
    }

    @Override
    public MarkedUpText applyAsMdTo(String text) {
        return new MarkedUpText(text, Markdown.class);
    }

    @Override
    public MarkedUpText applyAsExtdMdTo(String text) {
        return this.applyAsMdTo(text);
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
        return "DefaultFormat@" + this.hashCode() + "{}";
    }
}