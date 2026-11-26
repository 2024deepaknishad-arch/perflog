import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Item {
    int id;
    double weight;
    double profit;
    double ratio;

    Item(int id, double weight, double profit) {
        this.id = id;
        this.weight = weight;
        this.profit = profit;
        this.ratio = profit / weight;
    }
}

public class FractionalKnapsack {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of items: ");
        int n = sc.nextInt();

        Item[] items = new Item[n];
        System.out.println("Enter weight and profit for each item:");
        for (int i = 0; i < n; i++) {
            System.out.print("Item " + (i + 1) + " (weight profit): ");
            double w = sc.nextDouble();
            double p = sc.nextDouble();
            items[i] = new Item(i + 1, w, p);
        }

        System.out.print("Enter Knapsack capacity: ");
        double capacity = sc.nextDouble();

        // Sort items in descending order of profit/weight ratio
        Arrays.sort(items, new Comparator<Item>() {
            public int compare(Item a, Item b) {
                return Double.compare(b.ratio, a.ratio);
            }
        });

        double totalProfit = 0.0;
        double currentWeight = 0.0;

        System.out.println("\nSelected Items:");
        System.out.println("Item\tWeight Taken\tProfit Earned\tFraction");
        System.out.println("-------------------------------------------------");

        for (int i = 0; i < n; i++) {
            if (currentWeight + items[i].weight <= capacity) {
                // Take whole item
                currentWeight += items[i].weight;
                totalProfit += items[i].profit;
                System.out.println(items[i].id + "\t" + items[i].weight + "\t\t" + items[i].profit + "\t\t1.0 (100%)");
            } else {
                // Take fraction of item
                double remain = capacity - currentWeight;
                double fraction = remain / items[i].weight;
                double profitEarned = items[i].profit * fraction;

                currentWeight += remain;
                totalProfit += profitEarned;
                System.out.printf("%d\t%.2f\t\t%.2f\t\t%.2f (%.0f%%)\n", items[i].id, remain, profitEarned, fraction, fraction * 100);
                break; // knapsack is full
            }
        }

        System.out.println("-------------------------------------------------");
        System.out.printf("Total Weight in Knapsack = %.2f\n", currentWeight);
        System.out.printf("Maximum Total Profit     = %.2f\n", totalProfit);
    }
}
