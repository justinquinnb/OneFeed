package com.justinquinnb.onefeed.data.model.content.reception;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Marker interface for labelled, numeric information.
 */
@JsonDeserialize(as = BasicStat.class)
public interface Statistic {
}