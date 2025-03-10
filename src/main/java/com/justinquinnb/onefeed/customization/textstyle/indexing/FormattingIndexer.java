package com.justinquinnb.onefeed.customization.textstyle.indexing;

import com.justinquinnb.onefeed.customization.textstyle.application.TextFormattingIndex;
import com.justinquinnb.onefeed.customization.textstyle.application.TextFormattingLanguage;
import com.justinquinnb.onefeed.customization.textstyle.application.TextStylingInstructions;

/**
 * A type capable of generating a {@link TextFormattingIndex<U>} using language {@link U} for text marked up in
 * {@link T}.
 *
 * @param <T> the ({@link TextMarkupLanguage} used by the {@code String} to generate a
 *           {@code TextFormattingIndex} for
 * @param <U> the {@link TextFormattingLanguage} used to mark formatted elements in the {@code TextFormattingIndex}
 *           produced by {@link #indexStylesOf(MarkedUpText)}
 */
public interface FormattingIndexer <T extends TextMarkupLanguage, U extends TextFormattingLanguage>{
    public TextFormattingIndex<U> indexStylesOf(MarkedUpText<T> text);

    /**
     * Builds a {@link TextFormattingIndex} using language {@link U} for text marked up in {@link T}.
     *
     * @param text a {@link String} of text marked up in {@link TextMarkupLanguage} {@code U} linked to the language it
     *             uses
     * @return a {@code TextFormattingIndex} pairing substrings of the {@code text} with their respective
     * {@code TextFormatting}s included in the language {@code U}
     */
    public TextStylingInstructions<U> createStylingInstructionsFor(MarkedUpText<T> text);
}