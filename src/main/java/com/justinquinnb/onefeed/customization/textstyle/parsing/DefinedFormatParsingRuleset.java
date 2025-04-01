package com.justinquinnb.onefeed.customization.textstyle.parsing;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * A {@link Set} of {@link FormatParsingRule}s in the desired order of application to {@link MarkedUpText}. This type,
 * unlike {@link QuickFormatParsingRuleset}, is best for defining rulesets in a class.
 */
public abstract sealed class DefinedFormatParsingRuleset extends FormatParsingRuleset
        permits ImmutableFormatParsingRuleset, MutableFormatParsingRuleset {

    protected DefinedFormatParsingRuleset() {
        initializeMasterRules();
    }

    /**
     * The pre-defined rules that serve as {@code this} ruleset's master copy.
     */
    protected static final LinkedHashSet<FormatParsingRule> MASTER_RULES = new LinkedHashSet<>();

    /**
     * Initialize the rules of {@code this} ruleset.
     *
     * @implNote This method is called to make the desired modifications necessary to define the class' master ruleset.
     * For {@link ImmutableFormatParsingRuleset}s, the modifications made are immutable. For its mutable
     * counterpart, {@link MutableFormatParsingRuleset}, it serves as the base ruleset from which changes can be
     * made per instance through the class' constructor or getters/setters.
     * <br><br>
     * It is strongly encouraged to limit this method's body to the base ruleset manipulation functions:
     * {@link #addMasterRule}, {@link #addMasterRules}, {@link #removeMasterRule}, and {@link #removeMasterRules}.
     *
     * @see ImmutableFormatParsingRuleset
     * @see MutableFormatParsingRuleset
     */
    protected abstract void initializeMasterRules();

    /**
     * Adds the provided {@code FormatParsingRule} to {@code this} type's {@link #MASTER_RULES}.
     *
     * @param rule the {@link FormatParsingRule} to add to the master ruleset
     */
    protected static void addMasterRule(FormatParsingRule rule) {
        MASTER_RULES.add(rule);
    }

    /**
     * Adds the provided {@code rule}s to {@code this} type's {@link #MASTER_RULES}.
     *
     * @param rules the {@link FormatParsingRule}s to add to the master ruleset
     */
    protected static void addMasterRules(Set<FormatParsingRule> rules) {
        rules.forEach(DefinedFormatParsingRuleset::addMasterRule);
    }

    /**
     * Removes the desired {@code rule} from {@code this} type's {@link #MASTER_RULES}.
     *
     * @param rule the {@link FormatParsingRule} to remove from {@code this} type's master ruleset
     */
    protected static void removeMasterRule(FormatParsingRule rule) {
        MASTER_RULES.remove(rule);
    }

    /**
     * Removes the desired {@code rule} from {@code this} type's {@link #MASTER_RULES}.
     *
     * @param rules a set of {@link FormatParsingRule}s to remove from {@code this} type's master ruleset
     */
    protected static void removeMasterRules(Set<FormatParsingRule> rules) {
        rules.forEach(DefinedFormatParsingRuleset::removeMasterRule);
    }

    /**
     * Gets a copy of {@code this} {@code DefinedFormatParsingRuleset}'s {@link #MASTER_RULES}.
     *
     * @return a deep copy of {@code this} {@code DefinedParsingParsingRuleset}'s {@link #MASTER_RULES}.
     */
    public static LinkedHashSet<FormatParsingRule> getMasterRules()
    {
        return new LinkedHashSet<>(MASTER_RULES);
    }

    /**
     * Constructs a {@link FormatParsingRule} with regex {@code regex} and process {@code process}. A 
     * more concise means of constructing rules to add when defining a {@link DefinedFormatParsingRuleset}.
     *
     * @param regex a regex {@link Pattern} specifying what to search for in {@link MarkedUpText}
     * @param process the desired {@code Function} to run on matches to generate {@code FormattingMarkedText}
     * @return a {@code FormatParsingRule} with the desired {@code regex} and {@code process}
     *
     * @see FormatParsingRule
     */
    protected static FormatParsingRule ruleOf(Pattern regex, Function<MarkedUpText, FormattingMarkedText> process) {
        return new FormatParsingRule(regex, process);
    }

    /**
     * Constructs a {@link FormatParsingRule} with regex {@code regex} and process {@code process}. A 
     * more concise means of constructing rules to add when defining a {@link DefinedFormatParsingRuleset}.
     *
     * @param regex a regex {@link Pattern} specifying what to search for in the {@link MarkedUpText}
     * @param process the desired {@code Function} to run on matches to generate {@code FormattingMarkedText}
     * @return a {@code FormatParsingRule} with the desired {@code regex} and {@code process}
     *
     * @see FormatParsingRule
     */
    protected static FormatParsingRule ruleOf(String regex, Function<MarkedUpText, FormattingMarkedText> process) {
        return new FormatParsingRule(Pattern.compile(regex), process);
    }
}
