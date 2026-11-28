import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] freq = new int[n + 1];
        int minPrice = Integer.MAX_VALUE;

        for (int[] item : items) {
            freq[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        int[] gain = new int[n + 1];

        for (int f = 1; f <= n; f++) {
            for (int j = f; j <= n; j += f) {
                gain[f] += freq[j];
            }
            gain[f]--;
        }

        TreeMap<Integer, Long> offers = new TreeMap<>();

        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];

            if (gain[factor] > 0 && price < 2L * minPrice) {
                offers.put(price, offers.getOrDefault(price, 0L) + gain[factor]);
            }
        }

        long ans = 0;
        long remaining = budget;

        for (Map.Entry<Integer, Long> e : offers.entrySet()) {
            int price = e.getKey();
            long count = e.getValue();

            long buy = Math.min(count, remaining / price);

            ans += buy * 2;
            remaining -= buy * price;
        }

        ans += remaining / minPrice;

        return (int) ans;
    }
}