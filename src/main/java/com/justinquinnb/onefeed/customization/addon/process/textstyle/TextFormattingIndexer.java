package com.justinquinnb.onefeed.customization.addon.process.textstyle;

/**
 *
 */
public abstract class TextFormattingIndexer<T extends FormattingIndexLanguage, U extends TextFormattingRuleSet> {
    private T indexLanguage;
    private U ruleSet;

    /**
     * Gets the {@link FormattingIndexLanguage} used by {@code this} {@link TextFormattingIndexer}.
     *
     * @return the {@code FormattingIndexLanguage} used by {@code this} {@code TextFormattingIndexer}
     */
    public T getIndexLanguage() {
        return this.indexLanguage;
    }

    /**
     * Gets the {@link TextFormattingRuleSet} used by {@code this} {@link TextFormattingIndexer}.
     * @return the {@code TextFormattingRuleSet} used by {@code this} {@code TextFormattingIndexer}
     */
    public U getRuleSet() {
        return this.ruleSet;
    }

    /**
     * Builds a {@link TextFormattingIndex} using the
     *
     * @param plainText
     * @return
     */
    public abstract TextFormattingIndex<T> indexTextFormatting(String plainText);
}