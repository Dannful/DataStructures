package me.dannly.data_structures.suffix_array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuffixArrayTest {

    private SuffixArray suffixArray;

    @BeforeEach
    void setUp() {
        suffixArray = new SuffixArray("AZAZA");
    }

    @Test
    void uniqueSuffixes() {
        assertEquals(9, suffixArray.uniqueSuffixes());
    }
}