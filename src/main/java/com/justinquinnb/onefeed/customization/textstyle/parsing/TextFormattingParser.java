package com.justinquinnb.onefeed.customization.textstyle.parsing;

import com.justinquinnb.onefeed.customization.textstyle.FormattingTree;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.formattings.TextFormatting;

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
}