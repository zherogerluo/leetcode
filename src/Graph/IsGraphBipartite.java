/**
 * LeetCode #785, medium
 *
 * Given an undirected graph, return true if and only if it is bipartite.
 *
 * Recall that a graph is bipartite if we can split it's set of nodes into two independent subsets A and B such that
 * every edge in the graph has one node in A and another node in B.
 *
 * The graph is given in the following form: graph[i] is a list of indexes j for which the edge between nodes i and j
 * exists.  Each node is an integer between 0 and graph.length - 1.  There are no self edges or parallel edges:
 * graph[i] does not contain i, and it doesn't contain any element twice.
 *
 * Example 1:
 * Input: [[1,3], [0,2], [1,3], [0,2]]
 * Output: true
 * Explanation:
 * The graph looks like this:
 * 0----1
 * |    |
 * |    |
 * 3----2
 * We can divide the vertices into two groups: {0, 2} and {1, 3}.
 *
 * Example 2:
 * Input: [[1,2,3], [0,2], [0,1,3], [0,2]]
 * Output: false
 * Explanation:
 * The graph looks like this:
 * 0----1
 * | \  |
 * |  \ |
 * 3----2
 * We cannot find a way to divide the set of nodes into two independent subsets.
 *
 * Note:
 *
 * graph will have length in range [1, 100].
 * graph[i] will contain integers in range [0, graph.length - 1].
 * graph[i] will not contain i or duplicate values.
 * The graph is undirected: if any element j is in graph[i], then i will be in graph[j].
 */

package Graph;

import java.util.*;

public class IsGraphBipartite {
    /**
     * Solution 1: BFS
     *
     * Start from an unmarked node, mark it 1, then do BFS and mark each level the negative sign of last level. If
     * any neighbor is found not marked correctly (for the already visited node), return false, otherwise proceed
     * (correctly marked) or add to queue (not marked). Flip sign of mark flag every level.
     *
     * Note that the graph may not be connected (e.g. separated to several clusters), so the BFS must be embedded
     * into the outer loop.
     *
     * Time complexity: O(E + V). Space complexity: O(V).
     */
    class Solution1 {
        public boolean isBipartite(int[][] graph) {
            int flag = 1;
            int[] flags = new int[graph.length];
            Queue<Integer> queue = new LinkedList<>();
            for (int i = 0; i < graph.length; i++) {
                if (flags[i] != 0 || graph[i].length == 0) continue;
                // add any unvisited node to the queue as the starting point
                queue.add(i);
                flags[i] = flag;
                // BFS loop embedded
                while (!queue.isEmpty()) {
                    flag = -flag;
                    int size = queue.size();
                    for (int j = 0; j < size; j++) { // level by level
                        int node = queue.poll();
                        for (int neighbor : graph[node]) {
                            if (flags[neighbor] == -flag) return false;
                            if (flags[neighbor] == 0) {
                                flags[neighbor] = flag;
                                queue.offer(neighbor);
                            }
                        }
                    }
                }
            }
            return true;
        }
    }

    /**
     * Solution 2: BFS
     *
     * Similar to Solution 1, but removed the flag. We don't need the flag to keep track what flag we need to assign
     * to this level. Instead, for every node, we just check it against the neighbors making sure they are not the
     * same sign, and assign a different sign to those not visited. No need to strictly visit nodes level by level.
     *
     * Time complexity: O(E + V). Space complexity: O(V).
     */
    class Solution2 {
        public boolean isBipartite(int[][] graph) {
            int[] flags = new int[graph.length];
            Queue<Integer> queue = new LinkedList<>();
            for (int i = 0; i < graph.length; i++) {
                if (flags[i] != 0 || graph[i].length == 0) continue;
                queue.add(i);
                flags[i] = 1;
                while (!queue.isEmpty()) {
                    int node = queue.poll();
                    for (int neighbor : graph[node]) {
                        if (flags[neighbor] == flags[node]) return false; // same sign, not bipartite
                        if (flags[neighbor] == 0) {
                            flags[neighbor] = -flags[node]; // just assign different flag to the neighbor nodes
                            queue.offer(neighbor);
                        }
                    }
                }
            }
            return true;
        }
    }

    /**
     * Solution 3: DFS
     *
     * Recursively mark nodes using DFS. If not marked, mark as 1. For every neighbor, check the mark, if same,
     * return false. For those unmarked neighbor, mark as flipped sign and continue with the DFS process.
     *
     * Time complexity: O(E + V). Space complexity: O(V).
     */
    class Solution3 {
        public boolean isBipartite(int[][] graph) {
            int[] flags = new int[graph.length];
            for (int i = 0; i < graph.length; i++) {
                if (!dfs(graph, i, flags)) return false;
            }
            return true;
        }

        /* Mark nodes using DFS method, return true if bipartite, or false otherwise */
        private boolean dfs(int[][] graph, int node, int[] flags) {
            if (flags[node] == 0) flags[node] = 1;
            for (int neighbor : graph[node]) {
                if (flags[neighbor] == flags[node]) return false;
                // only continue DFS if the neighbor is unmarked
                if (flags[neighbor] == 0) {
                    flags[neighbor] = -flags[node];
                    if (!dfs(graph, neighbor, flags)) return false;
                }
            }
            return true;
        }
    }

    /**
     * Solution 4: Union-Find
     *
     * For every node, union the neighbors. If node and any neighbor belong to same set, return false. This method
     * strictly follows the definition of bipartite. In practice, union-find can be improved by path compression and
     * weighted concatenation.
     *
     * Time complexity: O(E + V). Space complexity: O(V).
     */
    class Solution4 {
        public boolean isBipartite(int[][] graph) {
            final int n = graph.length;
            int[] parent = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
            for (int i = 0; i < n; i++) {
                for (int j : graph[i]) {
                    if (find(i, parent) == find(j, parent)) return false;
                }
                union(graph[i], parent); // union the neighbors
            }
            return true;
        }

        private int find(int i, int[] parent) {
            if (parent[i] != i) parent[i] = find(parent[i], parent);
            return parent[i];
        }

        /* Union the entire array of nodes */
        private void union(int[] nodes, int[] parent) {
            for (int i = 1; i < nodes.length; i++) {
                parent[find(nodes[i], parent)] = find(nodes[i-1], parent);
            }
        }
    }
}
