package com.justinquinnb.onefeed.data.model.content.reception;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Marker interface for data about how something was received by an audience.
 */
@JsonDeserialize(as = BasicReception.class)
public interface Reception {
}
