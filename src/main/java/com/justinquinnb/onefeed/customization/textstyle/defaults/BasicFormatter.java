package com.justinquinnb.onefeed.customization.textstyle.defaults;

import com.justinquinnb.onefeed.customization.textstyle.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The default implementation of a {@link TextFormatter}, marking up a {@code String} according to its desired
 * formattings and the set of rules that specify how to apply them.
 */
public class BasicFormatter implements TextFormatter {
    private static final Logger logger = LoggerFactory.getLogger(BasicFormatter.class);

    // TODO finish implementing
    @Override
    public <T extends TextFormatting> String applyFormatting(FormattingKit<T> kit, FormattingRuleset<T> formattingRules) {
        String workingText = kit.getUnformattedText();

        // This method provides a deep copy, which is imperative as we'll be mutating it quite a bit during processing
        List<FormattingInstruction<T>> instructions = kit.getInstructions();

        /* For each instruction in the kit, apply the process specified by formattingRules to the substring calling for
        the desired formatting
         */
        for (FormattingInstruction<T> instruction : instructions) {
            // Get the substring of the working text specified by the instruction


            /* Apply the formatting rule specified by the TextFormatting in the instruction, providing that
               TextFormatting instance as the (possibly) necessary context
             */

            /* Insert the now-formatted String back into the working text at its original location (equal to the
               instruction's start location)
             */

            // Make the appropriate shifts to the workspace, now that the length of the text has changed
        }

        return workingText;
    }
}
