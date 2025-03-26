package com.justinquinnb.onefeed.customization.textstyle;

/**
 * A type capable of marking up text using some markup language using the {@link TextFormatting}s mapped to substrings
 * of text included in a {@link FormattingKit}.
 */
public interface TextFormatter {
    /**
     * Marks up the unformatted text included in the {@code formattingKit} using the kit's
     * {@link FormattingInstruction}s.
     *
     * @param kit the bundle of unformatted text and instructions to execute
     * @return the {@code formattingKit}'s text marked up as specified by the kit's instructions
     */
    public <T extends TextFormatting> String applyFormatting(FormattingKit<T> kit, FormattingRuleset<T> rules);
}