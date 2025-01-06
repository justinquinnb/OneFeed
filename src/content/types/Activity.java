package content.types;

import content.details.Platform;
import content.details.Profile;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * A type of content that describes something an actor has done.
 * Effectively, a collection of activity would look like an event log.
 */
public class Activity extends Content {
    private String[] actionUrls;
    private String summary;

    public Activity(
            LocalDateTime timestamp,
            Profile actor,
            Platform source,
            String[] actionUrls,
            String description
    ) {
        super(timestamp, actor, source);
        this.actionUrls = actionUrls;
        this.summary = summary;
    }

    public String[] getActionUrls() {
        return Arrays.copyOf(actionUrls, actionUrls.length);
    }

    public String getText() {
        return summary;
    }
}