package com.justinquinnb.onefeed.customization.textstyle.defaults;

import com.justinquinnb.onefeed.customization.textstyle.*;
import com.justinquinnb.onefeed.customization.textstyle.indexing.FormatIndexingRule;
import com.justinquinnb.onefeed.customization.textstyle.indexing.FormatIndexingRuleset;
import com.justinquinnb.onefeed.customization.textstyle.indexing.TextFormattingIndexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * The default implementation of a {@link TextFormattingIndexer}, associating substrings with {@link TextFormatting}s.
 */
public class BasicFormattingIndexer implements TextFormattingIndexer {
    private static final Logger logger = LoggerFactory.getLogger(BasicFormattingIndexer.class);

    @Override
    public <T extends TextFormatting> FormattingKit<T> buildKit(
            String markedUpText, FormatIndexingRuleset<T> formattingRules) {
        logger.debug("Building FormattingKit for text: {}", markedUpText);

        // For clarity, the text actively being worked on, which may or may not contain any markup at any given time,
        // shall be referred to as the working text to distinguish it from the original, untouched markedUpText
        String workingText = markedUpText;

        // Prepare the new FormattingInstruction ArrayList
        ArrayList<FormattingInstruction<T>> instructions =
                new ArrayList<FormattingInstruction<T>>();

        // Search for matches of each rule in the markedUpText and execute its associated method for every match,
        // building instructions with the results
        SubstringLocation locationInFullStr; // The location of the generated replacement text in the markedUpText
        int matchStartIndex, matchEndOffset, replacementTextLength;
        FormattingMarkedText<T> generatedFmt;
        T generatedFormatting;

        Matcher regexMatcher;
        for (FormatIndexingRule<T> rule : formattingRules) {
            logger.trace("Searching for substrings matching pattern: {}", rule.getRegex().toString());

            // Find matches in markedUpText for the current rule's Regex
            regexMatcher = rule.getRegex().matcher(workingText);

            // If the substring contains matches for the current rule's Regex, apply each rule to the match
            while (regexMatcher.find()) {
                // Get the position of the match
                matchStartIndex = regexMatcher.start();
                matchEndOffset = regexMatcher.end();

                // Extract the match and apply it according to the rule, saving the resultant unformatted text and
                // TextFormatting instance (bundled as FormattingMarkedText)
                generatedFmt = rule.apply(workingText.substring(matchStartIndex, matchEndOffset));
                logger.trace("Match found: {}", workingText.substring(matchStartIndex, matchEndOffset));
                logger.trace("Replaced with: \"{}\"", generatedFmt.getUnformattedText());

                // Replace the match with the FormattingMarkedText's unformatted text
                workingText = workingText.substring(0, matchStartIndex + 1) + // Original prefix
                        generatedFmt.getUnformattedText() + // Now-unformatted text
                        workingText.substring(matchEndOffset); // Original suffix
                logger.trace("Working text now: {}", workingText);

                // Note the location of the now-unformatted text in the context of the larger markedUpString
                replacementTextLength = generatedFmt.getUnformattedText().length();
                locationInFullStr = new SubstringLocation(matchStartIndex, (matchStartIndex + replacementTextLength) - 1);

                // Add this location and the TextFormatting generated by applying the rule to the list of instructions
                generatedFormatting = generatedFmt.getFormatting();
                logger.trace("Added instruction \"{} between {} and {}\" to FormattingKit",
                        generatedFormatting.toString(), locationInFullStr.getStart(), locationInFullStr.getEnd());
                instructions.add(new FormattingInstruction<T>(locationInFullStr, generatedFormatting));
            }

            logger.trace("All substrings matching pattern \"{}\" have been found", rule.getRegex().toString());
        }

        logger.debug("FormattingKit completed");
        return new FormattingKit<T>(workingText, instructions);
    }
}