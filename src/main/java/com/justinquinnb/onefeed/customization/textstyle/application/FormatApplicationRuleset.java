package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;
import com.justinquinnb.onefeed.utils.JsonToString;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * A mapping of {@link TextFormatting}s to the {@link FormattingApplierFunction}s that consume them and
 * {@link FormattingMarkedText} to produce {@link MarkedUpText}.
 */
public abstract sealed class FormatApplicationRuleset
        permits DefinedFormatApplicationRuleset, QuickFormatApplicationRuleset {

    /**
     * Gets the name of {@code this} {@code FormatApplicationRuleset}.
     *
     * @return {@code this} {@code FormatApplicationRuleset}'s name
     */
    public abstract String getName();

    /*
     * Rules are decomposed into KV pairs as they are only ever used in that manner when they're part of a ruleset.
     *
     * Order is irrelevant as the application of one formatting type has no effect on later application procedures like
     * parsing does.
     */
    /**
     * Gets {@code this} {@code FormatApplicationRuleset}'s rules.
     *
     * @return a deep copy of {@code this} {@code FormatApplicationRuleset}'s rules
     */
    public abstract HashMap<Class<? extends TextFormatting>, FormattingApplierFunction> getApplierMap();

    /**
     * Gets the {@link FormattingApplierFunction} that specifies how to mark up text with {@link TextFormatting} of
     * type {@code formatting}.
     *
     * @param formatting the type of {@code Formatting} whose markup function to retrieve from {@code this} ruleset
     *
     * @return the {@code Function} that specifies how to mark up text with {@code TextFormatting} of type
     * {@code formatting}, if such function exists in {@code this} ruleset
     * @throws NoSuchElementException if a {@code FormattingApplierFunction} couldn't be found for the provided {@code formatting} in
     * {@code this} ruleset
     */
    public abstract FormattingApplierFunction getApplierFor(Class<? extends TextFormatting> formatting)
            throws NoSuchElementException;

    @Override
    public String toString() {
        return JsonToString.of(this);
    }
}