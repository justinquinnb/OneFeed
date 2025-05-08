package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.customization.textstyle.*;
import com.justinquinnb.onefeed.customization.textstyle.markup.MarkupLanguage;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * The default implementation of a {@link TextFormattingApplier}, marking up a {@code String} according to its desired
 * formattings and the set of rules that specify how to apply them.
 */
public final class BasicFormattingApplier implements TextFormattingApplier {
    private static final Logger logger = LoggerFactory.getLogger(BasicFormattingApplier.class);

    // TODO: Be sure to reevaluate employed languages in the final MarkedUpText by checking the results of each
    // applier invocation (specifically ExtdMd -> Md)
    @Override
    public MarkedUpText applyFormattings(FormattingKit kit, FormatApplicationRuleset formattingRules) {
        logger.debug("Applying formatting to raw text: {}", kit.getUnformattedText());

        // Make a copy of the kit's unformatted text since we'll be mutating it with each instruction read
        String workingText = kit.getUnformattedText();

        // This method provides a deep copy, which is imperative as we'll be mutating it quite a bit during processing
        List<FormattingInstruction> instructionsCopy = kit.getInstructions();

        // For each instruction in the kit, apply the process specified by formattingRules to the substring calling for
        // the desired formatting. Add each output language to the set below.
        Set<Class<? extends MarkupLanguage>> outputLangs = new LinkedHashSet<>();
        for (FormattingInstruction instruction : instructionsCopy) {
            // Set this iteration's working context
            SubstringLocation location = instruction.getLocation();
            TextFormatting formatting = instruction.getFormatting();
            String ogSubstr = workingText.substring(location.getFirst(), location.getSecond() + 1);
            logger.trace("Following instruction: {} applied to \"{}\"", formatting, ogSubstr);

            // Apply the rule to the substring, if one exists in the ruleset for the current instruction's formatting
            // type
            logger.trace("Rule found for desired formatting in provided ruleset");


            // Apply the formatting rule specified by the TextFormatting in the instruction, providing that
            // TextFormatting instance as the (possibly) necessary context
            MarkedUpText generatedMut;
            FormattingMarkedText generatedFmt = new FormattingMarkedText(ogSubstr, formatting);
            try {
                FormattingApplierFunction rule = formattingRules.getApplierFor(formatting.getClass());
                generatedMut = rule.apply(generatedFmt);
            } catch (FormattingMismatchException e) {
                logger.warn("Could not apply desired formatting to \"{}\". Using fallback approach.", generatedFmt.getText());
                generatedMut = TextFormattingApplier.FALLBACK_APPROACH.apply(generatedFmt);
            } catch (NoSuchElementException e) {
                logger.warn("Could not find rule for \"{}\". Using fallback approach.", generatedFmt.getText());
                generatedMut = TextFormattingApplier.FALLBACK_APPROACH.apply(generatedFmt);
            }

            String newSubstr = generatedMut.getText();
            logger.trace("Formatting applied to produce: {}", newSubstr);

            // Add the language of the generated text to the list
            outputLangs.addAll(generatedMut.getMarkupLanguages());

            // Insert the now-formatted String back into the working text at its original location (equal to the
            // instruction's start location)
            workingText = workingText.substring(0, location.getFirst()) + // Original prefix
                    newSubstr + // Newly marked-up text
                    workingText.substring(location.getSecond() + 1); // Original suffix
            logger.trace("Working text now: {}", workingText);

            // Remove the instruction from the copy (reduces the number of things to shift below)
            instructionsCopy.remove(instruction);

            // Make the appropriate shifts to the workspace, now that the length of the text has changed
            int applicationDisplacement = newSubstr.length() - ogSubstr.length();
            int netDisplacement = applicationDisplacement + instruction.getParsingDisplacement();

            logger.trace("Shifting workspace to account for replacement: \"{}\" -> \"{}\" (shift of {}, " +
                            "net from parsing {})", ogSubstr, newSubstr, applicationDisplacement, netDisplacement);

            FormattingKit.shiftWorkspaceForReplacement(instructionsCopy, ogSubstr, newSubstr, location.getFirst());
        }

        logger.debug("Application of text formatting completed");
        return new MarkedUpText(workingText, outputLangs);
    }
}