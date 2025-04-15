package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.function.Function;
import java.util.regex.Matcher;
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
 * extractFrom()} method should mimic the functionality of {@link #extract(String, Class)}.
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
     * @implSpec
     * If no {@code Function} exists to generate {@code MarkedUpText} in the desired {@code markupLang} type,
     * {@code null} should be returned.
     *
     * @implNote
     * If the {@code Function} provided by this method's implementation generates {@code MarkedUpText} that does not
     * employ the desired {@code markupLang}, then attempts to use the applier may produce unknown and undesirable
     * behavior. By default, OneFeed's formatting approach will not produce any exceptions in such circumstances as a
     * mismatch may be intentional and can be handled automatically.
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
     */
    private Function<String, MarkedUpText> findMarkupLangApplierFor(Class<? extends MarkupLanguage> markupLang) throws
            NoSuchMethodException {
        Function<String, MarkedUpText> applier = this.getMarkupLangApplierFor(markupLang);

        // If an applier can't be found for the markupLang, throw an exception indicating the desired Formatting does
        // not specify a formatting implementation for the desired markup language
        if (applier == null) {
            throw new NoSuchMethodException("A formatting applier method could not be found in " +
                    this.getClass().getName() + " for markup language " + markupLang.getSimpleName());
        }

        return applier;
    }

    /**
     * Applies the desired formatting to the provided {@code text} to generated some {@link MarkedUpText}.
     * <br><br>
     * Note that the produced {@code MarkedUpText} may not employ the same {@code MarkupLanguage} as specified by
     * {@code markupLang}. In some cases, this may be the intended behavior of a {@code MarkupLanguage} implementor and
     * is handled without issue by OneFeed. If a mismatch is unacceptable in the environment in which this method is
     * employed though, consider checking whether the generated {@code MarkedUpText} employs the intended {@code
     * markupLang}.
     *
     * @param text the {@code String} to apply the desired formatting to
     *
     * @return {@code text} formatted in the desired way in the {@code MarkupLanguage}, bundled as {@link MarkedUpText}
     * @throws NoSuchMethodException when an applier method could not be found in {@code this} type's implementor for
     *     the target {@code markupLang}
     */
    public default MarkedUpText apply(String text, Class<? extends MarkupLanguage> markupLang) throws
            NoSuchMethodException {
        Function<String, MarkedUpText> applier = this.findMarkupLangApplierFor(markupLang);
        return applier.apply(text);
    }

    /**
     * Extracts the content from the single element in {@code rawText} using the provided {@code startBound} and
     * {@code endBound}.
     *
     * @param rawText a {@code String} containing a single HTML element represented by {@code startBound} and
     * {@code endBound}
     * @param startBound a pattern representing the opening tag of the desired HTML element in {@code rawText} whose
     *                 content to extract
     * @param endBound a pattern representing the closing tag of the desired HTML element in {@code rawText} whose content
     *               to extract
     *
     * @return the content contained within the element delimited by the provided {@code startBound} and {@code endBound}
     * @throws IllegalStateException if the provided tags cannot be found in {@code rawText}
     */
    public static String parseTextBetweenBounds(String rawText, Pattern startBound, Pattern endBound)
            throws IllegalStateException {
        Matcher match;
        int contentStart = -1, contentEnd = -1;

        // Get the leftmost tag
        match = startBound.matcher(rawText);
        if (match.find()) {
            contentStart = match.end() + 1;
        }

        // Get the rightmost tag
        match = endBound.matcher(rawText);
        if (match.find()) {
            contentEnd = match.start();
        }

        // If both outermost tags couldn't be found
        if (contentStart == -1 || contentEnd == -1) {
            throw new IllegalStateException("A complete pair of boundaries could not be found in the provided String: " + rawText);
        }

        return rawText.substring(contentStart, contentEnd);
    }

    /**
     * Produces a {@link FormattingMarkedText} object with text matching {@code text}'s content as bounded by the
     * {@code startBound} and {@code endBound} patterns, stripped of that markup, and the included
     * {@link TextFormatting} matching that specified by {@code label} (assuming parsing is successful).
     *
     * @param text some {@code MarkedUpText} containing text with the desired, markup-bounded formatting applied
     * @param startBound a pattern representing the opening tag of the desired HTML element in {@code rawText} whose
     *                 content to extract
     * @param endBound a pattern representing the closing tag of the desired HTML element in {@code rawText} whose content
     *               to extract
     * @param label the desired {@code TextFormatting} with which to include in the generated
     * {@code FormattingMarkedText} when a parse is successful
     *
     * @return the content contained within the element delimited by the provided {@code startBound} and
     * {@code endBound}, contained in a {@code FormattingMarkedText} object with {@code TextFormatting} {@code label}
     * @throws IllegalStateException if the desired element couldn't be found in the {@code text}
     */
    public static FormattingMarkedText parseFmtBetweenBounds(MarkedUpText text, Pattern startBound, Pattern endBound,
            TextFormatting label) throws IllegalStateException {
        String rawText = text.getText();

        // If a match can't be found, fall back to using the text's rawText labelled with the provided fallback
        String content = parseTextBetweenBounds(rawText, startBound, endBound);
        return new FormattingMarkedText(content, label);
    }

    /**
     * Determines which of the provided {@code regex} {@link Pattern}s has the earliest match in the {@code text}, if
     * any.
     *
     * @param text the {@link MarkedUpText} possibly containing a substring matching one of the provided {@code regexes}
     * @param regexes the {@code Pattern}s to search for in {@code MarkedUpText}'s text, in the order they were passed
     *                as an argument
     *
     * @return the {@code Pattern} in {@code regexes} that appears first in the {@code MarkedUpText}'s text, if a match
     * for any exists
     * @throws IllegalStateException if none of the provided {@code regexes} have a match in the provided {@code text}
     */
    public static Pattern findEarliestMatch(MarkedUpText text, Pattern ... regexes) throws IllegalStateException {
        String rawText = text.getText();
        int earliestMatchPos = rawText.length();
        Pattern earliestMatch = null;
        Matcher matcher;

        // Find the pattern with the earliest match in the text
        for (Pattern regex : regexes) {
            matcher = regex.matcher(rawText);
            if (matcher.find() && matcher.start() < earliestMatchPos) {
                earliestMatchPos = matcher.start();
                earliestMatch = regex;
            }
        }

        if (earliestMatch == null) {
            throw new IllegalStateException("No match could be found for any provided regex in string: " + rawText);
        }

        return earliestMatch;
    }
}