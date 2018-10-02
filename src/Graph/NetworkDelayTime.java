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
            dist[0] = 0;
            for (int d : dist) {
                if (d == Integer.MAX_VALUE) return -1;
                max = Math.max(max, d);
            }
            return max;
        }
    }
}
