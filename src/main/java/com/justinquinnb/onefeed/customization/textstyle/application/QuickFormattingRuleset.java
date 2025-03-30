package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingRule;
import com.justinquinnb.onefeed.customization.textstyle.TextFormatting;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;

/**
 * A mapping of {@link TextFormatting}s to the {@link Function}s that consume them and raw text to produce formatted
 * text. This type, unlike {@link DefinedFormattingRuleset}, is best for in-line, on-the-fly ruleset creation.
 *
 * @param <T> the language of {@link TextFormatting}s that can/might have rules defined by {@code this}
 * {@code FormattingRuleset}
 */
public final class QuickFormattingRuleset<T extends TextFormatting> extends FormattingRuleset<T> {
    /**
     * The name of {@code this} {@link QuickFormattingRuleset}.
     */
    private final String name;

    /**
     * A mapping of {@link TextFormatting} types to the functions used to convert text marked as requiring such
     * formatting into text that actually possesses it in some markup language.
     *
     * @see FormattingRule
     */
    private final HashMap<
            Class<TextFormatting>, Function<FormattingMarkedText<TextFormatting>, String>
            > rules = new HashMap<>();

    /**
     * Constructs a {@link QuickFormattingRuleset} with the provided {@code name} and {@link #rules}, {@code rules}.
     *
     * @param name the name of the {@code QuickFormattingRuleset} to create
     * @param rules the {@link FormattingRule}s to include in {@code this} {@code QuickFormattingRuleset}
     */
    public QuickFormattingRuleset(String name, Set<FormattingRule<TextFormatting>> rules) {
        this.name = name;

        for (FormattingRule<TextFormatting> rule : rules) {
            this.rules.put(rule.getFormatting(), rule.getProcess());
        }
    }

    /**
     * Gets the name of {@code this} ruleset.
     *
     * @return the name of {@code this} {@code QuickFormattingRuleset} instance
     */
    public final String getName() {
        return name;
    }

    public HashMap<Class<TextFormatting>, Function<FormattingMarkedText<TextFormatting>, String>> getRules() {
        return new HashMap<>(this.rules); // TODO make this actually a deep copy(?)
    }

    public final Function<FormattingMarkedText<TextFormatting>, String> getRuleFor(Class<? extends TextFormatting> formatting)
            throws NoSuchElementException {
        Function<FormattingMarkedText<TextFormatting>, String> rule = this.rules.get(formatting);

        if (rule == null) {
            throw new NoSuchElementException("No rule found for \"" + formatting.getName() + "\"-type formatting in ruleset \"" + name + "\"");
        }

        return rule;
    }
}