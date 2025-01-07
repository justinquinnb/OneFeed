package com.justinquinnb.onefeed.data.model.source.template;

/**
 * A basic API Endpoint-type data source to optionally extend.
 */
public class APIEndpoint implements DataSource {
    @Override
    public void init() {

    }

    @Override
    public boolean test() {
        return false;
    }
}
