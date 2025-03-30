package com.justinquinnb.onefeed.customization.textstyle.defaults;

import com.justinquinnb.onefeed.customization.textstyle.application.ImmutableFormattingRuleset;

/**
 * A ruleset specifying how to interpret {@link BasicFormatting}s to apply HTML to text.
 */
public class BasicHtmlRuleset extends ImmutableFormattingRuleset {
    @Override
    protected void initializeMasterRules() {
        addMasterRule(ruleOf(BoldFormat.class, ))
    }
}
