package com.justinquinnb.onefeed.customization.textstyle.parsing;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.MarkedUpText;
import com.justinquinnb.onefeed.customization.textstyle.MarkupLangMismatchException;

import java.text.ParseException;
import java.util.function.Function;

/**
 * A {@link Function}-like interface specifying a valid formatting parser's functionality.
 */
@FunctionalInterface
public interface FormattingParserFunction {
    FormattingMarkedText parse(MarkedUpText text) throws MarkupLangMismatchException, ParseException;
}