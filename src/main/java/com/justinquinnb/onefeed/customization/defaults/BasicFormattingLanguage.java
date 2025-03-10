package com.justinquinnb.onefeed.customization.defaults;

import com.justinquinnb.onefeed.customization.textstyle.application.TextFormattingLanguage;

public class BasicFormattingLanguage extends TextFormattingLanguage {
    public BasicFormattingLanguage() {
        super();
    }

    protected void addFormattings() {
        addFormatting(new TextFormatting("Bold"));
        addFormatting(new TextFormatting("Italic"));
        addFormatting(new TextFormatting("Underline"));
        addFormatting(new TextFormatting("Strikethrough"));
        addFormatting(new TextFormatting("Superscript"));
        addFormatting(new TextFormatting("Subscript"));
        addFormatting(new TextLink());
    }
}