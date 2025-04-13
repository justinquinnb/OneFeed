package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.text.ParseException;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO implement markdown
/**
 * Marker for block quote text formatting. Obtain an instance through {@link #getInstance()}.
 */
public class BlockQuoteFormat extends TextFormatting implements Html, Markdown, ExtendedMarkdown {
    private static volatile BlockQuoteFormat instance = null;

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

    @Override
    public Pattern getHtmlPattern() {
        return Pattern.compile("(<blockquote\s(.*)>)(.*)(</blockquote\s>)");
    }

    @Override
    public FormattingMarkedText extractFromHtml(MarkedUpText text) {
        // Define the blockquote element tags
        Pattern startTag = Pattern.compile("^(<blockquote\s(.*)>)");
        Pattern endTag = Pattern.compile("(</blockquote\s>)$");

        return MarkupLanguage.parseFmtBetweenBounds(text, startTag, endTag, BlockQuoteFormat.getInstance());
    }

    @Override
    public MarkedUpText applyMd(String text) {
        return new MarkedUpText(">" + text, Markdown.class);
    }

    @Override
    public Pattern getMdPattern() {
        return Pattern.compile("(>\s(.*)$)+", Pattern.MULTILINE);
    }

    @Override
    public FormattingMarkedText extractFromMd(MarkedUpText text) {
        String rawText = text.getText();

        // Specify the start-of-blockquote line pattern
        Pattern linePattern = Pattern.compile("^>\s");
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
    public Pattern getExtdMdPattern() {
        return this.getMdPattern();
    }

    @Override
    public FormattingMarkedText extractFromExtdMd(MarkedUpText text) {
        return extractFromMd(text);
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
        return "BoldFormat@" + this.hashCode() + "{}";
    }
}