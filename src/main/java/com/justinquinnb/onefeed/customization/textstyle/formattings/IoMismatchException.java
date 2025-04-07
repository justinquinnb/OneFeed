package com.justinquinnb.onefeed.customization.textstyle.formattings;

import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

/**
 * An exception to be thrown when the data contained in an invoked method's output does not match the desired type
 * provided by the invoking method.
 */
public class IoMismatchException extends RuntimeException {
    public IoMismatchException(String message) {
        super(message);
    }
}