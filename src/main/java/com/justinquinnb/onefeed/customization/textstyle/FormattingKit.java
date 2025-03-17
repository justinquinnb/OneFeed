package com.justinquinnb.onefeed.customization.textstyle;

// TODO implement

import java.util.Arrays;

/**
 * Contains everything necessary to apply {@link TextFormatting}s to some text.
 * @param <T> the language of {@code TextFormatting}s employed by {@code this} kit's {@link #instructions}
 */
public class FormattingKit<T extends TextFormatting> {
    /**
     * The unformatted text to format with the provided {@link #instructions}.
     */
    private String unformattedText;

    /**
     * The {@link FormattingInstruction}s outlining exactly how the {@link #unformattedText} should be formatted.
     */
    private FormattingInstruction<T>[] instructions;

    /**
     *
     * @param unformattedText
     * @param instructions
     */
    public FormattingKit(String unformattedText, FormattingInstruction<T>[] instructions) {
        this.unformattedText = unformattedText;
        this.instructions = instructions;
    }

    /*
    public void setInstructions(FormattingInstruction<T>[] instructions) {
        this.instructions = instructions;
    }

    public void addInstructionsToEnd(FormattingInstruction<T>[] instructions) {
        this.instructions =
    }

    public void addInstructionsToStart(FormattingInstruction<T>[] instructions) {
        this.instructions =
    }

    public void addInstructionsAt(FormattingInstruction<T>[] instructions, int index) {
        this.instructions =
    }

    public void addInstructionToEnd(FormattingInstruction<T> instruction) {

    }

    public void addInstructionToStart(FormattingInstruction<T> instruction) {

    }

    public void addInstructionAt(FormattingInstruction<T> instruction, int index) {

    } */

    /**
     *
     * @return
     */
    public String getUnformattedText() {
        return unformattedText;
    }

    /**
     *
     * @return
     */
    public FormattingInstruction<T>[] getInstructions() {
        return Arrays.copyOf(instructions, instructions.length);
    }
}
