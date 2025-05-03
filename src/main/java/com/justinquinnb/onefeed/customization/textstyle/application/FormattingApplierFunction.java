package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.FormattingMismatchException;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;

import java.util.function.Function;

/**
 * A {@link Function}-like interface specifying a valid formatting applier's functionality.
 */
@FunctionalInterface
public interface FormattingApplierFunction {
    MarkedUpText apply(FormattingMarkedText text) throws FormattingMismatchException;
}