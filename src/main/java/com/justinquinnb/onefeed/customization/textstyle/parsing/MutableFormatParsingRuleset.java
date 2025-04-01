package com.justinquinnb.onefeed.customization.textstyle.parsing;


import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A {@link Set} of {@link FormatParsingRule}s in the desired order of application to {@link MarkedUpText}. This type
 * is best for defining rulesets in a class that allow changes on a per-instance basis after the class has been
 * initialized.
 */
public abstract non-sealed class MutableFormatParsingRuleset extends DefinedFormatParsingRuleset {
    /**
     * The rules derived from applying the changes ordered by an instance of {@code this} mutable ruleset to
     * {@code this} type's base ruleset.
     */
    private final LinkedHashSet<FormatParsingRule> modifiedRules = new LinkedHashSet<>(MASTER_RULES);

    /**
     * The name given to an instance of this {@code MutableFormatParsingRuleset} type, if provided during
     * construction. By default, this will equal this type's name.
     */
    private final String name;

    /**
     * Gets an instance of {@code this} {@code MutableFormatParsingRuleset} as-is with {@code name} equal to the
     * type name. The instance's rules will match {@code this} type's base rules exactly (unless changed
     * post-construction).
     */
    public MutableFormatParsingRuleset() {
        super();
        this.name = this.getClass().getSimpleName();
    }

    /**
     * Gets an instance of {@code this} {@code MutableFormatParsingRuleset} with the modifications specified by the
     * {@code rulesToAdd} and {@code rulesToAdd} under the name {@code name}.
     *
     * @param name the name of the new {@code MutableFormatParsingRuleset} derived from the master rules with the
     *            desired modifications
     * @param rulesToAdd the {@code FormatParsingRule}s to add the constructed {@code MutableFormatParsingRuleset}'s
     *                   derivation of the master rules. Rules will be added to the end of the current list.
     * @param rulesToRemove the {@code FormatParsingRule}s to remove from the constructed
     *                     {@code MutableFormatParsingRuleset}'s derivation of the master rules
     */
    public MutableFormatParsingRuleset(
            String name, LinkedHashSet<FormatParsingRule> rulesToAdd, Set<FormatParsingRule> rulesToRemove
    ) {
        super();
        this.name = name;
        modifiedRules.addAll(rulesToAdd);
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
     * @return the name of {@code this} {@code MutableFormatParsingRuleset}
     * @see Class#getSimpleName()
     */
    public final String getName() {
        return this.name;
    }
}