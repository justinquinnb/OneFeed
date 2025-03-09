package com.justinquinnb.onefeed.customization.addon.process.textstyle;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class TextFormattingIndex {
    FormattingIndexLanguage usedLang;
    LinkedHashMap<TextFormat, SubstringParsingLocation[]> index;

    public TextFormattingIndex(TextFormat[] processingOrder) {
        this.index = new LinkedHashMap<>();
        for (TextFormat format : processingOrder) {
            this.index.put(format, null);
        }

        // Define the language as all the TextFormats included in the processingOrder
        this.usedLang = new FormattingIndexLanguage(processingOrder);
    }

    public TextFormattingIndex(FormattingIndexLanguage usedLang) {
        this.usedLang = usedLang;
    }
}
