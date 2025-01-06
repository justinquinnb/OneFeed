package com.justinquinnb.onefeed.api;

import org.springframework.stereotype.Service;
import com.justinquinnb.onefeed.data.model.content.Content;

import java.util.Optional;

@Service
public class ContentService {


    public Optional<Content[]> getContent(Integer count) {
        Optional optional = Optional.empty();

        return optional;
    }
}
