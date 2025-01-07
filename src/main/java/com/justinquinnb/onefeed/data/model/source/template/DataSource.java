package com.justinquinnb.onefeed.data.model.source.template;

/**
 * Outlines the functionalities of a valid data source.
 */
public interface DataSource {
    void init();
    boolean test();
}