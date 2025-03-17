package com.justinquinnb.onefeed.customization.textstyle;

// TODO implement

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
}