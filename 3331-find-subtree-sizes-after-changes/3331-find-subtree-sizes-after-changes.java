import java.util.*;
class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] children = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            children[parent[i]].add(i);
        }
        int[] newParent = new int[n];
        newParent[0] = -1;
        int[] last = new int[26];
        Arrays.fill(last, -1);
        int[] previous = new int[n];
        int[] stack = new int[2 * n];
        int top = 0;
        stack[top++] = 0;
        while (top > 0) {
            int node = stack[--top];
            if (node >= 0) {
                int ch = s.charAt(node) - 'a';
                previous[node] = last[ch];
                if (node != 0) {
                    if (last[ch] != -1) {
                        newParent[node] = last[ch];
                    } else {
                        newParent[node] = parent[node];
                    }
                }
                last[ch] = node;
                stack[top++] = ~node;
                for (int child : children[node]) {
                    stack[top++] = child;
                }
            } else {
                int nodeId = ~node;
                int ch = s.charAt(nodeId) - 'a';
                last[ch] = previous[nodeId];
            }
        }
        List<Integer>[] newChildren = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            newChildren[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            newChildren[newParent[i]].add(i);
        }
        int[] order = new int[n];
        top = 0;
        int count = 0;
        stack[top++] = 0;
        while (top > 0) {
            int node = stack[--top];
            order[count++] = node;
            for (int child : newChildren[node]) {
                stack[top++] = child;
            }
        }
        int[] answer = new int[n];
        Arrays.fill(answer, 1);
        for (int i = n - 1; i > 0; i--) {
            int node = order[i];
            answer[newParent[node]] += answer[node];
        }
        return answer;
    }
}