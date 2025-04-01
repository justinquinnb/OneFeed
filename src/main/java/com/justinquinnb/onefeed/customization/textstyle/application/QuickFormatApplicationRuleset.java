package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.formattings.TextFormatting;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;

/**
 * A mapping of {@link TextFormatting}s to the {@link Function}s that consume them and {@link FormattingMarkedText} to
 * produce {@link MarkedUpText}. This type, unlike {@link DefinedFormatApplicationRuleset}, is best for in-line,
 * on-the-fly ruleset creation.
 */
public final class QuickFormatApplicationRuleset extends FormatApplicationRuleset {
    /**
     * The name of {@code this} {@link QuickFormatApplicationRuleset}.
     */
    private final String name;

    /**
     * A mapping of {@link TextFormatting} types to the functions used to convert text marked as requiring such
     * formatting into text that actually possesses it in some markup language.
     *
     * @see FormatApplicationRule
     */
    private final HashMap<
            Class<? extends TextFormatting>, Function<FormattingMarkedText, MarkedUpText>> rules = new HashMap<>();

    /**
     * Constructs a {@link QuickFormatApplicationRuleset} with the provided {@code name} and {@link #rules}, {@code rules}.
     *
     * @param name the name of the {@code QuickFormatApplicationRuleset} to create
     * @param rules the {@link FormatApplicationRule}s to include in {@code this} {@code QuickFormatApplicationRuleset}
     */
    public QuickFormatApplicationRuleset(String name, Set<FormatApplicationRule> rules) {
        this.name = name;

        for (FormatApplicationRule rule : rules) {
            this.rules.put(rule.getFormatting(), rule.getProcess());
        }
    }

    /**
     * Gets the name of {@code this} ruleset.
     *
     * @return the name of {@code this} {@code QuickFormatApplicationRuleset} instance
     */
    public final String getName() {
        return name;
    }

    public HashMap<Class<? extends TextFormatting>, Function<FormattingMarkedText, MarkedUpText>> getRules() {
        return new HashMap<>(this.rules); // TODO make this actually a deep copy(?)
    }

    public final Function<FormattingMarkedText, MarkedUpText> getRuleFor(Class<? extends TextFormatting> formatting)
            throws NoSuchElementException {
        Function<FormattingMarkedText, MarkedUpText> rule = this.rules.get(formatting);

        if (rule == null) {
            throw new NoSuchElementException("No rule found for \"" + formatting.getName() + "\"-type formatting in ruleset \"" + name + "\"");
        }

        return rule;
    }
}