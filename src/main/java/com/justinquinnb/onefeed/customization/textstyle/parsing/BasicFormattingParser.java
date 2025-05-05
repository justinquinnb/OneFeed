package com.justinquinnb.onefeed.customization.textstyle.parsing;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingTree;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.MarkupLangMismatchException;
import com.justinquinnb.onefeed.customization.textstyle.markup.MarkupLanguage;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The default implementation of a {@link TextFormattingParser}, associating substrings with {@link TextFormatting}s.
 */
public final class BasicFormattingParser implements TextFormattingParser {
    private static final Logger logger = LoggerFactory.getLogger(BasicFormattingParser.class);

    @Override
    public FormattingTree parseFormattings(MarkedUpText markedUpText, FormatParsingRuleset rules) {
        // Search for matches of each rule in the markedUpText and execute its associated method for every match to
        // generate all the FMT objects
        String workingText = markedUpText.getText();
        TreeSet<FormattingInstruction.ReadOrderInstruction> readOrderFmts = new TreeSet<>(Collections.reverseOrder());
        for (FormatParsingRule rule : rules) {
            logger.trace("Searching for substrings matching pattern: {}", rule.getRegex().toString());
            Pattern regex = rule.getRegex();
            Matcher regexMatcher = regex.matcher(workingText);

            // If the substring contains matches for the current rule's Regex, apply the rule to each match
            while (regexMatcher.find()) {
                // Get the position of the match
                int matchStartIndex = regexMatcher.start();
                int matchEndOffset = regexMatcher.end();

                // Extract the match and attempt to emulate its intended formatting
                MarkedUpText matchedText = markedUpText.substring(matchStartIndex, matchEndOffset);
                logger.trace("Markup match found: {}", matchedText.getText());

                // If the formatting can't be successfully parsed/extracted, use the fallback approach
                FormattingMarkedText generatedFmt;
                try {
                    generatedFmt = rule.getParserFunction().parse(matchedText);
                } catch (MarkupLangMismatchException | ParseException e) {
                    logger.warn("Could not extract formatting from text: {}", matchedText.getText());
                    generatedFmt = TextFormattingParser.FALLBACK_APPROACH.apply(matchedText);
                }

                logger.trace("Markup match labelled as \"{}\" with markup-stripped text: {}",
                        generatedFmt.getFormatting().getLabel(), generatedFmt.getText());

                // Add the generated FMT to the list
                readOrderFmts.add(new FormattingInstruction.ReadOrderInstruction(generatedFmt, matchStartIndex));

                // Update the working text to reflect the markup removal
                workingText = workingText.substring(0, matchStartIndex + 1) + // Original prefix
                        generatedFmt.getText() + // Now-unformatted text
                        workingText.substring(matchEndOffset); // Original suffix
                logger.trace("Working text now: {}", workingText);

                // TODO make this an displaceAfter(TreeSet, int amount, int after) method (make other methods for above too)
                // Shift all sequent substring positions according to the change in this match's length
                int displacement = generatedFmt.getText().length() - matchedText.getText().length();

                // For all subsequent substring positions in the string, apply the displacement
                for (FormattingInstruction readOrderInstruction : readOrderFmts) {
                    if (!(readOrderInstruction.getStartIndex() > matchStartIndex)) {
                        break;
                    }
                    readOrderInstruction.shiftStartindex(displacement);
                }
            }
        }

        // Now sort the instructions in build order

    }

