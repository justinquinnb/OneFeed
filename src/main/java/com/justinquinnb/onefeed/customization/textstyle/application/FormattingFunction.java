package com.justinquinnb.onefeed.customization.textstyle.application;

import com.justinquinnb.onefeed.customization.textstyle.FormattingMarkedText;
import com.justinquinnb.onefeed.customization.textstyle.TextFormatting;

import java.util.function.Function;

public class FormattingFunction<T extends TextFormatting> {
    public Function<FormattingMarkedText<T>, String> process;
}