package com.justinquinnb.onefeed.customization.textstyle.markup;

import com.justinquinnb.onefeed.customization.textstyle.ComplementaryFormatRulePair;
import com.justinquinnb.onefeed.customization.textstyle.UnknownTextStyleEntityException;
import com.justinquinnb.onefeed.customization.textstyle.application.FormatApplicationRule;
import com.justinquinnb.onefeed.customization.textstyle.application.FormattingApplierFunction;
import com.justinquinnb.onefeed.customization.textstyle.parsing.FormatParsingRule;
import com.justinquinnb.onefeed.customization.textstyle.parsing.FormattingParserFunction;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Holds all known {@link TextFormatting} types alongside their markup extraction and application rules.
 */
public class TextFormattingRegistry {
    /**
     * The default type of {@link TextFormatting} that all text has.
     */
    public static final TextFormatting DEFAULT_FORMATTING = DefaultFormat.getInstance();

    /**
     * The default type of {@link MarkupLanguage} that all text employs. This must provide
     */
    public static final Class<? extends MarkupLanguage> DEFAULT_MARKUP_LANGUAGE = PlainText.class;

    /**
     * The default formatting rules that interpret any text, language and formatting type agnostic.
     */
    public static final ComplementaryFormatRulePair DEFAULT_FORMATTING_LOOP = new ComplementaryFormatRulePair(
            new FormatParsingRule(PlainText.getPlainTextPattern(), PlainText::extractFromPlainText),
            new FormatApplicationRule(DefaultFormat.class, PlainText::applyAsPlainText)
    );

    /**
     * A mapping of {@link TextFormatting} types to all the {@link MarkupLanguage}s they are defined in, where each
     * language subsequently maps to the {@link ComplementaryFormatRulePair} that defines its interpretation.
     */
    private static final HashMap<
        Class<? extends TextFormatting>,
            HashMap<
                Class<? extends MarkupLanguage>,
                ComplementaryFormatRulePair
            >
        > formattingsByType = new HashMap<>();

    /**
     * A mapping of {@link MarkupLanguage}s to all the {@link TextFormatting} types that exist within them, where each
     * formatting type subsequently maps to the {@link ComplementaryFormatRulePair} that defines its interpretation in
     * that language.
     */
    private static final HashMap<
        Class<? extends MarkupLanguage>,
            HashMap<
                Class<? extends TextFormatting>,
                ComplementaryFormatRulePair
            >
        > formattingsByLang = new HashMap<>();

    /**
     * Registers all the methods necessary to interpret a {@code type} of {@link TextFormatting} as it exists in a
     * {@link MarkupLanguage} ({@code language}). Registration enables OneFeed to translate text between markup
     * languages, remove markup, and generate language-agnostic formatting trees.  If a rule pair has already been
     * registered for the formatting and language, it is overridden by the arguments provided here.
     *
     * @param formatting the type of {@code TextFormatting} to register {@code language}-specific markup methods for
     * @param language the type of {@code MarkupLanguage} that the {@code markupRegex} and {@code formattingParser}
     * method are for
     * @param markupRegex the regex {@link Pattern} specifying how the {@code formatting} appears in text marked up
     * using the {@code language}
     * @param formattingParser a function capable of extracting a {@code TextFormatting} object of {@code type}
     * type from text that may or may not have a match for the {@code markupRegex}
     * @param formattingApplier a function capable of applying the markup necessary to represent an instance of the
     * {@code type}-type {@code formatting} directly in some text
     */
    public static void registerForLanguage(
            Class<? extends TextFormatting> formatting, Class<? extends MarkupLanguage> language, Pattern markupRegex,
            FormattingParserFunction formattingParser,
            FormattingApplierFunction formattingApplier
    ) {
        FormatParsingRule parsingRule = new FormatParsingRule(markupRegex, formattingParser);
        FormatApplicationRule applicationRule = new FormatApplicationRule(formatting, formattingApplier);
        registerForLanguage(formatting, language, parsingRule, applicationRule);
    }

