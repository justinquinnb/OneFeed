package com.justinquinnb.onefeed.content;

import com.justinquinnb.onefeed.content.details.ContentSourceId;
import com.justinquinnb.onefeed.customization.source.ContentSource;

import java.time.Instant;

/**
 * A piece of content as it comes straight from the external {@link ContentSource}.
 */
public abstract class RawContent extends Content {
    public RawContent(ContentSourceId origin, Instant timestamp, String contentUrl) {
        super(origin, timestamp, contentUrl);
    }
}