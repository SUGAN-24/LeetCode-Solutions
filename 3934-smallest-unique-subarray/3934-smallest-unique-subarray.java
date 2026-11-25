
import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int N = n + 1;

        int[] sa = new int[N];
        int[] rank = new int[N];
        int[] newRank = new int[N];
        int[] temp = new int[N];
        int[] cnt = new int[N];

        // Initial sorting
        int[] freq = new int[100002];

        for (int i = 0; i < n; i++) {
            freq[nums[i]]++;
        }
        freq[0]++;

        for (int i = 1; i < freq.length; i++) {
            freq[i] += freq[i - 1];
        }

        for (int i = N - 1; i >= 0; i--) {
            int val = (i == n) ? 0 : nums[i];
            sa[--freq[val]] = i;
        }

        int classes = 1;
        rank[sa[0]] = 0;

        for (int i = 1; i < N; i++) {
            int a = sa[i - 1];
            int b = sa[i];

            int x = (a == n) ? 0 : nums[a];
            int y = (b == n) ? 0 : nums[b];

            if (x != y) classes++;

            rank[b] = classes - 1;
        }

        // Build suffix array
        for (int len = 1; len < N; len <<= 1) {

            for (int i = 0; i < N; i++) {
                temp[i] = sa[i] - len;
                if (temp[i] < 0) {
                    temp[i] += N;
                }
            }

            Arrays.fill(cnt, 0, classes, 0);

            for (int i = 0; i < N; i++) {
                cnt[rank[temp[i]]]++;
            }

            for (int i = 1; i < classes; i++) {
                cnt[i] += cnt[i - 1];
            }

            for (int i = N - 1; i >= 0; i--) {
                int x = temp[i];
                sa[--cnt[rank[x]]] = x;
            }

            newRank[sa[0]] = 0;
            int newClasses = 1;

            for (int i = 1; i < N; i++) {
                int a = sa[i - 1];
                int b = sa[i];

                int a2 = (a + len) % N;
                int b2 = (b + len) % N;

                if (rank[a] != rank[b] ||
                    rank[a2] != rank[b2]) {
                    newClasses++;
                }

                newRank[b] = newClasses - 1;
            }

            int[] swap = rank;
            rank = newRank;
            newRank = swap;

            classes = newClasses;

            if (classes == N) break;
        }

        // Build inverse suffix array
        int[] pos = new int[N];

        for (int i = 0; i < N; i++) {
            pos[sa[i]] = i;
        }

        // Kasai's LCP algorithm
        int[] lcp = new int[N];
        int k = 0;

        for (int i = 0; i < n; i++) {
            int r = pos[i];

            if (r == N - 1) {
                k = 0;
                continue;
            }

            int j = sa[r + 1];

            while (i + k < n &&
                   j + k < n &&
                   nums[i + k] == nums[j + k]) {
                k++;
            }

            lcp[r] = k;

            if (k > 0) k--;
        }

        // Find smallest unique subarray
        int ans = n;

        for (int i = 0; i < n; i++) {
            int r = pos[i];

            int left = (r > 0) ? lcp[r - 1] : 0;
            int right = (r < N - 1) ? lcp[r] : 0;

            int required = Math.max(left, right) + 1;

            if (required <= n - i) {
                ans = Math.min(ans, required);
            }
        }

        return ans;
    }
}
