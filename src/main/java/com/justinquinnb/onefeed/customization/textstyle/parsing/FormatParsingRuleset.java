package com.justinquinnb.onefeed.customization.textstyle.parsing;

import com.justinquinnb.onefeed.JsonToString;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

// TODO add inputLangs[] field?
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

    /**
     * Gets {@code this} {@code FormatParsingRuleset}'s rules.
     *
     * @return a deep copy of {@code this} {@code FormatParsingRuleset}'s rules
     */
    public abstract LinkedHashSet<FormatParsingRule> getRules();

    /**
     * Provides an iterator for {@code this} {@link FormatParsingRuleset}s rules.
     *
     * @return an iterator for {@code this} {@code FormatParsingRuleset}s rules
     *
     * @see #getRules()
     */
    @Override
    public final Iterator<FormatParsingRule> iterator() {
        return this.getRules().iterator();
    }

    @Override
    public String toString() {
        return JsonToString.of(this);
    }
}