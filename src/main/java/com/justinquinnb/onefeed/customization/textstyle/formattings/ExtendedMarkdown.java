package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.text.ParseException;
import java.util.regex.Pattern;

/**
 * A type capable of generating {@link MarkedUpText} in Markdown (plus Extended Syntax) provided some text.
 */
public interface ExtendedMarkdown extends MarkupLanguage {
    /**
     * Marks up the provided {@code text} using Markdown's Extended Syntax according to the formatting specifications
     * specified in the implementing class.
     *
     * @param text the {@code String} to apply some desired Markdown to
     *
     * @return the {@code text} formatted in the desired way using Markdown
     */
    public MarkedUpText applyExtdMd(String text);

    /**
     * Provides the {@link Pattern} delimiting a type of {@link TextFormatting} in Markdown's Extended Syntax.
     *
     * @return the {@code Pattern} specifying how the implementing type is formatted in the Markdown's Extended Syntax
     *
     * @implSpec If no {@code Pattern} exists for the type in Markdown's Extended Syntax, {@code null} should be
     * returned.
     */
    public Pattern getExtdMdPattern();

    /**
     * Generates a {@link FormattingMarkedText} object that specifies how the {@code text} should be formatted given
     * its markup in Markdown's Extended Syntax. The returned {@code FormattingMarkedText} contains the
     * original {@code text} with all of its markup removed, making it markup-language-agnostic.
     *
     * @param text the text formatted as {@code this} {@link ExtendedMarkdown}-implementing type
     *
     * @return a {@code FormattingMarkedText} object representing the type of formatting specified by the Extended
     * Markdown contained in {@code text} with a copy of {@code text} that has all of its markup removed
     *
     * @throws ParseException if the provided {@code text} is malformed Extended Markdown
     *
     * @implNote
     * Note that the produced {@code FormattingMarkedText} may not employ the same type of {@link TextFormatting} as
     * {@code this} {@code ExtendedMarkdown} language implementor. In some cases, this may be the intended behavior and
     * is handled without issue by OneFeed. Generally speaking though, such behavior should be avoided.
     */
    public FormattingMarkedText extractFromExtdMd(String text) throws ParseException;
}