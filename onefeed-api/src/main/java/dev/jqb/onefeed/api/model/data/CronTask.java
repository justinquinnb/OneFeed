package dev.jqb.onefeed.api.model.data;

import java.time.Duration;
import lombok.Getter;
import org.pf4j.PluginWrapper;

/**
 * A task to perform on a cron schedule
 */
@Getter
public final class CronTask extends ScheduledTask {

    /**
     * The cron schedule to run the task on
     */
    private String cronExpression;

    /**
     * Creates a new runnable task to execute on the given cron schedule.
     *
     * @param task the task to execute
     * @param name the human-friendly name of the task
     * @param delay the delay between each execution of the task
     */
    public CronTask(Runnable task, String name, String cronExpression, Duration delay) {
        super(task, name);
        this.cronExpression = cronExpression;
    }
}
