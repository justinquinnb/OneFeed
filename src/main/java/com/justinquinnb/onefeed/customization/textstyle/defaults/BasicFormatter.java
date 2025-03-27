package com.justinquinnb.onefeed.customization.textstyle.defaults;

import com.justinquinnb.onefeed.customization.textstyle.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * The default implementation of a {@link TextFormatter}, marking up a {@code String} according to its desired
 * formattings and the set of rules that specify how to apply them.
 */
public class BasicFormatter implements TextFormatter {
    private static final Logger logger = LoggerFactory.getLogger(BasicFormatter.class);

    @Override
    public <T extends TextFormatting> String applyFormatting(FormattingKit<T> kit, FormattingRuleset<T> formattingRules) {
        logger.debug("Applying formatting to raw text: {}", kit.getUnformattedText());

        // Make a copy of the kit's unformatted text since we'll be mutating it with each instruction read
        String workingText = kit.getUnformattedText();

        // This method provides a deep copy, which is imperative as we'll be mutating it quite a bit during processing
        List<FormattingInstruction<T>> instructionsCopy = kit.getInstructions();

        // For each instruction in the kit, apply the process specified by formattingRules to the substring calling for
        // the desired formatting
        String ogSubstr, newSubstr;
        SubstringLocation location;
        T formatting;
        Function<FormattingMarkedText<T>, String> rule;
        for (FormattingInstruction<T> instruction : instructionsCopy) {
            // Set this iteration's working context
            location = instruction.getLocation();
            formatting = instruction.getFormatting();
            ogSubstr = workingText.substring(location.getStart(), location.getEnd() + 1);
            logger.trace("Following instruction: {} applied to \"{}\"", formatting, ogSubstr);

            // Apply the rule to the substring, if one exists in the ruleset for the current instruction's formatting
            // type
            try {
                logger.trace("Rule found for desired formatting in provided ruleset");
                rule = formattingRules.getRuleFor(formatting.getClass());

                // Apply the formatting rule specified by the TextFormatting in the instruction, providing that
                // TextFormatting instance as the (possibly) necessary context
                newSubstr = rule.apply(new FormattingMarkedText<T>(ogSubstr, formatting));
                logger.trace("Formatting applied to produce: {}", newSubstr);

                // Insert the now-formatted String back into the working text at its original location (equal to the
                // instruction's start location)
                workingText = workingText.substring(0, location.getStart()) + // Original prefix
                        newSubstr + // Newly marked-up text
                        workingText.substring(location.getEnd() + 1); // Original suffix
                logger.trace("Working text now: {}", workingText);

                // Remove the instruction from the copy (reduces the number of things to shift below)
                instructionsCopy.remove(instruction);

                // Make the appropriate shifts to the workspace, now that the length of the text has changed
                logger.trace("Shifting workspace to account for replacement: \"{}\" -> \"{}\" (shift of {})",
                        ogSubstr, newSubstr, newSubstr.length() - ogSubstr.length());
                FormattingKit.shiftWorkspaceForReplacement(instructionsCopy, ogSubstr, newSubstr, location.getStart());
            } catch (NoSuchElementException nSEE) {
                logger.debug("No markup function for {}-type formatting found in the provided FormattingRuleset",
                        formatting.getClass().getName());

                // Remove the instruction as it's now been "used"
                instructionsCopy.remove(instruction);
            }
            logger.trace("Instruction completed");
        }

        logger.debug("Application of text formatting completed");
        return workingText;
    }
}
