package dev.jqb.onefeed.core.platform;

import com.fasterxml.jackson.annotation.JsonSerializeAs;
import dev.jqb.onefeed.core.compat.rss.RssGuid;

/**
 * A reference to a resource on an external {@link Platform}
 *
 * @param url a direct URL to the resource
 * @param id the unique ID of the resource on its platform
 */@JsonSerializeAs(ExternalRef.class)
public record ExternalRef(String url, String id) implements RssGuid {
    @Override
    public String getRssGuidValue() {
        return url;
    }
}
