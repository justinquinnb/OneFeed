package dev.jqb.onefeed.api.plugin;

import dev.jqb.onefeed.api.pipeline.Provider;
import lombok.Getter;
import lombok.Setter;
import org.pf4j.Plugin;

/**
 * A plugin for OneFeed, representing the package containing a {@link Provider} implementation
 */
@Getter
@Setter
public abstract class OneFeedPlugin extends Plugin {
    // Nothing yet...
}
