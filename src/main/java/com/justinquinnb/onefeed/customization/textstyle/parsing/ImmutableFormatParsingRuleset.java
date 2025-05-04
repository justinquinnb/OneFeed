package com.justinquinnb.onefeed.customization.textstyle.parsing;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A {@link Set} of {@link FormatParsingRule}s in the desired order of application to {@link MarkedUpText}. This type
 * is best for defining rulesets in a class that should never be changed after initialization.
 */
public non-sealed abstract class ImmutableFormatParsingRuleset extends DefinedFormatParsingRuleset {
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
     * @return the simple name of {@code this} {@code ImmutableFormatParsingRuleset}'s type
     * @see Class#getSimpleName()
     */
    public final String getName() {
        return this.getClass().getSimpleName();
    }

    public final LinkedHashSet<FormatParsingRule> getParsingRules() {
        return new LinkedHashSet<>(MASTER_RULES);
    }
}