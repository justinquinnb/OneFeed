package com.justinquinnb.onefeed.data.model.content;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.data.model.content.attachments.Attachment;
import com.justinquinnb.onefeed.data.model.content.details.Platform;
import com.justinquinnb.onefeed.data.model.content.details.Producer;
import com.justinquinnb.onefeed.data.model.content.reception.Comment;
import com.justinquinnb.onefeed.data.model.content.reception.Reception;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.Arrays;

/**
 * A basic implementation of a single piece of content from some user-generated feed.
 */
public class BasicContent implements Content, Comment {
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
     * The written part of {@code this} content, usually referred to as the caption, description, or summary.
     */
    @Nullable
    private final String text;

    /**
     * Any {@link Attachment}s associated with {@code this} {@code Content}, in the order that they appear in the
     * content on its source.
     */
    @Nullable
    private final Attachment[] attachments;

    /**
     * Details about {@code this} {@link Content}'s reception.
     */
    @Nullable
    private final Reception reception;

    /**
     * Instantiates a piece of {@link BasicContent} containing text and attached media.
     *
     * @param timestamp the {@link Instant} the content was posted to or generated on the {@link #platform}
     * @param contentUrl the URL of {@code this} content on its host {@code platform}.
     * @param producer details about the user or entity that produced {@code this} {@code Content}
     * @param platform details about the site or platform hosting {@code this} {@code Content}
     * @param text the written part of {@code this} content, usually referred to as the caption, description, or summary
     * @param attachments any {@link Attachment}s associated with {@code this} {@code Content}, in the order that they
     *                    appear in the content on its source
     * @param reception details about the {@code Content}'s reception
     */
    @JsonCreator
    public BasicContent(
            @JsonProperty("timestamp") Instant timestamp,
            @JsonProperty("contentUrl") String contentUrl,
            @JsonProperty("producer") Producer producer,
            @JsonProperty("platform") Platform platform,
            @Nullable @JsonProperty("text") String text,
            @Nullable @JsonProperty("attachments") Attachment[] attachments,
            @Nullable @JsonProperty("reception") Reception reception
    ) {
        this.timestamp = timestamp;
        this.contentUrl = contentUrl;
        this.producer = producer;
        this.platform = platform;

        this.text = text;
        this.attachments = attachments;

        this.reception = reception;
    }

    /**
     * Instantiates a piece of {@link BasicContent} containing text but no attached media.
     *
     * @param timestamp the {@link Instant} the content was posted to or generated on the {@link #platform}
     * @param contentUrl the URL of {@code this} content on its host {@code platform}.
     * @param producer details about the user or entity that produced {@code this} {@code Content}
     * @param platform details about the site or platform hosting {@code this} {@code Content}
     * @param text the written part of {@code this} content, usually referred to as the caption, description, or summary
     * @param reception details about the {@code Content}'s reception
     */
    public BasicContent(
            Instant timestamp,
            String contentUrl,
            Producer producer,
            Platform platform,
            String text,
            Reception reception
    ) {
        this(timestamp, contentUrl, producer, platform, text, new Attachment[0], reception);
    }

    /**
     * Instantiates a piece of {@link BasicContent} containing no text but attached media.
     *
     * @param timestamp the {@link Instant} the content was posted to or generated on the {@link #platform}
     * @param contentUrl the URL of {@code this} content on its host {@code platform}.
     * @param producer details about the user or entity that produced {@code this} {@code Content}
     * @param platform details about the site or platform hosting {@code this} {@code Content}
     * @param attachments any {@link Attachment}s associated with {@code this} {@code Content}, in the order that they
     *                    appear in the content on its source
     * @param reception details about the {@code Content}'s reception
     */
    public BasicContent(
            Instant timestamp,
            String contentUrl,
            Producer producer,
            Platform platform,
            Attachment[] attachments,
            Reception reception
    ) {
        this(timestamp, contentUrl, producer, platform, "", attachments, reception);
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
     * Gets the attachments of {@code this} {@code Content}.
     *
     * @return the attachments of {@code this} {@code Content}.
     */
    public Attachment[] getAttachments() {
        return Arrays.copyOf(this.attachments, this.attachments.length);
    }

    /**
     * Gets details about the {@code Content}'s reception.
     *
     * @return the details about the {@code Content}'s reception
     */
    public Reception getReception() {
        return this.reception;
    }

    public String toString() {
        return "Content@" + this.hashCode() +
                "{timestamp=" + this.timestamp +
                ", contentUrl=\"" + this.contentUrl +
                "\", producer=" + this.producer +
                ", platform=" + this.platform +
                "\", text=\"" + this.text +
                "\", attachments=" + Arrays.toString(this.attachments) +
                "\", reception=" + this.reception + "}";
    }
}