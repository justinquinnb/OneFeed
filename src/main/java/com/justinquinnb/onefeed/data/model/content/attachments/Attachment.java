package com.justinquinnb.onefeed.data.model.content.attachments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.justinquinnb.onefeed.data.model.content.BasicContent;

/**
 * A marker interface for {@link BasicContent} attachments, which developers may implement however they desire. An
 * attachment is simply any media affiliated with a piece of {@code Content}.
 */
@JsonDeserialize(as = BasicAttachment.class)
public interface Attachment {
}