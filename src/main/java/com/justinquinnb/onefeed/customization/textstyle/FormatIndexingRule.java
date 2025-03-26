package com.justinquinnb.onefeed.customization.textstyle;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * An indication of what to do with text that matches the included regex {@link Pattern}.
 *
 * @param <T> the language that the {@link FormattingMarkedText}'s {@link TextFormatting} marker belongs to as the
 *           {@link #process}' output
 */
public class FormatIndexingRule<T extends TextFormatting> {
    /**
     * The regex {@link Pattern} specifying what substrings to execute the {@link #process} on.
     */
    private final Pattern regex;

    /**
     * The {@link Function} that {@code String}s matching the {@link #regex} are piped to in order to produce some
     * {@link FormattingMarkedText}.
     */
    private final Function<String, FormattingMarkedText<T>> process;

    /**
     * Creates a {@link FormatIndexingRule} mapping the provided {@code regex} to a {@link FormattingMarkedText}-producing
     * {@code Function} that processes its matches.
     *
     * @param regex a regex {@link Pattern} specifying what to search for in any given {@code String}
     * @param process the desired {@code Function} to run on matches to generate {@code FormattingMarkedText}
     */
    public FormatIndexingRule(Pattern regex, Function<String, FormattingMarkedText<T>> process) {
        this.regex = regex;
        this.process = process;
    }

    /**
     * Gets {@code this} {@link FormatIndexingRule}'s regex {@link Pattern}.
     *
     * @return the regex {@code Pattern} specifying what to search for in any given {@code String}
     */
    public Pattern getRegex() {
        return this.regex;
    }

    /**
     * Gets {@code this} {@link FormatIndexingRule}'s {@link #process}.
     *
     * @return the {@link Function} to run on {@link #regex} matches and generate {@code FormattingMarkedText} with
     */
    public Function<String, FormattingMarkedText<T>> getProcess() {
        return this.process;
    }

    /**
     * Processes text (particularly that matching {@code this} {@link FormatIndexingRule}'s {@link #regex}) using
     * {@code this} {@code FormattingMarkedText}'s {@link #process} {@link Function}.
     *
     * @param matchText any text to apply the {@code apply} function to, but particularly that which matches
     *                  {@code this} {@code FormatIndexingRule}'s {@code regex}.
     *
     * @return some {@link FormattingMarkedText} containing the (possibly) altered {@code matchText} and the
     * {@link TextFormatting} now associated with it
     */
    public FormattingMarkedText<T> apply(String matchText) {
        return this.process.apply(matchText);
    }
}