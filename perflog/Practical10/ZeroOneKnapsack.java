import java.util.Scanner;

public class ZeroOneKnapsack {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int W = 5; // Fixed capacity at 5 kg as per practical requirement

        System.out.print("Enter number of items: ");
        int n = sc.nextInt();

        int[] wt = new int[n + 1];
        int[] val = new int[n + 1];

        System.out.println("Enter weight and profit for each item:");
        for (int i = 1; i <= n; i++) {
            System.out.print("Item " + i + " (weight profit): ");
            wt[i] = sc.nextInt();
            val[i] = sc.nextInt();
        }

        // dp[i][w] = max profit considering first i items with capacity w
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (wt[i] <= w) {
                    // Option 1: Take item i -> val[i] + dp[i-1][w - wt[i]]
                    // Option 2: Leave item i -> dp[i-1][w]
                    dp[i][w] = Math.max(dp[i - 1][w], val[i] + dp[i - 1][w - wt[i]]);
                } else {
                    // Cannot take item i because it exceeds current capacity w
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Display DP Table
        System.out.println("\n--- DP Table ---");
        System.out.print("Item \\ W\t");
        for (int w = 0; w <= W; w++) System.out.print(w + "\t");
        System.out.println();

        for (int i = 0; i <= n; i++) {
            System.out.print("i = " + i + "\t\t");
            for (int w = 0; w <= W; w++) {
                System.out.print(dp[i][w] + "\t");
            }
            System.out.println();
        }

        System.out.println("\nMaximum Profit = " + dp[n][W]);

        // Backtrack to find selected items
        System.out.print("Selected Items: ");
        int res = dp[n][W];
        int w = W;
        for (int i = n; i > 0 && res > 0; i--) {
            // If the value came from taking item i
            if (res != dp[i - 1][w]) {
                System.out.print("Item " + i + " (wt=" + wt[i] + ", val=" + val[i] + ") ");
                res -= val[i];
                w -= wt[i];
            }
        }
        System.out.println();
    }
}
