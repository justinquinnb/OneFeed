package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.textstyle.application.FormatApplicationRuleset;
import com.justinquinnb.onefeed.customization.textstyle.markup.MarkupLanguage;
import com.justinquinnb.onefeed.customization.textstyle.parsing.FormatParsingRuleset;

import java.util.HashMap;

/**
 * A type capable of generating {@link FormatApplicationRuleset}s and {@code FormatParsingRuleset}s that represent a
 * defined {@link MarkupLanguage}.
 */
public class FormattingRulesetGenerator {
    // What's best about the caching approach is that Content Sources can choose to either define a markup language
    // class OR just provide a ruleset. If a markup language is used to generate a ruleset, then it gets cached as
    // soon as that content source gets called upon at OneFeed's initialization-- the prime time to load them all up.
    // This seems like the best approach

    // TODO Because the getApplicationRuleset method is very resource-intensive, cache its findings each time a new
    //  language is queried.
    /**
     *
     */
    private static final HashMap<Class<? extends MarkupLanguage>, FormatApplicationRuleset> CACHED_APPLICATION_RULESETS =
            new HashMap<>();

    // TODO If a ruleset for the desired language hasn't already been cached, find all implementors of the desired
    //  class and get its applier method using the getApplier interface method. Then, build a rule from that lambda.
    //  Otherwise, return the ruleset for the desired class from the cache to save resources.

    /**
     *
     * @param markupLang
     * @return
     */
    public static FormatApplicationRuleset getApplicationRulesetFor(Class<? extends MarkupLanguage> markupLang) {
        return null;
    }

    // TODO this will have to leverage the creation of new regex getters and String to FormattingMarkedText functions
    //  specified by the MarkupLanguage interface. Likely a similar approach to appliers will have to be taken.

    /**
     *
     * @param markupLang
     * @return
     */
    public static FormatParsingRuleset getParsingRulesetFor(Class<? extends MarkupLanguage> markupLang) {
        return null;
    }

    // TODO make this iterate through the existing hashmap and regenerate each ruleset
    // this method should be invoked when new customizations are loaded at runtime(?). depends on if I add that
    // capability
    /**
     * test
     */
    public static void refreshCachedApplicationRulesets() {

    }
}