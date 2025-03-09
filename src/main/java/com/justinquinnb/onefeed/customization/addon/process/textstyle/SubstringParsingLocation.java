package com.justinquinnb.onefeed.customization.addon.process.textstyle;

/**
 * Specifies the location of a substring to parse out.
 */
public class SubstringParsingLocation {
    /**
     * The index of the substring's first character, relative to the parent {@link String}.
     */
    private final int startIndex;

    /**
     * The index of the substring's final character + 1, relative to the parent {@link String}, in accordance with
     * {@link String#substring(int, int)}.
     */
    private final int endIndex;

    /**
     * Instantiates a {@link SubstringParsingLocation}.
     *
     * @param startIndex the index of the substring's first character, relative to the parent {@link String}
     * @param endIndex the index of the substring's final character + 1, relative to the parent {@link String}, in
     *                 accordance with {@link String#substring(int, int)}
     */
    public SubstringParsingLocation(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    /**
     * Gets the {@link #startIndex} of {@code this} {@link SubstringParsingLocation}.
     *
     * @return the {@code startIndex} of the substring to parse
     */
    public int getStartIndex() {
        return this.startIndex;
    }

    /**
     * Gets the {@link #endIndex} of {@code this} {@link SubstringParsingLocation}.
     *
     * @return the {@code endIndex} of the substring to parse
     */
    public int getEndIndex() {
        return this.endIndex;
    }
}
