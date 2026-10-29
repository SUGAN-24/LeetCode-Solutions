import java.util.*;
class Solution {
    public List<Boolean> palindromePath(int n, int[][] edges, String s,
                                        String[] queries) {
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }
        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;
        int[][] up = new int[LOG][n];
        int[] depth = new int[n];
        int[] tin = new int[n];
        int[] tout = new int[n];
        int[] prefix = new int[n];
        char[] chars = s.toCharArray();
        int[] parent = new int[n];
        int[] iter = new int[n];
        int[] stack = new int[n];
        Arrays.fill(parent, -1);
        int top = 0, timer = 0;
        stack[0] = 0;
        parent[0] = 0;
        tin[0] = timer++;
        prefix[0] = 1 << (chars[0] - 'a');
        while (top >= 0) {
            int u = stack[top];
            if (iter[u] < adj[u].size()) {
                int v = adj[u].get(iter[u]++);
                if (v == parent[u]) continue;
                parent[v] = u;
                depth[v] = depth[u] + 1;
                up[0][v] = u;
                for (int k = 1; k < LOG; k++) {
                    up[k][v] = up[k - 1][up[k - 1][v]];
                }
                prefix[v] = prefix[u] ^ (1 << (chars[v] - 'a'));
                tin[v] = timer++;
                stack[++top] = v;
            } else {
                tout[u] = timer - 1;
                top--;
            }
        }
        Fenwick bit = new Fenwick(n);
        List<Boolean> answer = new ArrayList<>();
        for (String q : queries) {
            String[] p = q.split("\\s+");
            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);
                if (chars[u] != c) {
                    int change = (1 << (chars[u] - 'a'))
                               ^ (1 << (c - 'a'));
                    bit.rangeXor(tin[u], tout[u], change);
                    chars[u] = c;
                }
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);
                int lca = getLCA(u, v, depth, up, LOG);
                int pu = prefix[u] ^ bit.pointQuery(tin[u]);
                int pv = prefix[v] ^ bit.pointQuery(tin[v]);
                int mask = pu ^ pv ^ (1 << (chars[lca] - 'a'));
                answer.add((mask & (mask - 1)) == 0);
            }
        }
        return answer;
    }
    private int getLCA(int u, int v, int[] depth,
                       int[][] up, int LOG) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        int diff = depth[u] - depth[v];
        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0) {
                u = up[k][u];
            }
        }
        if (u == v) return u;
        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }
        return up[0][u];
    }
    static class Fenwick {
        int[] tree;
        int n;
        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 2];
        }
        void update(int index, int value) {
            index++;
            while (index <= n) {
                tree[index] ^= value;
                index += index & -index;
            }
        }
        void rangeXor(int left, int right, int value) {
            update(left, value);
            update(right + 1, value);
        }
        int pointQuery(int index) {
            index++;
            int result = 0;
            while (index > 0) {
                result ^= tree[index];
                index -= index & -index;
            }
            return result;
        }
    }
}
