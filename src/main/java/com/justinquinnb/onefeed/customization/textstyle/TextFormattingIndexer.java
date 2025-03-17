package com.justinquinnb.onefeed.customization.textstyle;

// TODO implement
// TODO consider a MarkedUpText type to ensure safety between Indexers and Formatters
/**
 * A type capable of translating marked-up text into an index of {@link TextFormatting}s mapped to where they occur
 * @param <T> the language of {@link TextFormatting}s that may be derived from marked-up text
 */
public interface TextFormattingIndexer<T extends TextFormatting> {
    /**
     * Builds a {@link FormattingKit} for the provided {@code markedUpText} by applying the processes specified by
     * each {@link FormattingRule} in the {@link FormattingRuleset} to each substring matching its Regex.
     *
     * @param markedUpText plaintext marked up using some language that the {@code formattingRules} may recognize
     * @param formattingRules the {@code FormattingRuleset} specifying what to do for each type of marked-up substring
     *                        in {@code markedUpText} as it's encountered
     * @return a {@code FormattingKit} pairing a markup-less {@code markedUpText} instance with an index of
     * substring locations mapped to the {@code TextFormatting}(s) that should be applied to them. Should the provided
     * {@code formattingRules} not match any parts of the {@code markedUpText} (as might be when its markup
     * language isn't recognized by the rules), the {@code FormattingKit} will contain {@code markedUpText} as-is with
     * an empty index of formattings.
     */
    public FormattingKit<T> buildKitFor(String markedUpText, FormattingRuleset<T> formattingRules);
}