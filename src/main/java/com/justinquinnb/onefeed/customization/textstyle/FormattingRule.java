package com.justinquinnb.onefeed.customization.textstyle;

import java.util.function.Function;

// TODO implement

/**
 *
 * @param <T>
 */
public class FormattingRule<T extends TextFormatting> {
    private T formatting;
    private Function<FormattingMarkedText<T>, String> process;
}