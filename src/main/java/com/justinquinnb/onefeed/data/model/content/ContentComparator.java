package com.justinquinnb.onefeed.data.model.content;

import java.time.Instant;
import java.util.Comparator;

public class ContentComparator implements Comparator<Content> {
    @Override
    public int compare(Content o1, Content o2) {
        Instant o1Timestamp = o1.getTimestamp();
        Instant o2Timestamp = o2.getTimestamp();

        return o1Timestamp.compareTo(o2Timestamp);
    }
}
