import java.util.*;

public class BPlusTree {
    static class Node {
        boolean isLeaf;
        int n = 0;
        int[] keys = new int[3];       // capacity 3 for overflow
        Node[] child = new Node[4];    // for internal nodes
        Node next = null;             // linked list for leaf nodes
        Node parent = null;

        Node(boolean isLeaf) {
            this.isLeaf = isLeaf;
        }
    }

    static Node root = new Node(true);

    public static boolean search(int key) {
        Node curr = root;
        while (!curr.isLeaf) {
            int i = 0;
            while (i < curr.n && key >= curr.keys[i]) i++;
            curr = curr.child[i];
        }
        for (int i = 0; i < curr.n; i++) {
            if (curr.keys[i] == key) return true;
        }
        return false;
    }

    public static void insert(int key) {
        Node leaf = findLeaf(root, key);
        insertIntoLeaf(leaf, key);
    }

    static Node findLeaf(Node curr, int key) {
        while (!curr.isLeaf) {
            int i = 0;
            while (i < curr.n && key >= curr.keys[i]) i++;
            curr = curr.child[i];
        }
        return curr;
    }

    static void insertIntoLeaf(Node leaf, int key) {
        int i = leaf.n - 1;
        while (i >= 0 && key < leaf.keys[i]) {
            leaf.keys[i + 1] = leaf.keys[i];
            i--;
        }
        leaf.keys[i + 1] = key;
        leaf.n++;

        if (leaf.n > 2) { // overflow for Order 3
            splitLeaf(leaf);
        }
    }

    static void splitLeaf(Node leaf) {
        Node right = new Node(true);
        // keys: [0, 1, 2]. Left keeps [0], Right gets [1, 2]
        // Key 1 is copied up to parent
        int promoKey = leaf.keys[1];

        right.keys[0] = leaf.keys[1];
        right.keys[1] = leaf.keys[2];
        right.n = 2;

        leaf.n = 1;

        // link leaf nodes
        right.next = leaf.next;
        leaf.next = right;

        if (leaf == root) {
            Node newRoot = new Node(false);
            newRoot.keys[0] = promoKey;
            newRoot.child[0] = leaf;
            newRoot.child[1] = right;
            newRoot.n = 1;
            leaf.parent = newRoot;
            right.parent = newRoot;
            root = newRoot;
        } else {
            right.parent = leaf.parent;
            insertIntoInternal(leaf.parent, promoKey, right);
        }
    }

    static void insertIntoInternal(Node parent, int key, Node rightChild) {
        int i = parent.n - 1;
        while (i >= 0 && key < parent.keys[i]) {
            parent.keys[i + 1] = parent.keys[i];
            parent.child[i + 2] = parent.child[i + 1];
            i--;
        }
        parent.keys[i + 1] = key;
        parent.child[i + 2] = rightChild;
        rightChild.parent = parent;
        parent.n++;

        if (parent.n > 2) {
            splitInternal(parent);
        }
    }

    static void splitInternal(Node node) {
        Node right = new Node(false);
        int midKey = node.keys[1]; // middle key moves UP (not copied)

        right.keys[0] = node.keys[2];
        right.n = 1;
        right.child[0] = node.child[2];
        right.child[1] = node.child[3];
        if (right.child[0] != null) right.child[0].parent = right;
        if (right.child[1] != null) right.child[1].parent = right;

        node.n = 1;
        node.child[2] = null;
        node.child[3] = null;

        if (node == root) {
            Node newRoot = new Node(false);
            newRoot.keys[0] = midKey;
            newRoot.child[0] = node;
            newRoot.child[1] = right;
            newRoot.n = 1;
            node.parent = newRoot;
            right.parent = newRoot;
            root = newRoot;
        } else {
            right.parent = node.parent;
            insertIntoInternal(node.parent, midKey, right);
        }
    }

