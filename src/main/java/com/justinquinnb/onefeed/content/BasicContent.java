package com.justinquinnb.onefeed.content;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.JsonToString;
import com.justinquinnb.onefeed.content.attachments.Attachment;
import com.justinquinnb.onefeed.content.details.ContentSourceId;
import com.justinquinnb.onefeed.content.details.Platform;
import com.justinquinnb.onefeed.content.details.Producer;
import com.justinquinnb.onefeed.content.reception.Comment;
import com.justinquinnb.onefeed.content.reception.Reception;
import com.justinquinnb.onefeed.customization.source.ContentSource;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.Arrays;

/**
 * A basic implementation of a single piece of content from some user-generated feed.
 */
public class BasicContent extends OneFeedContent implements Comment {
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
     * @param contentUrl the URL of {@code this} content on its host {@code platform}
     * @param producer details about the user or entity that produced {@code this} {@code Content}
     * @param platform details about the site or platform hosting {@code this} {@code Content}
     * @param text the written part of {@code this} content, usually referred to as the caption, description, or summary
     * @param attachments any {@link Attachment}s associated with {@code this} {@code Content}, in the order that they
     *                    appear in the content on its source
     * @param reception details about the {@code Content}'s reception
     */
    @JsonCreator
    public BasicContent(
            @JsonProperty("origin") ContentSourceId origin,
            @JsonProperty("timestamp") Instant timestamp,
            @JsonProperty("contentUrl") String contentUrl,
            @JsonProperty("producer") Producer producer,
            @JsonProperty("platform") Platform platform,
            @Nullable @JsonProperty("text") String text,
            @Nullable @JsonProperty("attachments") Attachment[] attachments,
            @Nullable @JsonProperty("reception") Reception reception
    ) {
        super(origin, timestamp, contentUrl);
        this.producer = producer;
        this.platform = platform;

        this.text = text;
        this.attachments = attachments;

        this.reception = reception;
    }

    /**
     * Constructs an instance of {@link BasicContent} using the fields provided by the {@code builder}.
     *
     * @param builder a completed {@link BasicContentBuilder} containing the values to populate {@code this}
     * {@code BasicContent}'s fields with
     */
    public BasicContent(BasicContentBuilder builder) {
        super(builder.origin, builder.timestamp, builder.contentUrl);

        this.producer = builder.producer;
        this.platform = builder.platform;

        this.text = builder.text;
        this.attachments = builder.attachments;

        this.reception = builder.reception;
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
                "{origin=\"" + getOrigin() +
                "\", timestamp=" + getTimestamp() +
                ", contentUrl=\"" + getContentUrl() +
                "\", producer=" + this.producer +
                ", platform=" + this.platform +
                "\", text=\"" + this.text +
                "\", attachments=" + Arrays.toString(this.attachments) +
                "\", reception=" + this.reception + "}";
    }

    /**
     * Builder for {@link BasicContent}
     */
    public static class BasicContentBuilder {
        private ContentSourceId origin = null;
        private Instant timestamp = null;
        private String contentUrl = null;
        private Producer producer = null;
        private Platform platform = null;

        private String text = null;
        private Attachment[] attachments = null;

        private Reception reception = null;

        /**
         * Starts a {@link BasicContentBuilder} with the required fields.
         *
         * @param origin the {@link ContentSourceId} of the {@link ContentSource} instance that the {@code Content}
         *               originates from
         * @param timestamp the {@link Instant} the content was posted to or generated on the {@link #platform}
         * @param contentUrl the URL of {@code this} content on its host {@code platform}
         * @param producer details about the user or entity that produced {@code this} {@code Content}
         * @param platform platform details about the site or platform hosting {@code this} {@code Content}
         */
        public BasicContentBuilder(
                ContentSourceId origin, Instant timestamp, String contentUrl, Producer producer, Platform platform) {
            this.origin = origin;
            this.timestamp = timestamp;
            this.contentUrl = contentUrl;
            this.producer = producer;
            this.platform = platform;
        }

        /**
         * Sets the {@link BasicContent}'s text.
         *
         * @param text the written part of {@code this} content, usually referred to as the caption, description, or
         *             summary
         * @return {@code this} {@link BasicContentBuilder} with its {@code text} field populated by {@code text}
         */
        public BasicContentBuilder setText(String text) {
            this.text = text;
            return this;
        }

        /**
         * Sets the {@link BasicContent}'s attachments.
         *
         * @param attachments any {@link Attachment}s associated with {@code this} {@code Content}, in the order that
         *                    they appear in the content on its source
         * @return {@code this} {@link BasicContentBuilder} with its {@code text} field populated by {@code text}
         */
        public BasicContentBuilder setAttachments(Attachment[] attachments) {
            this.attachments = attachments;
            return this;
        }

        /**
         * Sets the {@link BasicContent}'s reception.
         *
         * @param reception details about the {@code Content}'s reception
         * @return {@code this} {@link BasicContentBuilder} with its {@code reception} field populated by
         * {@code reception}
         */
        public BasicContentBuilder setReception(Reception reception) {
            this.reception = reception;
            return this;
        }

        /**
         * Builds a {@link BasicContent} instance using the values provided through {@code this}
         * {@link BasicContentBuilder}.
         *
         * @return a new instance of an {@code BasicContent} with the fields specified by {@code this}
         * {@code BasicContentBuilder}
         */
        public BasicContent build() {
            if (text == null && attachments == null) {
                throw new IllegalStateException("Text and attachments fields cannot both be null");
            }

            return new BasicContent(this);
        }

        @Override
        public String toString() {
            return JsonToString.of(this);
        }
    }
}