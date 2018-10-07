/**
 * LeetCode #685, hard
 *
 * In this problem, a rooted tree is a directed graph such that, there is exactly one node (the root) for which all
 * other nodes are descendants of this node, plus every node has exactly one parent, except for the root node which
 * has no parents.
 *
 * The given input is a directed graph that started as a rooted tree with N nodes (with distinct values 1, 2, ..., N)
 * , with one additional directed edge added. The added edge has two different vertices chosen from 1 to N, and was
 * not an edge that already existed.
 *
 * The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] that represents a
 * directed edge connecting nodes u and v, where u is a parent of child v.
 *
 * Return an edge that can be removed so that the resulting graph is a rooted tree of N nodes. If there are multiple
 * answers, return the answer that occurs last in the given 2D-array.
 *
 * Example 1:
 * Input: [[1,2], [1,3], [2,3]]
 * Output: [2,3]
 * Explanation: The given directed graph will be like this:
 *   1
 *  / \
 * v   v
 * 2-->3
 *
 * Example 2:
 * Input: [[1,2], [2,3], [3,4], [4,1], [1,5]]
 * Output: [4,1]
 * Explanation: The given directed graph will be like this:
 * 5 <- 1 -> 2
 *      ^    |
 *      |    v
 *      4 <- 3
 * Note:
 * The size of the input 2D-array will be between 3 and 1000.
 * Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.
 */

package Graph;

import java.util.*;

public class RedundantConnectionTwo {
    /**
     * Solution 1: Union-Find
     *
     * There are only two possible scenarios: One node has two parents ("v" edges), or the graph has a loop. We iterate
     * through all edges, if we see two edges point towards same node, mark them as candidates, and invalidate the last
     * edge. During the loop we can also search for loops using union-find. After the edge invalidation, if no loop
     * can be found (valid tree), then the answer is clearly the other candidate; Otherwise the answer must be the
     * first candidate because the problem defines that by removing only one edge we can obtain a valid tree. If none
     * of the above (no candidates but has loop), we just remove the last edge that forms the loop.
     *
     * Note that the union-find here cannot utilize path compression because it stores the index of incoming edge,
     * not directly parents. Hence, find() here takes average O(log(V)). Alternatively we can use another array that
     * does general union-find with path compression, which requires another loop but reduces overall time cost to O(E).
     *
     * Time complexity: O(E * log(V)). Space complexity: O(V).
     */
    class Solution1 {
        public int[] findRedundantDirectedConnection(int[][] edges) {
            final int n = edges.length;
            int[] in = new int[n+1];
            Arrays.fill(in, -1);
            int[] vEdges = new int[]{-1, -1};
            int loopEntry = -1;
            // search for edge that results in certain node having two parents ("v" edges),
            // AND search for loops using union-find
            for (int i = 0; i < n; i++) {
                int n1 = edges[i][0], n2 = edges[i][1];
                if (in[n2] != -1) {
                    vEdges[0] = in[n2];
                    vEdges[1] = i;
                    continue; // skip (invalidate) this edge
                }
                if (loopEntry == -1) {
                    int p1 = find(n1, in, edges), p2 = find(n2, in, edges);
                    if (p1 == p2) loopEntry = n2;
                }
                // union in the last step
                in[n2] = i;
            }

            if (loopEntry == -1) return edges[vEdges[1]]; // no loop;
            if (vEdges[0] == -1) return edges[in[loopEntry]]; // no "v" edges
            // with second "v" edge invalidated, there is still loop, which must contain the first "v" edge
            return edges[vEdges[0]];
        }

        private int find(int i, int[] in, int[][] edges) {
            while (in[i] != -1) i = edges[in[i]][0];
            return i;
        }
    }
}
