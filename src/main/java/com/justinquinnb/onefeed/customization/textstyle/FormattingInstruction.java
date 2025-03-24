package com.justinquinnb.onefeed.customization.textstyle;

/**
 * A single instruction to format some text at {@link #location} with a {@link #formatting}.
 * @param <T> the langauge of {@link TextFormatting}s.
 */
public class FormattingInstruction<T extends TextFormatting> implements Cloneable {
    /**
     * The location some substring that the {@link #formatting} applies to.
     */
    private SubstringLocation location;

    /**
     * The {@link TextFormatting} to apply to some substring located at {@link #location}.
     */
    private TextFormatting formatting;

    /**
     * Instantiates a {@link FormattingInstruction} specifying a type of {@link TextFormatting} to apply to a substring
     * at {@code location} within some other, unknown {@code String}.
     *
     * @param location the {@link SubstringLocation} that the instantiated {@code FormattingInstruction}'s
     *                 {@code TextFormatting} should apply to
     * @param formatting the {@code TextFormatting} to apply to some substring at {@code location}
     */
    public FormattingInstruction(SubstringLocation location, TextFormatting formatting) {
        this.location = location;
        this.formatting = formatting;
    }

    /**
     * Gets the {@link #location} that {@code this} {@link FormattingInstruction} applies to.
     *
     * @return the {@link SubstringLocation} indicating where {@code this} {@code FormattingInstruction}'s
     * {@link #formatting} is to be applied on some {@code String}
     */
    public SubstringLocation getLocation() {
        return this.location;
    }

    /**
     * Gets the {@link #formatting} that {@code this} {@link FormattingInstruction} specifies the location of within
     * some unknown {@code String}.
     *
     * @return the {@code TextFormatting} to be applied to the substring at location {@link #location} of some
     * {@code String}
     */
    public TextFormatting getFormatting() {
        return this.formatting;
    }

    /**
     * Creates a deep copy of {@code this} {@link FormattingInstruction}.
     *
     * @return a deep copy of {@code this} {@link FormattingInstruction}.
     */
    @Override
    public FormattingInstruction<T> clone() {
        return new FormattingInstruction<T>(this.location, this.formatting);
    }
}
