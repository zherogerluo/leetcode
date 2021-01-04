/**
 * LeetCode #1489, hard
 *
 * Given a weighted undirected connected graph with n vertices numbered from 0 to n - 1, and an array edges where
 * edges[i] = [a_i, b_i, weight_i] represents a bidirectional and weighted edge between nodes ai and bi. A minimum
 * spanning tree (MST) is a subset of the graph's edges that connects all vertices without cycles and with the minimum
 * possible total edge weight.
 *
 * Find all the critical and pseudo-critical edges in the given graph's minimum spanning tree (MST). An MST edge whose
 * deletion from the graph would cause the MST weight to increase is called a critical edge. On the other hand, a
 * pseudo-critical edge is that which can appear in some MSTs but not all.
 *
 * Note that you can return the indices of the edges in any order.
 *
 * Example 1: <i, weight>
 *           2
 *        /     \
 *    <1,1>      <2,2>
 *   /               \
 * 1 -<0,1>- 0 -<3,2>- 3
 *   \       |       /
 *  <6,6>  <4,3>  <5,3>
 *       \   |   /
 *           4
 * Input: n = 5, edges = [[0,1,1],[1,2,1],[2,3,2],[0,3,2],[0,4,3],[3,4,3],[1,4,6]]
 * Output: [[0,1],[2,3,4,5]]
 * Explanation: The figure above describes the graph.
 * The following figure shows all the possible MSTs:
 *           2                          2                          2                          2
 *        /     \                    /     \                    /                          /
 *    <1,1>      <2,2>           <1,1>      <2,2>           <1,1>                      <1,1>
 *   /               \          /               \          /                          /
 * 1 -<0,1>- 0         3      1 -<0,1>- 0         3      1 -<0,1>- 0 -<3,2>- 3      1 -<0,1>- 0 -<3,2>- 3
 *                   /                  |                                  /                  |
 *                <5,3>               <4,3>                             <5,3>               <4,3>
 *               /                      |                              /                      |
 *           4                          4                          4                          4
 * Notice that the two edges 0 and 1 appear in all MSTs, therefore they are critical edges, so we return them in the
 * first list of the output.
 * The edges 2, 3, 4, and 5 are only part of some MSTs, therefore they are considered pseudo-critical edges. We add
 * them to the second list of the output.
 *
 * Example 2: <i, weight>
 *        1
 *      /    \
 *   <0,1>   <1,1>
 *  /           \
 * 0             2
 *  \           /
 * <3,1>    <2,1>
 *     \    /
 *       3
 * Input: n = 4, edges = [[0,1,1],[1,2,1],[2,3,1],[0,3,1]]
 * Output: [[],[0,1,2,3]]
 * Explanation: We can observe that since all 4 edges have equal weight, choosing any 3 edges from the given 4 will yield an MST. Therefore all 4 edges are pseudo-critical.
 *
 * Constraints:
 *
 * 2 <= n <= 100
 * 1 <= edges.length <= min(200, n * (n - 1) / 2)
 * edges[i].length == 3
 * 0 <= ai < bi < n
 * 1 <= weighti <= 1000
 * All pairs (ai, bi) are distinct.
 */

package Graph;

import java.util.*;

public class FindCriticalAndPseudoCriticalEdgesInMinimumSpanningTree {
    /**
     * Solution 1: Kruskal's algorithm and cycle traversal
     *
     * To solve this problem, we have to know how to find a Minimum Spanning Tree (MST). Kruskal's algorithm uses
     * greedy approach to pick least weighted edges to build a MST. Sort edges by weight, start from one with minimum
     * weight and add to MST if it does not create a cycle, until we are done with all edges. To detect cycle, the most
     * efficient way is to use a disjoint set. Try to join the edge points, if they belong to the same set (have same
     * parent), we know there is a cycle.
     *
     * Within the cycle, if any of the edge has same weight as the new edge that could create the cycle, they should
     * all be pseudo-critical, because we could have chosen any of them first to build the MST based on the workflow of
     * Kruskal's algorithm. Otherwise, any edge in the MST should be critical.
     *
     * Another way to determine critical-ness is to build MSTs with and without each edge, to see if the total weight
     * becomes greater.
     *
     * There is also Prim's algorithm which instead looks at vertices and uses a priority queue to decide which vertex
     * to add to MST next.
     *
     * Time complexity: O(E * logE) limited by edge sorting. Space complexity: O(E).
     */
    class Solution1 {
        public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
            Integer[] indices = new Integer[edges.length];
            for (int i = 0; i < indices.length; ++i) {
                indices[i] = i;
            }
            // sort indices, not edges
            Arrays.sort(indices, Comparator.comparingInt(i -> edges[i][2]));
            // prepare disjoint set initial values
            int[] parent = new int[n];
            for (int i = 0; i < n; ++i) {
                parent[i] = i;
            }
            Set<Integer> critical = new HashSet<>(), pseudo = new HashSet<>();
            List<Integer>[] connection = new ArrayList[n];
            for (int k = 0; k < indices.length; ++k) {
                int i = indices[k];
                int[] edge = edges[i];
                boolean joined = join(parent, edge[0], edge[1]);
                if (!joined) {
                    // found a cycle, all the forming edges with same weight are pseudo critical
                    // follow the cycle to determine if any edge should be pseudo critical
                    follow(edges, i, edge[0], edge[1], connection, edge[2], new boolean[n], critical, pseudo);
                } else {
                    // any MST edge should be first considered critical
                    critical.add(i);
                    // remember the bidirectional vertices connections for cycle traversal
                    if (connection[edge[0]] == null) {
                        connection[edge[0]] = new ArrayList<>();
                    }
                    connection[edge[0]].add(i);
                    if (connection[edge[1]] == null) {
                        connection[edge[1]] = new ArrayList<>();
                    }
                    connection[edge[1]].add(i);
                }
            }
            List<List<Integer>> res = new ArrayList<>();
            res.add(new ArrayList<>(critical));
            res.add(new ArrayList<>(pseudo));
            return res;
        }

        // DFS to follow cycle from start to end, convert critical to pseudo along the way if weight matches
        private boolean follow(int[][] edges, int i, int start, int end, List<Integer>[] connection, int weight,
                               boolean[] visited, Set<Integer> critical, Set<Integer> pseudo) {
            if (start == end) {
                return true;
            }
            if (visited[start]) {
                return false;
            }
            visited[start] = true;
            boolean found = false, returnVal = false;
            for (int index : connection[start]) {
                int next = edges[index][0] + edges[index][1] - start;
                if (follow(edges, i, next, end, connection, weight, visited, critical, pseudo)) {
                    // any same weighted edge within the cycle should be pseudo critical
                    if (weight == edges[index][2]) {
                        critical.remove(index);
                        pseudo.add(index);
                        found = true;
                    }
                    returnVal =  true;
                    break;
                }
            }
            // add the cycle-inducing edge to pseudo too but only if any weight matches, otherwise i is not part of MST
            if (found) {
                pseudo.add(i);
            }
            return returnVal;
        }

        // union find with path compression
        private int find(int[] parent, int i) {
            if (parent[i] == i) {
                return i;
            }
            int p = find(parent, parent[i]);
            if (parent[i] != p) {
                parent[i] = p;
            }
            return p;
        }

        // returns true if joined, false if not (have same parent)
        private boolean join(int[] parent, int i, int j) {
            int p1 = find(parent, i), p2 = find(parent, j);
            if (p1 == p2) {
                return false;
            }
            parent[p2] = p1;
            return true;
        }
    }
}
