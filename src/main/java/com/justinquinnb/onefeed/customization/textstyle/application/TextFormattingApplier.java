package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.customization.textstyle.FormattingKit;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.markup.MarkupLanguage;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormattingRegistry;

import java.util.function.Function;

/**
 * A type capable of marking up text in some {@link MarkupLanguage}, provided a {@link FormattingKit}.
 */
public interface TextFormattingApplier {
    /**
     * The {@link FormattingApplierFunction} providing a means of handling instances where a {@link FormattingKit}'s
     * specified {@link TextFormatting} cannot be applied to the text as desired.
     */
    public static final Function<FormattingMarkedText, MarkedUpText> FALLBACK_APPROACH =
            TextFormattingApplier::markAsDefault;
    
    /**
     * Applies the {@link TextFormatting}s specified in the {@code kit} to the {@code kit}'s text to generate
     * {@link MarkedUpText} in the {@code targetLang}.
     *
     * @param kit the hierarchy of substrings and their {@code TextFormatting}s in a given {@code String}
     * @param
     *
     * @return the formatted text represented by the {@code kit} represented in the {@code targetLang}, bundled with
     * that {@code targetLang} in a {@code MarkedUpText} object. If a conversion process to the {@code targetLang} isn't
     * specified for any of the {@code kit}'s {@code TextFormatting}s, the target substring will remain as-is.
     */
    /**
     * Applies the {@link TextFormatting}s specified in the {@code kit} to the {@code kit}'s text to generate
     * {@link MarkedUpText}.
     *
     * @param kit the hierarchy of substrings and their {@code TextFormatting}s in a given string of marked-up text
     * @param rules the {@link FormatApplicationRuleset} specifying how each {@code TextFormatting} called for by the 
     * {@code kit} should appear as markup in the final {@code MarkedUpText}
     *
     * @return a complete string of {@code MarkedUpText} derived from applying each of the markup {@code rules} to the 
     * {@code kit}'s substring
     */
    public MarkedUpText applyFormattings(FormattingKit kit, FormatApplicationRuleset rules);

    /**
     * Interprets any provided {@link FormattingMarkedText} as having only the
     * {@link TextFormattingRegistry#DEFAULT_MARKUP_LANGUAGE} respective {@link FormattingMarkedText} as a result.
     *
     * @param text the desired {@code FormattingMarkedText} instance to interpret as only having the default markup
     * language applied
     *
     * @return a {@code MarkedUpText} instance with the same text as {@code text} affiliated with the default markup
     * language and having no additional markup applied
     */
    private static MarkedUpText markAsDefault(FormattingMarkedText text) {
        return new MarkedUpText(text.getText(), TextFormattingRegistry.DEFAULT_MARKUP_LANGUAGE);
    }
}