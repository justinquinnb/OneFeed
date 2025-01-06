package content.types;

import content.details.Profile;
import content.details.Platform;

import java.time.LocalDateTime;

/**
 * User-generated content that exists within a feed.
 */
public abstract class Content {
    private LocalDateTime timestamp;
    private Profile actor;
    private Platform source;

    public Content(LocalDateTime timestamp, Profile actor, Platform source) {
        this.timestamp = timestamp;
        this.actor = actor;
        this.source = source;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public Profile getActor() {
        return this.actor;
    }

    public Platform getPlatform() {
        return this.source;
    }

    public abstract String getText();
}