    /**
     * Registers all the rules necessary to interpret a {@code type} of {@link TextFormatting} as it exists in a
     * {@link MarkupLanguage} ({@code language}). Registration enables OneFeed to translate text between markup
     * languages, remove markup, and generate language-agnostic formatting trees. If a rule pair has already been
     * registered for the formatting and language, it is overridden by the arguments provided here.
     *
     * @param formatting the type of {@code TextFormatting} to register {@code language}-specific markup methods for
     * @param language the type of {@code MarkupLanguage} that the parsing and application rules work in
     * @param parsingRule the {@link FormatParsingRule} specifying how {@code formatting} instances should be generated
     * from text that contains {@code formatting}-type, {@code language} markup (i.e., text that contains bold markup in
     * HTML)
     * @param applicationRule the {@link FormatApplicationRule} specifying how a {@code formatting} instance should
     * be applied to text using markup language {@code language}
     */
    public static void registerForLanguage(
            Class<? extends TextFormatting> formatting, Class<? extends MarkupLanguage> language,
            FormatParsingRule parsingRule, FormatApplicationRule applicationRule
    ) {
        ComplementaryFormatRulePair rulePair = new ComplementaryFormatRulePair(parsingRule, applicationRule);
        registerForLanguage(formatting, language, rulePair);
    }

    /**
     * Registers all the rules necessary to interpret a {@code type} of {@link TextFormatting} as it exists in a
     * {@link MarkupLanguage} ({@code language}). Registration enables OneFeed to translate text between markup
     * languages, remove markup, and generate language-agnostic formatting trees. If a rule pair has already been
     * registered for the formatting and language, it is overridden by the arguments provided here.
     *
     * @param formatting the type of {@code TextFormatting} to register {@code language}-specific markup methods for
     * @param language the type of {@code MarkupLanguage} that the parsing and application rules work in
     * @param rulePair a {@link ComplementaryFormatRulePair} containing markup application and extraction functions
     * for the chosen {@code language}, meaning they can undo each other's results
     */
    public static void registerForLanguage(
            Class<? extends TextFormatting> formatting, Class<? extends MarkupLanguage> language,
            ComplementaryFormatRulePair rulePair
    ) {
        if (!formattingsByType.containsKey(formatting)) {
            formattingsByType.put(formatting, new HashMap<>());
        }
        formattingsByType.get(formatting).put(language, rulePair);

        if (!formattingsByLang.containsKey(language)) {
            formattingsByLang.put(language, new HashMap<>());
        }
        formattingsByLang.get(language).put(formatting, rulePair);
    }

    /**
     * Removes the provided {@code formatting} type and its markup language formatting rules from the registry entirely.
     * <br><br>
     * Important to note: third-party modifications may re-register rules at runtime. This is not a default behavior of
     * OneFeed though.
     *
     * @param formatting the type of {@link TextFormatting} to remove from the registry
     *
     * @throws UnknownTextStyleEntityException if the provided {@code markupLang} does not exist in the
     * {@link TextFormattingRegistry}
     */
    public static void removeFormatting(Class<? extends TextFormatting> formatting)
            throws UnknownTextStyleEntityException
    {
        // Ensure the formatting type actually exists
        if (!formattingsByType.containsKey(formatting)) {
            throw new UnknownTextStyleEntityException(
                "The desired TextFormatting type \"" + formatting.getSimpleName() +
                "\" cannot be removed from the registry as it does not exist in it.");
        }

        // Remove all instances of the formatting as it appears in the registry
        formattingsByType.remove(formatting);
        for (Class<? extends MarkupLanguage> language : formattingsByLang.keySet()) {
            formattingsByLang.get(language).remove(formatting);
        }
    }

    /**
     * Forgets the provided markup {@code language} type and its formatting types from the registry entirely.
     * <br><br>
     * Important to note: "forget" is used here as languages are never explicitly registered, they are only inferred
     * through registration of each {@link TextFormatting} type's rule pairs.
     * <br><br>
     * Additionally, it is important to remember that third-party modifications may re-register rules at runtime. This
     * is not a default behavior of OneFeed though.
     *
     * @param language the type of {@link MarkupLanguage} to remove from the registry
     *
     * @throws UnknownTextStyleEntityException if the provided {@code markupLang} does not exist in the
     * {@link TextFormattingRegistry}
     */
    public static void forgetLanguage(Class<? extends MarkupLanguage> language)
            throws UnknownTextStyleEntityException
    {
        // Ensure the markup language type actually exists
        if (!formattingsByLang.containsKey(language)) {
            throw new UnknownTextStyleEntityException(
                    "The desired MarkupLanguage type \"" + language.getSimpleName() +
                    "\" cannot be removed from the registry as it does not exist in it.");
        }

        // Remove all instances of the markup language as it appears in the registry
        formattingsByLang.remove(language);
        for (Class<? extends TextFormatting> formatting : formattingsByType.keySet()) {
            formattingsByType.get(formatting).remove(language);
        }
    }

