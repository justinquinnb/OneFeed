package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Marker for italic text formatting. Obtain an instance through {@link #getInstance()}.
 */
public class ItalicFormat extends TextFormatting implements Html, Markdown, ExtendedMarkdown {
    private static volatile ItalicFormat instance = null;

    /**
     * Creates an instance of italic formatting.
     */
    private ItalicFormat() {
        super("Italic");
    }

    /**
     * Gets the single instance of {@code ItalicFormat}. Multiple instances aren't necessary as this format requires no
     * complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code ItalicFormat}
     */
    public static ItalicFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (ItalicFormat.class) {
                if (instance == null) {
                    instance = new ItalicFormat();
                }
            }
        }
        return instance;
    }

    @Override
    public MarkedUpText applyHtml(String text) {
        return new MarkedUpText("<i>" + text + "</i>", Html.class);
    }

    @Override
    public Pattern getHtmlPattern() {
        return Pattern.compile("<i(.*)>(.*)</i\\s>", Pattern.DOTALL);
    }

    @Override
    public FormattingMarkedText extractFromHtml(MarkedUpText text) {
        // Attempt to parse out the content
        try {
            return Html.extractContentFromElement(text, ItalicFormat.getInstance(), "i");
        } catch (IllegalStateException e) {
            return new FormattingMarkedText(text.getText(), DefaultFormat.getInstance());
        }
    }

    @Override
    public MarkedUpText applyMd(String text) {
        return new MarkedUpText("*" + text + "*", Markdown.class);
    }

    @Override
    public Pattern getMdPattern() {
        return Pattern.compile("\\*(.*)\\*", Pattern.DOTALL);
    }

    @Override
    public FormattingMarkedText extractFromMd(MarkedUpText text) {
        try {
            // Find the outermost italic bounds in order to determine that outermost element's content
            Pattern startTag = Pattern.compile("^(\\*)");
            Pattern endTag = Pattern.compile("(\\*)$");

            return MarkupLanguage.parseFmtBetweenBounds(text, startTag, endTag, ItalicFormat.getInstance());
        } catch (IllegalStateException e) {
            return new FormattingMarkedText(text.getText(), DefaultFormat.getInstance());
        }
    }

    @Override
    public MarkedUpText applyExtdMd(String text) {
        return new MarkedUpText("*" + text + "*", Markdown.class);
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

    public String toString() {
        return "ItalicFormat@" + this.hashCode() + "{}";
    }
}