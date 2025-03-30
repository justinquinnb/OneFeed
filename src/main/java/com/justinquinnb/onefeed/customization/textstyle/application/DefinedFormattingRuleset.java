package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingRule;
import com.justinquinnb.onefeed.customization.textstyle.TextFormatting;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;

/**
 * A mapping of {@link TextFormatting}s to the {@link Function}s that consume them and raw text to produce formatted
 * text. This type, unlike {@link QuickFormattingRuleset}, is best for defining rulesets in a class.
 *
 * @param <T> the language of {@link TextFormatting}s that can/might have rules defined by {@code this}
 * {@code FormattingRuleset}
 */
public abstract sealed class DefinedFormattingRuleset extends FormattingRuleset
    permits ImmutableFormattingRuleset, MutableFormattingRuleset {

    protected DefinedFormattingRuleset() {
        initializeMasterRules();
    }

    /**
     * The pre-defined rules that serve as {@code this} ruleset's master copy.
     */
    protected static final HashMap<
            Class<? extends TextFormatting>, Function<FormattingMarkedText<? extends TextFormatting>, String>
            > MASTER_RULES = new HashMap<>();

    /**
     * Initialize the rules of {@code this} ruleset.
     *
     * @implNote This method is called to make the desired modifications necessary to define the class' master ruleset.
     * For {@link ImmutableFormattingRuleset}s, the modifications made are immutable. For its mutable counterpart,
     * {@link MutableFormattingRuleset}, it serves as the base ruleset from which changes can be made per instance
     * through the class' constructor or getters/setters.
     * <br><br>
     * It is strongly encouraged to limit this method's body to the base ruleset manipulation functions:
     * {@link #addMasterRule}, {@link #addMasterRules}, {@link #removeMasterRule}, and {@link #removeMasterRules}.
     *
     * @see ImmutableFormattingRuleset
     * @see MutableFormattingRuleset
     */
    protected abstract void initializeMasterRules();

    /**
     * Adds the provided {@code rule} to {@code this} type's {@link #MASTER_RULES}.
     *
     * @param rule the {@link FormattingRule} to add to the master ruleset
     */
    protected static <T extends TextFormatting> void addMasterRule(Class<T> formatting, Function<FormattingMarkedText<T>, String> process) {
        MASTER_RULES.put(formatting, process);
    }

    /**
     * Adds the provided {@code rule}s to {@code this} type's {@link #MASTER_RULES}.
     *
     * @param rules the {@link FormattingRule}s to add to the master ruleset
     */
    protected static void addMasterRules(Set<FormattingRule<? extends TextFormatting>> rules) {
        rules.forEach(DefinedFormattingRuleset::addMasterRule);
    }

    /**
     * Removes the rule for the specified {@code formatting} from {@code this} type's {@link #MASTER_RULES}
     *
     * @param formatting the {@link TextFormatting} of the {@link FormattingRule} to remove from {@code this} type's
     *                   master ruleset
     */
    protected static void removeMasterRule(Class<? extends TextFormatting> formatting) {
        MASTER_RULES.remove(formatting);
    }

    /**
     * Removes the rule for the specified {@code formatting} from {@code this} type's {@link #MASTER_RULES}
     *
     * @param formattings a set of {@link TextFormatting} types that correspond to the {@link FormattingRule}s to remove
     *                   from {@code this} type's master ruleset
     */
    protected static void removeMasterRules(Set<Class<? extends TextFormatting>> formattings) {
        formattings.forEach(DefinedFormattingRuleset::removeMasterRule);
    }

    /**
     * Gets a copy of {@code this} {@code DefinedFormattingRuleset}'s {@link #MASTER_RULES}.
     *
     * @return a deep copy of {@code this} {@code DefinedFormattingRuleset}'s {@link #MASTER_RULES}.
     */
    public static HashMap<
            Class<? extends TextFormatting>, Function<FormattingMarkedText<? extends TextFormatting>, String>
            > getMasterRules()
    {
        return new HashMap<>(MASTER_RULES);
    }

    protected static <V extends TextFormatting> FormattingRule<V> ruleOf(
            Class<V> formatting, Function<FormattingMarkedText<V>, String> process
    ) {
        return new FormattingRule<>(formatting, process);
    }

    private static <T extends Function<FormattingMarkedText<? extends TextFormatting>, String>> T captureProcessFunction(T function) {
        return function;
    }
}