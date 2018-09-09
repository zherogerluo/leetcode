/**
 * LeetCode #310, medium
 *
 * For a undirected graph with tree characteristics, we can choose any node as the root. The result graph is then a
 * rooted tree. Among all possible rooted trees, those with minimum height are called minimum height trees (MHTs).
 * Given such a graph, write a function to find all the MHTs and return a list of their root labels.
 *
 * Format
 * The graph contains n nodes which are labeled from 0 to n - 1. You will be given the number n and a list of
 * undirected edges (each edge is a pair of labels).
 *
 * You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as
 * [1, 0] and thus will not appear together in edges.
 *
 * Example 1 :
 *
 * Input: n = 4, edges = [[1, 0], [1, 2], [1, 3]]
 *
 *         0
 *         |
 *         1
 *        / \
 *       2   3
 *
 * Output: [1]
 * Example 2 :
 *
 * Input: n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]
 *
 *      0  1  2
 *       \ | /
 *         3
 *         |
 *         4
 *         |
 *         5
 *
 * Output: [3, 4]
 * Note:
 *
 * According to the definition of tree on Wikipedia: “a tree is an undirected graph in which any two vertices are
 * connected by exactly one path. In other words, any connected graph without simple cycles is a tree.”
 * The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
 */

package Graph;

import java.util.*;

public class MinimumHeightTrees {
    /**
     * Solution 1: Brutal force
     *
     * For every node as root, traverse the entire graph and find the height. This solution receives TLE in LeetCode
     * OJ.
     *
     * Note that the graph does not need to store the actual edge. Instead we can store only the neighbor nodes. It
     * is because the edges have no weights.
     *
     * Time complexity: O(n^2). Space complexity: O(n).
     */
    class Solution1 {
        public List<Integer> findMinHeightTrees(int n, int[][] edges) {
            if (n == 1) return Arrays.asList(0);
            List<Integer>[] graph = new List[n];
            for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
            for (int[] edge : edges) {
                graph[edge[0]].add(edge[1]);
                graph[edge[1]].add(edge[0]);
            }

            List<Integer>[] mhts = new List[n];
            int mht = n;
            boolean[] visited = new boolean[n];
            for (int root = 0; root < n; root++) {
                int height = findHeight(root, graph, visited) - 1;
                if (height > mht) continue;
                mht = height;
                if (mhts[mht] == null) mhts[mht] = new ArrayList<>();
                mhts[mht].add(root);
            }
            return mhts[mht];
        }

        private int findHeight(int root, List<Integer>[] graph, boolean[] visited) {
            if (visited[root] || graph[root] == null) return 0;
            visited[root] = true;
            int height = 0;
            for (int neighbor : graph[root]) {
                height = Math.max(height, findHeight(neighbor, graph, visited));
            }
            visited[root] = false;
            return height + 1;
        }
    }

    /**
     * Solution 2: BFS
     *
     * The idea is to trim the leaves level by level using BFS. Start from leaf nodes, do BFS to find the nodes
     * connected to the leaves which are the next batch of potential leaves, then trim the current leaves. By doing
     * this, the problem reduces to finding the minimum height of the remaining nodes.
     *
     * One important thing to note is, the final results should be less than two nodes as root of MHTs. If there are
     * three nodes or more, they them selves must form trees (no cycles per the prerequisite) and therefore must
     * contain non-leaf nodes which allows for further trimming. So instead of using queue size as termination
     * indicator of BFS, we can use the total number of remaining nodes instead, just decrement n as we do BFS and
     * terminate if n goes to 2 or lower.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        public List<Integer> findMinHeightTrees(int n, int[][] edges) {
            if (n == 1) return Arrays.asList(0);
            Set<Integer>[] graph = new Set[n];
            for (int i = 0; i < n; i++) graph[i] = new HashSet<>();
            for (int[] edge : edges) {
                graph[edge[0]].add(edge[1]);
                graph[edge[1]].add(edge[0]);
            }
            // find all leaves nodes
            LinkedList<Integer> leaves = new LinkedList<>();
            for (int node = 0; node < n; node++) {
                if (graph[node].size() == 1) leaves.offer(node);
            }
            // trim leaves until empty
            while (n > 2) {
                int size = leaves.size();
                n -= size;
                for (int i = 0; i < size; i++) {
                    int leaf = leaves.poll(), neighbor = graph[leaf].iterator().next(); // only one neighbor for leaf
                    // graph[leaf].remove(neighbor); // don't need to do this
                    graph[neighbor].remove(leaf);
                    if (graph[neighbor].size() == 1) leaves.offer(neighbor);
                }
            }
            return leaves;
        }
    }
}
