package me.dannly.data_structures.hash_table.open_addressing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashTableOpenAddressingTest {

    private HashTableOpenAddressing<String, Integer> hashTableOpenAddressing;

    @BeforeEach
    void setUp() {
        hashTableOpenAddressing = new HashTableOpenAddressing<>(ProbingMethod.DOUBLE_HASHING);
        hashTableOpenAddressing.add("A", 1);
        hashTableOpenAddressing.add("B", 2);
        hashTableOpenAddressing.add("C", 3);
        hashTableOpenAddressing.add("D", 4);
        hashTableOpenAddressing.add("E", 5);
        hashTableOpenAddressing.add("F", 6);
        hashTableOpenAddressing.add("G", 7);
        hashTableOpenAddressing.add("H", 8);
    }

    @Test
    void containsKey() {
        assertTrue(hashTableOpenAddressing.containsKey("A"));
        assertTrue(hashTableOpenAddressing.containsKey("B"));
        assertTrue(hashTableOpenAddressing.containsKey("C"));
        assertTrue(hashTableOpenAddressing.containsKey("D"));
        assertTrue(hashTableOpenAddressing.containsKey("E"));
        assertTrue(hashTableOpenAddressing.containsKey("F"));
        assertTrue(hashTableOpenAddressing.containsKey("G"));
        assertTrue(hashTableOpenAddressing.containsKey("H"));
    }

    @Test
    void remove() {
        hashTableOpenAddressing.remove("A");
        hashTableOpenAddressing.remove("B");
        hashTableOpenAddressing.remove("C");
        hashTableOpenAddressing.remove("D");
        hashTableOpenAddressing.remove("E");
        assertFalse(hashTableOpenAddressing.containsKey("A"));
        assertFalse(hashTableOpenAddressing.containsKey("B"));
        assertFalse(hashTableOpenAddressing.containsKey("C"));
        assertFalse(hashTableOpenAddressing.containsKey("D"));
        assertFalse(hashTableOpenAddressing.containsKey("E"));
        assertTrue(hashTableOpenAddressing.containsKey("F"));
        assertTrue(hashTableOpenAddressing.containsKey("G"));
        assertTrue(hashTableOpenAddressing.containsKey("H"));
    }
}