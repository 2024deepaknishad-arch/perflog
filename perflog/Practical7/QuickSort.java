public class QuickSort {
    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // choose last element as pivot
        int i = low - 1;       // index of smaller element

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // swap arr[i+1] and arr[high] (pivot placed in correct position)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high); // pi is partition index

            quickSort(arr, low, pi - 1);  // sort elements before partition
            quickSort(arr, pi + 1, high); // sort elements after partition
        }
    }

    public static void main(String[] args) {
        int[] arr = {10, 80, 30, 90, 40, 50, 70};

        System.out.print("Original Array: ");
        for (int x : arr) System.out.print(x + " ");
        System.out.println();

        quickSort(arr, 0, arr.length - 1);

        System.out.print("Sorted Array  : ");
        for (int x : arr) System.out.print(x + " ");
        System.out.println();
    }
}
