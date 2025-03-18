package com.justinquinnb.onefeed.customization.defaults;

import com.justinquinnb.onefeed.customization.textstyle.TextFormatting;

/**
 * A language of basic text formattings like links and underlines. This class itself can function as a member of the
 * class it represents, such as boldface or italics.
 *
 * @see TextFormatting
 */
public class BasicFormatting extends TextFormatting {
    /**
     * Creates a data-less text formatting member of the {@link BasicFormatting} language with label {@code label}.
     *
     * @param label the name of the text formatting that the instance should represent
     */
    public BasicFormatting(String label) {
        super(label);
    }
}