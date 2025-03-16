package com.justinquinnb.onefeed.customization.defaults;

public class LinkFormat extends BasicFormatting {
    String url;
    String tooltip = "";

    public LinkFormat() {
        super("Link");
    }

    public LinkFormat(String url) {
        super("Link");
        this.url = url;
    }

    public LinkFormat(String url,String tooltip) {
        super("Link");
        this.url = url;
        this.tooltip = tooltip;
    }
}