package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.defaults.BasicFormatting;
import com.justinquinnb.onefeed.customization.defaults.LinkFormat;

import java.util.Objects;

// TODO implement
/**
 * A text formatting, like boldface or italics.
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
     *
     */
    private String label;

    /**
     *
     * @param label
     */
    public TextFormatting(String label) {
        this.label = label.toUpperCase().strip();
    }

    /**
     *
     * @return
     */
    public String getLabel() {
        return this.label;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getClass(), this.label);
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }
}