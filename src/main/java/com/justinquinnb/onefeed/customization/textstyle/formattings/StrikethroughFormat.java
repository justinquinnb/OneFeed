package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.function.Function;

/**
 * Marker for strikethrough text formatting. Obtain an instance through {@link #getInstance()}.
 */
public class StrikethroughFormat extends TextFormatting implements Html, ExtendedMarkdown {
    private static volatile StrikethroughFormat instance = null;

    /**
     * Creates an instance of strikethrough formatting.
     */
    private StrikethroughFormat() {
        super("Strikethrough");
    }

    /**
     * Gets the single instance of {@code StrikethroughFormat}. Multiple instances aren't necessary as this format
     * requires no complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code StrikethroughFormat}
     */
    public static StrikethroughFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (StrikethroughFormat.class) {
                if (instance == null) {
                    instance = new StrikethroughFormat();
                }
            }
        }
        return instance;
    }

    @Override
    public MarkedUpText applyHtml(String text) {
        return new MarkedUpText("<s>" + text + "</s>", Html.class);
    }

    @Override
    public MarkedUpText applyExtdMd(String text) {
        return new MarkedUpText("~~" + text + "~~", Markdown.class);
    }

    @Override
    public Function<String, MarkedUpText> getMarkupLangApplierFor(Class<? extends MarkupLanguage> markupLang) {
        if (markupLang.equals(Html.class)) {
            return this::applyHtml;
        } else if (markupLang.equals(ExtendedMarkdown.class)) {
            return this::applyExtdMd;
        } else {
            return null;
        }
    }

    public String toString() {
        return "StrikethroughFormat@" + this.hashCode() + "{}";
    }
}