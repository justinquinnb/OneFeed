package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
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

    private static final HashMap<
            Class<? extends TextFormatting>,
            HashMap<
                    Class<? extends MarkupLanguage>,
                    FormatParsingRule
                    >
            >
            formattingsByType = new HashMap<>();

    private static final HashMap<
            Class<? extends MarkupLanguage>,
            HashMap<
                    Class<? extends TextFormatting>,
                    FormatParsingRule
                    >
            >
            formattingsByLang = new HashMap<>();

    public static void registerForLanguage(
            Class<? extends TextFormatting> type, Class<? extends MarkupLanguage> language,
            Pattern markupRegex, Function<MarkedUpText, FormattingMarkedText> formattingExtractor
    ) {
        // TODO add the exception thrown below here
        // TODO continue making other formatting methods in TextFormatting classes static, removing ExtdMd methods,
        // and implementing the static {} register calls
        FormatParsingRule rule = new FormatParsingRule(markupRegex, formattingExtractor);
        registerForLanguage(type, language, rule);
    }

    public static void registerForLanguage(
            Class<? extends TextFormatting> type, Class<? extends MarkupLanguage> language, FormatParsingRule rule
    ) {
        // TODO throw an exception if the TextFormatting type does not implement the specified MarkupLanguage type
        // that type must be implemented to register for the language as failure to do so removes the guarantee of
        // application ability
        formattingsByType.computeIfAbsent(type, t -> new HashMap<>()).put(language, rule);
        formattingsByLang.computeIfAbsent(language, t -> new HashMap<>()).put(type, rule);
    }

    public static Class<? extends MarkupLanguage>[] getKnownLangs() {
        return (Class<? extends MarkupLanguage>[])formattingsByLang.keySet().toArray(new Class[0]);
    }

    public static Class<? extends TextFormatting>[] getKnownFormattings() {
        return (Class<? extends TextFormatting>[])formattingsByType.keySet().toArray(new Class[0]);
    }

    public static Class<? extends MarkupLanguage>[] getSupportedLangsOf(Class<? extends TextFormatting> formatting) {
        return (Class<? extends MarkupLanguage>[])formattingsByType.get(formatting).keySet().toArray(new Class[0]);
    }

    public static Class<? extends TextFormatting>[] getSupportedFormattingsOf(Class<? extends MarkupLanguage> language) {
        return (Class<? extends TextFormatting>[])formattingsByLang.get(language).keySet().toArray(new Class[0]);
    }

    public static FormatParsingRule getParsingRuleFor(
            Class<? extends TextFormatting> formatting, Class<? extends MarkupLanguage> language) {
        return formattingsByType.get(formatting).get(language);
    }
}