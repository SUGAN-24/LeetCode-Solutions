import java.util.*;
class Solution {
    static class Node {
        int value;
        int count;
        Node(int value, int count) {
            this.value = value;
            this.count = count;
        }
    }
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        long ans = 0;
        long cost = 0;
        Deque<Node> dq = new ArrayDeque<>();
        int j = n - 1;
        for (int i = n - 1; i >= 0; i--) {
            int num = nums[i];
            int count = 1;
            while (!dq.isEmpty() && dq.peekLast().value < num) {
                Node node = dq.pollLast();
                count += node.count;
                cost += (long) (num - node.value) * node.count;
            }
            dq.offerLast(new Node(num, count));
            while (cost > k) {
                Node node = dq.pollFirst();
                cost -= (long) (node.value - nums[j]);
                j--;
                if (node.count > 1) {
                    dq.offerFirst(
                        new Node(node.value, node.count - 1)
                    );
                }
            }
            ans += j - i + 1;
        }
        return ans;
    }
}
