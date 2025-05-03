package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingTree;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.markup.MarkupLanguage;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormattingRegistry;

/**
 * A type capable of marking up text in some {@link MarkupLanguage}, provided a {@link FormattingTree}.
 */
public interface TextFormattingApplier {
    /**
     * Applies the {@link TextFormatting}s specified in the {@code tree} to the {@code tree}'s text to generate
     * {@link MarkedUpText} in the {@code targetLang}.
     *
     * @param tree the hierarchy of substrings and their {@code TextFormatting}s in a given {@code String}
     * @param targetLang the type of {@code MarkupLanguage} the generated {@code MarkedUpText} should use
     *
     * @return the formatted text represented by the {@code tree} represented in the {@code targetLang}, bundled with
     * that {@code targetLang} in a {@code MarkedUpText} object. If a conversion process to the {@code targetLang} isn't
     * specified for any of the {@code tree}'s {@code TextFormatting}s, the target substring will remain as-is.
     */
    public MarkedUpText applyFormattings(FormattingTree tree, FormatApplicationRuleset rules);

    /**
     * Interprets any provided {@link FormattingMarkedText} as possessing only the
     * {@link TextFormattingRegistry#DEFAULT_MARKUP_LANGUAGE} respective {@link FormattingMarkedText} as a result.
     *
     * @param text the desired {@code FormattingMarkedText} instance to interpret as only having the default markup
     * language applied
     *
     * @return a {@code MarkedUpText} instance with the same text as {@code text} affiliated with the default markup
     * language and having no additional markup applied
     */
    private static MarkedUpText useFallbackApplier(FormattingMarkedText text) {
        return new MarkedUpText(text.getText(), TextFormattingRegistry.DEFAULT_MARKUP_LANGUAGE);
    }
}