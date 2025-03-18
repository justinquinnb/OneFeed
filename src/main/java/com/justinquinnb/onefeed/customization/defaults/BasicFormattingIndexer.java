package com.justinquinnb.onefeed.customization.defaults;

import com.justinquinnb.onefeed.customization.textstyle.*;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * The default implementation of a {@link TextFormattingIndexer}, associating substrings with {@link TextFormatting}s
 * included in the {@link BasicFormatting} language.
 */
public class BasicFormattingIndexer implements TextFormattingIndexer<BasicFormatting> {
    @Override
    public FormattingKit<BasicFormatting> buildKitFor(
            String markedUpText, FormattingRuleset<BasicFormatting> formattingRules) {

        // For clarity, the text actively being worked on, which may or may not contain any markup at any given time,
        // shall be referred to as the working text to distinguish it from the original, untouched markedUpText
        String workingText = markedUpText;

        // Prepare the new FormattingInstruction ArrayList
        ArrayList<FormattingInstruction<BasicFormatting>> instructions =
                new ArrayList<FormattingInstruction<BasicFormatting>>();

        // Search for matches of each rule in the markedUpText and execute its associated method for every match,
        // building instructions with the results
        SubstringLocation locationInOgStr; // The location of the generated replacement text in the markedUpText
        int matchStartIndex, matchEndOffset, replacementTextLength;
        FormattingMarkedText<BasicFormatting> generatedFmt;
        TextFormatting generatedFormatting;

        Matcher regexMatcher;
        for (FormattingRule<BasicFormatting> rule : formattingRules) {
            // Find matches in markedUpText for the current rule's Regex
            regexMatcher = rule.getRegex().matcher(workingText);

            // If the substring contains matches for the current rule's Regex, apply each rule to the match
            while (regexMatcher.find()) {
                // Get the position of the match
                matchStartIndex = regexMatcher.start();
                matchEndOffset = regexMatcher.end();

                // Extract the match and process it according to the rule, saving the resultant unformatted text and
                // TextFormatting instance (bundled as FormattingMarkedText)
                generatedFmt = rule.process(workingText.substring(matchStartIndex, matchEndOffset));

                // Replace the match with the FormattingMarkedText's unformatted text
                workingText = workingText.substring(0, matchStartIndex + 1) + // Original prefix
                        generatedFmt.getUnformattedText() + // Now-unformatted text
                        workingText.substring(matchEndOffset); // Original suffix

                // Note the location of the now-unformatted text in the context of the larger markedUpString
                replacementTextLength = generatedFmt.getUnformattedText().length();
                locationInOgStr = new SubstringLocation(matchStartIndex, (matchStartIndex + replacementTextLength) - 1);

                // Add this location and the TextFormatting generated by applying the rule to the list of instructions
                generatedFormatting = generatedFmt.getFormatting();
                instructions.add(new FormattingInstruction<BasicFormatting>(locationInOgStr, generatedFormatting));
            }
        }

        return new FormattingKit<BasicFormatting>(workingText, instructions);
    }
}