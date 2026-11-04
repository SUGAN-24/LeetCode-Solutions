import java.util.*;
class Solution {
    TreeMap<Integer, Integer> low = new TreeMap<>();
    TreeMap<Integer, Integer> high = new TreeMap<>();
    long lowSum = 0, highSum = 0;
    int lowSize = 0, highSize = 0;
    void add(int num) {
        if (low.isEmpty() || num <= low.lastKey()) {
            low.put(num, low.getOrDefault(num, 0) + 1);
            lowSum += num;
            lowSize++;
        } else {
            high.put(num, high.getOrDefault(num, 0) + 1);
            highSum += num;
            highSize++;
        }
        balance();
    }
    void remove(int num) {
        if (low.containsKey(num)) {
            decrease(low, num);
            lowSum -= num;
            lowSize--;
        } else {
            decrease(high, num);
            highSum -= num;
            highSize--;
        }
        balance();
    }
    void decrease(TreeMap<Integer, Integer> map, int num) {
        int count = map.get(num);
        if (count == 1)
            map.remove(num);
        else
            map.put(num, count - 1);
    }
    void balance() {
        int target = (lowSize + highSize + 1) / 2;
        while (lowSize > target) {
            int num = low.lastKey();
            decrease(low, num);
            lowSum -= num;
            lowSize--;
            high.put(num, high.getOrDefault(num, 0) + 1);
            highSum += num;
            highSize++;
        }
        while (lowSize < target) {
            int num = high.firstKey();
            decrease(high, num);
            highSum -= num;
            highSize--;
            low.put(num, low.getOrDefault(num, 0) + 1);
            lowSum += num;
            lowSize++;
        }
    }
    long getCost() {
        long median = low.lastKey();
        return median * lowSize - lowSum
             + highSum - median * highSize;
    }
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        int m = n - x + 1;
        long[] cost = new long[m];
        for (int i = 0; i < n; i++) {
            add(nums[i]);
            if (i >= x) {
                remove(nums[i - x]);
            }
            if (i >= x - 1) {
                cost[i - x + 1] = getCost();
            }
        }
        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[k + 1][n + 1];
        for (int j = 1; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }
        for (int j = 1; j <= k; j++) {
            for (int i = 1; i <= n; i++) {
                dp[j][i] = dp[j][i - 1];
                if (i >= x && dp[j - 1][i - x] != INF) {
                    dp[j][i] = Math.min(
                        dp[j][i],
                        dp[j - 1][i - x] + cost[i - x]
                    );
                }
            }
        }
        return dp[k][n];
    }
}
