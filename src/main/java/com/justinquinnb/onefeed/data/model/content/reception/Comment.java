package com.justinquinnb.onefeed.data.model.content.reception;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.justinquinnb.onefeed.data.model.content.BasicContent;

/**
 * Marker interface for comments on a piece of digital media.
 *
 * @see BasicContent
 */
@JsonDeserialize(as = BasicContent.class)
public interface Comment {
}