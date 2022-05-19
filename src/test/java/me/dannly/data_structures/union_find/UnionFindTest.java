package me.dannly.data_structures.union_find;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnionFindTest {

    private UnionFind unionFind;

    @BeforeEach
    void setUp() {
        unionFind = new UnionFind(10);
    }

    @Test
    void unionFind() {
        assertEquals(10, unionFind.find(10));
        unionFind.union(9, 10);
        assertEquals(10, unionFind.find(9));
        assertEquals(10, unionFind.find(10));
        unionFind.union(0, 1);
        assertEquals(1, unionFind.find(0));
        assertEquals(1, unionFind.find(1));
        unionFind.union(1, 5);
        assertEquals(1, unionFind.find(5));
        unionFind.union(1, 10);
        assertEquals(1, unionFind.find(10));
    }
}