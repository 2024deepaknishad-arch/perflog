import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Job {
    String id;
    int deadline;
    int profit;

    Job(String id, int deadline, int profit) {
        this.id = id;
        this.deadline = deadline;
        this.profit = profit;
    }
}

public class JobSequencing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of jobs: ");
        int n = sc.nextInt();

        Job[] jobs = new Job[n];
        int maxDeadline = 0;

        System.out.println("Enter Job ID, Deadline, Profit for each job:");
        for (int i = 0; i < n; i++) {
            System.out.print("Job " + (i + 1) + " (id deadline profit): ");
            String id = sc.next();
            int d = sc.nextInt();
            int p = sc.nextInt();
            jobs[i] = new Job(id, d, p);
            if (d > maxDeadline) maxDeadline = d;
        }

        // Sort jobs in descending order of profit
        Arrays.sort(jobs, new Comparator<Job>() {
            public int compare(Job a, Job b) {
                return b.profit - a.profit;
            }
        });

        // Time slots: 1 to maxDeadline
        String[] slot = new String[maxDeadline + 1];
        Arrays.fill(slot, null);

        int totalProfit = 0;
        int jobCount = 0;

        for (int i = 0; i < n; i++) {
            // Find an available slot from job.deadline down to 1
            for (int t = jobs[i].deadline; t > 0; t--) {
                if (slot[t] == null) {
                    slot[t] = jobs[i].id;
                    totalProfit += jobs[i].profit;
                    jobCount++;
                    break;
                }
            }
        }

        System.out.println("\nScheduled Jobs by Time Slot:");
        for (int t = 1; t <= maxDeadline; t++) {
            System.out.println("Slot [" + (t - 1) + " - " + t + "]: " + (slot[t] != null ? slot[t] : "Empty"));
        }

        System.out.println("\nTotal Jobs Done : " + jobCount);
        System.out.println("Maximum Profit  : " + totalProfit);
    }
}
