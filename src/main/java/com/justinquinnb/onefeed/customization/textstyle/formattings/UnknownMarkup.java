package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.text.ParseException;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * A type representing any unknown markup language. When formatted text cannot be interpreted for whatever reason, it
 * will often be assigned this type of {@link MarkupLanguage} as a fallback that enables formatting and parsing to
 * continue with the problematic piece ignored.
 */
class UnknownMarkup implements MarkupLanguage {
    /**
     * Provides a {@code Function} that always returns {@link MarkedUpText} containing the {@code String} provided to
     * it with {@link UnknownMarkup} as its type of {@link MarkupLanguage}.
     *
     * @param markupLang the desired {@code MarkupLanguage} that the applier's generated {@code MarkedUpText} should
     *                   employ. Ignored.
     *
     * @return a {@code Function} that always returns {@link MarkedUpText} containing the {@code String} provided to
     * it with {@link UnknownMarkup} as its type of {@link MarkupLanguage}
     */
    @Override
    public Function<String, MarkedUpText> getMarkupLangApplierFor(
            Class<? extends MarkupLanguage> markupLang) {
        Function<String, MarkedUpText> applier = null;

        return (text) -> {
            return new MarkedUpText(text, UnknownMarkup.class);
        };
    }

    /**
     * Provides a regex {@link Pattern} that accepts all {@code String}s.
     *
     * @param markupLang the desired {@code MarkupLanguage} that the {@code Pattern} should correspond to. Ignored.
     *
     * @return a regex {@code Pattern} of {@code .*}
     */
    @Override
    public Pattern getMarkupPatternFor(Class<? extends MarkupLanguage> markupLang) {
        return Pattern.compile(".*");
    }

    /**
     * Provides a {@link Function} that always returns {@link FormattingMarkedText} containing the text of the
     * {@link MarkedUpText} passed to it and the {@link DefaultFormat} formatting.
     *
     * @param markupLang the type of {@link MarkupLanguage} that the provided extractor should be able to interpret.
     *                   Ignored.
     *
     * @return a {@link Function} that always returns {@link FormattingMarkedText} containing the text of the
     * {@link MarkedUpText} passed to it and the {@link DefaultFormat} formatting
     */
    @Override
    public Function<MarkedUpText, FormattingMarkedText> getFormattingExtractorFor(
            Class<? extends MarkupLanguage> markupLang) {
        return (markedUpText) -> {
            return new FormattingMarkedText(markedUpText.getText(), DefaultFormat.getInstance());
        };
    }
}