package com.justinquinnb.onefeed.data.model.content.details;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.justinquinnb.onefeed.data.model.content.BasicContent;

/**
 * Marker interface for information about the place that a piece of {@link BasicContent} is hosted on or published to.
 */
@JsonDeserialize(as = BasicPlatform.class)
public interface Platform {
}
