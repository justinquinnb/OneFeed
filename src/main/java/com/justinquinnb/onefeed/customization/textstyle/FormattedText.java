package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.utils.JsonToString;

/**
 * Text coupled with its formatting data.
 */
public abstract class FormattedText {
    /**
     * The formatted or unformatted text that {@code this} {@code FormattedText}'s formatting data belongs to.
     */
    private final String text;

    /**
     * Creates an instance of {@code FormattedText} with text {@code text}.
     *
     * @param text the formatted or unformatted text that the created {@code FormattedText} instance's formatting data
     *             belongs to
     */
    protected FormattedText(String text) {
        this.text = text;
    }

    /**
     * Gets {@code this} {@code FormattedText}'s {@link #text}.
     *
     * @return the formatted or unformatted text that {@code this} {@code FormattedText}'s formatting data belongs to
     */
    public String getText() {
        return text;
    }

    @Override
    public final String toString() {
        return JsonToString.of(this);
    }
}