    public static void displayLeaves() {
        Node curr = root;
        while (!curr.isLeaf) curr = curr.child[0];
        System.out.print("Leaves linked list: ");
        while (curr != null) {
            System.out.print("[ ");
            for (int i = 0; i < curr.n; i++) System.out.print(curr.keys[i] + " ");
            System.out.print("] -> ");
            curr = curr.next;
        }
        System.out.println("null");
    }

    public static void printTree(Node node, String indent) {
        if (node == null) return;
        System.out.print(indent + (node.isLeaf ? "LEAF " : "INT  ") + "[ ");
        for (int i = 0; i < node.n; i++) System.out.print(node.keys[i] + " ");
        System.out.println("]");
        if (!node.isLeaf) {
            for (int i = 0; i <= node.n; i++) {
                printTree(node.child[i], indent + "   ");
            }
        }
    }

    public static void delete(int key) {
        Node leaf = findLeaf(root, key);
        int idx = -1;
        for (int i = 0; i < leaf.n; i++) {
            if (leaf.keys[i] == key) {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            System.out.println("Key " + key + " not found!");
            return;
        }

        for (int i = idx; i < leaf.n - 1; i++) {
            leaf.keys[i] = leaf.keys[i + 1];
        }
        leaf.n--;

        if (leaf != root && leaf.n < 1) {
            fixLeafUnderflow(leaf);
        }
    }

    static void fixLeafUnderflow(Node leaf) {
        Node p = leaf.parent;
        int idx = 0;
        while (idx <= p.n && p.child[idx] != leaf) idx++;

        // borrow from left
        if (idx > 0 && p.child[idx - 1].n > 1) {
            Node left = p.child[idx - 1];
            leaf.keys[0] = left.keys[left.n - 1];
            leaf.n = 1;
            left.n--;
            p.keys[idx - 1] = leaf.keys[0];
            return;
        }

        // borrow from right
        if (idx < p.n && p.child[idx + 1].n > 1) {
            Node right = p.child[idx + 1];
            leaf.keys[0] = right.keys[0];
            leaf.n = 1;
            for (int i = 0; i < right.n - 1; i++) right.keys[i] = right.keys[i + 1];
            right.n--;
            p.keys[idx] = right.keys[0];
            return;
        }

        // merge with sibling
        if (idx > 0) { // merge with left
            Node left = p.child[idx - 1];
            left.next = leaf.next;
            // remove child[idx] and keys[idx-1] from parent
            for (int i = idx - 1; i < p.n - 1; i++) {
                p.keys[i] = p.keys[i + 1];
                p.child[i + 1] = p.child[i + 2];
            }
            p.n--;
            if (p == root && p.n == 0) {
                root = left;
                root.parent = null;
            }
        } else { // merge with right
            Node right = p.child[idx + 1];
            leaf.keys[0] = right.keys[0];
            leaf.n = right.n;
            leaf.next = right.next;
            for (int i = idx; i < p.n - 1; i++) {
                p.keys[i] = p.keys[i + 1];
                p.child[i + 1] = p.child[i + 2];
            }
            p.n--;
            if (p == root && p.n == 0) {
                root = leaf;
                root.parent = null;
            }
        }
    }

    public static void main(String[] args) {
        int[] keys = {15, 25, 5, 10, 20, 30, 35, 40, 45, 50};
        System.out.println("Inserting keys: " + Arrays.toString(keys));
        for (int k : keys) {
            insert(k);
        }

        System.out.println("\nB+ Tree Structure:");
        printTree(root, "");

        System.out.println();
        displayLeaves();

        System.out.println("\nSearching 25: " + (search(25) ? "Found" : "Not Found"));
        System.out.println("Searching 99: " + (search(99) ? "Found" : "Not Found"));

        System.out.println("\nDeleting 20...");
        delete(20);
        displayLeaves();

        System.out.println("\nDeleting 5...");
        delete(5);
        displayLeaves();

        System.out.println("\nFinal B+ Tree Structure:");
        printTree(root, "");
    }
}
