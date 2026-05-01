package dev.jqb.onefeed.api.pipeline;

import dev.jqb.onefeed.api.plugin.ScheduledTask;
import java.util.List;

/**
 * A class that has recurring tasks for the Spring task scheduler to manage
 */
public interface ScheduledTasks {
    List<ScheduledTask> getScheduledTasks();
}
