package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.textstyle.application.FormatApplicationRule;
import com.justinquinnb.onefeed.customization.textstyle.application.FormatApplicationRuleset;
import com.justinquinnb.onefeed.customization.textstyle.application.QuickFormatApplicationRuleset;
import com.justinquinnb.onefeed.customization.textstyle.markup.MarkupLanguage;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormattingRegistry;
import com.justinquinnb.onefeed.customization.textstyle.parsing.FormatParsingRuleset;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A type capable of generating {@link FormatApplicationRuleset}s and {@link FormatParsingRuleset}s that represent a
 * defined {@link MarkupLanguage}.
 */
public class FormattingRulesetGenerator {
    /*
    What's best about the caching approach is that Content Sources can choose to either define a markup language
    class OR just provide a ruleset. If a markup language is used to generate a ruleset, then it gets cached as
    soon as that content source gets called upon at OneFeed's initialization-- the prime time to load them all up.
    This seems like the best approach
     */

    /**
     * A mapping of each {@link MarkupLanguage} type to its respective {@link FormatApplicationRuleset} so that ruleset
     * retrieval does not entail building (what should be) an unchanging ruleset each time.
     */
    private static final HashMap<Class<? extends MarkupLanguage>, FormatApplicationRuleset> CACHED_APPLICATION_RULESETS =
            new HashMap<>();

    /**
     * Maps unique {@link FormatParsingRuleset} names to their respective {@code FormatParsingRuleset}s. Names are used
     * since hash equality is impossible given the need to test lambda equality for the underlying FormatParsingRules,
     * which isn't possible.
     */
    private static final HashMap<String, FormatParsingRuleset> CACHED_PARSING_RULESETS =
            new HashMap<>();

    /**
     * Gets last-cached {@link FormatApplicationRuleset} for the desired {@code markupLang}. If no ruleset exists in
     * the cache, a new one is generated and cached before being returned.
     *
     * @param markupLang the {@link MarkupLanguage} type whose {@code FormatApplicationRuleset} to get
     *
     * @return a {@code FormatApplicationRuleset} specifying how the desired {@code markupLang}'s recognized
     * {@link TextFormatting}s should appear as markup in text
     * @throws UnknownTextStyleEntityException if the provided {@code markupLang} does not exist in the
     * {@link TextFormattingRegistry}
     */
    public static FormatApplicationRuleset getApplicationRuleset(Class<? extends MarkupLanguage> markupLang)
            throws UnknownTextStyleEntityException
    {
        // If the markup language's application ruleset does not exist in the cache already, add it
        if (!CACHED_APPLICATION_RULESETS.containsKey(markupLang)) {
            cacheApplicationRulesetFor(markupLang);
        }

        return CACHED_APPLICATION_RULESETS.get(markupLang);
    }

    // TODO finish implementing
    /**
     *
     * @param markupLang
     * @return
     */
    public static FormatParsingRuleset getParsingRulesetFor(Class<? extends MarkupLanguage> markupLang) {
        // If the markup language's parsing ruleset does not exist in the cache already, add it
        String rulesetKey = getParsingRulesetNameFor(markupLang);
        if (!CACHED_PARSING_RULESETS.containsKey(rulesetKey)) {
            cacheParsingRulesetFor(markupLang);
        }

        return CACHED_PARSING_RULESETS.get(rulesetKey);
    }

    // TODO finish implementing
    public static FormatParsingRuleset getParsingRulesetFor(
            Class<? extends MarkupLanguage> markupLang, LinkedHashSet<Class<? extends TextFormatting>> parsingOrder) {

    }

    /**
     * Creates and caches the {@link FormatApplicationRuleset} for the desired {@code markupLang}. If a ruleset for the
     * desired {@code markupLang} was already cached, this method will update it in place.
     *
     * @param markupLang the type of {@link MarkupLanguage} to make and cache a {@code FormatApplicationRuleset} for
     *
     * @throws UnknownTextStyleEntityException if the provided {@code markupLang} is unknown to the
     * {@link TextFormattingRegistry}, where the ruleset is derived from
     */
    private static void cacheApplicationRulesetFor(Class<? extends MarkupLanguage> markupLang)
            throws UnknownTextStyleEntityException
    {
        // Determine the TextFormatting types recognized by the markupLang
        Class<? extends TextFormatting>[] formattingsInLang = TextFormattingRegistry
                .getFormattingsSupportedBy(markupLang);

        // Get the set of parsing rules from the TextFormattingRegistry for the language
        Set<FormatApplicationRule> applicationRules = new LinkedHashSet<>();
        for (Class<? extends TextFormatting> formatting : formattingsInLang) {
            applicationRules.add(TextFormattingRegistry.getApplicationRuleFor(formatting, markupLang));
        }

        // Build a FormattingApplicationRuleset from these rules
        CACHED_APPLICATION_RULESETS.put(markupLang, new QuickFormatApplicationRuleset("Auto-Generated " +
                markupLang.getSimpleName(), applicationRules));
    }

    // TODO make this iterate through the existing hashmap and regenerate each ruleset
    // this method should be invoked when new customizations are loaded at runtime(?). depends on if I add that
    // capability
    /**
     * test
     */
    public static void refreshCachedApplicationRulesets() {

    }

    // HELPERS
    // TODO document
    /**
     *
     * @param markupLang
     * @return
     */
    private static String getParsingRulesetNameFor(Class<? extends MarkupLanguage> markupLang) {
        return "AutoOrdered" + markupLang.getSimpleName();
    }

    // TODO document
    /**
     *
     * @param markupLang
     * @param parsingOrder
     * @return
     */
    private static String getParsingRulesetNameFor(
            Class<? extends MarkupLanguage> markupLang,
            LinkedHashSet<Class<? extends TextFormatting>> parsingOrder)
    {
        return "CustomOrder" + parsingOrder.hashCode() + markupLang.getSimpleName();
    }
}