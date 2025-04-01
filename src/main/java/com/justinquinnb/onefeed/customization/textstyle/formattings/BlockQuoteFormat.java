package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.function.Function;

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
        return new MarkedUpText("<b>" + text + "</b>", Html.class);
    }

    @Override
    public MarkedUpText applyMd(String text) {
        return new MarkedUpText(">" + text, Markdown.class);
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