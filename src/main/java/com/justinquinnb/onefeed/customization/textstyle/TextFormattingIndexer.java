package com.justinquinnb.onefeed.customization.textstyle;

/**
 * A type capable of translating marked-up text into an index of {@link TextFormatting}s mapped to where they occur
 */
public interface TextFormattingIndexer {
    /**
     * Builds a {@link FormattingKit} for the provided {@code markedUpText} by applying the processes specified by
     * each {@link FormatIndexingRule} in the {@link FormatIndexingRuleset} to each substring matching its regex.
     *
     * @param <T> the language of {@link TextFormatting}s that may be derived from marked-up text
     * @param markedUpText plaintext marked up using some language that the {@code formattingRules} may recognize
     * @param formattingRules the {@code FormatIndexingRuleset} specifying what to do for each type of marked-up substring
     *                        in {@code markedUpText} as it's encountered
     *
     * @return a {@code FormattingKit} pairing a markup-less {@code markedUpText} instance with an index of
     * substring locations mapped to the {@code TextFormatting}(s) that should be applied to them. Should the provided
     * {@code formattingRules} not match any parts of the {@code markedUpText} (as might be when its markup
     * language isn't recognized by the rules), the {@code FormattingKit} will contain {@code markedUpText} as-is with
     * an empty index of formattings.
     */
    public <T extends TextFormatting> FormattingKit<T> buildKitFor(String markedUpText, FormatIndexingRuleset<T> formattingRules);
}