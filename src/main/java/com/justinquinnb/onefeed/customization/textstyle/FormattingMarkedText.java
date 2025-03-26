package com.justinquinnb.onefeed.customization.textstyle;

/**
 * Unformatted text accompanied by some {@link TextFormatting} indicating how it should be formatted.
 * @param <T> the language of {@code TextFormatting}s possibly employed by {@code this} marking
 */
public class FormattingMarkedText<T extends TextFormatting> {
    /**
     * The unformatted text to format with the provided {@link #formatting}.
     */
    private final String unformattedText;

    /**
     * The {@link TextFormatting} associated with the currently {@link #unformattedText}.
     */
    private final T formatting;

    /**
     * Constructs {@link FormattingMarkedText}, an association between some unformatted text, {@code unformattedText},
     * and a {@link TextFormatting} instance representing how it should be formatted.
     *
     * @param unformattedText unformatted text described by the {@code TextFormat} provided by {@code formatting}
     * @param formatting a {@code TextFormatting} instance representing the desired formatting of
     * {@code unformattedText}
     */
    public FormattingMarkedText(String unformattedText, T formatting) {
        this.unformattedText = unformattedText;
        this.formatting = formatting;
    }

    /**
     * Gets this {@code this} {@link FormattingMarkedText}'s {@link #unformattedText}.
     *
     * @return the unformatted text {@code this} {@code FormattingMarkedText}'s {@link #formatting} applies to
     */
    public String getUnformattedText() {
        return this.unformattedText;
    }

    /**
     * Gets this {@code this} {@link FormattingMarkedText}'s {@link #formatting}.
     *
     * @return the formatting {@code this} {@code FormattingMarkedText}'s {@link #unformattedText} should receive
     */
    public T getFormatting() {
        return this.formatting;
    }
}