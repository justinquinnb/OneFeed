package com.justinquinnb.onefeed.content.details;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Information about the entity producing some digital content.
 */
@JsonDeserialize(as = BasicProducer.class)
public interface Producer {
}
