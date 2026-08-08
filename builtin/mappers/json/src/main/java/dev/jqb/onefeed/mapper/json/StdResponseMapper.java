package dev.jqb.onefeed.mapper.json;

import dev.jqb.onefeed.core.actor.OneFeedActor;
import dev.jqb.onefeed.core.content.OneFeedAttachment;
import dev.jqb.onefeed.core.content.OneFeedContent;
import dev.jqb.onefeed.core.content.OneFeedMedia;
import dev.jqb.onefeed.core.feed.FeedAttribution;
import dev.jqb.onefeed.core.feed.FeedPos;
import dev.jqb.onefeed.core.platform.ExternalRef;
import dev.jqb.onefeed.core.platform.Platform;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

/**
 * Maps OneFeed entities to API response types
 */
@Mapper
public interface StdResponseMapper {
    FeedPosResponse toPosResponse(FeedPos pos);
    OneFeedActorResponse toOneFeedActorResponse(OneFeedActor actor);
    OneFeedContentResponse toOneFeedContentResponse(OneFeedContent content);

    @SubclassMapping(source = OneFeedMedia.class, target = OneFeedMediaResponse.class)
    OneFeedMediaResponse toOneFeedMediaResponse(OneFeedAttachment attachment);

    OneFeedMediaResponse toOneFeedMediaResponse(OneFeedMedia media);
    PlatformResponse toPlatformResponse(Platform platform);

    ExternalRefResponse toExternalRefResponse(ExternalRef externalRef);
    FeedAttributionResponse toFeedAttributionResponse(FeedAttribution feedAttribution);
}
