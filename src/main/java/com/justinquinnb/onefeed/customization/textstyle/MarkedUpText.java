package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.textstyle.formattings.MarkupLanguage;

/**
 * A {@code String} of text associated with the type of markup language it employs to represent its formatting.
 */
public class MarkedUpText extends FormattedText {
    /**
     * The type of markup language employed by {@code this} {@code FormattedText}.
     */
    private final Class<? extends MarkupLanguage> markupLanguage;

    /**
     * Creates an instance of {@code FormattedText} with text {@code text} employing markup language {@code markupLang}.
     *
     * @param text the formatted or unformatted text that the created {@code FormattedText} instance's formatting data
     *             belongs to
     * @param markupLang the type of markup language employed by the instance of {@code FormattedText} to create
     */
    public MarkedUpText(String text, Class<? extends MarkupLanguage> markupLang) {
        super(text);
        this.markupLanguage = markupLang;
    }

    /**
     * Gets the type of markup language employed by the text of {@code this} {@code MarkedUpText} instance.
     *
     * @return the type of markup language employed by the text of {@code this} {@code MarkedUpText} instance
     */
    public Class<? extends MarkupLanguage> getMarkupLanguage() {
        return this.markupLanguage;
    }
}
