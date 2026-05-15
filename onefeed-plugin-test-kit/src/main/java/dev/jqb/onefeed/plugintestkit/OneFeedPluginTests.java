package dev.jqb.onefeed.plugintestkit;

import dev.jqb.onefeed.api.plugin.OneFeedPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

/**
 * The root of OneFeed plugin tests, providing a shared, initialized plugin instance of the
 * target type to all tests
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public sealed abstract class OneFeedPluginTests<T extends OneFeedPlugin> permits
    ProviderPluginTests, CacherPluginTests
{
    protected T plugin;

    /**
     * Get an initialized instance of the plugin to test.
     */
    protected abstract T getInitializedPlugin();

    @BeforeAll
    void setSharedPlugin() {
        this.plugin = getInitializedPlugin();

        if (this.plugin == null) {
            throw new IllegalStateException("Plugin instance must be provided.");
        }
    }
}
