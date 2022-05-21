package me.dannly.data_structures.hash_table.separate_chaining;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableSeparateChainingTest {

    private HashTableSeparateChaining<String, Integer> hashTableSeparateChaining;

    @BeforeEach
    void setUp() {
        hashTableSeparateChaining = new HashTableSeparateChaining<>(5);
        hashTableSeparateChaining.add("Robert", 1);
        hashTableSeparateChaining.add("Frost", 2);
        hashTableSeparateChaining.add("Richard", 15);
    }

    @Test
    void add() {
        hashTableSeparateChaining.add("Christian", 91);
        assertTrue(hashTableSeparateChaining.containsKey("Christian"));
    }

    @Test
    void remove() {
        hashTableSeparateChaining.remove("Robert");
        assertFalse(hashTableSeparateChaining.containsKey("Robert"));
    }

    @Test
    void hasKey() {
        assertTrue(hashTableSeparateChaining.containsKey("Robert"));
        assertFalse(hashTableSeparateChaining.containsKey("Alexander"));
    }
}