package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.JsonToString;
import com.justinquinnb.onefeed.customization.textstyle.application.TextFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A bundle of unformatted/un-marked-up text and formatting instructions--everything necessary to apply
 * {@link TextFormatting}s to some text via a mark-up process.
 *
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
    private List<FormattingInstruction<T>> instructions;

    /**
     * Creates a {@link FormattingKit} with the provided {@code unformattedText} as the subject and the desired
     * formatting operations, {@code instructions}.
     *
     * @param unformattedText the unformatted ("un-marked-up") text that the {@code instructions} will act on to apply
     *                        formattings
     * @param instructions the {@link FormattingInstruction}s specifying what formatting the {@code unformattedFormat}
     *                     should receive
     */
    public FormattingKit(String unformattedText, FormattingInstruction<T>[] instructions) {
        this.unformattedText = unformattedText;
        this.instructions = Arrays.asList(instructions);
    }

    /**
     * Creates a {@link FormattingKit} with the provided {@code unformattedText} as the subject and the desired
     * formatting operations, {@code instructions}.
     *
     * @param unformattedText the unformatted ("un-marked-up") text that the {@code instructions} will act on to apply
     *                        formattings
     * @param instructions the {@link FormattingInstruction}s specifying what formatting the {@code unformattedFormat}
     *                     should receive
     */
    public FormattingKit(String unformattedText, List<FormattingInstruction<T>> instructions) {
        this.unformattedText = unformattedText;
        this.instructions = instructions;
    }

    // GETTERS/SETTERS
    /**
     * Gets {@code this} {@link FormattingKit}'s {@link #unformattedText}.
     *
     * @return the unformatted text that {@code this} {@code FormattingKit}'s {@link #instructions} are to format/mark
     * up
     */
    public String getUnformattedText() {
        return unformattedText;
    }

    /**
     * Gets a deep copy of {@code this} {@link FormattingKit}'s {@link #instructions}.
     * <br><br>
     * The most frequent use of this method should be by {@link TextFormatter}s consuming and manipulating the
     * {@link FormattingInstruction}s, thus the deep copy behavior. If the exact {@link List} instance is desired,
     * see {@link #getInstructionInstance()} instead.
     *
     * @return a deep copy of the {@link List} instance containing {@code this} {@code FormattingKit}'s
     * {@code instructions}
     */
    public List<FormattingInstruction<T>> getInstructions() {
        return new ArrayList<>(this.instructions);
    }

    /**
     * Gets {@code this} {@link FormattingKit}'s {@link #instructions}.
     * <br><br>
     * The most frequent use of {@code this} {@code FormattingKit}'s {@code instructions} is anticipated to be
     * {@link TextFormatter}s, which must mutate the instructions as they are read. Thus, limit use of this method to
     * other circumstances where possible mutation of {@code this} {@code FormattingKit}'s {@code instructions} is
     * acceptable.
     *
     * @return the {@link List} instance containing {@code this} {@code FormattingKit}'s {@code instructions}
     */
    public List<FormattingInstruction<T>> getInstructionInstance() {
        return this.instructions;
    }

    /**
     * Replaces all of {@code this} {@link FormattingKit}'s existing {@link #instructions} with those passed as
     * {@code instructions}.
     * <br><br>
     * The use of this method is discouraged in most applications as {@link FormattingInstruction}s only make sense in
     * the context of the {@code unformattedText} they were originally bound to. This method's inclusion is attributed
     * to the anticipation that it may be useful as an unforeseen means of customization.
     *
     * @param instructions the new {@code FormattingInstructions} to include in {@code this} {@code FormattingKit}
     */
    public void overrideInstructions(FormattingInstruction<T>[] instructions) {
        this.instructions = Arrays.asList(instructions);
    }

    /**
     * Replaces all of {@code this} {@link FormattingKit}'s existing {@link #instructions} with those passed as
     * {@code instructions}.
     * <br><br>
     * The use of this method is discouraged in most applications as {@link FormattingInstruction}s only make sense in
     * the context of the {@code unformattedText} they were originally bound to. This method's inclusion is attributed
     * to the anticipation that it may be useful as an unforeseen means of customization.
     *
     * @param instructions the new {@code FormattingInstructions} to include in {@code this} {@code FormattingKit}
     */
    public void overrideInstructions(List<FormattingInstruction<T>> instructions) {
        this.instructions = instructions;
    }

    /**
     * Replaces {@code this} {@link FormattingKit}'s existing {@link #unformattedText} with what's passed as
     * {@code unformattedText}.
     * <br><br>
     * The use of this method is discouraged in most applications as {@link FormattingInstruction}s only make sense in
     * the context of the {@code unformattedText} they were originally bound to. This method's inclusion is attributed
     * to the anticipation that it may be useful as an unforeseen means of customization.
     *
     * @param unformattedText the new, unformatted text to include in {@code this} {@code FormattingKit}
     */
    public void overrideText(String unformattedText) {
        this.unformattedText = unformattedText;
    }

    // MASS INSTRUCTION MANIPULATION
    /**
     * Adjusts a list of {@link FormattingInstruction}s' {@link SubstringLocation}s to account for the insertion or
     * deletion of a substring in the string the instructions are for.
     *
     * @param instructions the {@link List} of {@link FormattingInstruction}s whose workspace to shift
     * @param amount how many characters were added (+) or removed (-)
     * @param origin the index of where the insertion or removal began in the string the instructions are for
     *
     * @throws IllegalArgumentException if {@code origin} is negative
     */
    public static <V extends TextFormatting> void shiftWorkspace(
            List<FormattingInstruction<V>> instructions, int amount, int origin)
            throws IllegalArgumentException {
        // Insertion or deletion can't occur before the first index of a String, so prevent the implication of such
        if (origin < 0) {
            throw new IllegalArgumentException("String operations cannot occur before index 0");
        }

        // Determine the inflection point
        /* For substring deletion, the inflection point will be the index of the character after the substring being
        removed (where the String will collapse). For insertion, the location will be the index before the  origin
        (where the String will expand). */
        int inflectionPoint = (amount < 0) ? (origin + (-1 * amount)) : origin;

        // For each instruction, get the associated substring location and shift whichever indices come on or after the
        // inflection point
        SubstringLocation observedLocation;
        for (FormattingInstruction<V> instruction : instructions) {
            observedLocation = instruction.getLocation();

            // Only shift indices on or after the inflection point
            if (observedLocation.getStart() >= inflectionPoint) {
                observedLocation.shiftStart(amount);
            }
            if (observedLocation.getEnd() >= inflectionPoint) {
                observedLocation.shiftEnd(amount);
            }
        }
    }

    // Just some alternatives to improve code readability
    /**
     * Adjusts a list of {@link FormattingInstruction}s' {@link SubstringLocation}s to account for the insertion of
     * substring {@code substr} at index {@code at}
     *
     * @param instructions the {@link List} of {@link FormattingInstruction}s whose workspace to shift
     * @param substr the word whose insertion at location {@code at} to account for
     * @param at the index the insertion has occurred at
     */
    public static <V extends TextFormatting> void shiftWorkspaceForInsertion(
            List<FormattingInstruction<V>> instructions, String substr, int at) {
        shiftWorkspace(instructions, substr.length(), at);
    }

    /**
     * Adjusts a list of {@link FormattingInstruction}s' {@link SubstringLocation}s to account for the deletion of
     * substring {@code substr} that started at index {@code at}
     *
     * @param instructions the {@link List} of {@link FormattingInstruction}s whose workspace to shift
     * @param substr the word whose deletion at location {@code at} to account for
     * @param at the index the deletion has occurred at
     */
    public static <V extends TextFormatting> void shiftWorkspaceForDeletion(
            List<FormattingInstruction<V>> instructions, String substr, int at) {
        shiftWorkspace(instructions, (substr.length() * -1), at);
    }

    /**
     * Adjusts a list of {@link FormattingInstruction}s' {@link SubstringLocation}s to account for the replacement of
     * {@code String} {@code oldStr} with {@code String} {@code newStr} at location {@code at} in the complete
     * {@code String}.
     *
     * @param instructions the {@link List} of {@link FormattingInstruction}s whose workspace to shift
     * @param oldStr the original {@code String} that started at index {@code at}
     * @param newStr the new {@code String} starting at {@code at}
     * @param at the index the replacement has occurred at
     */
    public static <V extends TextFormatting> void shiftWorkspaceForReplacement(
            List<FormattingInstruction<V>> instructions, String oldStr, String newStr, int at) {
        shiftWorkspace(instructions, newStr.length() - oldStr.length(), at);
    }

    @Override
    public String toString() {
        return JsonToString.of(this);
    }
}