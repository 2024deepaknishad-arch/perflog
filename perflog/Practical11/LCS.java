import java.util.Scanner;

public class LCS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first string (S1): ");
        String s1 = sc.next();

        System.out.print("Enter second string (S2): ");
        String s2 = sc.next();

        int m = s1.length();
        int n = s2.length();

        // dp[i][j] stores length of LCS of s1[0..i-1] and s2[0..j-1]
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // Match found: take diagonal + 1
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // Mismatch: take max of top and left
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Print DP Table
        System.out.println("\n--- DP Table ---");
        System.out.print(" \t \t");
        for (int j = 0; j < n; j++) System.out.print(s2.charAt(j) + "\t");
        System.out.println();

        for (int i = 0; i <= m; i++) {
            if (i == 0) System.out.print(" \t");
            else System.out.print(s1.charAt(i - 1) + "\t");

            for (int j = 0; j <= n; j++) {
                System.out.print(dp[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("\nLength of LCS = " + dp[m][n]);

        // Backtrack to find actual subsequence string
        StringBuilder lcsStr = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                lcsStr.append(s1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--; // move up
            } else {
                j--; // move left
            }
        }

        // Reverse since we backtracked from end
        String lcs = lcsStr.reverse().toString();
        System.out.println("Longest Common Subsequence: \"" + lcs + "\"");
    }
}
