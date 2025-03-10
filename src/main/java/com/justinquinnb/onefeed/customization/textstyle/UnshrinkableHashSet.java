package com.justinquinnb.onefeed.customization.textstyle;

import java.util.HashSet;

/**
 * A {@link HashSet} type preventing the removal of elements.
 *
 * @param <E> the type of elements {@code this} {@code UnshrinkableHashSet} contains
 */
public class UnshrinkableHashSet<E> extends HashSet<E> {
    /**
     * Unsupported operation for {@link UnshrinkableHashSet}s. Elements can only be added.
     * @return {@code true} if the set contained the specified element
     */
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Cannot remove elements from an UnshrinkableHashSet");
    }

    /**
     * Unsupported operation for {@link UnshrinkableHashSet}s. Elements can only be added.
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Cannot remove elements from an UnshrinkableHashSet");
    }
}
