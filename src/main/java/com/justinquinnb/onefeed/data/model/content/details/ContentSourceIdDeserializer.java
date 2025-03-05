package com.justinquinnb.onefeed.data.model.content.details;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * Handles the deserialization of {@link ContentSourceId}s.
 */
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