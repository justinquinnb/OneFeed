package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.JsonToString;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;

import java.util.function.Function;

/**
 * An indication of how to apply a {@link TextFormatting} type to text in order to generate
 * {@link FormattingMarkedText}.
 */
public class FormatApplicationRule {
    /**
     * The type of {@link TextFormatting} that {@code this} {@code FormatApplicationRule}'s {@link #applierFunction} specifies
     * the  markup procedure for.
     */
    private final Class<? extends TextFormatting> formatting;

    /**
     * The {@link FormattingApplierFunction} that text formatted with {@code this} {@link FormatApplicationRule}'s
     * {@link #formatting} is piped into in order to produce some marked-up/formatted text.
     */
    private final FormattingApplierFunction applierFunction;

    /**
     * Creates a {@link FormatApplicationRule} mapping the provided {@link TextFormatting} to a {@code Function} that
     * can apply it to text.
     *
     * @param formatting the type of {@link TextFormatting} that the applierFunction should be invoked to generate
     * @param applierFunction the {@code FormattingApplierFunction} that can apply the specified type of 
     * {@code formatting} to text
     */
    public FormatApplicationRule(
            Class<? extends TextFormatting> formatting, FormattingApplierFunction applierFunction
    ) {
        this.formatting = formatting;
        this.applierFunction = applierFunction;
    }

    /**
     * Gets {@code this} {@link FormatApplicationRule}'s {@link #formatting}.
     *
     * @return the {@link TextFormatting} that {@code this} {@code FormatApplicationRule}'s {@link #applierFunction} specifies
     * the markup procedure for
     */
    public final Class<? extends TextFormatting> getFormatting() {
        return this.formatting;
    }

    /**
     * Gets {@code this} {@link FormatApplicationRule}'s {@link #applierFunction}.
     *
     * @return the {@link Function} that specifies how to mark up/format text that calls for {@code this}
     * {@code FormatApplicationRule}'s {@link #formatting}
     */
    public final FormattingApplierFunction getApplierFunction() {
        return this.applierFunction;
    }

    @Override
    public String toString() {
        return JsonToString.of(this);
    }
}