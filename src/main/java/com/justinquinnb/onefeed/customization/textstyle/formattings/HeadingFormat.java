package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A basic heading, leveled 1-6.
 */
public class HeadingFormat extends TextFormatting implements Html, Markdown, ExtendedMarkdown {
    /**
     * The level number of the heading.
     */
    private final int levelNumber;

    /**
     * Creates an instance of heading formatting of level {@code levelNumber}. If a level outside the range [1,6] is
     * provided, it is changed to be the closest extremity.
     *
     * @param levelNumber the level number of the {@code HeadingFormat} to construct
     */
    public HeadingFormat(int levelNumber) {
        super("Heading " + levelNumber);
        if (levelNumber < 1) {
            levelNumber = 1;
        } else if (levelNumber > 6) {
            levelNumber = 6;
        }
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
    public Pattern getHtmlPattern() {
        return Pattern.compile(
                // A number range isn't used to ensure matching tags are found
                "((<h1(.*)>)(.*)(</h1\\s>))|" +
                        "((<h2(.*)>)(.*)(</h2\\s>))|" +
                        "((<h3(.*)>)(.*)(</h3\\s>))|" +
                        "((<h4(.*)>)(.*)(</h4\\s>))|" +
                        "((<h5(.*)>)(.*)(</h5\\s>))|" +
                        "((<h6(.*)>)(.*)(</h6\\s>))",
                Pattern.DOTALL | Pattern.MULTILINE);
    }

    @Override
    public FormattingMarkedText extractFromHtml(MarkedUpText text) {
        // Define valid open heading tags
        Pattern[] openHeadingTags = new Pattern[] {
                Pattern.compile("<h1\\s(.*)>"), Pattern.compile("<h2\\s(.*)>"),
                Pattern.compile("<h3\\s(.*)>"), Pattern.compile("<h4\\s(.*)>"),
                Pattern.compile("<h5\\s(.*)>"), Pattern.compile("<h6\\s(.*)>")
        };

        // Determine the first heading level that appears and attempt to parse out the element's content
        try {
            // Determine the heading level
            Pattern firstOpenTag = MarkupLanguage.findEarliestMatch(text, openHeadingTags);
            int headingLevel = firstOpenTag.pattern().charAt(3) - 48; // ASCII to valid heading level

            return Html.extractContentFromElement(
                    text, new HeadingFormat(headingLevel), "h" + headingLevel);
        } catch (IllegalStateException e) {
            return new FormattingMarkedText(text.getText(), DefaultFormat.getInstance());
        }
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
    public Pattern getMdPattern() {
        return Pattern.compile("^\\s((#){1,6})(.*)$", Pattern.DOTALL);
    }

    @Override
    public FormattingMarkedText extractFromMd(MarkedUpText text) {
        // Determine the first heading level that appears and attempt to parse out the element's content
        try {
            // Define valid open heading tags
            Pattern[] headingDelimeters = new Pattern[] {
                    Pattern.compile("^\\s#"), Pattern.compile("^\\s##"),
                    Pattern.compile("^\\s###"), Pattern.compile("^\\s####"),
                    Pattern.compile("^\\s#####"), Pattern.compile("^\\s######")
            };

            // Determine the heading level to parse. EarliestMatch is invoked as it will yield the singular heading
            // markup used.
            Pattern employedHeading = MarkupLanguage.findEarliestMatch(text, headingDelimeters);
            int headingLevel = employedHeading.pattern().length() - 4; // Convert pattern to heading level

            return MarkupLanguage.parseFmtBetweenBounds(text, employedHeading, Pattern.compile("\\s$"),
                    new HeadingFormat(headingLevel));
        } catch (IllegalStateException e) {
            return new FormattingMarkedText(text.getText(), DefaultFormat.getInstance());
        }
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
    public Pattern getExtdMdPattern() {
        return this.getMdPattern();
    }

    @Override
    public FormattingMarkedText extractFromExtdMd(MarkedUpText text) {
        return this.extractFromMd(text);
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

    @Override
    public Pattern getMarkupPatternFor(Class<? extends MarkupLanguage> markupLang) {
        if (markupLang.equals(Html.class)) {
            return this.getMdPattern();
        } else if (markupLang.equals(Markdown.class)) {
            return this.getMdPattern();
        } else if (markupLang.equals(ExtendedMarkdown.class)) {
            return this.getExtdMdPattern();
        } else {
            return null;
        }
    }

    @Override
    public Function<MarkedUpText, FormattingMarkedText> getFormattingExtractorFor(
            Class<? extends MarkupLanguage> markupLang) {
        if (markupLang.equals(Html.class)) {
            return this::extractFromHtml;
        } else if (markupLang.equals(Markdown.class)) {
            return this::extractFromMd;
        } else if (markupLang.equals(ExtendedMarkdown.class)) {
            return this::extractFromExtdMd;
        } else {
            return null;
        }
    }

    public String toString() {
        return "HeadingFormatFormat@" + this.hashCode() + "{levelNumber=" + this.levelNumber + "}";
    }
}
