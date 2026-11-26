import java.util.Scanner;

public class RecursionTree {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter value of n (power of 2, e.g. 16): ");
        int n = sc.nextInt();

        int level = 0;
        int nodes = 1;
        int size = n;
        int totalCost = 0;

        System.out.println("\nLevel\tNodes\tSize\tCost per Node\tLevel Cost");
        System.out.println("--------------------------------------------------");

        while (size >= 1) {
            int levelCost = nodes * size;
            System.out.println(level + "\t" + nodes + "\t" + size + "\t" + size + "\t\t" + levelCost);
            totalCost += levelCost;

            level++;
            nodes = nodes * 2;
            size = size / 2;
        }

        System.out.println("--------------------------------------------------");
        System.out.println("Total Levels : " + level + " (which is log2(" + n + ") + 1)");
        System.out.println("Total Cost   : " + totalCost);
        System.out.println("Theoretical  : n * (log2(n) + 1) = " + (n * level));
        System.out.println("Complexity   : O(n log n)");
    }
}
