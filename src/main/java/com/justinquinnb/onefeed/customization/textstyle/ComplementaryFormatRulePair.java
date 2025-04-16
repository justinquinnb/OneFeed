package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.textstyle.application.FormatApplicationRule;
import com.justinquinnb.onefeed.customization.textstyle.parsing.FormatParsingRule;

/**
 * A complementary pairing of a {@link FormatParsingRule} and a {@link FormatApplicationRule}. By complementary, these
 * it is mean that the rules are capable of undoing each other.
 */
public class ComplementaryFormatRulePair {
    // TODO document
    /**
     *
     */
    private FormatParsingRule parsingRule;

    /**
     *
     */
    private FormatApplicationRule applicationRule;

    /**
     *
     * @param parsingRule
     * @param applicationRule
     */
    public ComplementaryFormatRulePair(FormatParsingRule parsingRule, FormatApplicationRule applicationRule) {
        // ensure the rules can undo each other before instantiating. otherwise throw an exception?
    }

    /**
     *
     * @return
     */
    public FormatParsingRule getParsingRule() {
        return parsingRule;
    }

    /**
     *
     * @param parsingRule
     */
    public void setParsingRule(FormatParsingRule parsingRule) {
        this.parsingRule = parsingRule;
    }

    /**
     *
     * @return
     */
    public FormatApplicationRule getApplicationRule() {
        return applicationRule;
    }

    /**
     *
     * @param applicationRule
     */
    public void setApplicationRule(
            FormatApplicationRule applicationRule) {
        this.applicationRule = applicationRule;
    }
}