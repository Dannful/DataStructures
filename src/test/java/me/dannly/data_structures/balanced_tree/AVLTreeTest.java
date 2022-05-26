package me.dannly.data_structures.balanced_tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AVLTreeTest {

    private AVLTree<Integer> avlTree;

    @BeforeEach
    void setUp() {
        avlTree = new AVLTree<>();
        for (int i = 1; i <= 16; i++)
            avlTree.add(i);
    }

    @Test
    void contains() {
        assertTrue(avlTree.contains(1));
        assertTrue(avlTree.contains(2));
        assertTrue(avlTree.contains(3));
        assertTrue(avlTree.contains(4));
    }

    @Test
    void remove() {
        avlTree.remove(1);
        assertFalse(avlTree.contains(1));
        avlTree.remove(2);
        assertFalse(avlTree.contains(2));
        avlTree.remove(3);
        assertFalse(avlTree.contains(3));
        avlTree.remove(4);
        assertFalse(avlTree.contains(4));
    }
}