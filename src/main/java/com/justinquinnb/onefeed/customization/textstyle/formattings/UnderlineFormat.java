package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.function.Function;

/**
 * Marker for underline text formatting. Obtain an instance through {@link #getInstance()}.
 */
public class UnderlineFormat extends TextFormatting implements Html {
    private static volatile UnderlineFormat instance = null;

    /**
     * Creates an instance of underline formatting.
     */
    private UnderlineFormat() {
        super("Underline");
    }

    /**
     * Gets the single instance of {@code UnderlineFormat}. Multiple instances aren't necessary as this format requires
     * no complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code UnderlineFormat}
     */
    public static UnderlineFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (UnderlineFormat.class) {
                if (instance == null) {
                    instance = new UnderlineFormat();
                }
            }
        }
        return instance;
    }

    @Override
    public MarkedUpText applyHtml(String text) {
        return new MarkedUpText("<u>" + text + "</u>", Html.class);
    }

    @Override
    public Function<String, MarkedUpText> getMarkupLangApplierFor(Class<? extends MarkupLanguage> markupLang) {
        if (markupLang.equals(Html.class)) {
            return this::applyHtml;
        } else {
            return null;
        }
    }

    public String toString() {
        return "UnderlineFormat@" + this.hashCode() + "{}";
    }
}