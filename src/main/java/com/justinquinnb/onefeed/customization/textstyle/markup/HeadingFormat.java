package com.justinquinnb.onefeed.customization.textstyle.markup;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMismatchException;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.MarkupLangMismatchException;

import java.text.ParseException;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * A basic heading, leveled 1-6.
 */
public class HeadingFormat extends TextFormatting implements Html, Markdown, ExtendedMarkdown {
    /**
     * The level number of the heading.
     */
    private final int levelNumber;

    // Register the format as Html, Markdown, and Extended Markdown compatible
    static {
        TextFormattingRegistry.registerForLanguage(
                HeadingFormat.class, Html.class, getHtmlPattern(),
                HeadingFormat::extractFromHtml, HeadingFormat::applyAsHtml);
        TextFormattingRegistry.registerForLanguage(
                HeadingFormat.class, Markdown.class, getMdPattern(),
                HeadingFormat::extractFromMd, HeadingFormat::applyAsMd);
        TextFormattingRegistry.registerForLanguage(
                HeadingFormat.class, ExtendedMarkdown.class, getMdPattern(),
                HeadingFormat::extractFromMd, HeadingFormat::applyAsMd);
    }
    
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

    // PATTERNS
    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code HeadingFormat} appears in text marked up in HTML.
     *
     * @return a {@code Pattern} capable of identifying heading formatting as it manifests in HTML
     */
    public static Pattern getHtmlPattern() {
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

    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code HeadingFormat} appears in text marked up in
     * Markdown.
     *
     * @return a {@code Pattern} capable of identifying heading formatting as it manifests in Markdown
     */
    public static Pattern getMdPattern() {
        return Pattern.compile("^\\s((#){1,6})(.*)$", Pattern.DOTALL);
    }

    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code HeadingFormat} appears in text marked up in
     * Extended Markdown.
     *
     * @return a {@code Pattern} capable of identifying heading formatting as it manifests in Extended Markdown
     *
     * @see #getMdPattern()
     */
    public static Pattern getExtdMdPattern() {
        return getMdPattern();
    }

    // EXTRACTORS
    /**
     * Extracts a {@code HeadingFormat} object replicating the formatting of {@code text} as is specified by its
     * HTML markup and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs HTML and contains a valid instance of heading formatting, a
     * {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup coupled with an
     * instance of {@code HeadingFormat} representing the formatting that markup called for.
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ HTML
     * @throws ParseException if an HTML heading could not be parsed from the {@code text}
     */
    public static FormattingMarkedText extractFromHtml(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        // Don't bother trying to parse the text if it doesn't employ any HTML
        MarkupLanguage.preventMarkupLangMismatch(text, "a heading", Html.class);

        // Define valid open heading tags
        Pattern[] openHeadingTags = new Pattern[] {
                Pattern.compile("<h1\\s(.*)>"), Pattern.compile("<h2\\s(.*)>"),
                Pattern.compile("<h3\\s(.*)>"), Pattern.compile("<h4\\s(.*)>"),
                Pattern.compile("<h5\\s(.*)>"), Pattern.compile("<h6\\s(.*)>")
        };

        // Determine the first heading level that appears and attempt to parse out the element's content
        Pattern firstOpenTag = MarkupLanguage.findEarliestMatch(text, openHeadingTags);
        int headingLevel = firstOpenTag.pattern().charAt(3) - 48; // ASCII to valid heading level

        return Html.extractContentFromElement(
                text, new HeadingFormat(headingLevel), "h" + headingLevel);
    }

    /**
     * Extracts a {@code HeadingFormat} object replicating the formatting of {@code text} as is specified by its
     * Markdown and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs Markdown and contains a valid instance of heading formatting, a
     * {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup coupled with an
     * instance of {@code HeadingFormat} representing the formatting that markup called for.
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ Markdown / Extended Markdown
     * @throws ParseException if a Markdown heading could not be parsed from the {@code text}
     */
    public static FormattingMarkedText extractFromMd(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        // Don't bother trying to parse the text if it doesn't employ any MD
        MarkupLanguage.preventMarkupLangMismatch(text, "a heading",
                Markdown.class, ExtendedMarkdown.class);

        // Determine the first heading level that appears and attempt to parse out the element's content
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
    }

    /**
     * Extracts a {@code HeadingFormat} object replicating the formatting of {@code text} as is specified by its
     * Extended Markdown and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return If the {@code MarkedUpText} employs Extended Markdown and contains a valid instance of heading
     * formatting, a {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup
     * coupled with an instance of {@code HeadingFormat} representing the formatting that markup called for.
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ Markdown / Extended Markdown
     * @throws ParseException if a Markdown heading could not be parsed from the {@code text}
     *
     * @see #extractFromMd
     */
    public static FormattingMarkedText extractFromExtdMd(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        return extractFromMd(text);
    }
    
    // APPLIERS
    /**
     * Marks up the provided {@code text}'s raw text in HTML.
     *
     * @param text the {@code FormattingMarkedText} to apply heading formatting to in HTML
     *
     * @return the raw text of {@code text} marked up as to indicate a heading in HTML
     * @throws FormattingMismatchException if the {@code text} does not call for heading formatting
     */
    public static MarkedUpText applyAsHtml(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, HeadingFormat.class);

        HeadingFormat headingFormat = (HeadingFormat)text.getFormatting();
        int levelNum = headingFormat.getLevelNumber();
        return new MarkedUpText("<h" + levelNum + ">" + text + "</h" + levelNum + ">", Html.class);
    }

    /**
     * Marks up the provided {@code text}'s raw text in Markdown / Extended Markdown.
     *
     * @param text the {@code FormattingMarkedText} to apply heading formatting to in Markdown / Extended Markdown
     *
     * @return the raw text of {@code text} marked up as to indicate a heading in Markdown / Extended Markdown
     * @throws FormattingMismatchException if the {@code text} does not call for heading formatting
     */
    public static MarkedUpText applyAsMd(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, HeadingFormat.class);

        HeadingFormat headingFormat = (HeadingFormat)text.getFormatting();
        int levelNum = headingFormat.getLevelNumber();
        String prefix = "";

        for (int i = 0; i < levelNum; i++) {
            prefix += "#";
        }

        return new MarkedUpText(prefix + "text", Markdown.class);
    }

    // OVERRIDES
    @Override
    public MarkedUpText applyAsHtmlTo(String text) {
        int num = this.levelNumber;

        try {
            return applyAsHtml(new FormattingMarkedText(text, new HeadingFormat(num)));
        } catch (FormattingMismatchException e) {
            /* So long as the static function employs the correct definition of a FormattingMismatchException (as would
            be the case with it using TextFormatting's preventFormattingMismatch() function), this clause should NEVER
            be executed.
             */
            throw new RuntimeException(e);
        }
    }

    @Override
    public MarkedUpText applyAsMdTo(String text) {
        int num = this.levelNumber;

        try {
            return applyAsMd(new FormattingMarkedText(text, new HeadingFormat(num)));
        } catch (FormattingMismatchException e) {
            /* So long as the static function employs the correct definition of a FormattingMismatchException (as would
            be the case with it using TextFormatting's preventFormattingMismatch() function), this clause should NEVER
            be executed.
             */
            throw new RuntimeException(e);
        }
    }

    @Override
    public MarkedUpText applyAsExtdMdTo(String text) {
        int num = this.levelNumber;

        try {
            return applyAsMd(new FormattingMarkedText(text, new HeadingFormat(num)));
        } catch (FormattingMismatchException e) {
            /* So long as the static function employs the correct definition of a FormattingMismatchException (as would
            be the case with it using TextFormatting's preventFormattingMismatch() function), this clause should NEVER
            be executed.
             */
            throw new RuntimeException(e);
        }
    }

    @Override
    public Function<String, MarkedUpText> getMarkupLangApplierFor(Class<? extends MarkupLanguage> markupLang) {
        if (markupLang.equals(Html.class)) {
            return this::applyAsHtmlTo;
        } else if (markupLang.equals(Markdown.class)) {
            return this::applyAsMdTo;
        } else if (markupLang.equals(ExtendedMarkdown.class)) {
            return this::applyAsExtdMdTo;
        } else {
            return null;
        }
    }

    public String toString() {
        return "HeadingFormat@" + this.hashCode() + "{levelNumber=" + this.levelNumber + "}";
    }
}