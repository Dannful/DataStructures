package me.dannly.data_structures.fenwick_tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FenwickTreeTest {

    private FenwickTree fenwickTree;

    @BeforeEach
    void setUp() {
        fenwickTree = new FenwickTree(1, 2, 3, 4, 5);
    }

    @Test
    void sum() {
        assertEquals(15, fenwickTree.sum(-1, 4));
    }

    @Test
    void update() {
        fenwickTree.update(0, 5);
        assertEquals(10, fenwickTree.sum(-1, 2));
    }
}