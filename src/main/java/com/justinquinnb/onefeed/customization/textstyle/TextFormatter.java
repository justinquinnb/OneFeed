package com.justinquinnb.onefeed.customization.textstyle;

// TODO implement
// TODO consider a MarkedUpText type to ensure safety between Indexers and Formatters
/**
 * A type capable of marking up text using some markup language using the {@link TextFormatting}s mapped to substrings
 * of text included in a {@link FormattingKit}.
 * @param <T> the language of {@link TextFormatting}s in the {@code FormattingKit} that {@code this}
 * {@link TextFormatter} can interpret
 */
public interface TextFormatter<T extends TextFormatting>{
    /**
     * Marks up the {@code rawText} using the provided mapping of substrings to {@link TextFormatting}s included in the
     * {@code instructions}.
     *
     * @param rawText the raw text to mark up in accordance to the {@code instructions}
     * @param instructions an array mapping substrings in {@code rawText} to markup that should be applied to them, as
     *                     represented by {@link TextFormatting}s
     * @return {@code rawText} with the markup specified by the {@code instructions} applied
     */
    public String applyFormattings(String rawText, FormattingInstruction<T>[] instructions);

    /**
     * Marks up the unformatted text included in the {@code formattingKit} using the kit's
     * {@link FormattingInstruction}s.
     *
     * @param formattingKit the bundle of unformatted text and instructions to execute
     *
     * @return the {@code formattingKit}'s text marked up as specified by the kit's instructions
     */
    public default String applyFormattings(FormattingKit<T> formattingKit) {
        return applyFormattings(formattingKit.getUnformattedText(), formattingKit.getInstructions());
    }
}