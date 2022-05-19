package me.dannly.data_structures.binary_search_tree;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class BinarySearchTreeTest {

    private BinarySearchTree<Integer> binarySearchTree;

    @BeforeEach
    void setUp() {
        binarySearchTree = new BinarySearchTree<>();
        Arrays.asList(10, 12, 8, 6, 9, 11, 13).forEach(binarySearchTree::add);
    }

    @Test
    void remove() {
        binarySearchTree.remove(12);
        assertEquals("6, 8, 9, 10, 11, 13", getTraversedString());
        binarySearchTree.remove(9);
        assertEquals("6, 8, 10, 11, 13", getTraversedString());
    }

    @Test
    void contains() {
        assertTrue(binarySearchTree.contains(9));
        assertFalse(binarySearchTree.contains(15));
        assertTrue(binarySearchTree.contains(10));
    }

    @Test
    void traverse() {
        assertEquals("6, 8, 9, 10, 11, 12, 13", getTraversedString());
    }

    private String getTraversedString() {
        final List<Integer> list = new ArrayList<>();
        binarySearchTree.traverse(TreeTraverseOrder.INORDER, list::add);
        return list.stream().map(String::valueOf).collect(Collectors.joining(", "));
    }
}