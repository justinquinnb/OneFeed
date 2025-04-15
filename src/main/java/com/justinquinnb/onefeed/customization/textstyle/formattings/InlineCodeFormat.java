package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Marker for inline code text formatting. Obtain an instance through {@link #getInstance()}.
 */
public class InlineCodeFormat extends TextFormatting implements Html, Markdown, ExtendedMarkdown {
    private static volatile InlineCodeFormat instance = null;

    /**
     * Creates an instance of code block formatting.
     */
    private InlineCodeFormat() {
        super("InlineCodeFormat");
    }

    /**
     * Gets the single instance of {@code InlineCodeFormat}. Multiple instances aren't necessary as this format requires no
     * complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code InlineCodeFormat}
     */
    public static InlineCodeFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (InlineCodeFormat.class) {
                if (instance == null) {
                    instance = new InlineCodeFormat();
                }
            }
        }
        return instance;
    }
    
    @Override
    public MarkedUpText applyExtdMd(String text) {
        return new MarkedUpText("`" + text + "`", ExtendedMarkdown.class);
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
    public MarkedUpText applyHtml(String text) {
        return new MarkedUpText("<code>" + text + "</code>", Html.class);
    }

    @Override
    public Pattern getHtmlPattern() {
        return Pattern.compile("(<code(.*)>)(.*)(</code\\s>)", Pattern.DOTALL);
    }

    @Override
    public FormattingMarkedText extractFromHtml(MarkedUpText text) {
        // Attempt to parse out the content
        try {
            return Html.extractContentFromElement(text, InlineCodeFormat.getInstance(), "code");
        } catch (IllegalStateException e) {
            return new FormattingMarkedText(text.getText(), DefaultFormat.getInstance());
        }
    }

    @Override
    public MarkedUpText applyMd(String text) {
        return new MarkedUpText("`" + text + "`", Markdown.class);
    }

    @Override
    public Pattern getMdPattern() {
        return Pattern.compile("`(.*)`", Pattern.DOTALL);
    }

    @Override
    public FormattingMarkedText extractFromMd(MarkedUpText text) {
        try {
            Pattern startBound = Pattern.compile("^`");
            Pattern endBound = Pattern.compile("`$");
            return MarkupLanguage.parseFmtBetweenBounds(text, startBound, endBound, InlineCodeFormat.getInstance());
        } catch (IllegalStateException e) {
            return new FormattingMarkedText(text.getText(), DefaultFormat.getInstance());
        }
    }

    @Override
    public Function<String, MarkedUpText> getMarkupLangApplierFor(
            Class<? extends MarkupLanguage> markupLang) {
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
            return this.getHtmlPattern();
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
}