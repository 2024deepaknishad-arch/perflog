import java.util.Scanner;

public class BinarySearch {
    public static int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == target) {
                return mid; // found at index mid
            } else if (arr[mid] < target) {
                low = mid + 1; // search right half
            } else {
                high = mid - 1; // search left half
            }
        }
        return -1; // not found
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] arr = {11, 22, 33, 44, 55, 66, 77, 88, 99};

        System.out.print("Sorted Array: ");
        for (int x : arr) System.out.print(x + " ");
        System.out.println();

        System.out.print("Enter element to search: ");
        int target = sc.nextInt();

        int result = binarySearch(arr, target);

        if (result != -1) {
            System.out.println("Element " + target + " found at index " + result + " (position " + (result + 1) + ").");
        } else {
            System.out.println("Element " + target + " does not exist in the array.");
        }
    }
}
