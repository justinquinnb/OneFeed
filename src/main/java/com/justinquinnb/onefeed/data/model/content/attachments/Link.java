package com.justinquinnb.onefeed.data.model.content.attachments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Marker interface for a link.
 */
@JsonDeserialize(as = BasicLink.class)
public interface Link {
}