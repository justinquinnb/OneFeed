package com.justinquinnb.onefeed.content.details;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Wrapper for a unique {@code String} that identifies a specific {@code ContentSource} instance when interacting with the
 * OneFeed API.
 * <br><br>
 * Facilitates the tracking of multiple {@code ContentSource} instances, a situation possible when
 * content is desired from multiple profiles on the same platform, for example.
 */
@JsonDeserialize(using = ContentSourceIdDeserializer.class)
public class ContentSourceId {
    /**
     * The actual ID.
     */
    @JsonValue
    private String id;

    /**
     * Constructs a {@link ContentSourceId} with the provided {@code id}.
     *
     * @param id the unique {@code String} identifier
     */
    public ContentSourceId(String id) {
        this.id = id;
    }

    /**
     * Creates a {@link ContentSourceId} instance with the provided {@code id}.
     *
     * @param id the unique {@code String} identifier
     *
     * @return a {@link ContentSourceId} instance with the provided {@code id}
     */
    public static ContentSourceId of(String id) {
        return new ContentSourceId(id);
    }

    /**
     * Gets the {@link #id} {@code this} {@link ContentSourceId} wraps.
     *
     * @return the unique Content Source ID contained in {@code this} {@link ContentSourceId}, {@link #id}
     */
    public String getId() {
        return this.id;
    }

    public boolean equals(ContentSourceId other) {
        return this.id.equals(other.getId());
    }

    public String toString() {
        return this.id;
    }
}