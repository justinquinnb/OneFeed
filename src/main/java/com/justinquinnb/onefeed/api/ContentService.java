package com.justinquinnb.onefeed.api;

import org.springframework.stereotype.Service;
import com.justinquinnb.onefeed.data.model.content.Content;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ContentService {
    public static Optional<Content[]> getContent(int count) {
        Optional optional = Optional.empty();
        return optional;
    }

    public static Optional<Content[]> getContent(int count, String[] fromSources) {
        Optional optional = Optional.empty();
        return optional;
    }

    public static Optional<Content[]> getContent(int count, LocalDateTime[] betweenTimes) {
        Optional optional = Optional.empty();
        return optional;
    }

    public static Optional<Content[]> getContent(int count, String[] fromSources, LocalDateTime[] betweenTimes) {
        Optional optional = Optional.empty();
        return optional;
    }

    private static Optional<Content[]> getContent(int count, String fromSource) {
        Optional optional = Optional.empty();
        return optional;
    }
}
