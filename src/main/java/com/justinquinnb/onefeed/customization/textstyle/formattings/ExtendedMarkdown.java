package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

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
}