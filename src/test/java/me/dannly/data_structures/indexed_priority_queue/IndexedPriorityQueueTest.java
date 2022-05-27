package me.dannly.data_structures.indexed_priority_queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndexedPriorityQueueTest {

    private IndexedPriorityQueue<Integer> indexedPriorityQueue;

    @BeforeEach
    void setUp() {
        indexedPriorityQueue = new IndexedPriorityQueue<>(false, 10);
        indexedPriorityQueue.add(0, 10);
        indexedPriorityQueue.add(1, 5);
        indexedPriorityQueue.add(2, 19);
        indexedPriorityQueue.add(3, 11);
        indexedPriorityQueue.add(4, 20);
    }

    @Test
    void size() {
        assertEquals(indexedPriorityQueue.size(), 5);
    }

    @Test
    void contains() {
        assertTrue(indexedPriorityQueue.contains(0));
        assertTrue(indexedPriorityQueue.contains(1));
        assertTrue(indexedPriorityQueue.contains(2));
        assertTrue(indexedPriorityQueue.contains(3));
        assertTrue(indexedPriorityQueue.contains(4));
        assertFalse(indexedPriorityQueue.contains(5));
    }

    @Test
    void poll() {
        assertEquals(20, indexedPriorityQueue.poll());
        assertEquals(4, indexedPriorityQueue.size());
        assertEquals(19, indexedPriorityQueue.peek());
        assertEquals(4, indexedPriorityQueue.size());
    }

    @Test
    void pollLast() {
        assertEquals(11, indexedPriorityQueue.pollLast());
        assertEquals(4, indexedPriorityQueue.size());
        assertEquals(5, indexedPriorityQueue.peekLast());
        assertEquals(4, indexedPriorityQueue.size());
    }

    @Test
    void peek() {
        assertEquals(20, indexedPriorityQueue.peek());
    }

    @Test
    void peekLast() {
        assertEquals(11, indexedPriorityQueue.peekLast());
    }

    @Test
    void remove() {
        indexedPriorityQueue.remove(4);
        assertFalse(indexedPriorityQueue.contains(4));
        assertEquals(19, indexedPriorityQueue.peek());
        indexedPriorityQueue.remove(3);
        assertFalse(indexedPriorityQueue.contains(3));
    }
}