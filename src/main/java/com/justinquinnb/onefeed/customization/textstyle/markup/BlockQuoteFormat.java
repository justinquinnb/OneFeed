package com.justinquinnb.onefeed.customization.textstyle.markup;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMismatchException;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.MarkupLangMismatchException;

import java.text.ParseException;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Marker for block quote text formatting. Obtain an instance through {@link #getInstance()}.
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
public class BlockQuoteFormat extends TextFormatting implements Html, Markdown, ExtendedMarkdown {
    /**
     * The singleton instance of {@code BlockQuoteFormat}. Only one instance is needed because the type is effectively
     * memberless--the formatting requires no extra data.
     */
    private static volatile BlockQuoteFormat instance = null;

    // Register the format as Html, Markdown, and Extended Markdown compatible
    static {
        TextFormattingRegistry.registerForLanguage(
                BlockQuoteFormat.class, Html.class, getHtmlPattern(),
                BlockQuoteFormat::extractFromHtml, BlockQuoteFormat::applyAsHtml);
        TextFormattingRegistry.registerForLanguage(
                BlockQuoteFormat.class, Markdown.class, getMdPattern(),
                BlockQuoteFormat::extractFromMd, BlockQuoteFormat::applyAsMd);
        TextFormattingRegistry.registerForLanguage(
                BlockQuoteFormat.class, ExtendedMarkdown.class, getMdPattern(),
                BlockQuoteFormat::extractFromMd, BlockQuoteFormat::applyAsMd);
    }

    /**
     * Creates an instance of bold formatting.
     */
    private BlockQuoteFormat() {
        super("BlockQuote");
    }

    /**
     * Gets the single instance of {@code BlockQuoteFormat}. Multiple instances aren't necessary as this format requires
     * no complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code BlockQuoteFormat}
     */
    public static BlockQuoteFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (BlockQuoteFormat.class) {
                if (instance == null) {
                    instance = new BlockQuoteFormat();
                }
            }
        }
        return instance;
    }

    // PATTERNS
    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code BlockQuoteFormat} appears in text marked up in HTML.
     *
     * @return a {@code Pattern} capable of identifying block quote formatting as it manifests in HTML
     */
    public static Pattern getHtmlPattern() {
        return Pattern.compile("(<blockquote(.*)>)(.*)(</blockquote\\s>)", Pattern.DOTALL);
    }

    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code BlockQuoteFormat} appears in text marked up in
     * Markdown.
     *
     * @return a {@code Pattern} capable of identifying block quote formatting as it manifests in Markdown
     */
    public static Pattern getMdPattern() {
        return Pattern.compile("(>(.*)$)+", Pattern.MULTILINE |
                Pattern.DOTALL);
    }

    /**
     * Gets the {@link Pattern} indicating how {@code this} {@code BlockQuoteFormat} appears in text marked up in
     * Extended Markdown.
     *
     * @return a {@code Pattern} capable of identifying block quote formatting as it manifests in Extended Markdown
     *
     * @see #getMdPattern()
     */
    public static Pattern getExtdMdPattern() {
        return getMdPattern();
    }

    // EXTRACTORS
    /**
     * Extracts a {@code BlockQuoteFormat} object replicating the formatting of {@code text} as is specified by its
     * HTML markup and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return a {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup
     * coupled with an instance of {@code BlockQuoteFormat} representing the formatting that markup called for
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ HTML
     * @throws ParseException if an HTML blockquote could not be parsed from the {@code text}
     */
    public static FormattingMarkedText extractFromHtml(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        // Don't bother trying to parse the text if it doesn't employ any HTML
        MarkupLanguage.preventMarkupLangMismatch(text, "a blockquote", Html.class);

        // Since it might have HTML, attempt to parse out the desired formatting
        return Html.extractContentFromElement(text, BlockQuoteFormat.getInstance(), "blockquote");
    }

    /**
     * Extracts a {@code BlockQuoteFormat} object replicating the formatting of {@code text} as is specified by its
     * Markdown and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return a {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup
     * coupled with an instance of {@code BlockQuoteFormat} representing the formatting that markup called for
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ Markdown / Extended Markdown
     * @throws ParseException if a Markdown blockquote could not be parsed from the {@code text}
     */
    public static FormattingMarkedText extractFromMd(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        String rawText = text.getText();

        // Don't bother trying to parse the text if it doesn't employ any MD
        MarkupLanguage.preventMarkupLangMismatch(text, "a blockquote", Markdown.class,
                ExtendedMarkdown.class);

        // Specify the start-of-blockquote line pattern
        Pattern linePattern = Pattern.compile("^\\s>\\s");
        Matcher match;
        boolean foundMatch = false;
        String blockquoteText = "";

        match = linePattern.matcher(rawText);

        // Find each line's delimiter in order to determine the blockquote's actual content, extracting what follows it
        while (match.find()) {
            foundMatch = true;
            blockquoteText += rawText.substring(match.start(), match.end());
        }

        if (!foundMatch) {
            return new FormattingMarkedText(rawText, DefaultFormat.getInstance());
        }

        throw new ParseException("Blockquote formatting in Markdown could not be parsed from:\n\t" + text.getText(), 0);
    }

    /**
     * Extracts a {@code BlockQuoteFormat} object replicating the formatting of {@code text} as is specified by its
     * Extended Markdown and pairs it with a copy of the marked-up text stripped of its markup.
     *
     * @param text the {@code MarkedUpText} to try parsing the formatting out of
     *
     * @return a {@code FormattingMakedText} instance containing the original {@code text} stripped of its markup
     * coupled with an instance of {@code BlockQuoteFormat} representing the formatting that markup called for
     *
     * @throws MarkupLangMismatchException if the {@code MarkedUpText} does not employ Markdown / Extended Markdown
     * @throws ParseException if an Extended Markdown blockquote could not be parsed from the {@code text}
     *
     * @see #extractFromMd
     */
    public static FormattingMarkedText extractFromExtMd(MarkedUpText text)
            throws MarkupLangMismatchException, ParseException
    {
        return extractFromMd(text);
    }

    // APPLIERS
    /**
     * Marks up the provided {@code text}'s raw text in HTML.
     *
     * @param text the {@code FormattingMarkedText} to apply blockquote formatting to in HTML
     *
     * @return the raw text of {@code text} marked up as to indicate a blockquote in HTML
     * @throws FormattingMismatchException if the {@code text} does not call for blockquote formatting
     */
    public static MarkedUpText applyAsHtml(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, BlockQuoteFormat.class);

        return new MarkedUpText("<blockquote>" + text.getText() + "</blockquote>", Html.class);
    }

    /**
     * Marks up the provided {@code text}'s raw text in Markdown / Extended Markdown.
     *
     * @param text the {@code FormattingMarkedText} to apply blockquote formatting to in Markdown / Extended Markdown
     *
     * @return the raw text of {@code text} marked up as to indicate a blockquote in Markdown / Extended Markdown
     * @throws FormattingMismatchException if the {@code text} does not call for blockquote formatting
     */
    public static MarkedUpText applyAsMd(FormattingMarkedText text) throws FormattingMismatchException {
        // Don't bother trying to apply the formatting to text that doesn't call for it
        TextFormatting.preventFormattingMismatch(text, BlockQuoteFormat.class);

        return new MarkedUpText(">" + text.getText(), Markdown.class);
    }

    // OVERRIDES
    @Override
    public MarkedUpText applyAsHtmlTo(String text) {
        try {
            return applyAsHtml(new FormattingMarkedText(text, BlockQuoteFormat.getInstance()));
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
        try {
            return applyAsMd(new FormattingMarkedText(text, BlockQuoteFormat.getInstance()));
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
        try {
            return applyAsMd(new FormattingMarkedText(text, BlockQuoteFormat.getInstance()));
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

    @Override
    public String toString() {
        return "BlockQuoteFormat@" + this.hashCode() + "{}";
    }
}