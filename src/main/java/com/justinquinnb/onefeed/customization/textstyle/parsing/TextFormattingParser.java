package com.justinquinnb.onefeed.customization.textstyle.parsing;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingTree;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormattingRegistry;

import java.util.function.Function;

/**
 * A type capable of parsing {@link MarkedUpText} into a {@link FormattingTree}.
 */
public interface TextFormattingParser {
    /**
     * The {@link FormattingParserFunction} providing a means of handling {@link MarkedUpText} that cannot be parsed
     * as desired.
     */
    public static final Function<MarkedUpText, FormattingMarkedText> FALLBACK_APPROACH =
            TextFormattingParser::readAsDefault;

    /**
     * Parses the markup contained in {@code markedUpText} to generate a {@link FormattingTree} specifying the desired
     * markup of each substring in the entire text.
     *
     * @param markedUpText plaintext marked up using some language that the {@code rules} may recognize
     * @param rules the {@link FormatParsingRuleset} specifying what markup patterns to look for in the
     * {@code markedUpText} and how to derive {@link TextFormatting} objects from them when matches are found
     *
     * @return a {@link FormattingTree} representing the formatting breakdown of the {@code markedUpText}. If parsing
     * for any substring is unsuccessful, the fallback parsing method specified by the {@link #FALLBACK_APPROACH}.
     */
    public FormattingTree parseFormattings(MarkedUpText markedUpText, FormatParsingRuleset rules);


    /**
     * Interprets any provided {@link MarkedUpText} as having only the
     * {@link TextFormattingRegistry#DEFAULT_FORMATTING} applied, providing the respective {@link FormattingMarkedText}
     * as a result.
     *
     * @param text the desired {@code MarkedUpText} instance to interpret as only having a default formatting applied
     *
     * @return a {@code FormattingMarkedText} instance with the same text as {@code text} affiliated with the
     * default formatting type regardless of what that text actually contains
     */
    private static FormattingMarkedText readAsDefault(MarkedUpText text) {
        return new FormattingMarkedText(text.getText(), TextFormattingRegistry.DEFAULT_FORMATTING);
    }
}