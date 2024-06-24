package lab2;

import java.util.Arrays;

public class App {
    private static final int NUM_THREADS = 4;

    private static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            // Find the middle point to divide the array into two halves:
            int mid = (left + right) / 2;
            // Call mergeSort for first half:
            mergeSort(array, left, mid);
            // Call mergeSort for second half:
            mergeSort(array, mid + 1, right);
            // Find the middle point to divide the array into two halves:
            merge(array, left, mid, right);
        }
    }

    private static void merge(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
    
        while (i <= mid) {
            temp[k++] = array[i++];
        }
    
        while (j <= right) {
            temp[k++] = array[j++];
        }
    
        System.arraycopy(temp, 0, array, left, temp.length);
      }


    public static void main(String[] args) {
        Thread[] threads = new Thread[NUM_THREADS];
        int[] arr = {1,9,2,3,20,4,7,5};
        System.out.println("Before sorting: " + Arrays.toString(arr));
        int segmentSize = arr.length / NUM_THREADS;

        for (int i = 0; i < NUM_THREADS; i++) {
            int startIndex = i * segmentSize;
            int endIndex;
            if (i == NUM_THREADS - 1){
                endIndex = arr.length - 1;
            }
            else{
                endIndex = (startIndex + segmentSize - 1);
            }
            threads[i] = new Thread(new SortingArray(arr, startIndex, endIndex));
            threads[i].start();
        }

        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        mergeSort(arr, 0, arr.length - 1);

        System.out.println("After sorting: " + Arrays.toString(arr));
    }
}
