import java.util.*;

public class BTree {
    static class Node {
        int[] keys = new int[3];       // capacity 3 for temporary overflow
        Node[] child = new Node[4];    // capacity 4 for temporary overflow
        int n = 0;
        boolean leaf = true;
        Node parent = null;

        Node(boolean leaf) {
            this.leaf = leaf;
        }
    }

    static Node root = new Node(true);

    public static boolean search(Node node, int key) {
        if (node == null) return false;
        int i = 0;
        while (i < node.n && key > node.keys[i]) i++;
        if (i < node.n && node.keys[i] == key) return true;
        if (node.leaf) return false;
        return search(node.child[i], key);
    }

    public static void insert(int key) {
        Node leaf = findLeaf(root, key);
        insertIntoNode(leaf, key, null);
    }

    static Node findLeaf(Node node, int key) {
        if (node.leaf) return node;
        int i = 0;
        while (i < node.n && key > node.keys[i]) i++;
        return findLeaf(node.child[i], key);
    }

    static void insertIntoNode(Node node, int key, Node newRightChild) {
        int i = node.n - 1;
        while (i >= 0 && key < node.keys[i]) {
            node.keys[i + 1] = node.keys[i];
            node.child[i + 2] = node.child[i + 1];
            i--;
        }
        node.keys[i + 1] = key;
        node.child[i + 2] = newRightChild;
        if (newRightChild != null) newRightChild.parent = node;
        node.n++;

        if (node.n > 2) { // overflow for Order 3
            split(node);
        }
    }

    static void split(Node node) {
        int midKey = node.keys[1];
        Node right = new Node(node.leaf);

        right.keys[0] = node.keys[2];
        right.n = 1;
        right.child[0] = node.child[2];
        right.child[1] = node.child[3];
        if (right.child[0] != null) right.child[0].parent = right;
        if (right.child[1] != null) right.child[1].parent = right;

        node.n = 1; // node retains keys[0] and child[0], child[1]
        node.child[2] = null;
        node.child[3] = null;

        if (node == root) {
            Node newRoot = new Node(false);
            newRoot.keys[0] = midKey;
            newRoot.n = 1;
            newRoot.child[0] = node;
            newRoot.child[1] = right;
            node.parent = newRoot;
            right.parent = newRoot;
            root = newRoot;
        } else {
            right.parent = node.parent;
            insertIntoNode(node.parent, midKey, right);
        }
    }

    public static void display(Node node) {
        if (node == null) return;
        for (int i = 0; i < node.n; i++) {
            if (!node.leaf) display(node.child[i]);
            System.out.print(node.keys[i] + " ");
        }
        if (!node.leaf) display(node.child[node.n]);
    }

    public static void printTree(Node node, String indent) {
        if (node == null) return;
        System.out.print(indent + "[ ");
        for (int i = 0; i < node.n; i++) System.out.print(node.keys[i] + " ");
        System.out.println("]");
        if (!node.leaf) {
            for (int i = 0; i <= node.n; i++) {
                printTree(node.child[i], indent + "   ");
            }
        }
    }

    public static void delete(int key) {
        Node target = findNode(root, key);
        if (target == null) {
            System.out.println("Key " + key + " not found!");
            return;
        }
        deleteKey(target, key);
    }

    static Node findNode(Node node, int key) {
        if (node == null) return null;
        int i = 0;
        while (i < node.n && key > node.keys[i]) i++;
        if (i < node.n && node.keys[i] == key) return node;
        if (node.leaf) return null;
        return findNode(node.child[i], key);
    }

    static void deleteKey(Node node, int key) {
        int idx = 0;
        while (idx < node.n && node.keys[idx] != key) idx++;

        if (!node.leaf) {
            // Internal node: replace with predecessor from left subtree
            Node pred = node.child[idx];
            while (!pred.leaf) pred = pred.child[pred.n];
            int predKey = pred.keys[pred.n - 1];
            node.keys[idx] = predKey;
            deleteKey(pred, predKey);
            return;
        }

        // Leaf node: remove key directly
        for (int i = idx; i < node.n - 1; i++) {
            node.keys[i] = node.keys[i + 1];
        }
        node.n--;

        // If underflow occurs
        if (node != root && node.n < 1) {
            fixUnderflow(node);
        }
    }

