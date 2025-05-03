package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.textstyle.markup.MarkupLanguage;

/**
 * An exception to be thrown when a provided {@link MarkupLanguage} type does not match what the recipient expects.
 */
public class MarkupLangMismatchException extends Exception {
    public MarkupLangMismatchException(String message) {
        super(message);
    }
}