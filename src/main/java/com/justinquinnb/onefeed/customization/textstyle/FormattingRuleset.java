package com.justinquinnb.onefeed.customization.textstyle;

import java.util.LinkedHashSet;

public class FormattingRuleset<T extends TextFormatting> {
    LinkedHashSet<FormattingRule<T>> rules;
}