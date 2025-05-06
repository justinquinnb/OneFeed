package com.justinquinnb.onefeed.customization.textstyle.parsing;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.utils.JsonToString;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A {@link Set} of {@link FormatParsingRule}s in the desired order of application to {@link MarkedUpText}.
 */
public abstract sealed class FormatParsingRuleset implements Iterable<FormatParsingRule>
    permits DefinedFormatParsingRuleset, QuickFormatParsingRuleset {
    /**
     * Gets the name of {@code this} {@code FormatParsingRuleset}.
     *
     * @return {@code this} {@code FormatApplicationRuleset}'s name
     */
    public abstract String getName();

    /*
     * Rules are not decomposed here like they are in FormatApplicationRulesets because easy-to-use, consistent
     * ordering is the most important trait of the parsing rules in a ruleset context.
     *
     * Order is relevant here as the parsing out of one formatting type can affect later parsing procedures.
     */
    /**
     * Gets {@code this} {@code FormatParsingRuleset}'s rules.
     *
     * @return a deep copy of {@code this} {@code FormatParsingRuleset}'s rules
     */
    public abstract LinkedHashSet<FormatParsingRule> getParsingRules();

    /**
     * Provides an iterator for {@code this} {@link FormatParsingRuleset}s rules.
     *
     * @return an iterator for {@code this} {@code FormatParsingRuleset}s rules
     *
     * @see #getParsingRules()
     */
    @Override
    public final Iterator<FormatParsingRule> iterator() {
        return this.getParsingRules().iterator();
    }

    @Override
    public String toString() {
        return JsonToString.of(this);
    }
}