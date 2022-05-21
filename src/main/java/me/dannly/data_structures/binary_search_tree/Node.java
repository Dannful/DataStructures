package me.dannly.data_structures.binary_search_tree;

public class Node<T extends Comparable<T>> {

    private T value;
    private Node<T> left;
    private Node<T> right;
    public Node(T value, Node<T> left, Node<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public void swap(Node<T> other) {
        if (other == null) {
            clear();
            return;
        }
        value = other.getValue();
        right = other.getRight();
        left = other.getLeft();
    }

    public void clear() {
        value = null;
        right = null;
        left = null;
    }
}
