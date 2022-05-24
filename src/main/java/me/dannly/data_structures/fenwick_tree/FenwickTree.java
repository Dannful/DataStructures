package me.dannly.data_structures.fenwick_tree;

import java.util.Arrays;

public class FenwickTree {

    private final int[] elements;

    public FenwickTree(int... values) {
        elements = Arrays.copyOf(values, values.length);
        for (int i = 0; i < values.length; i++) {
            final int j = i + lowestOneBit(i);
            if (j < values.length) {
                elements[j] += elements[i];
            }
        }
    }

    public int sum(int fromIndex, int toIndex) {
        return retrieveSum(toIndex) - retrieveSum(fromIndex - 1);
    }

    public int sum(int index) {
        return retrieveSum(index);
    }

    public int sum() {
        return retrieveSum(elements.length - 1);
    }

    private int retrieveSum(int index) {
        int sum = 0;
        for (; index >= 0; index = ((index + 1) & ~lowestOneBit(index)) - 1)
            sum += elements[index];
        return sum;
    }

    public void add(int index, int value) {
        for (; index < elements.length; index += lowestOneBit(index)) {
            elements[index] += value;
        }
    }

    public void set(int index, int value) {
        final int sum = value - elements[index];
        add(index, sum);
    }

    private int lowestOneBit(int index) {
        final int i = index + 1;
        return i & -i;
    }
}
