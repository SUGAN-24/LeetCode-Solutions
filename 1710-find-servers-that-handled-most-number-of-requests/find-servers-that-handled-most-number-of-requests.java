import java.util.*;
class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        TreeSet<Integer> available = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            available.add(i);
        }
        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );
        int[] count = new int[k];
        int max = 0;
        for (int i = 0; i < arrival.length; i++) {
            int time = arrival[i];
            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                int[] server = busy.poll();
                available.add(server[1]);
            }
            if (available.isEmpty()) {
                continue;
            }
            int index = i % k;
            Integer server = available.ceiling(index);
            if (server == null) {
                server = available.first();
            }
            available.remove(server);
            count[server]++;
            max = Math.max(max, count[server]);
            busy.offer(new int[]{
                time + load[i], server
            });
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (count[i] == max) {
                result.add(i);
            }
        }
        return result;
    }
}
