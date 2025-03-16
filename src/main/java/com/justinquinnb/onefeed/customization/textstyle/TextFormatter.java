package com.justinquinnb.onefeed.customization.textstyle;

// TODO
/**
 *
 * @param <T>
 */
public interface TextFormatter<T extends TextFormattingLang>{
    public String applyFormattings(String rawText, FormattingKit<T> formattingKit);
}