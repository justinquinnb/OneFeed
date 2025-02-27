package com.justinquinnb.onefeed.data.model.content;

import com.justinquinnb.onefeed.data.model.content.details.Producer;
import com.justinquinnb.onefeed.data.model.content.details.Platform;

import java.time.Instant;
import java.util.Arrays;

/**
 * A single piece of content from some user-generated feed.
 */
public abstract class Content {
    /**
     * The {@link Instant} the content was posted to or generated on the {@link #platform}.
     */
    private final Instant timestamp;

    /**
     * The URL of {@code this} content on its host {@code platform}.
     */
    private final String contentUrl;

    /**
     * Details about the user or entity that produced {@code this} {@code Content}.
     */
    private final Producer producer;

    /**
     * Details about the site or platform hosting {@code this} {@code Content}.
     */
    private final Platform platform;

    /**
     * The written part of {@code this} content, usually referred to as the caption, description, or summary
     */
    private final String text;

    /**
     * URLs to the media associated with {@code this} content, for example photos or videos in an Instagram post
     */
    private final String[] attachmentUrls;

    /**
     * Instantiates a piece of {@link Content} containing text and attached media.
     *
     * @param timestamp the {@link Instant} the content was posted to or generated on the {@link #platform}
     * @param contentUrl the URL of {@code this} content on its host {@code platform}.
     * @param producer details about the user or entity that produced {@code this} {@code Content}
     * @param platform details about the site or platform hosting {@code this} {@code Content}
     * @param text the written part of {@code this} content, usually referred to as the caption, description, or summary
     * @param attachmentUrls URLs to the media associated with {@code this} content, for example photos or videos in an
     *                       Instagram post
     */
    public Content(
            Instant timestamp,
            String contentUrl,
            Producer producer,
            Platform platform,
            String text,
            String[] attachmentUrls
    ) {
        this.timestamp = timestamp;
        this.contentUrl = contentUrl;
        this.producer = producer;
        this.platform = platform;

        this.text = text;
        this.attachmentUrls = attachmentUrls;

    }

    /**
     * Instantiates a piece of {@link Content} containing text and attached media.
     *
     * @param timestamp the {@link Instant} the content was posted to or generated on the {@link #platform}
     * @param contentUrl the URL of {@code this} content on its host {@code platform}.
     * @param producer details about the user or entity that produced {@code this} {@code Content}
     * @param platform details about the site or platform hosting {@code this} {@code Content}
     * @param text the written part of {@code this} content, usually referred to as the caption, description, or summary
     */
    public Content(
            Instant timestamp,
            String contentUrl,
            Producer producer,
            Platform platform,
            String text
    ) {
        this(timestamp, contentUrl, producer, platform, text, new String[0]);
    }

    /**
     * Instantiates a piece of {@link Content} containing text and attached media.
     *
     * @param timestamp the {@link Instant} the content was posted to or generated on the {@link #platform}
     * @param contentUrl the URL of {@code this} content on its host {@code platform}.
     * @param producer details about the user or entity that produced {@code this} {@code Content}
     * @param platform details about the site or platform hosting {@code this} {@code Content}
     * @param attachmentUrls URLs to the media associated with {@code this} content, for example photos or videos in an
     *                       Instagram post
     */
    public Content(
            Instant timestamp,
            String contentUrl,
            Producer producer,
            Platform platform,
            String[] attachmentUrls
    ) {
        this(timestamp, contentUrl, producer, platform, "", attachmentUrls);
    }

    /**
     * Gets the timestamp at which {@code this} {@code Content} was published.
     *
     * @return the timestamp at which the content was published.
     */
    public Instant getTimestamp() {
        return this.timestamp;
    }

    /**
     * Gets the details of the person or entity that produced {@code this} {@code Content}.
     *
     * @return the details of the person or entity that produced {@code this} {@code Content}.
     */
    public Producer getProducer() {
        return this.producer;
    }

    /**
     * Gets the details of the platform where {@code this} {@code Content} was published.
     *
     * @return the details of the platform where {@code this} {@code Content} was published.
     */
    public Platform getPlatform() {
        return this.platform;
    }

    /**
     * Gets the text body of {@code this} {@code Content}.
     *
     * @return the text body of {@code this} {@code Content}.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Gets the attachment URLs of {@code this} {@code Content}.
     *
     * @return the attachment URLs of {@code this} {@code Content}.
     */
    public String[] getAttachmentsUrls() {
        return Arrays.copyOf(this.attachmentUrls, this.attachmentUrls.length);
    }

    public String toString() {
        return "Content@" + this.hashCode() +
                "{timestamp=" + this.timestamp +
                ", contentUrl=" + this.contentUrl +
                ", producer=" + this.producer.getUsername() +
                ", platform=" + this.platform.getName() +
                ", text=" + this.text +
                ", attachmentUrls=" + Arrays.toString(this.attachmentUrls) + "}";
    }
}