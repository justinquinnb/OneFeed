package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Marker for block quote text formatting. Obtain an instance through {@link #getInstance()}.
 */
public class BlockQuoteFormat extends TextFormatting implements Html, Markdown, ExtendedMarkdown {
    private static volatile BlockQuoteFormat instance = null;

    static {
        TextFormattingRegistry.registerForLanguage(
                BlockQuoteFormat.class, Html.class, getHtmlPattern(), BlockQuoteFormat::extractFromHtml);
        TextFormattingRegistry.registerForLanguage(
                BlockQuoteFormat.class, Markdown.class, getMdPattern(), BlockQuoteFormat::extractFromMd);
        TextFormattingRegistry.registerForLanguage(
                BlockQuoteFormat.class, ExtendedMarkdown.class, getMdPattern(), BlockQuoteFormat::extractFromMd);
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

    @Override
    public MarkedUpText applyHtml(String text) {
        return new MarkedUpText("<blockquote>" + text + "</blockquote>", Html.class);
    }

    public static Pattern getHtmlPattern() {
        return Pattern.compile("(<blockquote(.*)>)(.*)(</blockquote\\s>)", Pattern.DOTALL);
    }

    public static FormattingMarkedText extractFromHtml(MarkedUpText text) {
        try {
            return Html.extractContentFromElement(text, BlockQuoteFormat.getInstance(), "blockquote");
        } catch (IllegalStateException e) {
            return new FormattingMarkedText(text.getText(), DefaultFormat.getInstance());
        }
    }

    @Override
    public MarkedUpText applyMd(String text) {
        return new MarkedUpText(">" + text, Markdown.class);
    }

    public static Pattern getMdPattern() {
        return Pattern.compile("(>(.*)$)+", Pattern.MULTILINE | Pattern.DOTALL);
    }

    public static FormattingMarkedText extractFromMd(MarkedUpText text) {
        String rawText = text.getText();

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

        return new FormattingMarkedText(blockquoteText, BlockQuoteFormat.getInstance());
    }

    @Override
    public MarkedUpText applyExtdMd(String text) {
        return new MarkedUpText(">" + text, ExtendedMarkdown.class);
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
        return "BoldFormat@" + this.hashCode() + "{}";
    }
}