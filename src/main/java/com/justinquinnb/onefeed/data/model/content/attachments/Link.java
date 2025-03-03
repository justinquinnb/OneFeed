package com.justinquinnb.onefeed.data.model.content.attachments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.justinquinnb.onefeed.data.model.content.BasicContent;

/**
 * A marker interface for a link that accompanies a piece of {@link BasicContent}.
 */
@JsonDeserialize(as = BasicLink.class)
public interface Link {
}