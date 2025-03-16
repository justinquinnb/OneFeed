package com.justinquinnb.onefeed.customization.textstyle;

// TODO implement
/**
 * Unformatted text accompanied by the type of {@link TextFormatting} pending application to it.
 * @param <T> the language of {@code TextFormatting}s possibly employed by {@code this} marking
 */
public class FormattingMarkedText<T extends TextFormatting> {
    /**
     * The unformatted text to format with the provided {@link #format}.
     */
    private String unformattedText;

    /**
     * The {@link TextFormatting} associated with the currently {@link #unformattedText}.
     */
    private TextFormatting formatting;

    public FormattingMarkedText(String unformattedText, T formatting) {
        this.unformattedText = unformattedText;
        this.formatting = formatting;
    }
}