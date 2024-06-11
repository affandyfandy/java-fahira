public class Assignment4 {

    public static int[] findZeroSumSubarray(int[] nums) {
        int[] res = new int[2];
        int sum = 0;
        boolean found = false;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum == 0 && !found) {
                res[0] = 0;
                res[1] = i;
                found = true;
            }
            if (sum == 0) {
                res[0] = res[0];
                res[1] = i;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, -6, 5, 4, 0};
        int[] res = findZeroSumSubarray(nums);
        System.out.println("["+res[0]+", "+res[1]+"]");
    }
}
