package com.justinquinnb.onefeed.customization.textstyle.markup;

import com.justinquinnb.onefeed.content.details.Platform;
import com.justinquinnb.onefeed.customization.source.ContentSource;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMismatchException;

/**
 * Some sort of text formatting, like boldface or italics.
 *
 * @implSpec For text formatting types that don't require supplementary data (like boldface, which is effectively a
 * flag), a singleton approach is strongly encouraged. See {@link DefaultFormat} for an example implementation.
 * <br><br>
 * Additionally, {@link ContentSource} developers wishing to add their {@link Platform}'s markup language regex and
 * parsing specification to an existing {@link MarkupLanguage} type should
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

    /**
     * TODO also change class documentations to reflect this change for markup langs
     */
    public abstract void initializeFormatting();

    /**
     * Ensures {@code formatting} and the {@link TextFormatting} used in the {@code text} match, throwing an error if
     * they do not and doing nothing otherwise.
     *
     * @param text the {@link FormattingMarkedText} whose employed {@code TextFormatting} type to check
     * @param formatting the desired {@code TextFormatting} for the {@code text} to employ
     *
     * @throws FormattingMismatchException if {@code text}'s employed {@code TextFormatting} and the desired
     * {@code formatting} do not match
     */
    public static void preventFormattingMismatch(FormattingMarkedText text, Class<? extends TextFormatting> formatting)
            throws FormattingMismatchException
    {
        if (text.getFormatting().getClass() == formatting) {
            throw new FormattingMismatchException("The provided text is marked with " +
                    text.getFormatting().getClass().getSimpleName() + ", not " + formatting.getSimpleName());
        }
    }
}