    static void fixUnderflow(Node node) {
        if (node == root) {
            if (node.n == 0 && !node.leaf) {
                root = node.child[0];
                root.parent = null;
            }
            return;
        }

        Node parent = node.parent;
        int idx = 0;
        while (idx <= parent.n && parent.child[idx] != node) idx++;

        // Try borrow from left sibling
        if (idx > 0 && parent.child[idx - 1].n > 1) {
            Node left = parent.child[idx - 1];
            node.keys[0] = parent.keys[idx - 1];
            node.n = 1;
            parent.keys[idx - 1] = left.keys[left.n - 1];
            node.child[1] = node.child[0];
            node.child[0] = left.child[left.n];
            if (node.child[0] != null) node.child[0].parent = node;
            left.n--;
            return;
        }

        // Try borrow from right sibling
        if (idx < parent.n && parent.child[idx + 1].n > 1) {
            Node right = parent.child[idx + 1];
            node.keys[0] = parent.keys[idx];
            node.n = 1;
            parent.keys[idx] = right.keys[0];
            node.child[1] = right.child[0];
            if (node.child[1] != null) node.child[1].parent = node;
            for (int i = 0; i < right.n - 1; i++) {
                right.keys[i] = right.keys[i + 1];
                right.child[i] = right.child[i + 1];
            }
            right.child[right.n - 1] = right.child[right.n];
            right.n--;
            return;
        }

        // Merge with sibling
        if (idx > 0) { // merge with left sibling
            Node left = parent.child[idx - 1];
            left.keys[left.n] = parent.keys[idx - 1];
            left.child[left.n + 1] = node.child[0];
            if (node.child[0] != null) node.child[0].parent = left;
            left.n++;

            for (int i = idx - 1; i < parent.n - 1; i++) {
                parent.keys[i] = parent.keys[i + 1];
                parent.child[i + 1] = parent.child[i + 2];
            }
            parent.n--;
            if (parent.n == 0 && parent == root) {
                root = left;
                root.parent = null;
            } else if (parent.n < 1 && parent != root) {
                fixUnderflow(parent);
            }
        } else { // merge with right sibling
            Node right = parent.child[idx + 1];
            node.keys[0] = parent.keys[idx];
            node.keys[1] = right.keys[0];
            node.child[1] = right.child[0];
            node.child[2] = right.child[1];
            if (node.child[1] != null) node.child[1].parent = node;
            if (node.child[2] != null) node.child[2].parent = node;
            node.n = 2;

            for (int i = idx; i < parent.n - 1; i++) {
                parent.keys[i] = parent.keys[i + 1];
                parent.child[i + 1] = parent.child[i + 2];
            }
            parent.n--;
            if (parent.n == 0 && parent == root) {
                root = node;
                root.parent = null;
            } else if (parent.n < 1 && parent != root) {
                fixUnderflow(parent);
            }
        }
    }

    public static void main(String[] args) {
        int[] keys = {10, 20, 5, 6, 12, 30, 7, 17};
        System.out.println("Inserting keys: " + Arrays.toString(keys));
        for (int k : keys) {
            insert(k);
        }

        System.out.println("\nTree Structure:");
        printTree(root, "");

        System.out.print("\nInorder Traversal: ");
        display(root);
        System.out.println();

        System.out.println("\nSearching 12: " + (search(root, 12) ? "Found" : "Not Found"));
        System.out.println("Searching 99: " + (search(root, 99) ? "Found" : "Not Found"));

        System.out.println("\nDeleting 6 (Leaf node)...");
        delete(6);
        System.out.print("Inorder after deleting 6: ");
        display(root);
        System.out.println();

        System.out.println("\nDeleting 10 (Internal node)...");
        delete(10);
        System.out.print("Inorder after deleting 10: ");
        display(root);
        System.out.println();

        System.out.println("\nFinal Tree Structure:");
        printTree(root, "");
    }
}
