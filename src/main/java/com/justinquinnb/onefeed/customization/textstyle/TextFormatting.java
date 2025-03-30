package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.textstyle.application.FormattingRuleset;
import com.justinquinnb.onefeed.customization.textstyle.application.TextFormatter;
import com.justinquinnb.onefeed.customization.textstyle.defaults.BasicFormatting;
import com.justinquinnb.onefeed.customization.textstyle.defaults.LinkFormat;

/**
 * Some sort of text formatting type or language, like boldface or italics.
 * <br><br>
 * Direct children of {@code TextFormatting} are considered a {@code TextFormatting} "language". All grandchildren of
 * {@code TextFormatting} are said to be {@code TextFormatting}s belonging to the language of whatever direct child of
 * {@code TextFormatting} they extend.
 * <br><br>
 * Semantically, then, this type can be interpreted through the lens of set theory:
 *
 * <ul>
 *     <li>This abstract class can be thought of as identifying the universe of all {@code TextFormatting} languages;
 *     </li>
 *     <li>Direct children can be thought of as a subset of that
 *     universe;</li>
 *     <li>And all grandchildren, which themselves are just members of the language, can be thought of as a subset of
 *     that parent language.</li>
 * </ul>
 *
 * @see BasicFormatting
 * @see LinkFormat
 *
 * @implSpec Developers wishing to create a new language of {@link TextFormatting}s should extend {@code this} class
 * with a {@code public abstract sealed} class that only {@code permits} children they themselves have developed. That
 * class, then, is effectively the root or definition of a new {@code TextFormatting} language.
 * <br><br>
 * The implementation of children is entirely up to the developer, but it's strongly encouraged to make concrete
 * implementations {@code final} to ensure compatibility with unknown {@link FormattingRuleset}s, which must account for
 * all members of a {@code TextFormatting} language in order for {@link TextFormatter}s to convert <i>all</i> instances
 * of {@link FormattingMarkedText} into literal marked-up text.
 * <br><br>
 * Note that the inclusion of {@code TextFormatting} language members unknown to a {@code FormattingRuleset} (by
 * yourself or a third-party extending {@code non-sealed} {@code TextFormatting}s) will <i>not</i> produce an exception
 * or cause the {@code TextFormatter} to fail. It will simply yield incomplete translations into markup, which is
 * undesirable.
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