package me.dannly.data_structures.binary_heap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class BinaryHeap<T extends Comparable<T>> {

    private final List<T> nodes = new ArrayList<>();
    private final boolean ascending;

    public BinaryHeap(boolean ascending) {
        this.ascending = ascending;
    }

    public void add(T value) {
        nodes.add(value);
        swim(nodes.size() - 1);
    }

    public T poll() {
        if (nodes.isEmpty())
            return null;
        final T aux = nodes.get(0);
        remove(0);
        return aux;
    }

    public T pollLast() {
        if (nodes.isEmpty())
            return null;
        final T aux = nodes.get(nodes.size() - 1);
        removeLast();
        return aux;
    }

    public T peek() {
        if (nodes.isEmpty())
            return null;
        return nodes.get(0);
    }

    public T peekLast() {
        if (nodes.isEmpty())
            return null;
        return nodes.get(nodes.size() - 1);
    }

    private void swim(int index) {
        while (index > 0 && index < nodes.size()) {
            final int parentIndex = findParent(index);
            final T value = nodes.get(index), parent = nodes.get(parentIndex);
            if (ascending ? value.compareTo(parent) < 0 : value.compareTo(parent) > 0) {
                swap(parentIndex, index);
                index = parentIndex;
                continue;
            }
            break;
        }
    }

    public void remove(T value) {
        final int index = search(value);
        if (index == -1)
            return;
        if (index == nodes.size() - 1) {
            removeLast();
            return;
        }
        remove(index);
    }

    private void remove(int index) {
        if (index != nodes.size() - 1)
            swap(index, nodes.size() - 1);
        removeLast();
        sink(index);
        swim(index);
    }

    private void sink(int index) {
        while (index >= 0 && index < nodes.size()) {
            final T node = nodes.get(index);
            final int leftChildIndex = findLeftChild(index), rightChildIndex = findRightChild(index);
            final Stream<Integer> stream = Stream.of(leftChildIndex, rightChildIndex).filter(i -> i < nodes.size());
            final Optional<Integer> optional = ascending ? stream.min((i, j) -> nodes.get(i).compareTo(nodes.get(j))) : stream.max((i, j) -> nodes.get(i).compareTo(nodes.get(j)));
            if (optional.isPresent()) {
                final int foundIndex = optional.get();
                final T foundChild = nodes.get(foundIndex);
                if (ascending ? node.compareTo(foundChild) < 0 : node.compareTo(foundChild) > 0)
                    break;
                swap(index, foundIndex);
                index = foundIndex;
            }
        }
    }

    private int search(T value) {
        int index = 0;
        if ((ascending && value.compareTo(nodes.get(index)) < 0) || (!ascending && value.compareTo(nodes.get(index)) > 0))
            return -1;
        while (index < nodes.size() && nodes.get(index).compareTo(value) != 0)
            index++;
        return index;
    }

    private void removeLast() {
        nodes.remove(nodes.size() - 1);
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
        final T aux = nodes.get(firstIndex);
        nodes.set(firstIndex, nodes.get(secondIndex));
        nodes.set(secondIndex, aux);
    }

    public void traverse(Consumer<T> action) {
        nodes.forEach(action);
    }

    public List<T> traverse() {
        return nodes;
    }
}