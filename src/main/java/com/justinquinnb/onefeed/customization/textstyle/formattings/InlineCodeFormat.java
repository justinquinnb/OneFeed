package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.TextFormatting;

import java.util.function.Function;

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
    public MarkedUpText applyHtml(String text) {
        return new MarkedUpText("<code>" + text + "</code>", Html.class);
    }

    @Override
    public MarkedUpText applyMd(String text) {
        return new MarkedUpText("`" + text + "`", Markdown.class);
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
}