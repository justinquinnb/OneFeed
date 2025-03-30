package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.TextFormatting;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * A mapping of {@link TextFormatting}s to the {@link Function}s that consume them and raw text to produce formatted
 * text. This type is best for defining rulesets in a class that should never be changed after initialization.
 */
public non-sealed abstract class ImmutableFormattingRuleset extends DefinedFormattingRuleset {
    /**
     * Initialize the rules of {@code this} ruleset.
     *
     * @implNote This method is called to make the desired modifications necessary to define the class' ruleset. The
     * modifications are immutable.
     * <br><br>
     * It is strongly encouraged to limit this method's body to the base ruleset manipulation functions:
     * {@link #addMasterRules}, {@link #addMasterRules}, {@link #removeMasterRule}, and {@link #removeMasterRules}.
     */
    protected abstract void initializeMasterRules();

    /**
     * Gets {@code this} class' simple name.
     *
     * @return the simple name of {@code this} {@code ImmutableFormattingRuleset}'s type
     * @see Class#getSimpleName()
     */
    public final String getName() {
        return this.getClass().getSimpleName();
    }

    public final HashMap<
            Class<? extends TextFormatting>, Function<FormattingMarkedText<? extends TextFormatting>, String>
            > getRules()
    {
        return new HashMap<>(MASTER_RULES);
    }

    public final Function<FormattingMarkedText<? extends TextFormatting>, String> getRuleFor(
            Class<? extends TextFormatting> formatting) throws NoSuchElementException
    {

        Function<FormattingMarkedText<? extends TextFormatting>, String> rule = MASTER_RULES.get(formatting);

        if (rule == null) {
            throw new NoSuchElementException("No rule found for \"" + formatting.getName() + "\"-type formatting in ruleset \"" + this.getName() + "\"");
        }
        return rule;
    }
}