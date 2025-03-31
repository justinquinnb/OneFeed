package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

/**
 * An exception to be thrown when the {@code Function} provided by {@link MarkupLanguage#getMarkupLangApplierFor}
 * in {@link MarkupLanguage} implementors does not produce {@link MarkedUpText} employing the required type of
 * {@link MarkupLanguage}.
 */
public class ApplierMismatchException extends RuntimeException {
    public ApplierMismatchException(String message) {
        super(message);
    }
}
