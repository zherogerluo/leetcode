/**
 * LeetCode #802, medium
 *
 * In a directed graph, we start at some node and every turn, walk along a directed edge of the graph.  If we reach a
 * node that is terminal (that is, it has no outgoing directed edges), we stop.
 *
 * Now, say our starting node is eventually safe if and only if we must eventually walk to a terminal node.  More
 * specifically, there exists a natural number K so that for any choice of where to walk, we must have stopped at a
 * terminal node in less than K steps.
 *
 * Which nodes are eventually safe?  Return them as an array in sorted order.
 *
 * The directed graph has N nodes with labels 0, 1, ..., N-1, where N is the length of graph.  The graph is given in
 * the following form: graph[i] is a list of labels j such that (i, j) is a directed edge of the graph.
 *
 * Example:
 * Input: graph = [[1,2],[2,3],[5],[0],[5],[],[]]
 * Output: [2,4,5,6]
 * Here is a diagram of the above graph.
 *
 * Note:
 *
 * graph will have length at most 10000.
 * The number of edges in the graph will not exceed 32000.
 * Each graph[i] will be a sorted list of different integers, chosen within the range [0, graph.length - 1].
 */

package Graph;

import java.util.*;

public class FindEventualSafeStates {
    /**
     * Solution 1: DFS
     *
     * This solution is essentially cycle detection. We mark visited nodes as we DFS, if any marked nodes are reached
     * again, it creates a loop and all nodes along the entire path are not eventual safe. To improve performance, we
     * memoize results for visited nodes.
     *
     * Time complexity: O(E + V). Space complexity: O(V).
     */
    class Solution1 {
        public List<Integer> eventualSafeNodes(int[][] graph) {
            List<Integer> result = new ArrayList<>();
            int[] safe = new int[graph.length];
            boolean[] marked = new boolean[graph.length];
            for (int i = 0; i < graph.length; i++) {
                if (isEventualSafe(graph, i, marked, safe)) result.add(i);
            }
            return result;
        }

        private boolean isEventualSafe(int[][] graph, int node, boolean[] marked, int[] safe) {
            if (graph[node].length == 0) return true;
            if (safe[node] != 0) return safe[node] == 1;
            if (marked[node]) return false;
            marked[node] = true;
            for (int next : graph[node]) {
                if (!isEventualSafe(graph, next, marked, safe)) return false;
            }
            marked[node] = false;
            safe[node] = 1;
            return true;
        }
    }
}
