/**
 * LeetCode #787, medium
 *
 * There are n cities connected by m flights. Each fight starts from city u and arrives at v with a price w.
 *
 * Now given all the cities and fights, together with starting city src and the destination dst, your task is to find
 * the cheapest price from src to dst with up to k stops. If there is no such route, output -1.
 *
 * Example 1:
 * Input:
 * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 * src = 0, dst = 2, k = 1
 * Output: 200
 * Explanation:
 * The graph looks like this:
 *        0
 *      /   \
 *   (100) (500)
 *   /        \
 *  v          v
 * 1---(100)-->2
 *
 * The cheapest price from city 0 to city 2 with at most 1 stop costs 200, as marked red in the picture.
 *
 * Example 2:
 * Input:
 * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 * src = 0, dst = 2, k = 0
 * Output: 500
 * Explanation:
 * The graph looks like this: (same as above)
 * The cheapest price from city 0 to city 2 with at most 0 stop costs 500, as marked blue in the picture.
 *
 * Note:
 * The number of nodes n will be in range [1, 100], with nodes labeled from 0 to n - 1.
 * The size of flights will be in range [0, n * (n - 1) / 2].
 * The format of each flight will be (src, dst, price).
 * The price of each flight will be in the range [1, 10000].
 * k is in the range of [0, n - 1].
 * There will not be any duplicated flights or self cycles.
 */

package Graph;

import java.util.*;

public class CheapestFlightsWithinKStops {
    /**
     * Solution 1: DFS with memoization (top-down DP)
     *
     * Typical DFS, except that it returns if the stop count becomes larger than K, which comes down to an additional
     * termination clause. The memoization matrix memo[i][k] stores the cheapest price to reach node i in k stops,
     * where k ranges from 0 to K, so the memo size should be n * (K+1). One trick is to return MAX_VALUE when a
     * result cannot be found, and then convert it to -1.
     *
     * Time complexity: O(min(n, E) * K) where E is the total number of edges. Space complexity: O(n * K).
     */
    class Solution1 {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
            List<int[]>[] graph = new List[n];
            for (int[] flight : flights) {
                if (graph[flight[0]] == null) graph[flight[0]] = new ArrayList<>();
                graph[flight[0]].add(flight);
            }
            int[][] memo = new int[n][K+1]; // note the dimension here
            return cheapestPrice(graph, src, dst, K, memo);
        }

