package com.justinquinnb.onefeed.data.model.content;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * A marker interface for a single piece of content from some user-generated feed.
 */
@JsonDeserialize(as = BasicContent.class)
public interface Content {
}