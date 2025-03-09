package com.justinquinnb.onefeed.content.attachments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * A marker interface for any media attached to a piece of published content.
 */
@JsonDeserialize(as = BasicAttachment.class)
public interface Attachment {
}