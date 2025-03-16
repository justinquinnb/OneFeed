package com.justinquinnb.onefeed.customization.textstyle;

// TODO complete implementation
/**
 * A single instruction to format some text at {@link #location} with a {@link #formatting}.
 * @param <T> the langauge of {@link TextFormatting}s.
 */
public class FormattingInstruction<T extends TextFormatting> {
    /**
     * The location some substring that the {@link #formatting} applies to.
     */
    private SubstringLocation location;

    /**
     * The {@link TextFormatting} to apply to some substring located at {@link #location}.
     */
    private TextFormatting formatting;
}
