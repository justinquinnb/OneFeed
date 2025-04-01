package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.function.Function;

/**
 * Marker for default/unformatted text. Obtain an instance through {@link #getInstance()}.
 */
public class DefaultFormat extends TextFormatting implements Html, Markdown, ExtendedMarkdown {
    private static volatile DefaultFormat instance = null;

    /**
     * Creates an instance of bold formatting.
     */
    private DefaultFormat() {
        super("DefaultFormat");
    }

    /**
     * Gets the single instance of {@code DefaultFormat}. Multiple instances aren't necessary as this format requires no
     * complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code DefaultFormat}
     */
    public static DefaultFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (DefaultFormat.class) {
                if (instance == null) {
                    instance = new DefaultFormat();
                }
            }
        }
        return instance;
    }

    @Override
    public MarkedUpText applyHtml(String text) {
        return new MarkedUpText(text, Html.class);
    }

    @Override
    public MarkedUpText applyMd(String text) {
        return new MarkedUpText(text, Markdown.class);
    }

    @Override
    public MarkedUpText applyExtdMd(String text) {
        return new MarkedUpText(text, ExtendedMarkdown.class);
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
        return "DefaultFormat@" + this.hashCode() + "{}";
    }
}