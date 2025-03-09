package com.justinquinnb.onefeed.content.details;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Marker interface for information about the place that some digital media is hosted on or published to.
 */
@JsonDeserialize(as = BasicPlatform.class)
public interface Platform {
}
