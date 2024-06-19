package lab2;

import java.util.Arrays;

public class SortingArray implements Runnable {

    private int[] array;
    private int startIndex;
    private int endIndex;

    public SortingArray(int[] array, int startIndex, int endIndex) {
      this.array = array;
      this.startIndex = startIndex;
      this.endIndex = endIndex;
    }

    @Override
    public void run() {
        Arrays.sort(array, startIndex, endIndex);
    }
    
}


