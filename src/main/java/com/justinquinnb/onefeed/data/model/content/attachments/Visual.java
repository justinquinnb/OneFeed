package com.justinquinnb.onefeed.data.model.content.attachments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.justinquinnb.onefeed.data.model.content.BasicContent;

/**
 * Marker interface for a photo that accompanies a piece of {@link BasicContent}.
 */
@JsonDeserialize(as = BasicVisual.class)
public interface Visual {
}
