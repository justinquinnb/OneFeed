package com.justinquinnb.onefeed.customization.textstyle.indexing;

/**
 * Plain text marked up using the specified {@link TextMarkupLanguage} {@link T}.
 *
 * @param <T> the {@code TextMarkupLanguage} used in the {@link #text}
 */
public class MarkedUpText<T extends TextMarkupLanguage> {
    /**
     * The plain text using the {@link TextMarkupLanguage} specified by {@link T}
     */
    private final String text;

    /**
     * Creates an instance of {@link MarkedUpText}, with the language specified by {@link T} and the marked up text
     * provided by {@code text}.
     *
     * @param text the text marked up using the {@link TextMarkupLanguage} {@code T}.
     */
    public MarkedUpText(String text) {
        this.text = text;
    }

    /**
     * Gets the marked up text.
     *
     * @return the text marked up in the {@link TextMarkupLanguage} {@link T}
     */
    public String getMarkedUpText() {
        return this.text;
    }
}