package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.textstyle.formattings.BoldFormat;

/**
 * Some sort of text formatting, like boldface or italics.
 *
 * @implSpec For text formatting types that don't require supplementary data (like boldface, which is effectively a
 * flag), a singleton approach is strongly encouraged. See {@link BoldFormat} for an example implementation.
 */
public abstract class TextFormatting {
    /**
     * A label or name for the type of text formatting, like "Bold" or "Italic".
     */
    private final String label;

    /**
     * Instantiates a type of text formatting with label {@code label}.
     *
     * @param label the label/name for the constructed {@link TextFormatting} instance to assume
     */
    public TextFormatting(String label) {
        this.label = label.toUpperCase().strip();
    }

    /**
     * Gets {@code this} {@link TextFormatting}'s {@link #label}.
     *
     * @return the label/name of {@code this} {@code TextFormatting} instance
     */
    public final String getLabel() {
        return this.label;
    }
}