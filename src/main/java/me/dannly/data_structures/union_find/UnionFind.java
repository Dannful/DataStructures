package me.dannly.data_structures.union_find;

public class UnionFind {

    private final int[] parents, sizes;

    public UnionFind(int n) {
        parents = new int[n + 1];
        sizes = new int[n + 1];
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
            sizes[i] = 1;
        }
    }

    public int find(int i) {
        if (i < 0 || i >= sizes.length)
            return -1;
        if (parents[i] == i)
            return i;
        final int root = find(parents[i]);
        parents[i] = root;
        return root;
    }

    public void union(int i, int j) {
        final int iRoot = find(i), jRoot = find(j);
        if (iRoot == jRoot)
            return;
        int minIndex = iRoot, maxIndex = jRoot;
        if (sizes[minIndex] > sizes[maxIndex]) {
            minIndex = maxIndex;
            maxIndex = iRoot;
        }
        sizes[maxIndex] += sizes[minIndex];
        sizes[minIndex] = 0;
        parents[minIndex] = maxIndex;
    }
}
