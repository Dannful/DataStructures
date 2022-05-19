package me.dannly.data_structures.binary_heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryHeapTest {

    private BinaryHeap<Integer> binaryHeap;

    @BeforeEach
    void setUp() {
        binaryHeap = new BinaryHeap<>(true);
        Arrays.asList(0, 3, 2, 7, 5, 4, 1).forEach(binaryHeap::add);
    }

    @Test
    void poll() {
        assertEquals("0, 3, 1, 7, 5, 4, 2", getTraversedString());
        binaryHeap.poll();
        assertEquals("1, 3, 2, 7, 5, 4", getTraversedString());
    }

    @Test
    void pollLast() {
        binaryHeap.pollLast();
        assertEquals("0, 3, 1, 7, 5, 4", getTraversedString());
    }

    @Test
    void peek() {
        assertEquals(0, binaryHeap.peek());
    }

    @Test
    void peekLast() {
        assertEquals(2, binaryHeap.peekLast());
    }

    @Test
    void remove() {
        binaryHeap.remove(3);
        assertEquals("0, 2, 1, 7, 5, 4", getTraversedString());
        binaryHeap.remove(4);
        assertEquals("0, 2, 1, 7, 5", getTraversedString());
    }

    @Test
    void traverse() {
        assertEquals("0, 3, 1, 7, 5, 4, 2", getTraversedString());
    }

    private String getTraversedString() {
        return binaryHeap.traverse().stream().map(String::valueOf).collect(Collectors.joining(", "));
    }
}