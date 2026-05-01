package dev.jqb.onefeed.api.pipeline;

import dev.jqb.onefeed.api.content.ContentPackage;
import dev.jqb.onefeed.api.content.RawContent;

/**
 * Denotes a {@link Provider} capable of notifying OneFeed when a feed has been updated via webhooks
 *
 * @param <Out> the type of {@link RawContent} produced
 */
public interface AutoProvider<Out extends RawContent> extends Provider<Out> {

    /**
     * Gets the slug of the {@code /api/webhooks} path to listen for updates on
     * @return the path after {@code /api/webhooks/} to listen for updates on
     */
    String getWebhookSlug();

    /**
     * Gets the updated content that the webhook notification is making us aware of
     *
     * @param notifPayload the payload of the request directed to the path specified by
     * {@link #getWebhookSlug()}
     *
     * @return the updated content that the webhook notification was referring to
     */
    ContentPackage<Out> getUpdatedContent(String notifPayload);
}
