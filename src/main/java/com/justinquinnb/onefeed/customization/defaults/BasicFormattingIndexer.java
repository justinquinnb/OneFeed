package com.justinquinnb.onefeed.customization.defaults;

import com.justinquinnb.onefeed.customization.textstyle.FormattingKit;
import com.justinquinnb.onefeed.customization.textstyle.FormattingRuleset;
import com.justinquinnb.onefeed.customization.textstyle.TextFormattingIndexer;

/**
 * The default implementation of a {@link TextFormattingIndexer}.
 */
public class BasicFormattingIndexer implements TextFormattingIndexer<BasicFormatting> {
    // TODO implement
    @Override
    public FormattingKit<BasicFormatting> buildKitFor(String markedUpText, FormattingRuleset<BasicFormatting> formattingRules) {
        return null;
    }
}