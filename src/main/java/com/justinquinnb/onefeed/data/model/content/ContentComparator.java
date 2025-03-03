package com.justinquinnb.onefeed.data.model.content;

import java.time.Instant;
import java.util.Comparator;

/**
 * Specifies the comparison of {@link BasicContent} via their {@code timestamp} fields.
 */
public class ContentComparator implements Comparator<BasicContent> {
    @Override
    public int compare(BasicContent o1, BasicContent o2) {
        Instant o1Timestamp = o1.getTimestamp();
        Instant o2Timestamp = o2.getTimestamp();

        return o1Timestamp.compareTo(o2Timestamp);
    }
}
