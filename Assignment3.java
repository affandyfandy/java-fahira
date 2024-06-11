public class Assignment3 {

    public static int[] findBiggestTwo(int[] lst){
        int big1 = Integer.MIN_VALUE;
        int big2 = Integer.MIN_VALUE;
        int big1Idx = -1;
        int big2Idx = -1;

        for (int i = 0; i < lst.length; i++){
            if (lst[i] > big1){
                big2 = big1;
                big1 = lst[i];
                big2Idx = big1Idx;
                big1Idx = i;
            }
            else if (lst[i] > big2){
                big2 = lst[i];
                big2Idx = i;
            }
        }

        int[] res = new int[2];
        res[0] = big1Idx;
        res[1] = big2Idx;
        return res;
    }

    public static void main(String[] string){
        int[] nums = {1, 4, 3, -6, 5, 4};
        int[] res = findBiggestTwo(nums);
        System.out.println("[" + res[0] + ", " + res[1] + "]");
    }
}

