package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.customization.textstyle.indexing.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.indexing.TextMarkupLanguage;

/**
 * A type capable of generating a {@link TextFormattingIndex <U>} using language {@link U} for text marked up in
 * {@link T}.
 *
 * @param <T> the {@link TextFormattingLanguage} used to mark formatted elements in the {@code TextFormattingIndex}
 *           required to {@link #applyFormattings}
 * @param <U> the ({@link TextMarkupLanguage} the text produced by {@code applyFormatting} will be in
 */
public interface TextStylizer<T extends TextFormattingLanguage, U extends TextMarkupLanguage>{
    /**
     *
     * @param index
     * @param unmarkedText
     * @return
     */
    public MarkedUpText<U> applyFormattings(TextFormattingIndex<T> index, String unmarkedText);

    public TextMarkupLanguage getLanguage()
}