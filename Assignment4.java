public class Assignment4 {

    public static int[] findZeroSumSubarrayIndices(int[] nums) {
        int[] indices = new int[2];
        int sum = 0;
        boolean found = false;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum == 0 && !found) {
                indices[0] = 0;
                indices[1] = i;
                found = true;
            }
            if (sum == 0) {
                indices[0] = indices[0];
                indices[1] = i;
            }
        }

        return indices;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, -6, 5, 4, 0};
        int[] indices = findZeroSumSubarrayIndices(nums);
        if (indices[0] != 0 && indices[1] != 0) {
            System.out.println("Index of the first number: " + indices[0]);
            System.out.println("Index of the last number: " + indices[1]);
        } else {
            System.out.println("No subarray with zero sum found");
        }
    }
}
