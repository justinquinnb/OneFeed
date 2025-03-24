package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.defaults.BasicFormatting;
import com.justinquinnb.onefeed.customization.defaults.LinkFormat;

/**
 * A marker for some sort of text formatting, like boldface or italics.
 * <br><br>
 * Direct children of {@code TextFormatting} are considered a {@code TextFormatting} "language". All
 * grandchildren of {@code TextFormatting} are said to be {@code TextFormatting}s belonging to the language of whatever
 * direct child of {@code TextFormatting} they extend.
 * <br><br>
 * Semantically, then, this type can be interpreted through the lens of set theory:
 *
 * <ul>
 *     <li>This abstract class can be thought of as identifying the universe of all {@code TextFormatting} languages;
 *     </li>
 *     <li>Direct children, which themselves can be members of their language, can be thought of as a subset of that
 *     universe;</li>
 *     <li>And all grandchildren, which themselves should primarily be just members of the language, can be thought of
 *     as a subset of that parent language.</li>
 * </ul>
 *
 * @see BasicFormatting
 * @see LinkFormat
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