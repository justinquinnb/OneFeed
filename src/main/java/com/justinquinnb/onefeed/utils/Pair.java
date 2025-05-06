package com.justinquinnb.onefeed.utils;

/**
 * An ordered pair of two related items.
 * @param <T> the type of the pair's contents
 */
public interface Pair<T>  {
    /**
     * Gets the first item in the pair.
     *
     * @return the first item in the pair
     */
    public T getFirst();

    /**
     * Gets the second item in the pair.
     *
     * @return the second item in the pair
     */
    public T getSecond();

    /**
     * Sets the first item in the pair to the provided {@code value}.
     *
     * @param value the new value to set the pair's first item to
     */
    public void setFirst(T value);

    /**
     * Sets the second item in the pair to the provided {@code value}.
     *
     * @param value the new value to set the pair's second item to
     */
    public void setSecond(T value);
}