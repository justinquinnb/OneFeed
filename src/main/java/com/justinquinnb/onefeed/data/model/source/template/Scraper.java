package com.justinquinnb.onefeed.data.model.source.template;

/**
 * A basic Scraping-type data source to optionally extend.
 */
public class Scraper implements DataSource {
    @Override
    public void init() {

    }

    @Override
    public boolean test() {
        return false;
    }
}
