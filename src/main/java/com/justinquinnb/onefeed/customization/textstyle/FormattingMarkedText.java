package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.textstyle.formattings.TextFormatting;

/**
 * Text accompanied by some {@link TextFormatting} indicating how it should be formatted.
 */
public class FormattingMarkedText extends FormattedText {
    /**
     * The {@link TextFormatting} associated with {@code this} {@code FormattedText}'s text.
     */
    private final TextFormatting formatting;

    /**
     * Constructs {@link FormattingMarkedText}, an association between some unformatted text, {@code unformattedText},
     * and a {@link TextFormatting} instance representing how it should be formatted.
     *
     * @param text the {@code String} described by the {@code TextFormatting} provided by {@code formatting}
     * @param formatting a {@code TextFormatting} instance representing the desired formatting of
     * {@code text}
     */
    public FormattingMarkedText(String text, TextFormatting formatting) {
        super(text);
        this.formatting = formatting;
    }

    /**
     * Gets this {@code this} {@link FormattingMarkedText}'s {@link #formatting}.
     *
     * @return the formatting {@code this} {@code FormattingMarkedText}'s text should receive
     */
    public TextFormatting getFormatting() {
        return this.formatting;
    }
}