/**
 * LeetCode #684, medium
 *
 * In this problem, a tree is an undirected graph that is connected and has no cycles.
 *
 * The given input is a graph that started as a tree with N nodes (with distinct values 1, 2, ..., N), with one
 * additional edge added. The added edge has two different vertices chosen from 1 to N, and was not an edge that
 * already existed.
 *
 * The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] with u < v, that
 * represents an undirected edge connecting nodes u and v.
 *
 * Return an edge that can be removed so that the resulting graph is a tree of N nodes. If there are multiple
 * answers, return the answer that occurs last in the given 2D-array. The answer edge [u, v] should be in the same
 * format, with u < v.
 *
 * Example 1:
 * Input: [[1,2], [1,3], [2,3]]
 * Output: [2,3]
 * Explanation: The given undirected graph will be like this:
 *   1
 *  / \
 * 2 - 3
 * Example 2:
 * Input: [[1,2], [2,3], [3,4], [1,4], [1,5]]
 * Output: [1,4]
 * Explanation: The given undirected graph will be like this:
 * 5 - 1 - 2
 *     |   |
 *     4 - 3
 * Note:
 * The size of the input 2D-array will be between 3 and 1000.
 * Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.
 */

package Graph;

import java.util.*;

public class RedundantConnection {
    /**
     * Solution 1: DFS
     *
     * Use DFS to detect cycles, then find the right edge in this cycle. The helper DFS method will return max index
     * of such edge, with the help of multiple flags, including cycleEntry which marks the node of cycle entry, and
     * boolean closed which marks whether we have left the cycle or not. Not an elegant solution.
     */
    class Solution1 {
        public int[] findRedundantConnection(int[][] edges) {
            Map<Integer, List<Integer>> graph = new HashMap<>(); // node : list of indexes of edges
            for (int i = 0; i < edges.length; i++) {
                int[] edge = edges[i];
                if (!graph.containsKey(edge[0])) graph.put(edge[0], new ArrayList<>());
                if (!graph.containsKey(edge[1])) graph.put(edge[1], new ArrayList<>());
                graph.get(edge[0]).add(i);
                graph.get(edge[1]).add(i);
            }
            int i = detectCycle(graph, edges, -1, edges[0][0], new HashSet<>(), new int[1], new boolean[1]);
            if (i != -1) return edges[i];
            return null;
        }

        /* Returns max index of cycle edge, or -1 if no cycle is found */
        private int detectCycle(Map<Integer, List<Integer>> graph, int[][] edges, int last, int cur,
                                Set<Integer> visited, int[] cycleEntry, boolean[] closed) {
            if (visited.contains(cur)) {
                cycleEntry[0] = cur; // marks the entry node
                return last;
            }
            visited.add(cur);
            for (int i : graph.get(cur)) {
                if (i != last) {
                    int neighbor = edges[i][0] == cur ? edges[i][1] : edges[i][0];
                    int result = detectCycle(graph, edges, i, neighbor, visited, cycleEntry, closed);
                    if (result != -1) {
                        if (closed[0]) return result; // if closed, don't update edge index any more
                        else {
                            if (cur == cycleEntry[0]) closed[0] = true; // reached entry node, mark it
                            return Math.max(i, result);
                        }
                    }
                }
            }
            return -1;
        }
    }

    /**
     * Solution 2: DFS
     *
     * Same idea of cycle detection. Use array instead of map for the graph. Instead of using flags, use a "to" array
     * to record the edge that leads to node i, which is common technique in graph traversal. Once a cycle is
     * detected and an entry point has been identified, we can easily follow the cycle using the recorded paths until
     * reached back to the entry point, and find the max index of edge along the way.
     */
    class Solution2 {
        public int[] findRedundantConnection(int[][] edges) {
            // note the dimension of all arrays are n+1, because there are n nodes per the problem definition.
            final int n = edges.length;
            List<Integer>[] graph = new List[n+1]; // node : list of edge indexes
            for (int i = 0; i < n; i++) {
                if (graph[edges[i][0]] == null) graph[edges[i][0]] = new ArrayList<>();
                if (graph[edges[i][1]] == null) graph[edges[i][1]] = new ArrayList<>();
                graph[edges[i][0]].add(i);
                graph[edges[i][1]].add(i);
            }
            int[] to = new int[n+1];
            int entry = dfs(graph, edges, -1, 1, to, new boolean[n+1]);
            int next = entry, max = -1;
            // follow the cycle path and update max edge index
            while (next != entry || max == -1) {
                max = Math.max(max, to[next]);
                int[] edge = edges[to[next]];
                next = edge[0] == next ? edge[1] : edge[0];
            }
            return edges[max];
        }

        /* Performs DFS and returns the entry point of a cycle, or -1 if no cycles */
        private int dfs(List<Integer>[] graph, int[][] edges, int lastEdgeIndex, int node,
                        int[] to, boolean[] visited) {
            if (visited[node]) return node;
            visited[node] = true;
            for (int i : graph[node]) {
                if (i != lastEdgeIndex) { // avoid going back to previous node (2-node cycle)
                    int neighbor = edges[i][0] == node ? edges[i][1] : edges[i][0];
                    to[neighbor] = i;
                    int entry = dfs(graph, edges, i, neighbor, to, visited);
                    if (entry != -1) return entry;
                }
            }
            return -1;
        }
    }

    /**
     * Solution 3: Simple union-find
     *
     * Use a disjoint set to group nodes, edge-by-edge. If two nodes in one edge belongs to same set, it means that
     * this edge will close a cycle, hence we can just return the edge. Otherwise, union the two nodes.
     *
     * In this disjoint set implementation, find takes O(log(n)) time average but O(n) worst case, and union takes O
     * (1) time. Overall time complexity is O(n * log(n)) average.
     */
    class Solution3 {
        public int[] findRedundantConnection(int[][] edges) {
            int[] parent = new int[edges.length+1];
            for (int[] edge : edges) {
                int p = edge[0], q = edge[1];
                int pParent = find(p, parent), qParent = find(q, parent);
                if (pParent == qParent) return edge;
                parent[pParent] = qParent;
            }
            return new int[2];
        }

        private int find(int node, int[] parent) {
            while (parent[node] != 0) node = parent[node];
            return node;
        }
    }

    /**
     * Solution 4: Weighted union-find with path compression
     *
     * Use weight and path compression to flatten tree and improve run time.
     *
     * Time complexity: O(n * lg*(n)) = O(n). Space complexity: O(n).
     */
    class Solution4 {
        public int[] findRedundantConnection(int[][] edges) {
            DisjointSet uf = new DisjointSet(edges.length+1);
            for (int[] edge : edges) {
                int p = edge[0], q = edge[1];
                if (!uf.union(p, q)) return edge;
            }
            return new int[]{};
        }

        class DisjointSet {
            int[] parent;
            int[] size;

            DisjointSet(int n) {
                parent = new int[n];
                size = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                    size[i] = 1;
                }
            }

            /* Returns the parent of given node */
            int find(int node) {
                if (parent[node] != node) parent[node] = find(parent[node]); // recursive path compression
                return parent[node];
            }

            /* Joins the given two nodes, returns true if they can be joined, false otherwise */
            boolean union(int n1, int n2) {
                int p1 = find(n1), p2 = find(n2);
                if (p1 == p2) return false;
                // append smaller tree to larger tree
                if (size[p1] < size[p2]) {
                    parent[p1] = p2;
                    size[p1] += size[p2];
                } else {
                    parent[p2] = p1;
                    size[p2] += size[p1];
                }
                return true;
            }
        }
    }
}
