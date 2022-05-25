package me.dannly.data_structures.suffix_array.longest_common_suffix;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestCommonSuffixArrayTest {

    private LongestCommonSuffixArray longestCommonSuffixArray;

    @BeforeEach
    void setUp() {
        longestCommonSuffixArray = new LongestCommonSuffixArray(2, "AABC", "BCDC", "BCDE", "CDED");
    }

    @Test
    void longestCommonSuffixes() {
        final String[] longestCommonSuffixes = longestCommonSuffixArray.longestCommonSuffixes();
        assertEquals(2, longestCommonSuffixes.length);
        assertEquals("BCD, CDE", String.join(", ", longestCommonSuffixes));
    }
}