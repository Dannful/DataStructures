package me.dannly.data_structures.balanced_tree;

public class AVLTree<T extends Comparable<T>> {

    private static class Node<T extends Comparable<T>> {

        private T value;
        private Node<T> left, right;
        private int height, balanceFactor;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public void clear() {
            value = null;
            right = null;
            left = null;
        }
    }

    private Node<T> root = null;
    private int count = 0;

    public void add(T value) {
        if (root == null) {
            root = new Node<>(value);
            count++;
            return;
        }
        root = add(root, value);
        count++;
    }

    public int size() {
        return count;
    }

    private Node<T> add(Node<T> node, final T value) {
        if (node == null)
            return new Node<>(value);
        if (value.compareTo(node.value) > 0)
            node.right = add(node.right, value);
        else
            node.left = add(node.left, value);
        update(node);
        return balance(node);
    }

    private Node<T> balance(Node<T> node) {
        if (node == null) return null;
        if (node.balanceFactor == -2) {
            if (node.left.balanceFactor <= 0) {
                return rotateRight(node);
            } else {
                return leftRightCase(node);
            }
        } else if (node.balanceFactor == 2) {
            if (node.right.balanceFactor >= 0) {
                return rotateLeft(node);
            } else {
                return rightLeftCase(node);
            }
        }
        return node;
    }

    private Node<T> leftRightCase(Node<T> node) {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    private Node<T> rightLeftCase(Node<T> node) {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    private Node<T> rotateRight(Node<T> node) {
        final Node<T> newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        update(node);
        update(newParent);
        return newParent;
    }

    private Node<T> rotateLeft(Node<T> node) {
        final Node<T> newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        update(node);
        update(newParent);
        return newParent;
    }

    private void update(Node<T> node) {
        if (node == null) return;
        final int leftHeight = node.left != null ? node.left.height : -1;
        final int rightHeight = node.right != null ? node.right.height : -1;
        node.height = 1 + Math.max(leftHeight, rightHeight);
        node.balanceFactor = rightHeight - leftHeight;
    }

    public boolean contains(T value) {
        return find(root, value) != null;
    }

    public void remove(T value) {
        root = remove(root, value);
    }

    private Node<T> remove(Node<T> node, final T value) {
        if (node == null) return null;
        final int compareTo = value.compareTo(node.value);
        if (compareTo > 0) {
            node.right = remove(node.right, value);
        } else if (compareTo < 0) {
            node.left = remove(node.left, value);
        } else {
            count--;
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                if (node.left.height > node.right.height) {
                    final Node<T> rightmost = digRight(node.left);
                    node.value = rightmost.value;
                    node.left = remove(node.left, node.value);
                } else {
                    final Node<T> leftmost = digLeft(node.right);
                    node.value = leftmost.value;
                    node.right = remove(node.right, node.value);
                }
            }
        }
        update(node);
        return balance(node);
    }

    private Node<T> digLeft(Node<T> node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    private Node<T> digRight(Node<T> node) {
        while (node.right != null)
            node = node.right;
        return node;
    }

    private Node<T> find(Node<T> node, final T value) {
        if (node == null) return null;
        if (node.value.compareTo(value) == 0)
            return node;
        return value.compareTo(node.value) > 0 ? find(node.right, value) : find(node.left, value);
    }
}
