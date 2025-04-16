package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.ComplementaryFormatRulePair;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.application.FormatApplicationRule;
import com.justinquinnb.onefeed.customization.textstyle.parsing.FormatParsingRule;

import java.util.HashMap;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Holds all known {@link TextFormatting} types alongside their markup regex, markup applier function, and markup
 * extraction functions per {@link MarkupLanguage}.
 */
public class TextFormattingRegistry {
    // TODO document
    /**
     *
     */
    private static final HashMap<
            Class<? extends TextFormatting>,
            HashMap<
                    Class<? extends MarkupLanguage>,
                    ComplementaryFormatRulePair
                    >
            >
            formattingsByType = new HashMap<>();

    /**
     *
     */
    private static final HashMap<
            Class<? extends MarkupLanguage>,
            HashMap<
                    Class<? extends TextFormatting>,
                    ComplementaryFormatRulePair
                    >
            >
            formattingsByLang = new HashMap<>();

    /**
     *
     *
     * @param type
     * @param language
     * @param markupRegex
     * @param formattingExtractor
     *
     * @throws IllegalArgumentException
     */
    public static void registerForLanguage(
            Class<? extends TextFormatting> type, Class<? extends MarkupLanguage> language, Pattern markupRegex,
            Function<MarkedUpText, FormattingMarkedText> formattingExtractor,
            Function<FormattingMarkedText, MarkedUpText> formattingApplier
    ) throws IllegalArgumentException {
        // TODO add the exception thrown below here
        // TODO continue making other formatting methods in TextFormatting classes static, removing ExtdMd methods,
        // and implementing the static {} register calls
        FormatParsingRule parsingRule = new FormatParsingRule(markupRegex, formattingExtractor);
        FormatApplicationRule applicationRule = new FormatApplicationRule(type, formattingApplier);
        registerForLanguage(type, language, new ComplementaryFormatRulePair(parsingRule, applicationRule));
    }

    /**
     *
     *
     * @param type
     * @param language
     * @param parsingRule
     * @param applicationRule
     *
     * @throws IllegalArgumentException
     */
    public static void registerForLanguage(
            Class<? extends TextFormatting> type, Class<? extends MarkupLanguage> language,
            FormatParsingRule parsingRule, FormatApplicationRule applicationRule
    ) throws IllegalArgumentException {

        // Ensure the desired TextFormatting type actually implements the language
        if (!type.isInstance(language)) {
            throw new IllegalArgumentException("Provided type \"" + type.getSimpleName() +
                    "\" does not implement desired language \"" + language.getSimpleName() + "\"");
        }

        ComplementaryFormatRulePair rulePair = new ComplementaryFormatRulePair(parsingRule, applicationRule);

        formattingsByType.computeIfAbsent(type, t -> new HashMap<>()).put(language, rulePair);
        formattingsByLang.computeIfAbsent(language, t -> new HashMap<>()).put(type, rulePair);
    }

    /**
     *
     *
     * @param type
     * @param language
     * @param rulePair
     *
     * @throws IllegalArgumentException
     */
    public static void registerForLanguage(
            Class<? extends TextFormatting> type, Class<? extends MarkupLanguage> language,
            ComplementaryFormatRulePair rulePair
    ) throws IllegalArgumentException {

        // Ensure the desired TextFormatting type actually implements the language
        if (!type.isInstance(language)) {
            throw new IllegalArgumentException("Provided type \"" + type.getSimpleName() +
                    "\" does not implement desired language \"" + language.getSimpleName() + "\"");
        }

        formattingsByType.computeIfAbsent(type, t -> new HashMap<>()).put(language, rulePair);
        formattingsByLang.computeIfAbsent(language, t -> new HashMap<>()).put(type, rulePair);
    }

    /**
     *
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends MarkupLanguage>[] getKnownLangs() {
        return (Class<? extends MarkupLanguage>[])formattingsByLang.keySet().toArray(new Class[0]);
    }

    /**
     *
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends TextFormatting>[] getKnownFormattings() {
        return (Class<? extends TextFormatting>[])formattingsByType.keySet().toArray(new Class[0]);
    }

    /**
     *
     *
     * @param formatting
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends MarkupLanguage>[] getSupportedLangsOf(Class<? extends TextFormatting> formatting) {
        return (Class<? extends MarkupLanguage>[])formattingsByType.get(formatting).keySet().toArray(new Class[0]);
    }

    /**
     *
     *
     * @param language
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends TextFormatting>[] getSupportedFormattingsOf(Class<? extends MarkupLanguage> language) {
        return (Class<? extends TextFormatting>[])formattingsByLang.get(language).keySet().toArray(new Class[0]);
    }

    /**
     *
     *
     * @param formatting
     * @param language
     *
     * @return
     */
    public static FormatParsingRule getParsingRuleFor(
            Class<? extends TextFormatting> formatting, Class<? extends MarkupLanguage> language) {
        return formattingsByType.get(formatting).get(language);
    }
}