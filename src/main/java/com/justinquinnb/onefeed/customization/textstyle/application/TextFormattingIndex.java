package com.justinquinnb.onefeed.customization.textstyle.application;

// TODO

import com.justinquinnb.onefeed.customization.defaults.TextFormatting;
import com.justinquinnb.onefeed.customization.textstyle.SubstringParsingLocation;

import java.util.HashMap;

/**
 *
 * @param <T>
 */
public class TextFormattingIndex<T extends TextFormattingLanguage> {
    HashMap<SubstringParsingLocation, TextFormatting> index;

    public TextFormattingIndex() {}
}