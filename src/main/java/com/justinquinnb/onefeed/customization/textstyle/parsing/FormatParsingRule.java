package com.justinquinnb.onefeed.customization.textstyle.parsing;

import com.justinquinnb.onefeed.JsonToString;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.formattings.TextFormatting;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * An indication of how to extract {@link TextFormatting}s from {@link MarkedUpText}.
 */
public class FormatParsingRule {
    /**
     * The regex {@link Pattern} specifying what substrings to execute the {@link #process} on.
     */
    private final Pattern regex;

    /**
     * The {@link Function} that {@code String}s matching the {@link #regex} are piped to in order to produce some
     * {@link FormattingMarkedText}.
     */
    private final Function<MarkedUpText, FormattingMarkedText> process;

    /**
     * Creates a {@link FormatParsingRule} mapping the provided {@code regex} to a {@link FormattingMarkedText}-producing
     * {@code Function} that processes its matches.
     *
     * @param regex a regex {@link Pattern} specifying what to search for in {@link MarkedUpText}
     * @param process the desired {@code Function} to run on matches to generate {@code FormattingMarkedText}
     */
    public FormatParsingRule(Pattern regex, Function<MarkedUpText, FormattingMarkedText> process) {
        this.regex = regex;
        this.process = process;
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
     * Gets {@code this} {@link FormatParsingRule}'s {@link #process}.
     *
     * @return the {@link Function} to run on {@link #regex} matches and generate {@code FormattingMarkedText} with
     */
    public Function<MarkedUpText, FormattingMarkedText> getProcess() {
        return this.process;
    }

    /**
     * Processes text (particularly that matching {@code this} {@link FormatParsingRule}'s {@link #regex}) using
     * {@code this} {@code FormatParsingRule}'s {@link #process} {@link Function}.
     *
     * @param matchText the {@code MarkedUpText} to try extracting {@link FormattingMarkedText} from
     *
     * @return some {@link FormattingMarkedText} containing the (possibly) markup-stripped {@code matchText} and the
     * {@link TextFormatting} that represents the formatting it should have
     */
    public FormattingMarkedText apply(MarkedUpText matchText) {
        return this.process.apply(matchText);
    }

    @Override
    public String toString() {
        return JsonToString.of(this);
    }
}