package dev.jqb.onefeed.app.plugins;

import org.pf4j.PluginState;
import org.pf4j.PluginStateEvent;
import org.pf4j.PluginStateListener;
import org.pf4j.PluginWrapper;

/**
 * Listener for OneFeed plugin state changes
 */
public class OneFeedPluginStateListener implements PluginStateListener {
    private final PluginTypeRegistry registry;

    public OneFeedPluginStateListener(PluginTypeRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void pluginStateChanged(PluginStateEvent event) {
        // Only register types when the plugin is actually ready
        PluginState state = event.getPluginState();
        PluginWrapper wrapper = event.getPlugin();
        if (state == PluginState.STARTED) {
            registry.registerTypesFrom(wrapper);
        } else if (state == PluginState.STOPPED || state == PluginState.DISABLED ||
            state == PluginState.UNLOADED
        ) {
            registry.unregisterTypesFrom(wrapper);
        }
    }
}
