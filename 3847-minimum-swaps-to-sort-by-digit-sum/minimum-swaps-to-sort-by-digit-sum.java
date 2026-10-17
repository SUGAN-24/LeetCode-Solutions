import java.util.*;
class Solution {
    public int minSwaps(int[] nums) {
        int n = nums.length;
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            int sum = 0;
            int num = nums[i];

            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }
            arr[i][0] = sum;
            arr[i][1] = nums[i];
        }
        Arrays.sort(arr, (a, b) -> {
            if (a[0] != b[0])
                return a[0] - b[0];
            return a[1] - b[1];
        });
        int[] sorted = new int[n];
        for (int i = 0; i < n; i++) {
            sorted[i] = arr[i][1];
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(nums[i], i);
        }
        int swaps = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] != sorted[i]) {
                int j = map.get(sorted[i]);
                map.put(nums[i], j);
                map.put(sorted[i], i);
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                 swaps++;
            }
        }
        return swaps;
    }
}