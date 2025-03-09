package com.justinquinnb.onefeed.content;

import com.justinquinnb.onefeed.content.details.ContentSourceId;

import java.time.Instant;

/**
 * A piece of processed {@link RawContent} to be returned by OneFeed in response to API hits.
 */
public abstract class OneFeedContent extends Content {
    public OneFeedContent(ContentSourceId origin, Instant timestamp, String contentUrl) {
        super(origin, timestamp, contentUrl);
    }
}