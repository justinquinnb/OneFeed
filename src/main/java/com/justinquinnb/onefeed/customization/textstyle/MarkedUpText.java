package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.textstyle.markup.MarkupLanguage;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * A {@code String} of text associated with the types of markup languages it employs to represent its formatting. The
 * text should only contain the literal text and its surrounding and embedded markup.
 */
public class MarkedUpText extends FormattedText {
    /**
     * The type of markup languages employed by {@code this} {@code FormattedText}.
     */
    private final LinkedHashSet<Class<? extends MarkupLanguage>> markupLanguages = new LinkedHashSet<>();

    /**
     * Creates an instance of {@code FormattedText} with text {@code text} employing markup language {@code markupLang}.
     *
     * @param text the formatted or unformatted text that the created {@code FormattedText} instance's formatting data
     *             belongs to
     * @param markupLang the type of markup language employed by the instance of {@code FormattedText} to create
     */
    public MarkedUpText(String text, Class<? extends MarkupLanguage> markupLang) {
        super(text);
        this.markupLanguages.add(markupLang);
    }

    /**
     * Creates an instance of {@code FormattedText} with text {@code text} employing markup language {@code markupLang}.
     *
     * @param text the formatted or unformatted text that the created {@code FormattedText} instance's formatting data
     *             belongs to
     * @param markupLangs the types of markup languages employed by the instance of {@code FormattedText} to create
     */
    public MarkedUpText(String text, Set<Class<? extends MarkupLanguage>> markupLangs) {
        super(text);
        this.markupLanguages.addAll(markupLangs);
    }

    /**
     * Gets the types of markup languages employed by the text of {@code this} {@code MarkedUpText} instance.
     *
     * @return the types of markup language employed by the text of {@code this} {@code MarkedUpText} instance
     */
    public LinkedHashSet<Class<? extends MarkupLanguage>> getMarkupLanguages() {
        return this.markupLanguages;
    }

    /**
     * Creates a {@code MarkedUpText} instance containing the desired substring from {@code this} {@code MarkedUpText}'s
     * text, taking on the same {@link MarkupLanguage}.
     *
     * @param beginIndex the beginning index, inclusive
     * @param endIndex the ending index, exclusive
     *
     * @return a {@code MarkedUpText} object populated with the same language as this one, but with text matching the
     * desired substring of this one's text
     *
     * @throws IndexOutOfBoundsException if the {@code beginIndex} is negative, or {@code endIndex} is larger than the
     * length of {@code this} {@code MarkedUpText}'s text, or {@code beginIndex} is larger than {@code endIndex}
     */
    public MarkedUpText substring(int beginIndex, int endIndex) {
        return new MarkedUpText(this.getText().substring(beginIndex, endIndex), this.markupLanguages);
    }
}