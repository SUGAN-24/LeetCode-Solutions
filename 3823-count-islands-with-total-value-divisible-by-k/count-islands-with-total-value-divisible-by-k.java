class Solution {
    public int countIslands(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0) {
                    long[] sum = new long[1];
                    dfs(grid, i, j, sum);

                    if (sum[0] % k == 0) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    private void dfs(int[][] grid, int r, int c, long[] sum) {
        int m = grid.length;
        int n = grid[0].length;

        if (r < 0 || c < 0 || r >= m || c >= n ||
            grid[r][c] == 0) {
            return;
        }
        sum[0] += grid[r][c];
        grid[r][c] = 0;
        dfs(grid, r + 1, c, sum);
        dfs(grid, r - 1, c, sum);
        dfs(grid, r, c + 1, sum);
        dfs(grid, r, c - 1, sum);
    }
}
