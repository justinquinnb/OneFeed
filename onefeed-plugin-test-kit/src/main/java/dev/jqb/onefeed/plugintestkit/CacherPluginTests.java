package dev.jqb.onefeed.plugintestkit;

import dev.jqb.onefeed.api.caching.OneFeedCacherPlugin;

/**
 * Tests the basic functionalities of a {@link OneFeedCacherPlugin}, particularly its provided
 * {@link dev.jqb.onefeed.api.caching.Cacher} implementation
 *
 * @param <T> your specific {@link OneFeedCacherPlugin} implementation
 */
public non-sealed abstract class CacherPluginTests<T extends OneFeedCacherPlugin>
    extends OneFeedPluginTests<T>
{

}
