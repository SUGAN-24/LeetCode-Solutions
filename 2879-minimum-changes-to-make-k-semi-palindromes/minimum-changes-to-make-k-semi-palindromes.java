import java.util.*;
class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int INF = 100000000;
        int[][] cost = new int[n][n];
        for (int len = 2; len <= n; len++) {
            for (int l = 0; l + len <= n; l++) {
                int r = l + len - 1;
                cost[l][r] = INF;
                for (int d = 1; d < len; d++) {
                    if (len % d != 0) {
                        continue;
                    }
                    int changes = 0;
                    for (int rem = 0; rem < d; rem++) {
                        int left = l + rem;
                        int right = l + rem
                                + ((len - 1 - rem) / d) * d;
                        while (left < right) {
                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }

                            left += d;
                            right -= d;
                        }
                    }

                    cost[l][r] = Math.min(cost[l][r], changes);
                }
            }
        }
        int[][] dp = new int[n + 1][k + 1];

        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int parts = 1; parts <= k; parts++) {

            for (int i = 2 * parts; i <= n; i++) {

                for (int j = 2 * (parts - 1); j <= i - 2; j++) {

                    if (dp[j][parts - 1] == INF) {
                        continue;
                    }

                    dp[i][parts] = Math.min(
                        dp[i][parts],
                        dp[j][parts - 1] + cost[j][i - 1]
                    );
                }
            }
        }

        return dp[n][k];
    }
}
