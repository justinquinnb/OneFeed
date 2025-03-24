package com.justinquinnb.onefeed.customization.textstyle;

// TODO implement

// TODO consider a MarkedUpText type to ensure safety between Indexers and Formatters
/* Leaving the above in case things change, but opting against this because for the following reasons:
   - In some strange cases, text may be formatting using multiple markup languages that generics' wouldn't allow us to
   easily handle
   - Text formatted in a language that a TextFormattingIndexer cannot understand won't really break anything as much as
   it'll incorrectly interpret markup into gibberish or interpret nothing at all
 */

/**
 * A type capable of marking up text using some markup language using the {@link TextFormatting}s mapped to substrings
 * of text included in a {@link FormattingKit}.
 * @param <T> the language of {@link TextFormatting}s in the {@code FormattingKit} that {@code this}
 * {@link TextFormatter} can interpret
 */
public interface TextFormatter<T extends TextFormatting> {
    /**
     * Marks up the unformatted text included in the {@code formattingKit} using the kit's
     * {@link FormattingInstruction}s.
     *
     * @param kit the bundle of unformatted text and instructions to execute
     * @return the {@code formattingKit}'s text marked up as specified by the kit's instructions
     */
    public String applyFormatting(FormattingKit<T> kit, FormattingRuleset<T> rules);
}