    /**
     * Removes the rules specified for the provided type of {@code formatting} in the provided type of markup
     * {@code language}.
     *
     * @param formatting the type of {@code TextFormatting} whose markup rules for {@code language} to remove
     * @param language the type of {@code MarkupLanguage} whose rules to remove for the provided type of
     * {@code formatting}
     *
     * @throws UnknownTextStyleEntityException if rules for the provided {@code formatting} in language
     * {@code language} do not exist in the registry
     */
    public static void removeRulesFor(
            Class<? extends TextFormatting> formatting, Class<? extends MarkupLanguage> language)
            throws UnknownTextStyleEntityException
    {
        // Ensure both types actually exist in the registry
        throwExceptionIfEntryDne(formatting, language);

        formattingsByLang.get(language).remove(formatting);
        formattingsByType.get(formatting).remove(language);
    }

    /**
     * Clears the entire registry.
     */
    public static void clearRegistry() {
        formattingsByType.clear();
        formattingsByLang.clear();
    }

    /**
     * Gets the {@link MarkupLanguage}s known via registration of {@link TextFormatting} types.
     *
     * @return an unordered array of the {@code MarkupLanguage}s known by OneFeed at the time of invocation
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends MarkupLanguage>[] getKnownLanguages() {
        return (Class<? extends MarkupLanguage>[])formattingsByLang.keySet().toArray(new Class[0]);
    }

    /**
     * Gets all of the {@link TextFormatting} types registered via {@link #registerForLanguage}.
     *
     * @return an unordered array of the {@code TextFormatting} types registered to OneFeed at the time of invocation
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends TextFormatting>[] getRegisteredFormattings() {
        return (Class<? extends TextFormatting>[])formattingsByType.keySet().toArray(new Class[0]);
    }

    /**
     * Gets all of the {@link MarkupLanguage} types supported by the specified {@code formatting} type.
     *
     * @param formatting the type of {@link TextFormatting} whose supported {@code MarkupLanguage} types to get
     *
     * @return an unordered array of the {@code MarkupLanguage} types supported by the provided {@code formatting}
     * @throws UnknownTextStyleEntityException if the provided {@code markupLang} does not exist in the
     * {@link TextFormattingRegistry}
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends MarkupLanguage>[] getLanguagesSupportedBy(Class<? extends TextFormatting> formatting)
            throws UnknownTextStyleEntityException
    {
        // Ensure the formatting type is actually registered
        if (!formattingsByLang.containsKey(formatting)) {
            throw new UnknownTextStyleEntityException("The specified TextFormatting, \"" + formatting.getSimpleName()
                + "\", has not been registered to OneFeed");
        }

        return (Class<? extends MarkupLanguage>[])formattingsByType.get(formatting).keySet().toArray(new Class[0]);
    }

    /**
     * Gets all of the {@link TextFormatting} types supported by the specified markup {@code language} type.
     *
     * @param language the type of {@link MarkupLanguage} whose supported {@code TextFormatting} types to get
     *
     * @return an unordered array of the {@code TextFormatting} types supported by the provided {@code language}
     * @throws UnknownTextStyleEntityException if the provided {@code markupLang} does not exist in the
     * {@link TextFormattingRegistry}
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends TextFormatting>[] getFormattingsSupportedBy(Class<? extends MarkupLanguage> language)
            throws UnknownTextStyleEntityException
    {
        // Ensure the markup language type is actually known
        if (!formattingsByLang.containsKey(language)) {
            throw new UnknownTextStyleEntityException("The specified MarkupLanguage, \"" + language.getSimpleName()
                    + "\", has not been registered to OneFeed");
        }
        return (Class<? extends TextFormatting>[])formattingsByLang.get(language).keySet().toArray(new Class[0]);
    }

    /**
     * Gets the {@link FormatParsingRule} for extracting instances of the specified {@code formatting} from text marked
     * up with it in {@code language}.
     *
     * @param formatting the type of {@code language-supporting} {@link TextFormatting} whose {@code FormatParsingRule}
     * to retrieve
     * @param language the type of {@link MarkupLanguage} whose {@code FormatParsingRule} to try getting for the
     * provided {@code formatting}
     *
     * @return the {@code FormatParsingRule} for extracting {@code formatting}-type {@code TextFormatting} instances
     * from texted marked up with the formatting as it manifests in the markup {@code language}
     * @throws UnknownTextStyleEntityException  if either the {@code formatting} or {@code language} type cannot be
     * found in the registry, indicating the absence of an entry matching the specifications
     */
    public static FormatParsingRule getParsingRuleFor(
            Class<? extends TextFormatting> formatting, Class<? extends MarkupLanguage> language)
            throws UnknownTextStyleEntityException
    {
        // Ensure both types actually exist in the registry
        throwExceptionIfEntryDne(formatting, language);

        return formattingsByType.get(formatting).get(language).getParsingRule();
    }

