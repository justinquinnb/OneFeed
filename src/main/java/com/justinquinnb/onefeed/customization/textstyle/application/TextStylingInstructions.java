package com.justinquinnb.onefeed.customization.textstyle.application;
// TODO
/**
 *
 * @param <T>
 */
public class TextStylingInstructions<T extends TextFormattingLanguage> {
    /**
     *
     */
    private TextFormattingIndex<T> formattingIndex;

    /**
     *
     */
    private String rawText;

    /**
     *
     * @param formattingIndex
     * @param rawText
     */
    public TextStylingInstructions(TextFormattingIndex<T> formattingIndex, String rawText) {
        this.formattingIndex = formattingIndex;
        this.rawText = rawText;
    }

    /**
     *
     * @return
     */
    public TextFormattingIndex<T> getFormattingIndex() {
        return this.formattingIndex;
    }

    /**
     *
     * @return
     */
    public String getRawText() {
        return this.rawText;
    }
}