package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.textstyle.application.FormatApplicationRule;
import com.justinquinnb.onefeed.customization.textstyle.markup.MarkupLanguage;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;
import com.justinquinnb.onefeed.customization.textstyle.parsing.FormatParsingRule;

/**
 * A complementary pairing of a {@link FormatParsingRule} and a {@link FormatApplicationRule}. By complementary, these
 * it is mean that the rules are capable of undoing each other.
 */
public class ComplementaryFormatRulePair {
    /**
     * A {@code FormatParsingRule} capable of extracting {@link FormattingMarkedText} instances from any
     * {@link MarkedUpText} the {@link #applicationRule} produces such that the extracted {@code FormattingMarkedText}
     * yields the same {@code MarkedUpText} when the {@code applicationRule} is applied to it.
     */
    private FormatParsingRule parsingRule;

    /**
     * A {@code FormatApplicationRule} capable of producing {@link MarkedUpText} instances from any
     * {@link FormattingMarkedText} the {@link #parsingRule} extracts such that the produced {@code MarkedUpText}
     * yields the same {@code FormattingMarkedText} when the {@code parsingRule} is applied to it.
     */
    private FormatApplicationRule applicationRule;

    /**
     * Pairs a {@link FormatParsingRule} and {@link FormatApplicationRule} capable of undoing each other's actions
     * together, as should be the case for markup operations performed on the same type of {@link TextFormatting}
     * within the same type of {@link MarkupLanguage}.
     *
     * @param parsingRule a {@code FormatParsingRule} capable of undoing the operation specified by the
     * {@code applicationRule} when the {@code TextFormatting} and {@code MarkupLanguage} context they are used within
     * remains the same
     * @param applicationRule a {@code FormatApplicationRule} capable of undoing the operation specified by the
     * {@code pparsingRule} when the {@code TextFormatting} and {@code MarkupLanguage} context they are used within
     * remains the same
     */
    public ComplementaryFormatRulePair(FormatParsingRule parsingRule, FormatApplicationRule applicationRule) {
        this.parsingRule = parsingRule;
        this.applicationRule = applicationRule;
    }

    /**
     * Gets {@code this} {@link ComplementaryFormatRulePair}'s {@link #parsingRule}.
     *
     * @return {@code this} {@link ComplementaryFormatRulePair}'s {@link #parsingRule}
     */
    public FormatParsingRule getParsingRule() {
        return this.parsingRule;
    }

    /**
     * Sets the {@link #parsingRule} of {@code this} {@link ComplementaryFormatRulePair}.
     *
     * @param parsingRule the new {@link FormatParsingRule} for this rule pair to contain
     */
    public void setParsingRule(FormatParsingRule parsingRule) {
        this.parsingRule = parsingRule;
    }

    /**
     * Gets {@code this} {@link ComplementaryFormatRulePair}'s {@link #applicationRule}.
     *
     * @return {@code this} {@link ComplementaryFormatRulePair}'s {@link #applicationRule}
     */
    public FormatApplicationRule getApplicationRule() {
        return this.applicationRule;
    }

    /**
     * Sets the {@link #applicationRule} of {@code this} {@link ComplementaryFormatRulePair}.
     *
     * @param applicationRule the new {@link FormatApplicationRule} for this rule pair to contain
     */
    public void setApplicationRule(
            FormatApplicationRule applicationRule) {
        this.applicationRule = applicationRule;
    }
}