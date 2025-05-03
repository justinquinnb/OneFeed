package com.justinquinnb.onefeed.customization.textstyle.parsing;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingTree;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormattingRegistry;

/**
 * A type capable of parsing {@link MarkedUpText} into a {@link FormattingTree}.
 */
public interface TextFormattingParser {
    /**
     * Par
     *
     * @param <T> the language of {@link TextFormatting}s that may be derived from marked-up text
     * @param markedUpText plaintext marked up using some language that the {@code formattingRules} may recognize
     *
     * @return a {@code FormattingKit} pairing a markup-less {@code markedUpText} instance with an index of
     * substring locations mapped to the {@code TextFormatting}(s) that should be applied to them. Should the provided
     * {@code formattingRules} not match any parts of the {@code markedUpText} (as might be when its markup
     * language isn't recognized by the rules), the {@code FormattingKit} will contain {@code markedUpText} as-is with
     * an empty index of formattings.
     */
    public FormattingTree parseFormattings(MarkedUpText markedUpText, FormatParsingRuleset rules);


    /**
     * Interprets any provided {@link MarkedUpText} as possessing only the
     * {@link TextFormattingRegistry#DEFAULT_FORMATTING} applied, providing the respective {@link FormattingMarkedText} as a result.
     *
     * @param text the desired {@code MarkedUpText} instance to interpret as only having a default formatting applied
     *
     * @return a {@code FormattingMarkedText} instance with the same text as {@code text} affiliated with the
     * default formatting type regardless of what that text actually contains
     */
    private static FormattingMarkedText useFallbackParser(MarkedUpText text) {
        return new FormattingMarkedText(text.getText(), TextFormattingRegistry.DEFAULT_FORMATTING);
    }
}