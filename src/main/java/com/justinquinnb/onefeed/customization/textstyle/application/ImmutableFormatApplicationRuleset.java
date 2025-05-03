package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * A mapping of {@link TextFormatting}s to the {@link Function}s that consume them and {@link FormattingMarkedText} to
 * produce {@link MarkedUpText}. This type is best for defining rulesets in a class that should never be changed after
 * initialization.
 */
public abstract non-sealed class ImmutableFormatApplicationRuleset extends DefinedFormatApplicationRuleset {
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
     * @return the simple name of {@code this} {@code ImmutableFormatApplicationRuleset}'s type
     * @see Class#getSimpleName()
     */
    public final String getName() {
        return this.getClass().getSimpleName();
    }

    public final HashMap<
            Class<? extends TextFormatting>, Function<FormattingMarkedText, MarkedUpText>> getRules()
    {
        return new HashMap<>(MASTER_RULES);
    }

    public final Function<FormattingMarkedText, MarkedUpText> getRuleFor(
            Class<? extends TextFormatting> formatting) throws NoSuchElementException
    {

        Function<FormattingMarkedText, MarkedUpText> rule = MASTER_RULES.get(formatting);

        if (rule == null) {
            throw new NoSuchElementException("No rule found for \"" + formatting.getName() + "\"-type formatting in ruleset \"" + this.getName() + "\"");
        }
        return rule;
    }
}