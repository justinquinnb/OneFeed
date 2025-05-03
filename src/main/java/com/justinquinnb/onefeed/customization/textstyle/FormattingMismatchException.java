package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;

/**
 * An exception to be thrown when a provided {@link TextFormatting} type does not match what the recipient expects.
 */
public class FormattingMismatchException extends Exception {
    public FormattingMismatchException(String message) {
        super(message);
    }
}