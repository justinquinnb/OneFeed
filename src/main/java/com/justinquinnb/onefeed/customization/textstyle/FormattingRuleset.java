package com.justinquinnb.onefeed.customization.textstyle;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;

/**
 * A {@link Set} of {@link FormattingRule}s in the desired order that they be applied to {@link FormattingMarkedText}.
 * @param <T> the language of {@link TextFormatting}s that {@code this} {@code FormattingRuleset}'s {@link #rules} can
 *           interpret
 */
public class FormattingRuleset<T extends TextFormatting> {
    /**
     * The name of {@code this} {@link FormattingRuleset}.
     */
    private final String name;

    /**
     * A mapping of {@link TextFormatting} types to the functions used to convert text marked as requiring such
     * formatting into text that actually possesses it in some markup language.
     *
     * @see FormattingRule
     */
    private final LinkedHashMap<Class<? extends TextFormatting>, Function<FormattingMarkedText<T>, String>> rules =
            new LinkedHashMap<>();

    /**
     * Constructs a {@link FormattingRuleset} with the provided {@code name} and {@link #rules}, {@code rules}.
     *
     * @param name the name of the {@code FormattingRuleset} to create
     * @param rules the {@link FormattingRule}s to include in {@code this} {@code FormattingRuleset}
     */
    public FormattingRuleset(String name, LinkedHashSet<FormattingRule<T>> rules) {
        this.name = name;

        for (FormattingRule<T> rule : rules) {
            this.rules.put(rule.getFormatting(), rule.getProcess());
        }
    }

    /**
     * Gets the name of {@code this} {@link FormattingRuleset}.
     *
     * @return {@code this} {@code FormattingRuleset}'s name
     */
    public final String getName() {
        return name;
    }

    /**
     * Gets {@code this} {@link FormattingRuleset}'s {@link #rules}.
     *
     * @return a deep copy of {@code this} {@code FormattingRuleset}'s {@code #rules}
     */
    public final LinkedHashMap<Class<? extends TextFormatting>, Function<FormattingMarkedText<T>, String>> getRules() {
        return new LinkedHashMap<>(this.rules); // TODO make this actually a deep copy(?)
    }

    /**
     * Gets the {@link Function} that specifies how to mark up text with {@link TextFormatting} of type
     * {@code formatting}.
     *
     * @param formatting the type of {@code Formatting} whose markup function to retrieve from {@code this} ruleset
     *
     * @return the {@code Function} that specifies how to mark up text with {@code TextFormatting} of type
     * {@code formatting}, if such function exists in {@code this} ruleset
     * @throws NoSuchElementException if a {@code Function} couldn't be found for the provided {@code formatting} in
     * {@code this} ruleset
     */
    public final Function<FormattingMarkedText<T>, String> getRuleFor(Class<? extends TextFormatting> formatting)
            throws NoSuchElementException {
        Function<FormattingMarkedText<T>, String> rule = this.rules.get(formatting);

        if (rule == null) {
            throw new NoSuchElementException("No rule found for \"" + formatting.getName() + "\"-type formatting in ruleset \"" + name + "\"");
        }
        return this.rules.get(formatting);
    }
}