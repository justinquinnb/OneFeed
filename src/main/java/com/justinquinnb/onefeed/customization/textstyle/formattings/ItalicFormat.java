package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.function.Function;

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
    public MarkedUpText applyMd(String text) {
        return new MarkedUpText("*" + text + "*", Markdown.class);
    }

    @Override
    public MarkedUpText applyExtdMd(String text) {
        return new MarkedUpText("*" + text + "*", Markdown.class);
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
        return "ItalicFormat@" + this.hashCode() + "{}";
    }
}