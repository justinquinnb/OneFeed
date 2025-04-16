package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.content.details.Platform;
import com.justinquinnb.onefeed.customization.source.ContentSource;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.function.Function;

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
}