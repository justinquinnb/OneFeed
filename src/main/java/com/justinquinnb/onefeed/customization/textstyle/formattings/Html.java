package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

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
}