package com.justinquinnb.onefeed.data.sources.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.justinquinnb.onefeed.data.model.content.Content;
import com.justinquinnb.onefeed.data.model.content.details.Platform;
import com.justinquinnb.onefeed.data.model.content.details.Producer;
import com.justinquinnb.onefeed.data.model.source.APIEndpoint;
import com.justinquinnb.onefeed.logging.Logger;

import java.time.Instant;

public class SampleService extends APIEndpoint {
    private static final String baseUrl = "sampleurl";
    private static final String sourceName = "Sample";

    @Override
    protected String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public Content[] getLatestContent(int count) {
        Content[] content = new Content[1];
        content[0] = new SampleContent(
            Instant.now(),
            new Producer(
                    "https://www.instagram.com/justinquinnb/",
                    "https://scontent-atl3-3.cdninstagram.com/v/t51.2885-19/472386579_1307793557027997_7501062276520195543_n.jpg?stp=dst-jpg_s150x150_tt6&_nc_ht=scontent-atl3-3.cdninstagram.com&_nc_cat=109&_nc_ohc=h0658ooTVv8Q7kNvgEDggEi&_nc_gid=b4149aec98ba4d92bf7c561b7edfbd6e&edm=AP4sbd4BAAAA&ccb=7-5&oh=00_AYBI9vyezWAYuB5RkyipbXA-AjwP6MOQofwhnnE9gaacHA&oe=67975912&_nc_sid=7a9f4b",
                    "Justin", "Quinn", "justinquinnb"
            ),
            new Platform(
                "https://www.instagram.com",
                "Instagram",
                "@"
            ),
            "https://www.instagram.com/p/CupnjC2vJAH/",
            "Sure the weather wasn't great for humans, but it sure worked well for photos!",
            new String[]{"https://scontent-atl3-3.cdninstagram.com/v/t51.29350-15/360048719_588622033179681_461359604397151286_n.webp?stp=dst-jpg_e35_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDgxMC5zZHIuZjI5MzUwLmRlZmF1bHRfaW1hZ2UifQ&_nc_ht=scontent-atl3-3.cdninstagram.com&_nc_cat=109&_nc_ohc=BdYMaYgmS0QQ7kNvgFWwR0n&_nc_gid=44a5615a2dec429d9e65ae612a28c008&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MzE0NjIxOTc0MjMxOTA4MTI4Ng%3D%3D.3-ccb7-5&oh=00_AYA7ZlVa_LjBMhPQSzPo5HCAA4P4uLZt3Wq3bj9DYfYAnw&oe=67972D23&_nc_sid=10d13b"}
        );

        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        try {
            String jsonString = mapper.writeValueAsString(mapper);
            Logger.logToFile(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return content;
    }

    @Override
    public String getSourceName() {
        return sourceName;
    }
}