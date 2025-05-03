package com.justinquinnb.onefeed.customization.textstyle.markup;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMismatchException;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Marker for default/unformatted text. Obtain an instance through {@link #getInstance()}.
 */
public class DefaultFormat extends TextFormatting implements Html, Markdown, ExtendedMarkdown {
    private static volatile DefaultFormat instance = null;

    // Register the format as Html, Markdown, and Extended Markdown compatible
    static {
        TextFormattingRegistry.registerForLanguage(
                DefaultFormat.class, Html.class, getHtmlPattern(),
                DefaultFormat::extract, DefaultFormat::applyAsHtml);
        TextFormattingRegistry.registerForLanguage(
                DefaultFormat.class, Markdown.class, getMdPattern(),
                DefaultFormat::extract, DefaultFormat::applyAsMd);
        TextFormattingRegistry.registerForLanguage(
                DefaultFormat.class, ExtendedMarkdown.class, getMdPattern(),
                DefaultFormat::extract, DefaultFormat::applyAsMd);
    }
    
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
     * Pairs the raw text of {@code text} with a {@code DefaultFormat} instance in a {@link FormattingMarkedText}
     * object.
     *
     * @param text the {@code MarkedUpText} whose raw text to copy over
     *
     * @return a copy of {@code text}'s raw text paired with a {@code DefaultFormat} instance, both contained in a
     * {@code FormattingMarkedText} object
     */
    public static FormattingMarkedText extract(MarkedUpText text) {
        return new FormattingMarkedText(text.getText(), DefaultFormat.getInstance());
    }

    // APPLIERS
    /**
     * Pairs the raw text of {@code text} with an {@link Html} instance in a {@link MarkedUpText} object.
     *
     * @param text the {@code FormattingMarkedText} whose raw text to copy over
     *
     * @return the raw text of {@code text} paired with an {@code Html} instance in a {@code MarkedUpText} object
     */
    public static MarkedUpText applyAsHtml(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, DefaultFormat.class);

        return new MarkedUpText(text.getText(), Html.class);
    }

    /**
     * Pairs the raw text of {@code text} with an {@link Markdown} instance in a {@link MarkedUpText} object.
     *
     * @param text the {@code FormattingMarkedText} whose raw text to copy over
     *
     * @return the raw text of {@code text} paired with an {@code Markdown} instance in a {@code MarkedUpText} object
     */
    public static MarkedUpText applyAsMd(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, DefaultFormat.class);

        return new MarkedUpText(text.getText(), Markdown.class);
    }

    // OVERRIDES
    @Override
    public MarkedUpText applyAsHtmlTo(String text) {
        try {
            return applyAsHtml(new FormattingMarkedText(text, DefaultFormat.getInstance()));
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
            return applyAsMd(new FormattingMarkedText(text, DefaultFormat.getInstance()));
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
            return applyAsMd(new FormattingMarkedText(text, DefaultFormat.getInstance()));
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
        return "DefaultFormat@" + this.hashCode() + "{}";
    }
}