package com.justinquinnb.onefeed.customization.textstyle.formattings;

import java.util.regex.Pattern;

/**
 * An exception to be thrown when a regex {@link Pattern} could not be found by a {@link MarkupLanguage} implementor
 * for the desired {@link MarkupLanguage}.
 *
 */
public class NoSuchPatternException extends RuntimeException {
    public NoSuchPatternException(String message) {
        super(message);
    }
}
