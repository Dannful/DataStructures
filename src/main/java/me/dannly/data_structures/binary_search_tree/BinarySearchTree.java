package me.dannly.data_structures.binary_search_tree;

import me.dannly.data_structures.binary_search_tree.TreeTraverseOrder;

import java.util.function.Consumer;

public class BinarySearchTree<T extends Comparable<T>> {

    private static class Node<T extends Comparable<T>> {

        public T value;
        public Node<T> left;
        public Node<T> right;

        public Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public void swap(Node<T> other) {
            if (other == null) {
                clear();
                return;
            }
            value = other.value;
            right = other.right;
            left = other.left;
        }

        public void clear() {
            value = null;
            right = null;
            left = null;
        }
    }

    private
    Node<T> root = null;
    private
    int count = 0;

    public boolean contains(T value) {
        return find(root, value) != null;
    }

    public void traverse(TreeTraverseOrder treeTraverseOrder, Consumer<T> action) {
        traverse(root, treeTraverseOrder, action);
    }

    private void traverse(Node<T> node, final TreeTraverseOrder treeTraverseOrder, final Consumer<T> action) {
        switch (treeTraverseOrder) {
            case PREORDER: {
                action.accept(node.value);
                traverse(node.left, treeTraverseOrder, action);
                traverse(node.right, treeTraverseOrder, action);
                break;
            }
            case INORDER: {
                traverse(node.left, treeTraverseOrder, action);
                action.accept(node.value);
                traverse(node.right, treeTraverseOrder, action);
                break;
            }
            case POSTORDER: {
                traverse(node.left, treeTraverseOrder, action);
                traverse(node.right, treeTraverseOrder, action);
                action.accept(node.value);
                break;
            }
        }
    }

    public void insert(T element) {
        if (count == 0) {
            root = new Node<>(element, null, null);
            count++;
            return;
        }
        if (contains(element)) return;
        root = insert(root, element);
        count++;
    }

    private Node<T> insert(Node<T> node, final T element) {
        if (node == null) {
            node = new Node<>(element, null, null);
        } else {
            if (element.compareTo(node.value) < 0) {
                node.left = insert(node.left, element);
            } else {
                node.right = insert(node.right, element);
            }
        }
        return node;
    }

    public void remove(T element) {
        if (element == null) return;
        if (!contains(element)) return;
        root = remove(root, element);
        count--;
    }

    private Node<T> remove(Node<T> node, final T value) {
        if (node == null) return null;
        int compare = value.compareTo(node.value);
        if (compare < 0) {
            node.left = remove(node.left, value);
        } else if (compare > 0) {
            node.right = remove(node.right, value);
        } else {
            if (node.left == null) {
                final Node<T> right = node.right;
                node.clear();
                return right;
            } else if (node.right == null) {
                final Node<T> left = node.left;
                node.clear();
                return left;
            } else {
                final Node<T> leftmost = digLeft(node.right);
                node.value = leftmost.value;
                node.right = remove(node.right, node.value);
            }
        }
        return node;
    }

    private Node<T> digLeft(Node<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private Node<T> find(Node<T> node, final T element) {
        if (node == null) return null;
        if (node.value.compareTo(element) == 0) return node;
        if (node.left == null && node.right == null) return null;
        return find(element.compareTo(node.value) < 0 ? node.left : node.right, element);
    }
}
