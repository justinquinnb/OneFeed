package com.justinquinnb.onefeed.content.reception;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.justinquinnb.onefeed.content.BasicContent;

/**
 * Marker interface for comments on a piece of digital media.
 *
 * @see BasicContent
 */
@JsonDeserialize(as = BasicContent.class)
public interface Comment {
}