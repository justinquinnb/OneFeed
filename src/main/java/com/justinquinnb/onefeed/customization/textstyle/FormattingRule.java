package com.justinquinnb.onefeed.customization.textstyle;

import java.util.function.Function;
import java.util.regex.Pattern;

// TODO implement
/**
 * An indication of what to do with
 */
public class FormattingRule<T extends TextFormatting> {
    /**
     *
     */
    private Pattern regex;

    /**
     *
     */
    private Function<String, FormattingMarkedText<T>> process;
}