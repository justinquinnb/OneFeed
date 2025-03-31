package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.TextFormatting;

import java.util.function.Function;

/**
 * A basic heading, leveled 1-6.
 */
public class HeadingFormat extends TextFormatting implements Html, Markdown, ExtendedMarkdown {
    /**
     * The level number of the heading.
     */
    private final int levelNumber;

    /**
     * Creates an instance of heading formatting of level {@code levelNumber}. If a number outside the range [1,6]
     *
     * @param levelNumber the level number of the {@code HeadingFormat} to construct
     */
    public HeadingFormat(int levelNumber) {
        super("Heading " + levelNumber);
        this.levelNumber = levelNumber;
    }

    /**
     * Gets {@code this} {@code HeadingFormat}'s level number.
     *
     * @return {@code this} {@code HeadingFormat}'s level number
     */
    public int getLevelNumber() {
        return this.levelNumber;
    }

    @Override
    public MarkedUpText applyHtml(String text) {
        int num = this.levelNumber;
        return new MarkedUpText("<h" + num + ">" + text + "</h" + num + ">", Html.class);
    }

    @Override
    public MarkedUpText applyMd(String text) {
        String prefix = "";

        for (int i = 0; i < this.levelNumber; i++) {
            prefix += "#";
        }

        return new MarkedUpText(prefix + "text", Markdown.class);
    }

    @Override
    public MarkedUpText applyExtdMd(String text) {
        String prefix = "";

        for (int i = 0; i < this.levelNumber; i++) {
            prefix += "#";
        }

        return new MarkedUpText(prefix + "text", ExtendedMarkdown.class);
    }

    @Override
    public Function<String, MarkedUpText> getMarkupLangApplierFor(Class<? extends MarkupLanguage> markupLang) {
        if (markupLang.equals(Html.class)) {
            return this::applyHtml;
        } else if (markupLang.equals(Markdown.class)) {
            return this::applyMd;
        } else if (markupLang.equals(ExtendedMarkdown.class)) {
            return this::applyExtdMd;
        } else {
            return null;
        }
    }

    public String toString() {
        return "HeadingFormatFormat@" + this.hashCode() + "{levelNumber=" + this.levelNumber + "}";
    }
}
