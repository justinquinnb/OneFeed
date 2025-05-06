package com.justinquinnb.onefeed.content.attachments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.justinquinnb.onefeed.utils.JsonToString;
import org.springframework.lang.Nullable;

/**
 * Mixed media accompanying a piece of user-generated content. A basic/default implementation of
 * {@link Attachment}.
 */
public class BasicAttachment implements Attachment {
    /**
     * A visual for the attachment, if one is desired. If a {@link #link} is provided for {@code this}
     * {@code BasicAttachment} and no visual is to accompany it, this field may be {@code null}.
     * <br><br>
     * For example, this field may contain a visual like that of an Instagram post's photo or a thumbnail preview of the
     * website provided in {@code link}. If only a link is desired for display by the end user on their site, this field
     * might be left {@code null}.
     */
    @Nullable
    private Visual visual = null;

    /**
     * An Internet link for the attachment, if one is desired. If a {@link #visual} is provided for {@code this}
     * {@code BasicAttachment} and no additional link is to accompany it, this field may be {@code null}.
     * <br><br>
     * For example, this field may contain the URL necessary to achieve on-click functionality for the accompanying
     * {@code visual}. Alternatively, a {@code link} without an accompanying visual may be desired, if only link text
     * is wanted on the end user's site. If neither functionality is desired, this field may be omitted as {@code null}.
     */
    @Nullable
    private BasicLink link = null;

    /**
     * Optional caption text for the attachment, whether it's a standalone URL or visual.
     */
    @Nullable
    private String caption = null;

    /**
     * Constructs a {@link BasicAttachment} using the fields provided by the {@code builder}.
     *
     * @param builder a completed {@link BasicAttachmentBuilder} containing the values to populate {@code this}
     * {@code Attachment}'s fields with
     */
    private BasicAttachment(BasicAttachmentBuilder builder) {
        this.visual = builder.visual;
        this.link = builder.link;
        this.caption = builder.caption;
    }

    /**
     * Constructs a {@link BasicAttachment} containing a {@link Visual}, {@link BasicLink}, or both, and an optional caption
     * to accompany {@code this} {@code Attachment}.
     *
     * @param visual an optional standalone visual (such as an Instagram post photo) or accompaniment to the
     * {@code link} (such as a website preview thumbnail)
     * @param link a standalone Internet link (such as a one to view an Instagram post or accompaniment to the
     * {@code visual} (such as a link to the website previewed by the {@code visual}
     * @param caption a (typically) brief blurb to accompany the media, whether it be a {@link BasicLink} of
     * {@link Visual}
     */
    @JsonCreator
    public BasicAttachment(
            @Nullable @JsonProperty("visual") BasicVisual visual,
            @Nullable @JsonProperty("link") BasicLink link,
            @Nullable @JsonProperty("caption") String caption) {
        this.visual = visual;
        this.link = link;
        this.caption = caption;
    }

    /**
     * Gets the {@link #visual} for {@code this} {@code Attachment}, if one was provided.
     *
     * @return the visual for {@code this} {@code Attachment}, if one was provided
     */
    public Visual getVisual() {
        return visual;
    }

    /**
     * Gets the {@link #link} for {@code this} {@code Attachment}, if one was provided.
     *
     * @return the {@code #link} for {@code this} {@code Attachment}, if one was provided
     */
    public BasicLink getLink() {
        return link;
    }

    /**
     * Gets the caption text for {@code this} {@code Attachment}.
     *
     * @return the caption text for {@code this} {@code Attachment}
     */
    public String getCaption() {
        return caption;
    }

    public String toString() {
        return "BasicAttachment@" + hashCode() + "{" +
                "visual=" + visual + ", " +
                "link=" + link + ", " +
                "caption\"" + caption + "\"}";
    }

    /**
     * Builder for {@link BasicAttachment}.
     */
    public static class BasicAttachmentBuilder {
        private Visual visual = null;

        private BasicLink link = null;

        private String caption = null;

        /**
         * Sets the {@link BasicAttachment}'s visual.
         *
         * @param visual an optional standalone visual (such as an Instagram post photo) or accompaniment to the
         * {@code link} (such as a website preview thumbnail)
         *
         * @return {@code this} {@link BasicAttachmentBuilder} with its {@code visual} field populated by {@code visual}
         */
        public BasicAttachmentBuilder setVisual(Visual visual) {
            this.visual = visual;
            return this;
        }

        /**
         * Sets the {@link BasicAttachment}'s link.
         *
         * @param link a standalone Internet link (such as a one to view an Instagram post or accompaniment to the
         * {@code visual} (such as a link to the website previewed by the {@code visual}
         *
         * @return {@code this} {@link BasicAttachmentBuilder} with its {@code link} field populated by {@code link}
         */
        public BasicAttachmentBuilder setLink(BasicLink link) {
            this.link = link;
            return this;
        }

        /**
         * Sets the caption of the {@link BasicAttachment}.
         *
         * @param caption a (typically) brief blurb to accompany the media, whether it be a {@link BasicLink} of
         * {@link Visual}
         *
         * @return {@code this} {@link BasicAttachmentBuilder} with its {@code caption} field populated by {@code caption}
         */
        public BasicAttachmentBuilder setCaption(String caption) {
            this.caption = caption;
            return this;
        }

        /**
         * Builds a {@link BasicAttachment} using the values provided through {@code this}
         * {@link BasicAttachmentBuilder}.
         *
         * @return a new instance of an {@code BasicAttachment} with the fields specified by previous setter methods
         */
        public BasicAttachment build() {
            return new BasicAttachment(this);
        }

        @Override
        public String toString() {
            return JsonToString.of(this);
        }
    }
}