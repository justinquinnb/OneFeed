package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.formattings.TextFormatting;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;

/**
 * A mapping of {@link TextFormatting}s to the {@link Function}s that consume them and {@link FormattingMarkedText} to
 * produce {@link MarkedUpText}. This type, unlike {@link QuickFormatApplicationRuleset}, is best for defining rulesets
 * in a class.
 */
public abstract sealed class DefinedFormatApplicationRuleset extends FormatApplicationRuleset
    permits ImmutableFormatApplicationRuleset, MutableFormatApplicationRuleset {

    protected DefinedFormatApplicationRuleset() {
        initializeMasterRules();
    }

    /**
     * The pre-defined rules that serve as {@code this} ruleset's master copy.
     */
    protected static final HashMap<Class<? extends TextFormatting>, Function<FormattingMarkedText, MarkedUpText>
            > MASTER_RULES = new HashMap<>();

    /**
     * Initialize the rules of {@code this} ruleset.
     *
     * @implNote This method is called to make the desired modifications necessary to define the class' master ruleset.
     * For {@link ImmutableFormatApplicationRuleset}s, the modifications made are immutable. For its mutable
     * counterpart, {@link MutableFormatApplicationRuleset}, it serves as the base ruleset from which changes can be
     * made per instance through the class' constructor or getters/setters.
     * <br><br>
     * It is strongly encouraged to limit this method's body to the base ruleset manipulation functions:
     * {@link #addMasterRule}, {@link #addMasterRules}, {@link #removeMasterRule}, and {@link #removeMasterRules}.
     *
     * @see ImmutableFormatApplicationRuleset
     * @see MutableFormatApplicationRuleset
     */
    protected abstract void initializeMasterRules();

    /**
     * Adds the provided {@code FormatApplicationRule} to {@code this} type's {@link #MASTER_RULES}.
     *
     * @param rule the {@link FormatApplicationRule} to add to the master ruleset
     */
    protected static void addMasterRule(FormatApplicationRule rule) {
        MASTER_RULES.put(rule.getFormatting(), rule.getProcess());
    }

    /**
     * Adds the provided {@code rule}s to {@code this} type's {@link #MASTER_RULES}.
     *
     * @param rules the {@link FormatApplicationRule}s to add to the master ruleset
     */
    protected static void addMasterRules(Set<FormatApplicationRule> rules) {
        rules.forEach(DefinedFormatApplicationRuleset::addMasterRule);
    }

    /**
     * Removes the rule for the specified {@code formatting} from {@code this} type's {@link #MASTER_RULES}.
     *
     * @param formatting the {@link TextFormatting} of the {@link FormatApplicationRule} to remove from {@code this}
     *                   type's master ruleset
     */
    protected static void removeMasterRule(Class<? extends TextFormatting> formatting) {
        MASTER_RULES.remove(formatting);
    }

    /**
     * Removes the rule for the specified {@code formatting} from {@code this} type's {@link #MASTER_RULES}.
     *
     * @param formattings a set of {@link TextFormatting} types that correspond to the {@link FormatApplicationRule}s to
     *                   remove from {@code this} type's master ruleset
     */
    protected static void removeMasterRules(Set<Class<? extends TextFormatting>> formattings) {
        formattings.forEach(DefinedFormatApplicationRuleset::removeMasterRule);
    }

    /**
     * Gets a copy of {@code this} {@code DefinedFormatApplicationRuleset}'s {@link #MASTER_RULES}.
     *
     * @return a deep copy of {@code this} {@code DefinedFormatApplicationRuleset}'s {@link #MASTER_RULES}.
     */
    public static HashMap<
            Class<? extends TextFormatting>, Function<FormattingMarkedText, MarkedUpText>
            > getMasterRules()
    {
        return new HashMap<>(MASTER_RULES);
    }

    /**
     * Constructs a {@link FormatApplicationRule} with formatting {@code formatting} and process {@code process}. A
     * more concise means of constructing rules to add when defining a {@link DefinedFormatApplicationRuleset}.
     *
     * @param formatting the type of {@link TextFormatting} that the process should be invoked to generate
     * @param process the {@code Function} that can apply the specified type of {@code formatting} to text
     * @return a {@code FormatApplicationRule} with the desired {@code formatting} and {@code process}
     *
     * @see FormatApplicationRule
     */
    protected static FormatApplicationRule ruleOf(
            Class<? extends TextFormatting> formatting, Function<FormattingMarkedText, MarkedUpText> process
    ) {
        return new FormatApplicationRule(formatting, process);
    }
}