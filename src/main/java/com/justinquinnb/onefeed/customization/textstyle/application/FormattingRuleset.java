package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.JsonToString;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.TextFormatting;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * A mapping of {@link TextFormatting}s to the {@link Function}s that consume them and raw text to produce formatted
 * text.
 */
public sealed abstract class FormattingRuleset
        permits DefinedFormattingRuleset, QuickFormattingRuleset {

    /**
     * Gets the name of {@code this} {@link QuickFormattingRuleset}.
     *
     * @return {@code this} {@code QuickFormattingRuleset}'s name
     */
    public abstract String getName();

    /**
     * Gets {@code this} {@link QuickFormattingRuleset}'s rules.
     *
     * @return a deep copy of {@code this} {@code QuickFormattingRuleset}'s rules
     */
    public abstract HashMap<Class<? extends TextFormatting>, Function<FormattingMarkedText<? extends TextFormatting>, String>> getRules();

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
    public abstract Function<FormattingMarkedText<? extends TextFormatting>, String> getRuleFor(Class<? extends TextFormatting> formatting)
            throws NoSuchElementException;

    @Override
    public String toString() {
        return JsonToString.of(this);
    }
}