package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.text.ParseException;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Marker for code block text formatting. Obtain an instance through {@link #getInstance()}.
 */
public class CodeBlockFormat extends TextFormatting implements Html {
    private static volatile CodeBlockFormat instance = null;

    /**
     * Creates an instance of code block formatting.
     */
    private CodeBlockFormat() {
        super("CodeBlockFormat");
    }

    /**
     * Gets the single instance of {@code CodeBlockFormat}. Multiple instances aren't necessary as this format requires no
     * complementary data--it's effectively a marker.
     *
     * @return the singleton instance of {@code CodeBlockFormat}
     */
    public static CodeBlockFormat getInstance() {
        // Lazy initialization delaying instantiation until first invocation w/ double-checked locking to avoid race
        // conditions
        if (instance == null) {
            synchronized (CodeBlockFormat.class) {
                if (instance == null) {
                    instance = new CodeBlockFormat();
                }
            }
        }
        return instance;
    }

    @Override
    public MarkedUpText applyHtml(String text) {
        return new MarkedUpText("<pre><code>" + text + "</code></pre>", Html.class);
    }

    @Override
    public Pattern getHtmlPattern() {
        return Pattern.compile("((<pre(.*)>)\\s(<code(.*)>)(.*)(</code\\s>)\\s(</pre\\s>))", Pattern.DOTALL);
    }

    @Override
    public FormattingMarkedText extractFromHtml(MarkedUpText text) {
        try {
            // Find the outermost element tags in order to determine that outermost element's content
            Pattern startTag = Pattern.compile("^((<pre\\s(.*)>)\\s(<code\\s(.*)>))");
            Pattern endTag = Pattern.compile("((</code\\s>)\\s(</pre\\s))$");
            return MarkupLanguage.parseFmtBetweenBounds(text, startTag, endTag, CodeBlockFormat.getInstance());
        } catch (IllegalStateException e) {
            return new FormattingMarkedText(text.getText(), DefaultFormat.getInstance());
        }
    }

    @Override
    public Function<String, MarkedUpText> getMarkupLangApplierFor(
            Class<? extends MarkupLanguage> markupLang) {
        if (markupLang.equals(Html.class)) {
            return this::applyHtml;
        } else {
            return null;
        }
    }

    @Override
    public Pattern getMarkupPatternFor(Class<? extends MarkupLanguage> markupLang) {
        if (markupLang.equals(Html.class)) {
            return this.getHtmlPattern();
        } else {
            return null;
        }
    }

    @Override
    public Function<MarkedUpText, FormattingMarkedText> getFormattingExtractorFor(
            Class<? extends MarkupLanguage> markupLang) {
        if (markupLang.equals(Html.class)) {
            return this::extractFromHtml;
        } else {
            return null;
        }
    }
}