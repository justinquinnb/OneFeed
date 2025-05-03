package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.JsonToString;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.function.Function;

// TODO add outputLangs[] field?
/**
 * A mapping of {@link TextFormatting}s to the {@link Function}s that consume them and {@link FormattingMarkedText} to
 * produce {@link MarkedUpText}.
 */
public abstract sealed class FormatApplicationRuleset
        permits DefinedFormatApplicationRuleset, QuickFormatApplicationRuleset {

    /**
     * Gets the name of {@code this} {@code FormatApplicationRuleset}.
     *
     * @return {@code this} {@code FormatApplicationRuleset}'s name
     */
    public abstract String getName();

    /**
     * Gets {@code this} {@code FormatApplicationRuleset}'s rules.
     *
     * @return a deep copy of {@code this} {@code FormatApplicationRuleset}'s rules
     */
    public abstract HashMap<Class<? extends TextFormatting>, Function<FormattingMarkedText, MarkedUpText>> getRules();

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
    public abstract Function<FormattingMarkedText, MarkedUpText> getRuleFor(Class<? extends TextFormatting> formatting)
            throws NoSuchElementException;

    @Override
    public String toString() {
        return JsonToString.of(this);
    }
}