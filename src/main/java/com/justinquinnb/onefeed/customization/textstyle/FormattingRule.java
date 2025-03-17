package com.justinquinnb.onefeed.customization.textstyle;

import java.util.function.Function;
import java.util.regex.Pattern;

// TODO implement

/**
 * An indication of what to do with text that matches the included Regex {@link Pattern}.
 * @param <T> the language that the {@link FormattingMarkedText}'s {@link TextFormatting} marker belongs to as the
 *           {@link #process}' output
 */
public class FormattingRule<T extends TextFormatting> {
    /**
     * The Regex {@link Pattern} specifying what substrings to execute the {@link #process} on.
     */
    private Pattern regex;

    /**
     * The {@link Function} that {@code String}s matching the {@link #regex} are piped to in order to produce some
     * {@link FormattingMarkedText}.
     */
    private Function<String, FormattingMarkedText<T>> process;
}