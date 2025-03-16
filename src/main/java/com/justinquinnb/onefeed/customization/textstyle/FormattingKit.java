package com.justinquinnb.onefeed.customization.textstyle;

// TODO implement

/**
 * Contains everything necessary to apply {@link TextFormatting}s to some text.
 * @param <T> the language of {@code TextFormatting}s employed by {@code this} kit's {@link #instructions}
 */
public class FormattingKit<T extends TextFormatting> {
    /**
     * The unformatted text to format with the provided {@link #instructions}.
     */
    private String unformattedText;

    /**
     * The {@link FormattingInstruction}s outlining exactly how the {@link #unformattedText} should be formatted.
     */
    private FormattingInstruction<T>[] instructions;
}
