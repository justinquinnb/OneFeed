package com.justinquinnb.onefeed.customization.textstyle.defaults;

import com.justinquinnb.onefeed.customization.textstyle.TextFormatting;

/**
 * A language of basic text formattings like links and underlines.
 *
 * @see TextFormatting
 */
public abstract sealed class BasicFormatting extends TextFormatting
        permits BoldFormat, ItalicFormat, LinkFormat, StrikethroughFormat, UnderlineFormat {
    /**
     * Creates a data-less text formatting member of the {@link BasicFormatting} language with label {@code label}.
     *
     * @param label the name of the text formatting that the instance should represent
     */
    protected BasicFormatting(String label) {
        super(label);
    }
}