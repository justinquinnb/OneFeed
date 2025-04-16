package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

/**
 * A type capable of generating {@link MarkedUpText} in the originally-defined Markdown language provided some text.
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
 * In addition to the static methods, each interface will mandate the implementation of some instance methods.
 */
public non-sealed interface Markdown extends MarkupLanguage {
    /**
     * Marks up the provided {@code text} in Markdown according to the formatting specifications specified in the
     * implementing class.
     *
     * @param text the {@code String} to apply some desired Markdown to
     *
     * @return the {@code text} formatted in the desired way using Markdown
     */
    public MarkedUpText applyAsMdTo(String text);
}