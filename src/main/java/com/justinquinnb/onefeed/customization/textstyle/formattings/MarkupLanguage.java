package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

/**
 * A markup language such as HTML or Markdown. Implementors specify the generation of {@link MarkedUpText} using their
 * type of {@code MarkupLanguage} provided some text.
 *
 * @implNote Intended use is for {@link TextFormatting} types to implement children of this interface to indicate their
 * ability to be represented in various markup languages through methods they, themselves, contain.
 * @implSpec As there is no good way to represent the concept of set theory required to mimic the concept of markup
 * language supersets, all supersets or extensions of a language should be defined as separate interfaces extending
 * {@code MarkupLanguage}.
 */
public interface MarkupLanguage {
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
     * @implSpec If no {@code Function} exists to generated {@code MarkedUpText} in the desired {@code markupLang} type,
     * {@code null} should be returned.
     * <br><br>
     * If the {@code Function} provided by this method's implementation generates {@code MarkedUpText} that does not
     * employ the desired {@code markupLang}, then attempts to use the applier will throw an
     * {@link ApplierMismatchException} via {@link #apply}, the handling of which is unknown if OneFeed's default
     * approach is overridden.
     * <br><br>
     */
    public Function<String, MarkedUpText> getMarkupLangApplierFor(Class<? extends MarkupLanguage> markupLang);

    /**
     * Applies the desired formatting to the provided {@code text} to generated some {@code MarkedUpText}.
     *
     * @param text the {@code String} to apply the desired formatting to
     *
     * @return {@code text} formatted in the desired way in the {@code MarkupLanguage}, packaged as {@link MarkedUpText}
     * @throws NoSuchMethodException when an applier method could not be found in {@code this} type's implementor for
     *     the target {@code markupLang}
     * @throws ApplierMismatchException when an applier method is found, but generates {@code MarkedUpText} that doesn't
     *     employ the target {@code markupLang} or a parent (subset) of it
     */
    public default MarkedUpText apply(String text, Class<? extends MarkupLanguage> markupLang) throws
            NoSuchMethodException, ApplierMismatchException {
        Function<String, MarkedUpText> applier = this.findMarkupLangApplierFor(markupLang);
        return applier.apply(text);
    }

    /**
     * Attempts to find a markup-applying function in {@code this} type's implementor for the target {@code markupLang}.
     *
     * @param markupLang the {@code markupLang} to try to find a method for in {@code this} type's implementor
     *
     * @return the method capable of generating {@link MarkedUpText} in the desired {@code markupLang}.
     *
     * @throws NoSuchMethodException when an applier method could not be found in {@code this} type's implementor for
     *     the target {@code markupLang}
     * @throws ApplierMismatchException when an applier method is found, but generates {@code MarkedUpText} that doesn't
     *     employ the target {@code markupLang} or a parent (subset) of it
     */
    private Function<String, MarkedUpText> findMarkupLangApplierFor(Class<? extends MarkupLanguage> markupLang) throws
            NoSuchMethodException, ApplierMismatchException {
        Function<String, MarkedUpText> applier = this.getMarkupLangApplierFor(markupLang);

        // If an applier can't be found for the markupLang, throw an exception indicating the desired Formatting does
        // not specify a formatting implementation for the desired markup language
        if (applier == null) {
            throw new NoSuchMethodException("A formatting applier method could not be found in " +
                    this.getClass().getName() + " for markup language " + markupLang.getSimpleName());
        }

        // Because the applier methods are non-static, we can attempt to retrieve MarkedUpText using any String at any
        // time. Leverage this ability to check if the applier provided by getMarkupLangApplierFor() actually
        // generates MarkedUpText that uses strictly the desired markupLang (or a subset of it).
        MarkedUpText testText = applier.apply("");

        ArrayList<Class<? extends MarkupLanguage>> employedLangs = testText.getMarkupLanguages();

        // Ensure only the desired markupLang is used in the generated text
        if (employedLangs.size() == 1 && markupLang.isAssignableFrom(employedLangs.getFirst())) {
            // Gather the simple names of each markup lang used to they can be provided in the exception message
            String[] langNames = new String[employedLangs.size()];
            for (int i = 0; i < employedLangs.size(); i++) {
                langNames[i] = employedLangs.get(i).getSimpleName();
            }

            throw new ApplierMismatchException(this.getClass().getSimpleName() + "'s implementation of " +
                    "getMarkupLangApplierFor() provides a method generating MarkedUpText of MarkupLanguage(s) " +
                    Arrays.toString(langNames) + " instead of the expected " + markupLang.getSimpleName());
        }

        return applier;
    }
}