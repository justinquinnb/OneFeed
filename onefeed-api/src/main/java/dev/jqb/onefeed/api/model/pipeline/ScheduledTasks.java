package dev.jqb.onefeed.api.model.pipeline;

import dev.jqb.onefeed.api.model.data.plugin.ScheduledTask;
import java.util.List;

/**
 * A class that has recurring tasks for the Spring task scheduler to manage
 */
public interface ScheduledTasks {
    List<ScheduledTask> getScheduledTasks();
}
