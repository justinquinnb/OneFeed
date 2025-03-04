package com.justinquinnb.onefeed.data.model.content.reception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A basic implementation of a numeric value with a label to identify/describe it.
 *
 * @param <T> the type of number to store
 */
public class BasicStat<T extends Number> implements Statistic {
    /**
     * A label succinctly identifying the {@link #value}.
     */
    private final String label;

    /**
     * Some metric/statistic/value (type extending {@link Number}) quantifying the {@link #label}.
     */
    private final T value;

    /**
     * Constructs a {@link BasicStat} with a numeric {@code value} and some {@code label} to describe it.
     *
     * @param label a label succinctly describing the {@code value}
     * @param value some number quantifying the {@code label}
     */
    @JsonCreator
    public BasicStat(
            @JsonProperty("label") String label,
            @JsonProperty("value") T value) {
        this.label = label;
        this.value = value;
    }

    /**
     * Gets the label succinctly identifying {@code this} {@link BasicStat}'s {@link #value}.
     *
     * @return {@code this} {@link BasicStat}'s {@link #label}
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gets the numeric value quantifying {@code this} {@link BasicStat}'s {@link #label}.
     *
     * @return {@code this} {@link BasicStat}'s {@link #value}
     */
    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BasicStat@" + hashCode() + "{label=\"" + label + "\", value=" + value + "}";
    }
}