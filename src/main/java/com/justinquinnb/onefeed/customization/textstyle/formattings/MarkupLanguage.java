package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * A markup language such as HTML or Markdown. Implementors specify the generation of {@link MarkedUpText} using their
 * type of {@code MarkupLanguage} provided some text.
 *
 * @implNote Intended use is for {@link TextFormatting} types to implement children of this interface to indicate their
 * ability to be represented in various markup languages through methods they, themselves, contain.
 * @implSpec <b>Defining New Languages and Their Supersets</b><br>
 * As there is no good way to represent the concept of set theory required to mimic the concept of markup language
 * supersets, all supersets or extensions of a language should be defined as separate interfaces extending
 * {@code MarkupLanguage}.
 * <br><br><b>Encouraged Method Prototypes</b><br>
 * While not required, it is strongly encouraged to prototype {@code apply___()}, {@code get___Pattern()}, and
 * {@code extractFrom___()} methods (where {@code ___} equals an abbreviation of the language name) for consistency’s
 * sake. The {@code apply___()} method should mimic the functionality of {@link #apply(String, Class)}; the {@code
 * get___Pattern()} method should mimic the functionality of {@link #getMarkupPatternFor(Class)}; and the {@code
 * extractFrom()} method should mimic the functionality of {@link #extract(MarkedUpText)}.
 * <br><br>
 * The motivation for this is requiring {@code TextFormatting}s that implement a {@code MarkupLanguage} to
 * individually implement and distinguish the requirements of each language.
 */
public interface MarkupLanguage {
    // MARKING UP TEXT IN DIFFERENT MARKUP LANGUAGES
    /**
     * Provides a {@code Function} capable of marking up a {@code String} into {@link MarkedUpText} employing
     * {@link MarkupLanguage} {@code markupLang}.
     *
     * @param markupLang the desired {@code MarkupLanguage} that the applier's generated {@code MarkedUpText} should
     *                   employ
     *
     * @return the {@code Function} capable of marking up a {@code String} into {@link MarkedUpText} employing
     * {@link MarkupLanguage} {@code markupLang}
     *
     * @implSpec If no {@code Function} exists to generate {@code MarkedUpText} in the desired {@code markupLang} type,
     * {@code null} should be returned.
     * <br><br>
     * If the {@code Function} provided by this method's implementation generates {@code MarkedUpText} that does not
     * employ the desired {@code markupLang}, then attempts to use the applier will throw an
     * {@link IoMismatchException} via {@link #apply}, the handling of which is unknown if OneFeed's default
     * approach is overridden.
     */
    public Function<String, MarkedUpText> getMarkupLangApplierFor(Class<? extends MarkupLanguage> markupLang);

    /**
     * Attempts to find a markup-applying function in {@code this} type's implementor for the target {@code markupLang}.
     *
     * @param markupLang the {@code markupLang} to try to find a method for in {@code this} type's implementor
     *
     * @return the method capable of generating {@link MarkedUpText} in the desired {@code markupLang}
     *
     * @throws NoSuchMethodException when an applier method could not be found in {@code this} type's implementor for
     *     the target {@code markupLang}
     * @throws IoMismatchException when an applier method is found, but generates {@code MarkedUpText} that doesn't
     *     employ the target {@code markupLang}
     */
    private Function<String, MarkedUpText> findMarkupLangApplierFor(Class<? extends MarkupLanguage> markupLang) throws
            NoSuchMethodException, IoMismatchException {
        Function<String, MarkedUpText> applier = this.getMarkupLangApplierFor(markupLang);

        // If an applier can't be found for the markupLang, throw an exception indicating the desired Formatting does
        // not specify a formatting implementation for the desired markup language
        if (applier == null) {
            throw new NoSuchMethodException("A formatting applier method could not be found in " +
                    this.getClass().getName() + " for markup language " + markupLang.getSimpleName());
        }

        // Because the applier methods are non-static, we can attempt to retrieve MarkedUpText using any String at any
        // time. Leverage this ability to check if the applier provided by getMarkupLangApplierFor() actually
        // generates MarkedUpText that uses strictly the desired markupLang.
        MarkedUpText testText = applier.apply("");

        ArrayList<Class<? extends MarkupLanguage>> employedLangs = testText.getMarkupLanguages();

        // Ensure only the desired markupLang is used in the generated text
        if (employedLangs.size() == 1 && markupLang.isAssignableFrom(employedLangs.getFirst())) {
            // Gather the simple names of each markup lang used to they can be provided in the exception message
            String[] langNames = new String[employedLangs.size()];
            for (int i = 0; i < employedLangs.size(); i++) {
                langNames[i] = employedLangs.get(i).getSimpleName();
            }

            throw new IoMismatchException(this.getClass().getSimpleName() + "'s implementation of " +
                    "getMarkupLangApplierFor() provides a method generating MarkedUpText of MarkupLanguage(s) " +
                    Arrays.toString(langNames) + " instead of the expected " + markupLang.getSimpleName());
        }

        return applier;
    }

    /**
     * Applies the desired formatting to the provided {@code text} to generated some {@code MarkedUpText}.
     *
     * @param text the {@code String} to apply the desired formatting to
     *
     * @return {@code text} formatted in the desired way in the {@code MarkupLanguage}, packaged as {@link MarkedUpText}
     * @throws NoSuchMethodException when an applier method could not be found in {@code this} type's implementor for
     *     the target {@code markupLang}
     * @throws IoMismatchException when {@code MarkedUpText} that doesn't employ the target {@code markupLang} is
     * generated by the implementing type
     */
    public default MarkedUpText apply(String text, Class<? extends MarkupLanguage> markupLang) throws
            NoSuchMethodException, IoMismatchException {
        Function<String, MarkedUpText> applier = this.findMarkupLangApplierFor(markupLang);
        return applier.apply(text);
    }

    // GETTING THE FORMATTING'S REPRESENTATION IN DIFFERENT MARKUP LANGUAGES
    /**
     * Provides the {@link Pattern} delimiting a type of {@link TextFormatting} in some {@link MarkedUpText}.
     *
     * @param markupLang the desired {@code MarkupLanguage} that the {@code Pattern} should correspond to
     *
     * @return the {@code Pattern} specifying how the implementing type is formatted in the {@code markupLang}
     *
     * @implSpec If no {@code Pattern} exists for the type in the desired {@code markupLang}, {@code null} should be
     * returned.
     */
    public Pattern getMarkupPatternFor(Class<? extends MarkupLanguage> markupLang);

    /**
     * Attempts to get the {@link Pattern} delimiting a type of {@link TextFormatting} in some {@link MarkedUpText}.
     *
     * @param markupLang the desired {@code MarkupLanguage} that the {@code Pattern} should correspond to
     *
     * @return the {@code Pattern} specifying how the implementing type is formatted in the {@code markupLang}
     * @throws NoSuchPatternException when a {@link Pattern} could not be found in {@code this} type's implementor for
     * the target {@code markupLang}
     */
    private Pattern findMarkupPatternFor(Class<? extends MarkupLanguage> markupLang) throws NoSuchPatternException {
        return null;
    }

    // TODO getRegexFor(Class<? extends MarkupLanguage)
    // TODO findRegexFor(Class<? extends MarkupLanguage)
    // TODO getRegex(Class<? extends MarkupLanguage) effectively just getRegexFor... not necessary

    // TODO getFormattingExtractorFor(Class<? extends MarkupLanguage)
    // TODO findFormattingExtractorFor(Class<? extends MarkupLanguage)
    // TODO extractFormatting(MarkedUpText) default method in this class that just combines the results of an invocation
    //  of extractFormatting(String, Class<? extends MarkupLanguage) for each MarkupLanguage the MarkedUpText employs
    // TODO extractFormatting(String, Class<? extends MarkupLanguage )

    // GENERATING TEXT FORMATTING OBJECTS FROM MARKED-UP TEXT
    /**
     * Provides a {@code Function} capable of producing a {@link TextFormatting} object that represents the formatting
     * indicated by some text's markup, as contained in a {@link MarkedUpText} object.
     *
     * @param markupLang the type of {@link MarkupLanguage} that the provided extractor should be able to interpret
     *
     * @return the {@code Function} capable of extracting a {@code TextFormatting} whose contents represent the
     * formatting specified by the text of a {@code MarkedUpText} object's markup
     *
     * @implSpec If no {@code Function} exists to produce {@code FormattingMarkedText} from {@code MarkedUpText} in the
     * specified language, {@code null} should be returned.
     * <br><br>
     * If the {@code Function} provided by this method's implementation generates {@code FormattingMarkedText} that does
     * not match the implementing object's type, then attempts to use the extractor will throw an
     * {@link IoMismatchException} via {@link #extract}, the handling of which is unknown if OneFeed's default
     * approach is overridden.
     */
    public Function<MarkedUpText, FormattingMarkedText> getFormattingExtractorFor(
            Class<? extends MarkupLanguage> markupLang);

    /**
     * Attempts to find a {@code Function} capable of producing a {@link TextFormatting} object that represents the
     * formatting indicated by some text's markup, as contained in a {@link FormattingMarkedText} object.
     *
     * @param markupLang the type of {@link MarkupLanguage} that the provided extractor should be able to interpret
     *
     * @return the {@code Function} capable of extracting a {@code TextFormatting} whose contents represent the
     * formatting specified by the text of a {@code MarkedUpText} object's markup
     *
     * @throws NoSuchMethodException when an extractor method could not be found in {@code this} type's implementor for
     *     the target {@code markupLang}
     * @throws IoMismatchException when an extractor method is found, but generates {@code FormattingMarkedText} that
     * doesn't employ the target {@code markupLang}
     */
    private Function<MarkedUpText, FormattingMarkedText> findFormattingExtractorFor(
            Class<? extends MarkupLanguage> markupLang) throws NoSuchMethodException {
        return null;
    }

    // Make sure to catch possible exceptions due to parsing and handle them by throwing a parsing error that the user of
    // the extract formatting method can choose how to handle
    // TODO add additional check to applier method to chuck the formatted text that it generates into the same type's
    // extractor to ensure it works error-free. If it raises an error, then throw some sort of exception

    /**
     *
     * Generates a {@link FormattingMarkedText} object that specifies how the {@code text} should be formatted given
     * the markup it contains in language {@code markupLang}. The returned {@code FormattingMarkedText} contains the
     * original {@code text} with all of its markup removed, making it markup-language-agnostic.
     *
     * @param text the text formatted as {@code this} {@link MarkupLanguage}-implementing type, as specified by its
     *             markup in language {@code markupLang}
     * @param markupLang the type of {@code MarkupLanguage} employed by the format-specifying markup in {@code text}
     *
     * @return a {@code FormattingMarkedText} object associating an instance of {@code this}
     * {@code MarkupLanguage}-implementing type representing the formatting specified by the {@code markupLang}-type
     * markup in {@code text} with a copy of {@code text} that has all of its markup removed
     *
     * @throws ParseException if the provided {@code text} is malformed for the specified {@code markupLang}
     * @throws NoSuchMethodException if a method couldn't be found to derive {@code FormattingMarkedText} from the
     * provided, {@code markupLang}-marked {@code text}
     * @throws IoMismatchException when {@code FormattingMarkedText} that doesn't reference the invoking
     * {@code MarkupLanguage}-implementing type is generated
     */
    public default FormattingMarkedText extract(String text, Class<? extends MarkupLanguage> markupLang)
            throws ParseException, NoSuchMethodException, IoMismatchException {
        return null;
    }
}