        private int cheapestPrice(List<int[]>[] graph, int src, int dst, int K, int[][] memo) {
            if (src == dst) return 0;
            if (K < 0 || graph[src] == null) return -1;
            if (memo[src][K] != 0) return memo[src][K];
            int price = Integer.MAX_VALUE;
            for (int[] flight : graph[src]) {
                int nextPrice = cheapestPrice(graph, flight[1], dst, K-1, memo);
                if (nextPrice >= 0) price = Math.min(price, nextPrice + flight[2]);
            }
            price = price == Integer.MAX_VALUE ? -1 : price;
            memo[src][K] = price;
            return price;
        }
    }

    /**
     * Solution 2: DFS with memoization and pruning
     *
     * Same as Solution 1 except adding pruning for the case when cost so far exceeds the current found cheapest.
     * Slightly improves performance. Memoization is a bigger deal.
     */
    class Solution2 {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
            List<int[]>[] graph = new List[n];
            for (int[] flight : flights) {
                if (graph[flight[0]] == null) graph[flight[0]] = new ArrayList<>();
                graph[flight[0]].add(flight);
            }
            int[][] memo = new int[n][K+1];
            int[] res = new int[]{Integer.MAX_VALUE};
            return cheapestPrice(graph, src, dst, K, 0, res, memo);
        }

        private int cheapestPrice(List<int[]>[] graph, int src, int dst, int K, int cost, int[] res, int[][] memo) {
            if (src == dst) {
                res[0] = Math.min(res[0], cost);
                return 0;
            }
            if (K < 0 || graph[src] == null || cost >= res[0]) return -1; // pruning the case where cost exceed res
            if (memo[src][K] != 0) return memo[src][K];
            int price = Integer.MAX_VALUE;
            for (int[] flight : graph[src]) {
                int nextPrice = cheapestPrice(graph, flight[1], dst, K-1, cost + flight[2], res, memo);
                if (nextPrice >= 0) price = Math.min(price, nextPrice + flight[2]);
            }
            price = price == Integer.MAX_VALUE ? -1 : price;
            memo[src][K] = price;
            return price;
        }
    }

    /**
     * Solution 3: BFS
     *
     * Use a queue of int[] to perform BFS, where each int[] is a pair of origin and cost-so-far. Another
     * implementation idea is to only keep origin in the queue and keep the cheapest price to reach node i in a
     * separate array. However if a temporary array is not used, there will be bug: In the kth loop, if node i gets
     * updated and later we reach node j from node i (i.e. there is a i->j edge), the cost of reaching j calculated
     * from cheapest[i] + flight_cost[i->j] is wrong because we are using cheapest[i] for k stops, not k-1 stops. To
     * resolve this issue, one straightforward idea is to instead use a temporary array copied from global array for
     * each loop. Another way is to build a n * (K+1) dimension cheapest array, just like Solution 1 and 2.
     *
     * Time complexity: O(E * K) where E is the total number of edges. Space complexity: O(E).
     */
    class Solution3 {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
            List<int[]>[] graph = new List[n];
            for (int[] flight : flights) {
                if (graph[flight[0]] == null) graph[flight[0]] = new ArrayList<>();
                graph[flight[0]].add(flight);
            }
            Queue<int[]> queue = new LinkedList<>(); // origin and cost-so-far
            queue.offer(new int[]{src, 0});
            int res = Integer.MAX_VALUE;
            for (; K >= 0 && !queue.isEmpty(); K--) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    int[] pair = queue.poll();
                    int cur = pair[0], cost = pair[1];
                    if (graph[cur] == null) continue;
                    for (int[] flight : graph[cur]) {
                        int next = flight[1], costForNext = cost + flight[2];
                        if (costForNext >= res) continue;
                        if (next == dst) res = Math.min(res, costForNext);
                        queue.offer(new int[]{next, costForNext});
                    }
                }
            }
            return res == Integer.MAX_VALUE ? -1 : res;
        }
    }

    /**
     * Solution 4: Dijkstra's algorithm
     *
     * Use a priority queue (heap) to prioritize nodes according to the cost-so-far, return the cost as soon as we
     * reach the destination. Typical Dijkstra's would require a heap that supports mutable priority, however for
     * this problem it is okay to use a Java priority queue as long as we also keep the stops-so-far information in
     * the heap. This way, the heap will probably contain duplicate nodes, but with different stops, which can be
     * filtered out by comparing it with K. It is not a strict classic shortest path problem.
     *
     * Use a boolean array to mark the visited nodes to improve performance. This is the standard practice for
     * Dijkstra's algorithm!
     *
     * Time complexity: O(E * K)?. Space complexity: O(E)?.
     */
    class Solution4 {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
            List<int[]>[] graph = new List[n];
            for (int[] flight : flights) {
                if (graph[flight[0]] == null) graph[flight[0]] = new ArrayList<>();
                graph[flight[0]].add(flight);
            }
            Queue<int[]> queue = new PriorityQueue<>((a, b) -> a[1] - b[1]); // {origin, cost-so-far, stops-so-far}
            queue.offer(new int[]{src, 0, -1});
            boolean[] visited = new boolean[n]; // don't forget to record visited nodes
            while(!queue.isEmpty()) {
                int[] tuple = queue.poll();
                int cur = tuple[0], cost = tuple[1], stops = tuple[2];
                if (stops > K) continue;
                if (cur == dst) return cost;
                if (graph[cur] == null) continue;
                visited[cur] = true; // mark the node
                for (int[] flight : graph[cur]) {
                    int next = flight[1], costForNext = cost + flight[2];
                    if (visited[next]) continue;
                    queue.offer(new int[]{next, costForNext, stops + 1});
                }
            }
            return -1;
        }
    }

    /**
     * Solution 5: Dynamic programming
     *
     * cheapest[k][n] is defined to be the cheapest price to reach destination n within k flights. This time, the
     * graph is built such that the key is destination instead of origin. For each k ranging from 1 to K+1,
     * cheapest[k][i] = min(cheapest[k-1][i], cheapest[k-1][j] + price from j to i for every j that has direct flight
     * to i), where this j can be looked up in the graph.
     *
     * One trick to improve performance is to check if the result for k is the same as result for k-1. If so, there
     * is no need to proceed to larger k.
     *
     * Time complexity: O(E * K) since we go through all edges for each k. Space complexity: O(n * K).
     */
    class Solution5 {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
            List<int[]>[] graph = new List[n];
            for (int[] flight : flights) {
                if (graph[flight[1]] == null) graph[flight[1]] = new ArrayList<>();
                graph[flight[1]].add(flight); // destination as key
            }
            int[][] cheapest = new int[K+2][n];
            Arrays.fill(cheapest[0], Integer.MAX_VALUE);
            cheapest[0][src] = 0;
            for (int k = 1; k <= K+1; k++) {
                Arrays.fill(cheapest[k], Integer.MAX_VALUE);
                boolean same = true;
                for (int to = 0; to < n; to++) {
                    if (graph[to] == null) continue;
                    int min = Integer.MAX_VALUE;
                    for (int[] flight : graph[to]) {
                        int from = flight[0], price = flight[2];
                        if (cheapest[k-1][from] == Integer.MAX_VALUE) continue;
                        min = Math.min(min, cheapest[k-1][from] + price);
                    }
                    cheapest[k][to] = Math.min(cheapest[k-1][to], min); // need to look at last result as well
                    // check if results are the same as those of last k
                    if (cheapest[k][to] != cheapest[k-1][to]) same = false;
                }
                if (same) return cheapest[k][dst] == Integer.MAX_VALUE ? -1 : cheapest[k][dst];
            }
            return cheapest[K+1][dst] == Integer.MAX_VALUE ? -1 : cheapest[K+1][dst];
        }
    }

    /**
     * Solution 6: Dynamic programming, without building graph
     *
     * Instead of building graph and go through vertices, we just need to go through all edges and update result for
     * those destinations when doing DP. One useful trick is to copy k-1 results to k rather than initializing kth
     * row to Integer.MAX_VALUE, so that we save one min operation. Again, we can use a boolean to keep track of
     * whether results of k are the same as k-1.
     *
     * Time complexity: O(E * K) since we go through all edges for each k. Space complexity: O(n * K).
     */
    class Solution6 {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
            int[][] cheapest = new int[K+2][n];
            Arrays.fill(cheapest[0], Integer.MAX_VALUE);
            cheapest[0][src] = 0;
            for (int k = 1; k <= K+1; k++) {
                cheapest[k] = Arrays.copyOf(cheapest[k-1], n);
                boolean same = true;
                for (int[] flight : flights) {
                    int from = flight[0], to = flight[1], price = flight[2];
                    if (cheapest[k-1][from] == Integer.MAX_VALUE) continue;
                    cheapest[k][to] = Math.min(cheapest[k][to], cheapest[k-1][from] + price);
                    if (cheapest[k][to] != cheapest[k-1][to]) same = false;
                }
                if (same) return cheapest[k][dst] == Integer.MAX_VALUE ? -1 : cheapest[k][dst];
            }
            return cheapest[K+1][dst] == Integer.MAX_VALUE ? -1 : cheapest[K+1][dst];
        }
    }

    /**
     * Solution 7: Dynamic programming with two arrays
     *
     * We don't need to keep all O(K) rows. For results of kth row, we only need the (k-1)th row, so we can just use
     * a last and current array. This reduces space complexity to O(n).
     *
     * Note: The DP solutions and BFS here are essentially Bellman-Ford algorithm, which solves shortest path problem
     * stop by stop. Bellman-Ford works for negative weighted graphs, but is slower than Dijkstra's.
     *
     * Time complexity: O(E * K). Space complexity: O(n).
     */
    class Solution7 {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
            int[] last = new int[n];
            Arrays.fill(last, Integer.MAX_VALUE);
            last[src] = 0;
            for (int k = 1; k < K+2; k++) {
                int cur[] = Arrays.copyOf(last, n);
                boolean same = true;
                for (int[] flight : flights) {
                    int from = flight[0], to = flight[1], price = flight[2];
                    if (last[from] == Integer.MAX_VALUE) continue;
                    cur[to] = Math.min(cur[to], last[from] + price);
                    if (cur[to] != last[to]) same = false;
                }
                last = cur;
                if (same) break;
            }
            return last[dst] == Integer.MAX_VALUE ? -1 : last[dst];
        }
    }
}
