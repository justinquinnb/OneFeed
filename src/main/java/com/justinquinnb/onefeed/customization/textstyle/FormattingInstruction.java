package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;
import com.justinquinnb.onefeed.utils.JsonToString;

/**
 * A single instruction to format a substring at {@link #location} with {@link #formatting} within a larger string.
 */
public class FormattingInstruction implements Cloneable {
    /**
     * The location of the substring that the {@link #formatting} applies to within the larger, parent string.
     */
    private final SubstringLocation location;

    /**
     * The {@link TextFormatting} to apply to some substring located at {@link #location}.
     */
    private final TextFormatting formatting;

    /**
     * The change in length from the marked-up substring to the unmarked substring. Necessary to calculate net
     * displacement when re-applying markup and adjusting later instructions.
     */
    private final int parsingDisplacement;

    /**
     * Instantiates a {@link FormattingInstruction} specifying a type of {@link TextFormatting} to apply to a substring
     * at {@code location} within some other, unknown {@code String}.
     *
     * @param location the {@link SubstringLocation} that the instantiated {@code FormattingInstruction}'s
     *                 {@code TextFormatting} should apply to
     * @param formatting the {@code TextFormatting} to apply to some substring at {@code location}
     * @param parsingDisplacement when the text the constructed {@code FormattingInstruction} applies to was parsed
     * from its parent string, the change in length it underwent when its markup was removed
     */
    public FormattingInstruction(SubstringLocation location, TextFormatting formatting, int parsingDisplacement) {
        this.location = location;
        this.formatting = formatting;
        this.parsingDisplacement = parsingDisplacement;
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
     * Gets the change in length the string {@code this} instruction applies to when it underwent parsing from
     * a marked-up substring to an unmarked substring.
     *
     * @return the change in length the string {@code this} instruction applies to saw when its parsing rule was applied
     */
    public int getParsingDisplacement() {
        return this.parsingDisplacement;
    }

    /**
     * Creates a deep copy of {@code this} {@link FormattingInstruction}.
     *
     * @return a deep copy of {@code this} {@link FormattingInstruction}.
     */
    @Override
    public FormattingInstruction clone() {
        return new FormattingInstruction(this.location, this.formatting, this.parsingDisplacement);
    }

    @Override
    public String toString() {
        return JsonToString.of(this);
    }
}