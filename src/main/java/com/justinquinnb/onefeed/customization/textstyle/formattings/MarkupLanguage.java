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
        Pattern markupPattern = this.getMarkupPatternFor(markupLang);

        // If a pattern can't be found for the markupLang, throw an exception indicating the desired Formatting does
        // not specify a markup pattern for the desired markup language
        if (markupPattern == null) {
            throw new NoSuchPatternException("A regex pattern could not be found in " +
                    this.getClass().getName() + " for markup language " + markupLang.getSimpleName());
        }

        return markupPattern;
    }

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
     * @implSpec
     * If no {@code Function} exists to produce {@code FormattingMarkedText} from {@code MarkedUpText} in the
     * specified language, {@code null} should be returned.
     *
     * @implNote
     * If the {@code Function} provided by this method's implementation generates {@code FormattingMarkedText} that does
     * not match the implementing object's type, then attempts to use the extractor may produce unknown and
     * undesirable behavior. By default, OneFeed's formatting approach will not produce any exceptions in such
     * circumstances as a mismatch may be intentional and can be handled automatically.
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
     */
    private Function<MarkedUpText, FormattingMarkedText> findFormattingExtractorFor(
            Class<? extends MarkupLanguage> markupLang) throws NoSuchMethodException {
        Function<MarkedUpText, FormattingMarkedText> extractor = this.getFormattingExtractorFor(markupLang);

        // If an extractor can't be found for the markupLang, throw an exception indicating the desired Formatting does
        // not specify a parsing implementation for the desired markup language
        if (extractor == null) {
            throw new NoSuchMethodException("A formatting extractor method could not be found in " +
                    this.getClass().getName() + " for markup language " + markupLang.getSimpleName());
        }

        return extractor;
    }

    /**
     * Generates a {@link FormattingMarkedText} object that specifies how the {@code text} should be formatted given
     * the markup it contains in language {@code markupLang}. The returned {@code FormattingMarkedText} contains the
     * original {@code text} with all of its markup removed, making it markup-language-agnostic.
     * <br><br>
     * Note that the produced {@code FormattingMarkedText} may not employ the same type of {@link TextFormatting} as
     * {@code this} {@code MarkupLanguage} implementor. In some cases, this may be the intended behavior of a
     * {@code MarkupLanguage} implementor and is handled without issue by OneFeed. If a mismatch is unacceptable in the
     * environment in which this method is employed though, consider checking whether the generated
     * {@code FormattingMarkedText} employs the intended {@code TextFormatting} type.
     *
     * @param text the text formatted as {@code this} {@link MarkupLanguage}-implementing type, as specified by its
     *             markup in language {@code markupLang}
     * @param markupLang the type of {@code MarkupLanguage} employed by the format-specifying markup in {@code text}
     *
     * @return a {@code FormattingMarkedText} object associating an instance of {@code this}
     * {@code MarkupLanguage}-implementing type representing the formatting specified by the {@code markupLang}-type
     * markup in {@code text} with a copy of {@code text} that has all of its markup removed
     *
     * @throws NoSuchMethodException if a method couldn't be found to derive {@code FormattingMarkedText} from the
     * provided, {@code markupLang}-marked {@code text}
     *
     * @implNote
     * If the desired {@code TextFormatting} could not be extracted from the provided {@code text}, it is strongly
     * encouraged to return a new {@code FormattingMarkedText} instance containing the original {@code text} as-is with
     * a default {@code TextFormatting} type such as the provided {@link DefaultFormat}.
     * <br><br>
     * Additionally, the {@code text} provided to this method may not contain markup for the implementing type. In the
     * majority of cases, the {@code text} will contain just the content itself and its surrounding and embedded
     * formatting markup (see example A). However, as it is possible for the text to include more (example B), it is
     * encouraged to implement parsing that can account for this edge case, even when that edge case has no practical
     * use, does not adhere to the principles of the system, and therefore should not be employed.<br>
     * <b>Examples in HTML:</b>
     * <ul>
     *     <li>
     *         <b>Example A:</b> Expected {@code String} in the provided {@code MarkedUpText}<br>
     *         {@code <b>content <i>text</i></b>}
     *     </li>
     *     <li>
     *         <b>Example B:</b> Possible (but unlikely) edge case<br>
     *         {@code Junk surrounding the <b>content <i>text</i></b>}
     *     </li>
     * </ul>
     */
    public default FormattingMarkedText extract(String text, Class<? extends MarkupLanguage> markupLang)
            throws NoSuchMethodException {
        Function<MarkedUpText, FormattingMarkedText> extractor = this.findFormattingExtractorFor(markupLang);
        return extractor.apply(new MarkedUpText(text, markupLang));
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