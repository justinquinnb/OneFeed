package com.justinquinnb.onefeed.data.model.content.details;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Handles the deserialization of {@link ContentSourceId}s.
 */
@Component
public class ContentSourceIdDeserializer extends StdDeserializer<ContentSourceId> {
    public ContentSourceIdDeserializer() {
        this(null);
    }

    public ContentSourceIdDeserializer(Class<?> unknownClass) {
        super(unknownClass);
    }

    @Override
    public ContentSourceId deserialize(JsonParser parser, DeserializationContext context
    ) throws IOException {
        String id = parser.getText();
        return new ContentSourceId(id);
    }
}