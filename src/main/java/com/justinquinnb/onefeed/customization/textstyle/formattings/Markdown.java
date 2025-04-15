package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.text.ParseException;
import java.util.regex.Pattern;

/**
 * A type capable of generating {@link MarkedUpText} in the originally-defined Markdown language provided some text.
 */
public interface Markdown extends MarkupLanguage {
    /**
     * Marks up the provided {@code text} in Markdown according to the formatting specifications specified in the
     * implementing class.
     *
     * @param text the {@code String} to apply some desired Markdown to
     *
     * @return the {@code text} formatted in the desired way using Markdown
     */
    public MarkedUpText applyMd(String text);
}