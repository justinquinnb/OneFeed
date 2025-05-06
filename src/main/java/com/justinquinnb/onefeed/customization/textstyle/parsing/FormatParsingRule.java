package com.justinquinnb.onefeed.customization.textstyle.parsing;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;
import com.justinquinnb.onefeed.utils.JsonToString;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * An indication of how to extract {@link TextFormatting}s from {@link MarkedUpText}.
 */
public class FormatParsingRule {
    /**
     * The regex {@link Pattern} specifying what substrings to execute the {@link #formattingParser} on.
     */
    private final Pattern regex;

    /**
     * The {@link FormattingParserFunction} that {@code String}s matching the {@link #regex} are piped into to
     * produce some {@link FormattingMarkedText}.
     */
    private final FormattingParserFunction formattingParser;

    /**
     * Creates a {@link FormatParsingRule} mapping the provided {@code regex} to a
     * {@link FormattingMarkedText}-producing {@link FormattingParserFunction} that processes its matches.
     *
     * @param regex a regex {@link Pattern} specifying what to search for in {@link MarkedUpText}
     * @param formattingParser the desired {@code FormattingParser} to run on matches to generate
     * {@code FormattingMarkedText}
     */
    public FormatParsingRule(Pattern regex, FormattingParserFunction formattingParser) {
        this.regex = regex;
        this.formattingParser = formattingParser;
    }

    /**
     * Gets {@code this} {@link FormatParsingRule}'s regex {@link Pattern}.
     *
     * @return the regex {@code Pattern} specifying what to search for in any given {@code String}
     */
    public Pattern getRegex() {
        return this.regex;
    }

    /**
     * Gets {@code this} {@link FormatParsingRule}'s {@link #formattingParser}.
     *
     * @return the {@link Function} to run on {@link #regex} matches and generate {@code FormattingMarkedText} with
     */
    public FormattingParserFunction getParserFunction() {
        return this.formattingParser;
    }

    @Override
    public String toString() {
        return JsonToString.of(this);
    }
}