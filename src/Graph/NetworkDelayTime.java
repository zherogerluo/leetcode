/**
 * LeetCode #743, medium
 *
 * There are N network nodes, labelled 1 to N.
 *
 * Given times, a list of travel times as directed edges times[i] = (u, v, w), where u is the source node, v is the
 * target node, and w is the time it takes for a signal to travel from source to target.
 *
 * Now, we send a signal from a certain node K. How long will it take for all nodes to receive the signal? If it is
 * impossible, return -1.
 *
 * Note:
 * N will be in the range [1, 100].
 * K will be in the range [1, N].
 * The length of times will be in the range [1, 6000].
 * All edges times[i] = (u, v, w) will have 1 <= u, v <= N and 1 <= w <= 100.
 */

package Graph;

import java.util.*;

public class NetworkDelayTime {
    /**
     * Solution 1: Bellman-Ford
     *
     * This problem is classic shortest paths of single source to all vertices. Bellman-Ford is an easy-to-code
     * algorithm for this. The idea is to loop through all edges and update the shortest paths, which is like walking
     * from the source step-by-step (similar to BFS), and do this for at most V-1 steps (worst case, vertices form a
     * chain). One optimization is to break out the loop if no shortest path has been updated.
     *
     * Time complexity: O(V * E). Space complexity: O(V).
     */
    class Solution1 {
        public int networkDelayTime(int[][] times, int N, int K) {
            int[] dist = new int[N+1];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[K] = 0;
            dist[0] = 0; // zeroth node will not be used, set to zero to avoid bug in later max search
            for (int i = 1; i < N; i++) {
                boolean changed = false;
                for (int[] time : times) {
                    int source = time[0], target = time[1], weight = time[2];
                    if (dist[source] != Integer.MAX_VALUE && dist[target] > dist[source] + weight) {
                        dist[target] = dist[source] + weight;
                        changed = true;
                    }
                }
                if (!changed) break;
            }
            int max = 0;
            for (int d : dist) {
                if (d == Integer.MAX_VALUE) return -1;
                max = Math.max(max, d);
            }
            return max;
        }
    }

    /**
     * Solution 2: Dijkstra's algorithm
     *
     * Heap-based Dijkstra's. Instead of using a modifiable heap, just abandon heap nodes with longer distance than
     * recorded (outdated entry). There are few tricky lines that can easily be mistaken, see comments below.
     *
     * Time complexity: O((E + V) * log(V)). Space complexity: O(E + V).
     */
    class Solution2 {
        public int networkDelayTime(int[][] times, int N, int K) {
            List<int[]>[] graph = new List[N+1]; // list of edges for node i
            for (int[] time : times) {
                if (graph[time[0]] == null) graph[time[0]] = new ArrayList<>();
                graph[time[0]].add(time);
            }
            int[] dist = new int[N+1];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[0] = 0; // don't set dist[K] to zero, otherwise won't populate its neighbors!
            Queue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]); // [node, distance]
            heap.offer(new int[]{K, 0});
            while (!heap.isEmpty()) {
                int[] pair = heap.poll();
                int curNode = pair[0], curDist = pair[1];
                if (dist[curNode] <= curDist) continue; // skip those outdated entries in heap
                dist[curNode] = curDist;
                if (graph[curNode] == null) continue; // necessary for leaf nodes!
                for (int[] edge : graph[curNode]) {
                    int next = edge[1], weight = edge[2];
                    heap.offer(new int[]{next, curDist + weight});
                }
            }
            int max = 0;
            for (int d : dist) {
                if (d == Integer.MAX_VALUE) return -1;
                max = Math.max(max, d);
            }
            return max;
        }
    }
}
