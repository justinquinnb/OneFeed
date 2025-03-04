package com.justinquinnb.onefeed.data.model.content.attachments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Marker interface for a photo or other visual.
 */
@JsonDeserialize(as = BasicVisual.class)
public interface Visual {
}