    /**
     * Gets the {@link FormatApplicationRule} for marking up text with an instance of the specified {@code formatting}
     * using the provided {@code language}.
     *
     * @param formatting the type of {@code language-supporting} {@link TextFormatting} whose
     * {@code FormatApplicationRule} to retrieve
     * @param language the type of {@link MarkupLanguage} whose {@code FormatApplicationRule} to try getting for the
     * provided {@code formatting}
     *
     * @return the {@code FormatApplicationRule} for applying {@code formatting}-type {@code TextFormatting} instances
     * to text using {@code language}-type markup
     * @throws UnknownTextStyleEntityException  if either the {@code formatting} or {@code language} type cannot be
     * found in the registry, indicating the absence of an entry matching the specifications
     */
    public static FormatApplicationRule getApplicationRuleFor(
            Class<? extends TextFormatting> formatting, Class<? extends MarkupLanguage> language)
            throws UnknownTextStyleEntityException
    {
        // Ensure both types actually exist in the registry
        throwExceptionIfEntryDne(formatting, language);

        return formattingsByType.get(formatting).get(language).getApplicationRule();
    }

    /**
     * Gets the {@link ComplementaryFormatRulePair} for applying and extracting text with {@code formatting}-type
     * {@link TextFormatting} using {@link MarkupLanguage} {@code language}.
     *
     * @param formatting the type of {@code language-supporting} {@link TextFormatting} whose
     * {@code ComplementaryFormatRulePair} to retrieve
     * @param language the type of {@link MarkupLanguage} whose {@code ComplementaryFormatRulePair} to try getting for
     * the provided {@code formatting}
     *
     * @return the {@code ComplementaryFormatRulePair} for applying {@code formatting}-type {@code TextFormatting}
     * instances to text using {@code language}-type markup
     * @throws UnknownTextStyleEntityException if either the {@code formatting} or {@code language} type cannot be
     * found in the registry, indicating the absence of an entry matching the specifications
     */
    public static ComplementaryFormatRulePair getComplementaryRulePairFor(
            Class<? extends TextFormatting> formatting, Class<? extends MarkupLanguage> language)
            throws UnknownTextStyleEntityException
    {
        // Ensure both types actually exist in the registry
        throwExceptionIfEntryDne(formatting, language);

        return formattingsByType.get(formatting).get(language);
    }

    /**
     * Throws an {@link IllegalStateException} if an entry does not exist (DNE) for the provided {@code formatting}
     * and/or {@code language}.
     *
     * @param formatting the type of {@link TextFormatting} whose existence to check for in the registry
     * @param language the type of {@link MarkupLanguage} whose existence to check for in the registry
     *
     * @throws UnknownTextStyleEntityException if either the {@code formatting} or {@code language} could not be found
     * in the registry
     */
    private static void throwExceptionIfEntryDne(
            Class<? extends TextFormatting> formatting, Class<? extends MarkupLanguage> language)
            throws UnknownTextStyleEntityException
    {
        if (!formattingsByLang.containsKey(language) && !formattingsByType.containsKey(formatting)) {
            throw new UnknownTextStyleEntityException("The specified TextFormatting, \"" + formatting.getSimpleName() +
                    "\", and MarkupLanguage, \"" + language.getSimpleName() + "\", do not exist in the registry.");
        } else if (!formattingsByLang.containsKey(language)) {
            throw new UnknownTextStyleEntityException("The specified TextFormatting, \"" + formatting.getSimpleName() +
                    "\" does not exist in the registry for MarkupLanguage \"" + language.getSimpleName() + "\"");
        } else if (!formattingsByType.containsKey(formatting)) {
            throw new UnknownTextStyleEntityException("The specified MarkupLanguage, \"" + formatting.getSimpleName() +
                    "\" does not exist in the registry for TextFormatting \"" + language.getSimpleName() + "\"");
        }
    }
}