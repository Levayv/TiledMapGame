package com.mygdx.game.world;

class MyTree {
    Node root;
    public void add(int value) {
        root = addRecursive(root, value);
    }
    private Node addRecursive(Node current, int data) {
        if (current == null) {
            return new Node(data);
        }

        if (data < current.data) {
            current.childA = addRecursive(current.childA, data);
        } else if (data > current.data) {
            current.childB = addRecursive(current.childB, data);
        } else {
            // value already exists
            return current;
        }

        return current;
    }
    public int get(){
        return 0;
    }
    public boolean containsNode(int data) {
        return containsNodeRecursive(root, data);
    }
    private boolean containsNodeRecursive(Node current, int data) {
        if (current == null) {
            return false;
        }
        if (data == current.data) {
            return true;
        }
        return data < current.data
                ? containsNodeRecursive(current.childA, data)
                : containsNodeRecursive(current.childB, data);
    }
    public void delete(int value) {
        root = deleteRecursive(root, value);
    }
    private Node deleteRecursive(Node current, int data) {
        if (current == null) {
            return null;
        }

        if (data == current.data) {
            // Node to delete found
            if (current.childA == null && current.childB == null) {
                return null;
            }
            if (current.childA == null) {
                return current.childA;
            }

            if (current.childB == null) {
                return current.childB;
            }
            int smallestValue = findSmallestData(current.childB);
            current.data = smallestValue;
            current.childB = deleteRecursive(current.childB, smallestValue);
            return current;

        }
        if (data < current.data) {
            current.childA = deleteRecursive(current.childA, data);
            return current;
        }
        current.childB = deleteRecursive(current.childB, data);
        return current;
    }
    private int findSmallestData(Node root) {
        return root.childA == null ? root.data : findSmallestData(root.childB);
    }

}

