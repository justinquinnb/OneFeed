package com.justinquinnb.onefeed.customization.textstyle;

// TODO

/**
 *
 * @param <T>
 */
public interface TextFormatIndexer<T extends TextFormattingLang> {
    public FormattingKit<T> generateIndex(String markedText, FormattingRuleset<T> formattingRules);
}