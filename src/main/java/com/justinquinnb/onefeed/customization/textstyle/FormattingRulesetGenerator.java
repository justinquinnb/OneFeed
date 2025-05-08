package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.textstyle.application.FormatApplicationRule;
import com.justinquinnb.onefeed.customization.textstyle.application.FormatApplicationRuleset;
import com.justinquinnb.onefeed.customization.textstyle.application.QuickFormatApplicationRuleset;
import com.justinquinnb.onefeed.customization.textstyle.markup.MarkupLanguage;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormattingRegistry;
import com.justinquinnb.onefeed.customization.textstyle.parsing.FormatParsingRule;
import com.justinquinnb.onefeed.customization.textstyle.parsing.FormatParsingRuleset;
import com.justinquinnb.onefeed.customization.textstyle.parsing.QuickFormatParsingRuleset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(FormattingRulesetGenerator.class);

    /**
     * A mapping of each {@link MarkupLanguage} type to its respective {@link FormatApplicationRuleset} so that ruleset
     * retrieval does not entail building (what should be) an unchanging ruleset each time.
     */
    private static final HashMap<Class<? extends MarkupLanguage>, FormatApplicationRuleset> CACHED_APPLICATION_RULESETS =
            new HashMap<>();

    /**
     * A mapping of each {@link MarkupLanguage} type to its respective {@link FormatApplicationRuleset} so that ruleset
     * retrieval does not entail building (what should be) an unchanging ruleset each time.
     */
    private static final HashMap<Class<? extends MarkupLanguage>, FormatParsingRuleset> CACHED_PARSING_RULESETS =
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
    public static FormatApplicationRuleset getApplicationRulesetFor(Class<? extends MarkupLanguage> markupLang)
            throws UnknownTextStyleEntityException
    {
        logger.debug("Getting the application ruleset for {}", markupLang.getSimpleName());

        // If the markup language's application ruleset does not exist in the cache already, add it
        if (!CACHED_APPLICATION_RULESETS.containsKey(markupLang)) {
            logger.trace("An application ruleset for {} has not been cached yet", markupLang.getSimpleName());
            cacheApplicationRulesetFor(markupLang);
        }

        return CACHED_APPLICATION_RULESETS.get(markupLang);
    }

    /**
     * Gets last-cached {@link FormatParsingRuleset} for the desired {@code markupLang}. If no ruleset exists in
     * the cache, a new one is generated and cached before being returned.
     *
     * @param markupLang the {@link MarkupLanguage} type whose {@code FormatApplicationRuleset} to get
     *
     * @return a {@code FormatParsingRuleset} specifying how the desired {@code markupLang}'s recognized
     * {@link TextFormatting}s should appear as markup in text
     * @throws UnknownTextStyleEntityException if the provided {@code markupLang} does not exist in the
     * {@link TextFormattingRegistry}
     */
    public static FormatParsingRuleset getParsingRulesetFor(Class<? extends MarkupLanguage> markupLang)
            throws UnknownTextStyleEntityException
    {
        logger.debug("Getting the parsing ruleset for {}", markupLang.getSimpleName());

        // If the markup language's parsing ruleset does not exist in the cache already, add it
        if (!CACHED_PARSING_RULESETS.containsKey(markupLang)) {
            logger.trace("A parsing ruleset for {} has not been cached yet", markupLang.getSimpleName());
            cacheParsingRulesetFor(markupLang);
        }

        return CACHED_PARSING_RULESETS.get(markupLang);
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
        logger.debug("Caching the application ruleset for {}", markupLang.getSimpleName());

        // Determine the TextFormatting types recognized by the markupLang
        Class<? extends TextFormatting>[] formattingsInLang = TextFormattingRegistry
                .getFormattingsSupportedBy(markupLang);

        // For easier debugging, make this list a nice string of the class names
        String fmtsInLangStr = "[";
        for (Class<? extends TextFormatting> formatting : formattingsInLang) {
            fmtsInLangStr += formatting.getSimpleName();
            if (formatting != formattingsInLang[formattingsInLang.length - 1]) {
                fmtsInLangStr += ", ";
            }
        }
        fmtsInLangStr += "]";

        logger.trace("Determined {}'s supported formattings: {}", markupLang.getSimpleName(), fmtsInLangStr);

        // Get the set of application rules from the TextFormattingRegistry for the language
        Set<FormatApplicationRule> applicationRules = new LinkedHashSet<>();
        for (Class<? extends TextFormatting> formatting : formattingsInLang) {
            applicationRules.add(TextFormattingRegistry.getApplicationRuleFor(formatting, markupLang));
            logger.trace("Added application rule for {}", formatting.getSimpleName());
        }

        // Build a FormattingApplicationRuleset from these rules
        if (CACHED_APPLICATION_RULESETS.put(markupLang, new QuickFormatApplicationRuleset("Auto-Generated " +
                markupLang.getName(), applicationRules)) == null)
        {
            logger.debug("Cached new application ruleset for {}", markupLang.getSimpleName());
        } else {
            logger.debug("Updated existing application ruleset in cache for {}", markupLang.getSimpleName());
        }
    }

    /**
     * Creates and caches the {@link FormatParsingRuleset} for the desired {@code markupLang}. If a ruleset for the
     * desired {@code markupLang} was already cached, this method will update it in place.
     *
     * @param markupLang the type of {@link MarkupLanguage} to make and cache a {@code FormatParsingRuleset} for
     *
     * @throws UnknownTextStyleEntityException if the provided {@code markupLang} is unknown to the
     * {@link TextFormattingRegistry}, where the ruleset is derived from
     */
    private static void cacheParsingRulesetFor(Class<? extends MarkupLanguage> markupLang)
            throws UnknownTextStyleEntityException
    {
        logger.debug("Caching the parsing ruleset for {}", markupLang.getSimpleName());

        // Determine the TextFormatting types recognized by the markupLang
        Class<? extends TextFormatting>[] formattingsInLang = TextFormattingRegistry
                .getFormattingsSupportedBy(markupLang);

        // For easier debugging, make this list a nice string of the class names
        String fmtsInLangStr = "[";
        for (Class<? extends TextFormatting> formatting : formattingsInLang) {
            fmtsInLangStr += formatting.getSimpleName();
            if (formatting != formattingsInLang[formattingsInLang.length - 1]) {
                fmtsInLangStr += ", ";
            }
        }
        fmtsInLangStr += "]";

        logger.trace("Determined {}'s supported formattings: {}", markupLang.getSimpleName(), fmtsInLangStr);

        // Get the set of parsing rules from the TextFormattingRegistry for the language
        LinkedHashSet<FormatParsingRule> parsingRules = new LinkedHashSet<>();
        for (Class<? extends TextFormatting> formatting : formattingsInLang) {
            parsingRules.add(TextFormattingRegistry.getParsingRuleFor(formatting, markupLang));
            logger.trace("Added parsing rule for {}", formatting.getSimpleName());
        }

        // Build a FormattingParsingRuleset from these rules
        if (CACHED_PARSING_RULESETS.put(markupLang, new QuickFormatParsingRuleset("Auto-Generated " +
                markupLang.getName(), parsingRules)) == null)
        {
            logger.debug("Cached new parsing ruleset for {}", markupLang.getSimpleName());
        } else {
            logger.debug("Updated existing parsing ruleset in cache for {}", markupLang.getSimpleName());
        }
    }

    /**
     * Creates and caches the {@link FormatApplicationRuleset} and {@link FormatParsingRuleset} for the desired
     * {@code markupLang}. If a ruleset for the desired {@code markupLang} was already cached, this method will update
     * it in place.
     *
     * @param markupLang the type of {@link MarkupLanguage} to make and cache a {@link FormatApplicationRuleset} and
     * {@link FormatParsingRuleset} for
     *
     * @throws UnknownTextStyleEntityException if the provided {@code markupLang} is unknown to the
     * {@link TextFormattingRegistry}, where the ruleset is derived from
     */
    public static void cacheRulesetsFor(Class<? extends MarkupLanguage> markupLang)
            throws UnknownTextStyleEntityException
    {
        logger.debug("Caching the rulesets for {}", markupLang.getSimpleName());
        cacheApplicationRulesetFor(markupLang);
        cacheParsingRulesetFor(markupLang);
    }

    /**
     * Clears both ruleset caches entirely and repopulates them with the most recent rules provided by the
     * {@link TextFormattingRegistry}.
     */
    public static void refreshCache() {
        logger.debug("Refreshing the ruleset cache");
        logger.trace("Clearing both caches");
        CACHED_APPLICATION_RULESETS.clear();
        CACHED_PARSING_RULESETS.clear();

        // Repopulate the cache language by language
        Class<? extends MarkupLanguage>[] knownLangs = TextFormattingRegistry.getKnownLanguages();
        for (Class<? extends MarkupLanguage> markupLang : knownLangs) {
            try {
                logger.trace("Generating the ruleset for {}", markupLang.getSimpleName());
                cacheRulesetsFor(markupLang);
            } catch (UnknownTextStyleEntityException e) {
                // Continue with the others
            }
        }
    }

    /**
     * Clears only rulesets that are no longer known by the {@link TextFormatting} and adds/updates the cached rulesets
     * for all others.
     */
    public static void smartCacheRefresh() {
        logger.debug("Smart refreshing the ruleset cache");

        // Remove any forgotten languages
        // Since the cache's ruleset languages match each other (since they are both from the TextFormattingRegistry),
        // only one keyset is necessary
        Set<Class<? extends MarkupLanguage>> cachedApplicationLangs = CACHED_APPLICATION_RULESETS.keySet();
        Set<Class<? extends MarkupLanguage>> knownLangs = Set.of(TextFormattingRegistry.getKnownLanguages());

        for (Class<? extends MarkupLanguage> markupLang : cachedApplicationLangs) {
            if (!knownLangs.contains(markupLang)) {
                logger.trace("MarkupLanguage {} no longer exists in the TextFormattingRegistry, so removing it from " +
                        "the cache", markupLang.getSimpleName());
                CACHED_APPLICATION_RULESETS.remove(markupLang);
                CACHED_PARSING_RULESETS.remove(markupLang);
            } else {
                logger.trace("MarkupLanguage {} still exists in the TextFormattingRegistry, so keeping it",
                        markupLang.getSimpleName());
            }
        }

        // Add and update any remaining languages
        for (Class<? extends MarkupLanguage> markupLang : knownLangs) {
            try {
                logger.trace("Regenerating the ruleset for {}", markupLang.getSimpleName());
                cacheRulesetsFor(markupLang);
            } catch (UnknownTextStyleEntityException e) {
                logger.warn("An UknownTextStyleEntityException occurred while generating the ruleset for {}",
                        markupLang.getSimpleName());
                // Proceed as nothing can be done. This should never happen.
            }
        }
    }
}