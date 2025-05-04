package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.customization.textstyle.markup.MarkupLanguage;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormatting;
import com.justinquinnb.onefeed.customization.textstyle.markup.TextFormattingRegistry;

/**
 * An exception to be thrown when a {@link MarkupLanguage} or {@link TextFormatting} type is referenced, but the
 * {@link TextFormattingRegistry} is unaware of.
 */
public class UnknownTextStyleEntityException extends Exception {
    public UnknownTextStyleEntityException(String message) {
        super(message);
    }
}