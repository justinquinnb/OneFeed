package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.text.ParseException;
import java.util.regex.Pattern;

/**
 * A type capable of generating {@link MarkedUpText} in HTML provided some text.
 */
public interface Html extends MarkupLanguage {
    /**
     * Marks up the provided {@code text} in HTML according to the formatting specifications specified in the
     * implementing class.
     *
     * @param text the {@code String} to apply some desired HTML to
     *
     * @return the {@code text} formatted in the desired way using HTML
     */
    public MarkedUpText applyHtml(String text);

    /**
     * Provides the {@link Pattern} delimiting a type of {@link TextFormatting} in HTML.
     *
     * @return the {@code Pattern} specifying how the implementing type is formatted in the HTML
     *
     * @implSpec If no {@code Pattern} exists for the type in HTML, {@code null} should be
     * returned.
     */
    public Pattern getHtmlPattern();

    /**
     * Generates a {@link FormattingMarkedText} object that specifies how the {@code text} should be formatted given
     * its markup in HTML. The returned {@code FormattingMarkedText} contains the original {@code text} with all of its 
     * markup removed, making it markup-language-agnostic.
     *
     * @param text the text formatted as {@code this} {@link Html}-implementing type
     *
     * @return a {@code FormattingMarkedText} object representing the type of formatting specified by the HTML contained
     * in {@code text} with a copy of {@code text} that has all of its markup removed
     *
     * @throws ParseException if the provided {@code text} is malformed HTML
     *
     * @implNote
     * Note that the produced {@code FormattingMarkedText} may not employ the same type of {@link TextFormatting} as
     * {@code this} {@code Html} language implementor. In some cases, this may be the intended behavior and 
     * is handled without issue by OneFeed. Generally speaking though, such behavior should be avoided.
     */
    public FormattingMarkedText extractFromHtml(String text) throws ParseException;
}