package me.dannly.data_structures.indexed_priority_queue;

import java.util.Arrays;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class IndexedPriorityQueue<V extends Comparable<V>> {

    private final Object[] values;
    private final int[] positions;
    private final int[] inverse;
    private int size = 0;
    private final boolean ascending;

    public IndexedPriorityQueue(boolean ascending, int size) {
        this.ascending = ascending;
        values = new Object[size];
        positions = new int[size];
        inverse = new int[size];
        Arrays.fill(positions, -1);
        Arrays.fill(inverse, -1);
    }

    public int size() {
        return size;
    }

    public boolean contains(int keyIndex) {
        return values[keyIndex] != null;
    }

    public void add(int keyIndex, V value) {
        values[keyIndex] = value;
        positions[keyIndex] = size;
        inverse[size] = keyIndex;
        swim(size++);
    }

    public V poll() {
        final int keyIndex = inverse[0];
        if (keyIndex == -1)
            return null;
        final V aux = (V) values[keyIndex];
        remove(keyIndex);
        return aux;
    }

    public V pollLast() {
        final int keyIndex = inverse[size - 1];
        if (keyIndex == -1)
            return null;
        final V aux = (V) values[keyIndex];
        remove(keyIndex);
        return aux;
    }

    public V peek() {
        final int keyIndex = inverse[0];
        if (keyIndex == -1)
            return null;
        return (V) values[keyIndex];
    }

    public V peekLast() {
        final int keyIndex = inverse[size - 1];
        if (keyIndex == -1)
            return null;
        return (V) values[keyIndex];
    }

    private void swim(int index) {
        for (int parent = findParent(index); index > 0 && satisfied(parent, index); parent = findParent(parent)) {
            swap(index, parent);
            index = parent;
        }
    }

    private void sink(int index) {
        for (Integer child = findWhichChildToSwapWith(index); child != null && child < values.length; child = findWhichChildToSwapWith(index)) {
            swap(child, index);
            index = child;
        }
    }

    private Integer findWhichChildToSwapWith(int parent) {
        final Stream<Integer> childrenStream = Stream.of(findLeftChild(parent), findRightChild(parent)).filter(child -> child < values.length && satisfied(parent, child));
        return ascending ? childrenStream.min(Integer::compare).orElse(null) : childrenStream.max(Integer::compare).orElse(null);
    }

    private boolean satisfied(final int parent, final int child) {
        final int parentKeyIndex = inverse[parent], childKeyIndex = inverse[child];
        if (parentKeyIndex == -1)
            return true;
        if (childKeyIndex == -1)
            return false;
        final V parentValue = (V) values[parentKeyIndex], childValue = (V) values[childKeyIndex];
        return ascending ? parentValue.compareTo(childValue) > 0 : parentValue.compareTo(childValue) < 0;
    }

    public void remove(int keyIndex) {
        final int i = positions[keyIndex];
        if (i == -1)
            return;
        final boolean isLast = i == --size;
        if (!isLast)
            swap(i, size);
        values[keyIndex] = null;
        positions[keyIndex] = -1;
        inverse[size] = -1;
        if (isLast)
            return;
        sink(i);
        swim(i);
    }

    private int findParent(int index) {
        return (index - 1) / 2;
    }

    private int findLeftChild(int index) {
        return index * 2 + 1;
    }

    private int findRightChild(int index) {
        return index * 2 + 2;
    }

    private void swap(int firstIndex, int secondIndex) {
        positions[inverse[firstIndex]] = secondIndex;
        positions[inverse[secondIndex]] = firstIndex;
        final int aux = inverse[firstIndex];
        inverse[firstIndex] = inverse[secondIndex];
        inverse[secondIndex] = aux;
    }
}