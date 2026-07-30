package dev.jqb.onefeed.core.feed;

/**
 * Attributes an item to a source feed
 * @param feedId the ID of the source feed
 * @param feedUrl the URL of the source feed
 */
public record FeedAttribution(FeedId feedId, String feedUrl) {

}
