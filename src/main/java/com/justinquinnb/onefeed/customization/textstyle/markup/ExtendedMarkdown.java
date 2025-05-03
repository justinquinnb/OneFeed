package com.justinquinnb.onefeed.customization.textstyle.markup;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMismatchException;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.MarkupLangMismatchException;
import com.justinquinnb.onefeed.customization.textstyle.application.TextFormattingApplier;
import com.justinquinnb.onefeed.customization.textstyle.parsing.TextFormattingParser;

import java.text.ParseException;

/**
 * A type capable of generating {@link MarkedUpText} in Markdown (plus Extended Syntax) provided some text.
 *
 * @implNote
 * While the intention is for multiple types of {@link TextFormatting}s to implement this interface when they are
 * compatible with Extended Markdown, multiple {@code TextFormatting} definitions and behaviors can be
 * defined and registered anywhere this interface is implemented. While it is discouraged for consistency and
 * maintainability, it may be desirable or offer unknown advantages that are open to exploration and utilization.
 *
 * @implSpec
 * <b>Implementing & Registering a Type of {@code MarkupLanguage}</b><br>
 * Most of the functionality that an implementor of a {@code MarkupLanguage} type must perform is static. For this
 * reason, developers must, in the static body of their implementing type, use
 * {@link TextFormattingRegistry#registerForLanguage} for each implemented language for the {@code TextFormatting} and
 * its various {@code MarkupLanguage} type-specific implementations to be recognized by OneFeed. It is advised to
 * create static methods titled {@code extractFrom___}, {@code apply___}, and {@code get___Pattern} methods that are
 * passed as lambdas to the {@code registerForLanguage} function for consistency's sake, but any means of providing a
 * valid function as an argument is acceptable.
 * <br><br>
 * Developers are encouraged to throw the following exceptions for the following methods so their handling can be
 * globally handled by the employed {@link TextFormattingParser} and {@link TextFormattingApplier}:
 * <br><br>
 * <b>{@code extractFrom___() throws }</b>
 *     <ul>
 *         <li><b>{@link MarkupLangMismatchException}</b> when the provided {@link MarkedUpText} does not employ the
 *         expected {@link MarkupLanguage} type.</li>
 *         <li><b>{@link ParseException}</b> when a {@code TextFormatting} instance cannot be parsed from the
 *         {@code MarkedUpText}'s text.
 *     </ul>
 * <b>{@code apply___() throws }</b>
 *     <ul>
 *         <li><b>{@link FormattingMismatchException}</b> when the provided {@link FormattingMarkedText} does not
 *         employ the expected {@code TextFormatting} type.</li>
 *     </ul>
 * <br><br>
 * In addition to the static methods, each interface will mandate the implementation of some instance methods.
 */
public non-sealed interface ExtendedMarkdown extends MarkupLanguage {
    /**
     * Marks up the provided {@code text} using Markdown's Extended Syntax according to the formatting specifications
     * specified in the implementing class.
     *
     * @param text the {@code String} to apply some desired Markdown to
     *
     * @return the {@code text} formatted in the desired way using Markdown
     */
    public MarkedUpText applyAsExtdMdTo(String text);
}