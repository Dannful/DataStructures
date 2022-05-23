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
        return retrieveSum(toIndex) - retrieveSum(fromIndex);
    }

    private int retrieveSum(int index) {
        int sum = 0;
        for (; index >= 0; index -= lowestOneBit(index))
            sum += elements[index];
        return sum;
    }

    public void update(int index, int value) {
        final int sum = value - elements[index];
        for (; index < elements.length; index += lowestOneBit(index)) {
            elements[index] += sum;
        }
    }

    private int lowestOneBit(int index) {
        return Integer.lowestOneBit(index + 1);
    }
}
