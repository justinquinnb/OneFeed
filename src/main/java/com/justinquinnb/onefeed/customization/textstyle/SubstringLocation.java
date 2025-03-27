package com.justinquinnb.onefeed.customization.textstyle;

import com.justinquinnb.onefeed.JsonToString;

/**
 * The location of a substring within a {@code String}, inclusive.
 */
public class SubstringLocation {
    /**
     * The index of the substring's first character.
     *
     * @see String#charAt(int)
     */
    private int start;

    /**
     * The index of the character after the substring's last character, as {@link String#substring(int, int)} functions.
     *
     * @see String#charAt(int)
     * @see String#substring(int, int)
     */
    private int end; // Perhaps make this length instead?

    /**
     * Instantiates a {@link SubstringLocation} with starting position {@code start} and ending position {@code end}.
     *
     * @param start the start index of some substring, inclusive
     * @param end the end index of some substring, inclusive
     */
    public SubstringLocation(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Gets the {@link #start} index of {@code this} {@link SubstringLocation}.
     *
     * @return the index that the substring {@code this} {@code SubstringLocation} represents starts at
     */
    public int getStart() {
        return this.start;
    }

    /**
     * Gets the {@link #end} index of {@code this} {@link SubstringLocation}.
     *
     * @return the index that the substring {@code this} {@code SubstringLocation} represents ends at
     */
    public int getEnd() {
        return this.end;
    }

    /**
     * Gets the length of the substring represented by {@code this} {@link SubstringLocation}.
     *
     * @return the length of the substring represented {@code this} {@code SubstringLocation}, as determined by its
     * {@link #start} and {@link #end} locations.
     */
    public int getSubstringLength() {
        return 1 + (this.end - this.start);
    }

    /**
     * Sets the {@link #start} position of {@code this} {@link SubstringLocation}.
     *
     * @param newStart the new start position for {@code this} {@code SubstringLocation} to include
     */
    public void setStart(int newStart) {
        this.start = newStart;
    }

    /**
     * Sets the {@link #end} position of {@code this} {@link SubstringLocation}.
     *
     * @param newEnd the new end position for {@code this} {@code SubstringLocation} to include
     */
    public void setEnd(int newEnd) {
        this.end = newEnd;
    }

    /**
     * Shifts {@code this} {@link SubstringLocation}'s {@link #start} index by the provided {@code amount}.
     *
     * @param amount the amount by which to shift {@code this} {@code SubstringLocation}'s {@code start} index
     */
    public void shiftStart(int amount) {
        this.start += amount;
    }

    /**
     * Shifts {@code this} {@link SubstringLocation}'s {@link #end} index by the provided {@code amount}.
     *
     * @param amount the amount by which to shift {@code this} {@code SubstringLocation}'s {@code end} index
     */
    public void shiftEnd(int amount) {
        this.end += amount;
    }

    /**
     * Shifts {@code this} {@link SubstringLocation}'s {@link #start} and {@link #end} indices by the provided
     * {@code amount}.
     *
     * @param amount the amount by which to shift both of {@code this} {@code SubstringLocation}'s indices
     */
    public void shift(int amount) {
        this.start += amount;
        this.end += amount;
    }

    @Override
    public String toString() {
        return JsonToString.of(this);
    }
}