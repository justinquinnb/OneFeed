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
 * text. This type is best for defining rulesets in a class that allow changes on a per-instance basis after
 * the class has been initialized.
 */
public abstract non-sealed class MutableFormattingRuleset extends DefinedFormattingRuleset {
    /**
     * The rules derived from applying the changes ordered by an instance of {@code this} mutable ruleset to {@code this}
     * type's base ruleset.
     */
    private final HashMap<
            Class<? extends TextFormatting>, Function<FormattingMarkedText<? extends TextFormatting>, String>
            > modifiedRules = new HashMap<>(MASTER_RULES);

    /**
     * The name given to an instance of this {@code MutableFormattingRuleset} type, if provided during construction. By
     * default, this will equal this type's name.
     */
    private final String name;

    /**
     * Gets an instance of {@code this} {@code MutableFormattingRuleset} as-is with {@code name} equal to the type name.
     * The instance's rules will match {@code this} type's base rules exactly (unless changed post-construction).
     */
    public MutableFormattingRuleset() {
        super();
        this.name = this.getClass().getSimpleName();
    }

    private MutableFormattingRuleset(String name, Set<FormattingRule<? extends TextFormatting>> rulesToAdd, Set<Class<? extends TextFormatting>> rulesToRemove) {
        super();
        this.name = name;
        rulesToAdd.forEach(rule ->
                this.modifiedRules.put(rule.getFormatting(), rule.getProcess()));
        rulesToRemove.forEach(this.modifiedRules::remove);
    }

    /**
     * Initialize the base rules of {@code this} ruleset.
     *
     * @implNote This method is called to make the desired modifications necessary to define the class' base ruleset.
     * Ruleset modifications called for by instances of {@code this} type are performed on the ruleset defined here.
     * <br><br>
     * It is strongly encouraged to limit this method's body to the base ruleset manipulation functions:
     * {@link #addMasterRule}, {@link #addMasterRules}, {@link #removeMasterRule}, and {@link #removeMasterRules}.
     */
    protected abstract void initializeMasterRules();

    /**
     * Gets {@code this} ruleset's name. If the rules differ from that of the master set, the name returned will equal
     * the simple name of the invoking object's type.
     *
     * @return the name of {@code this} {@code MutableFormattingRuleset}
     * @see Class#getSimpleName()
     */
    public final String getName() {
        return this.name;
    }

    public final Function<FormattingMarkedText<? extends TextFormatting>, String>
        getRuleFor(Class<? extends TextFormatting> formatting) throws NoSuchElementException
    {
        Function<FormattingMarkedText<? extends TextFormatting>, String> rule = this.modifiedRules.get(formatting);

        if (rule == null) {
            throw new NoSuchElementException("No rule found for \"" + formatting.getName() + "\"-type formatting in ruleset \"" + name + "\"");
        }
        return this.modifiedRules.get(formatting);
    }

    public final HashMap<
            Class<? extends TextFormatting>, Function<FormattingMarkedText<? extends TextFormatting>, String>
            > getRules()
    {
        return new HashMap<>(this.modifiedRules);
    }
}