package com.justinquinnb.onefeed.data.model.content.details;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.justinquinnb.onefeed.data.model.content.BasicContent;

/**
 * Information about the entity producing the {@link BasicContent}.
 */
@JsonDeserialize(as = BasicProducer.class)
public interface Producer {
}
