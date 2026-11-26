import java.util.Scanner;

public class MasterMethod {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Form: T(n) = a * T(n/b) + n^k");
        System.out.print("Enter a (number of subproblems): ");
        double a = sc.nextDouble();

        System.out.print("Enter b (subproblem divisor): ");
        double b = sc.nextDouble();

        System.out.print("Enter k (power of n in f(n)): ");
        double k = sc.nextDouble();

        if (a < 1 || b <= 1) {
            System.out.println("Master theorem not applicable: must have a >= 1 and b > 1.");
            return;
        }

        double log_b_a = Math.log(a) / Math.log(b);

        System.out.println("\n--- Analysis ---");
        System.out.printf("log_b(a) = log_%.0f(%.0f) = %.3f\n", b, a, log_b_a);
        System.out.printf("k        = %.3f\n", k);

        double eps = 0.0001;

        if (k < log_b_a - eps) {
            System.out.println("\nResult: Case 1 applies (k < log_b(a))");
            System.out.printf("Final Complexity: Theta(n^%.3f) or Theta(n^(log_%.0f(%.0f)))\n", log_b_a, b, a);
        } else if (Math.abs(k - log_b_a) < eps) {
            System.out.println("\nResult: Case 2 applies (k == log_b(a))");
            if (k == 0) {
                System.out.println("Final Complexity: Theta(log n)");
            } else if (k == 1) {
                System.out.println("Final Complexity: Theta(n log n)");
            } else {
                System.out.printf("Final Complexity: Theta(n^%.3f * log n)\n", k);
            }
        } else {
            System.out.println("\nResult: Case 3 applies (k > log_b(a))");
            if (k == 1) {
                System.out.println("Final Complexity: Theta(n)");
            } else {
                System.out.printf("Final Complexity: Theta(n^%.3f)\n", k);
            }
        }
    }
}
