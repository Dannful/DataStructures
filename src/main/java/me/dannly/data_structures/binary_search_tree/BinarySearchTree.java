package me.dannly.data_structures.binary_search_tree;

import java.util.function.Consumer;

public class BinarySearchTree<T extends Comparable<T>> {

    private Node<T> root = null;
    private int count = 0;

    public void add(T element) {
        if (count == 0) {
            root = new Node<>(element, null, null);
            count++;
            return;
        }
        if (contains(element))
            return;
        root = add(root, element);
        count++;
    }

    private Node<T> add(Node<T> node, T element) {
        if (node == null) {
            node = new Node<>(element, null, null);
        } else {
            if (element.compareTo(node.getValue()) < 0) {
                node.setLeft(add(node.getLeft(), element));
            } else {
                node.setRight(add(node.getRight(), element));
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

    private Node<T> remove(Node<T> node, T element) {
        if (node == null)
            return null;
        int compare = element.compareTo(node.getValue());
        if (compare < 0) {
            node.setLeft(remove(node.getLeft(), element));
        } else if (compare > 0) {
            node.setRight(remove(node.getRight(), element));
        } else {
            if (node.getLeft() == null) {
                final Node<T> right = node.getRight();
                node.clear();
                return right;
            } else if (node.getRight() == null) {
                final Node<T> left = node.getLeft();
                node.clear();
                return left;
            } else {
                final Node<T> leftmost = digLeft(node.getRight());
                node.setValue(leftmost.getValue());
                node.setRight(remove(node.getRight(), node.getValue()));
            }
        }
        return node;
    }

    private Node<T> digLeft(Node<T> node) {
        while (node.getLeft() != null)
            node = node.getLeft();
        return node;
    }

    public boolean contains(T element) {
        return findNode(root, element) != null;
    }

    private Node<T> findNode(Node<T> node, T element) {
        if (node == null) return null;
        if (node.getValue().compareTo(element) == 0)
            return node;
        if (node.getLeft() == null && node.getRight() == null)
            return null;
        return findNode(element.compareTo(node.getValue()) < 0 ? node.getLeft() : node.getRight(), element);
    }

    public void traverse(TreeTraverseOrder order, Consumer<T> action) {
        traverse(root, order, action);
    }

    private void traverse(Node<T> node, TreeTraverseOrder order, Consumer<T> action) {
        if (node == null) return;
        switch (order) {
            case PREORDER: {
                action.accept(node.getValue());
                traverse(node.getLeft(), order, action);
                traverse(node.getRight(), order, action);
                break;
            }
            case INORDER: {
                traverse(node.getLeft(), order, action);
                action.accept(node.getValue());
                traverse(node.getRight(), order, action);
                break;
            }
            case POSTORDER: {
                traverse(node.getLeft(), order, action);
                traverse(node.getRight(), order, action);
                action.accept(node.getValue());
                break;
            }
        }
    }
}
