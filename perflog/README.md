# AOA Practical Exam Master Guide — All 11 Practicals

This directory contains complete, working, humanly-written Java programs for all 11 Analysis of Algorithms (AOA) college practical examinations.

---

## Quick Navigation & Summary Table

| No. | Practical Name | Folder | Key Class File | Approach | Time Complexity | Space |
|:---:|:---|:---:|:---:|:---:|:---:|:---:|
| **1** | Recursion Tree Method | `Practical1` | `RecursionTree.java` | Mathematical / Iterative | $O(\log n)$ (Program) / $\Theta(n \log n)$ | $O(1)$ |
| **2** | Master Method | `Practical2` | `MasterMethod.java` | Divide & Conquer Analysis | $O(1)$ | $O(1)$ |
| **3** | B-Tree (Order 3) | `Practical3` | `BTree.java` | Multiway Search Tree | $O(\log n)$ (Insert, Search, Delete) | $O(n)$ |
| **4** | B+ Tree (Order 3) | `Practical4` | `BPlusTree.java` | Multiway Index + Linked Leaves | $O(\log n)$ (Search/Insert/Delete) | $O(n)$ |
| **5** | Binary Search | `Practical5` | `BinarySearch.java` | Divide and Conquer / Decrease | $O(\log n)$ | $O(1)$ |
| **6** | Merge Sort | `Practical6` | `MergeSort.java` | Divide and Conquer | $O(n \log n)$ (All cases) | $O(n)$ |
| **7** | Quick Sort | `Practical7` | `QuickSort.java` | Divide and Conquer | Avg: $O(n \log n)$, Worst: $O(n^2)$ | $O(\log n)$ |
| **8** | Fractional Knapsack | `Practical8` | `FractionalKnapsack.java` | Greedy Method | $O(n \log n)$ | $O(n)$ |
| **9** | Job Sequencing | `Practical9` | `JobSequencing.java` | Greedy Method | $O(n \log n + n \cdot d)$ | $O(d)$ |
| **10** | 0/1 Knapsack (Cap=5) | `Practical10` | `ZeroOneKnapsack.java` | Dynamic Programming | $O(n \cdot W)$ | $O(n \cdot W)$ |
| **11** | Longest Common Subseq | `Practical11` | `LCS.java` | Dynamic Programming | $O(m \cdot n)$ | $O(m \cdot n)$ |

---

## Compilation & Execution Commands

```bash
# Practical 1: Recursion Tree
cd "Practical1"; javac RecursionTree.java; java RecursionTree

# Practical 2: Master Method
cd "Practical2"; javac MasterMethod.java; java MasterMethod

# Practical 3: B-Tree
cd "Practical3"; javac BTree.java; java BTree

# Practical 4: B+ Tree
cd "Practical4"; javac BPlusTree.java; java BPlusTree

# Practical 5: Binary Search
cd "Practical5"; javac BinarySearch.java; java BinarySearch

# Practical 6: Merge Sort
cd "Practical6"; javac MergeSort.java; java MergeSort

# Practical 7: Quick Sort
cd "Practical7"; javac QuickSort.java; java QuickSort

# Practical 8: Fractional Knapsack
cd "Practical8"; javac FractionalKnapsack.java; java FractionalKnapsack

# Practical 9: Job Sequencing
cd "Practical9"; javac JobSequencing.java; java JobSequencing

# Practical 10: 0/1 Knapsack
cd "Practical10"; javac ZeroOneKnapsack.java; java ZeroOneKnapsack

# Practical 11: LCS
cd "Practical11"; javac LCS.java; java LCS
```
