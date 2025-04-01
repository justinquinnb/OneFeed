package com.justinquinnb.onefeed.customization.textstyle.parsing;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.formattings.TextFormatting;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A {@link Set} of {@link FormatParsingRule}s in the desired order of application to {@link MarkedUpText}. This type,
 * unlike {@link DefinedFormatParsingRuleset}, is best for in-line, on-the-fly ruleset creation.
 */
public final class QuickFormatParsingRuleset extends FormatParsingRuleset {
    /**
     * The name of {@code this} {@link QuickFormatParsingRuleset}.
     */
    private final String name;

    /**
     * A mapping of {@link TextFormatting} types to the functions used to convert text marked as requiring such
     * formatting into text that actually possesses it in some markup language.
     *
     * @see FormatParsingRule
     */
    private final LinkedHashSet<FormatParsingRule> rules;

    /**
     * Constructs a {@link QuickFormatParsingRuleset} with the provided {@code name} and {@link #rules}, {@code rules}.
     *
     * @param name the name of the {@code QuickFormatParsingRuleset} to create
     * @param rules the {@link FormatParsingRule}s to include in {@code this} {@code QuickFormatParsingRuleset}
     */
    public QuickFormatParsingRuleset(String name, LinkedHashSet<FormatParsingRule> rules) {
        this.name = name;
        this.rules = rules;
    }

    /**
     * Gets the name of {@code this} ruleset.
     *
     * @return the name of {@code this} {@code QuickFormatParsingRuleset} instance
     */
    public final String getName() {
        return name;
    }

    public LinkedHashSet<FormatParsingRule> getRules() {
        return new LinkedHashSet<>(this.rules); // TODO make this actually a deep copy(?)
    }
}