    /**
     * A single instruction as to how a {@link FormattingTree} should be constructed.
     */
    private static abstract class FormattingInstruction implements Comparable<FormattingInstruction> {
        /**
         * A single instruction as to how a {@link FormattingTree} should be constructed with an order specified by the
         * {@link #startIndex}.
         */
        private static class ReadOrderInstruction extends FormattingInstruction {
            /**
             * Creates a new {@link FormattingInstruction} containing the provided {@code markedText} placed at
             * {@code startIndex} in the larger, markup-less {@code String} that the {@code markedText} belongs in.
             *
             * @param markedText the {@link MarkedUpText} that the generated {@code FormattingInstruction} applies to
             * @param startIndex the index of {@link FormattingMarkedText}'s first character as it would appear in the
             * final markup-stripped version of {@link MarkedUpText}'s raw text
             */
            public ReadOrderInstruction(FormattingMarkedText markedText, int startIndex) {
                super(markedText, startIndex);
            }

            @Override
            public int compareTo(FormattingInstruction o) {
                return this.getStartIndex() - o.getStartIndex();
            }
        }

        /**
         * A single instruction as to how a {@link FormattingTree} should be constructed with an order specified by the
         * {@link #length}.
         */
        private static class BuildOrderInstruction extends FormattingInstruction {
            /**
             * Creates a new {@link FormattingInstruction} containing the provided {@code markedText} placed at
             * {@code startIndex} in the larger, markup-less {@code String} that the {@code markedText} belongs in.
             *
             * @param markedText the {@link MarkedUpText} that the generated {@code FormattingInstruction} applies to
             * @param startIndex the index of {@link FormattingMarkedText}'s first character as it would appear in the
             * final markup-stripped version of {@link MarkedUpText}'s raw text
             */
            public BuildOrderInstruction(FormattingMarkedText markedText, int startIndex) {
                super(markedText, startIndex);
            }

            @Override
            public int compareTo(FormattingInstruction o) {
                return this.getLength() - o.getLength();
            }
        }

        /**
         * The {@link FormattingMarkedText} the instruction applies to.
         */
        private FormattingMarkedText markedText;

        /**
         * The index of {@link FormattingMarkedText}'s first character as it would appear in the final markup-stripped
         * version of {@link MarkedUpText}'s raw text.
         */
        private int startIndex;

        /**
         * The length of the {@link #markedText}'s markup-stripped text.
         */
        private final int length;

        /**
         * Creates a new {@link FormattingInstruction} containing the provided {@code markedText} placed at
         * {@code startIndex} in the larger, markup-less {@code String} that the {@code markedText} belongs in.
         *
         * @param markedText the {@link MarkedUpText} that the generated {@code FormattingInstruction} applies to
         * @param startIndex the index of {@link FormattingMarkedText}'s first character as it would appear in the
         * final markup-stripped version of {@link MarkedUpText}'s raw text
         */
        public FormattingInstruction(FormattingMarkedText markedText, int startIndex) {
            this.markedText = markedText;
            this.startIndex = startIndex;
            this.length = markedText.getText().length();
        }

        /**
         * Gets {@code this} {@code FormattingInstruction}'s {@link #markedText}.
         * @return {@code this} {@code FormattingInstruction}'s {@code markedText}
         */
        public FormattingMarkedText getMarkedText() {
            return this.markedText;
        }

        /**
         * Gets {@code this} {@code FormattingInstruction}'s {@link #startIndex}.
         * @return {@code this} {@code FormattingInstruction}'s {@code startIndex}
         */
        public int getStartIndex() {
            return this.startIndex;
        }

        /**
         * Gets {@code this} {@code FormattingInstruction}'s {@link #length}.
         * @return the length of {@code this} {@code FormattingInstruction}'s {@link #markedText}
         */
        public int getLength() {
            return this.length;
        }

        /**
         * Sets {@code this} {@code FormattingInstruction}'s {@link #startIndex}.
         * @param startIndex the new start index for {@code this} {@code FormattingInstruction} to use
         */
        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }

        /**
         * Shifts {@code this} {@code FormattingInstruction}'s {@link #startIndex} by the provided {@code amount}.
         * @param amount the amount to displace the start index, positive or negative
         */
        public void shiftStartindex(int amount) {
            this.startIndex += amount;
        }

        @Override
        public abstract int compareTo(FormattingInstruction o);
    }
}