/**
 * LeetCode #834, hard
 *
 * An undirected, connected tree with N nodes labelled 0...N-1 and N-1 edges are given.
 *
 * The ith edge connects nodes edges[i][0] and edges[i][1] together.
 *
 * Return a list ans, where ans[i] is the sum of the distances between node i and all other nodes.
 *
 * Example 1:
 *
 * Input: N = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
 * Output: [8,12,6,10,10,10]
 * Explanation:
 * Here is a diagram of the given tree:
 *   0
 *  / \
 * 1   2
 *    /|\
 *   3 4 5
 * We can see that dist(0,1) + dist(0,2) + dist(0,3) + dist(0,4) + dist(0,5)
 * equals 1 + 1 + 2 + 2 + 2 = 8.  Hence, answer[0] = 8, and so on.
 *
 * Note: 1 <= N <= 10000
 */

package Tree;

import java.util.*;

public class SumOfDistancesInTree {
    /**
     * Solution 1: Depth-first search
     *
     * Starting from zero as root, it is easy to calculate the total distance from a node to all of its descendants by
     * using DFS to traverse the tree. From these results we can do another DFS to add the total distance to the rest
     * of nodes. We can think of each node connecting to one other node with a "ribbon", apparently the closer a node
     * to the root, the more ribbons it is going to have. Each edge would have effectively a weight that equals the
     * number of ribbons, which is equal to the count of nodes in the subtree. Back to the previous statement, to
     * calculate the total distance for a node, we can use ans[parent] and just need to replace the weight between
     * node and parent. The old weight is descendants[node] + 1 (self), and the new weight is number of rest of nodes
     * which is N - descendants[node] - 1 (self).
     */
    class Solution1 {
        public int[] sumOfDistancesInTree(int N, int[][] edges) {
            if (N == 1) {
                return new int[1];
            }
            List<Integer>[] tree = new ArrayList[N];
            for (int[] edge : edges) {
                if (tree[edge[0]] == null) {
                    tree[edge[0]] = new ArrayList<>();
                }
                if (tree[edge[1]] == null) {
                    tree[edge[1]] = new ArrayList<>();
                }
                tree[edge[0]].add(edge[1]);
                tree[edge[1]].add(edge[0]);
            }
            int[] ans = new int[N], des = new int[N];
            firstPass(tree, 0, -1, ans, des);
            secondPass(tree, 0, -1, ans, des);
            return ans;
        }

        private void firstPass(List<Integer>[] tree, int cur, int from, int[] ans, int[] des) {
            int res = 0;
            for (int next : tree[cur]) {
                if (from != next) {
                    firstPass(tree, next, cur, ans, des);
                    des[cur] += des[next] + 1;
                    res += ans[next] + des[next] + 1;
                }
            }
            ans[cur] = res;
        }

        private void secondPass(List<Integer>[] tree, int cur, int from, int[] ans, int[] des) {
            final int N = ans.length;
            if (from >= 0) {
                ans[cur] = ans[from] - (des[cur] + 1) + (N - des[cur] - 1);
            }
            for (int next : tree[cur]) {
                if (from != next) {
                    secondPass(tree, next, cur, ans, des);
                }
            }
        }
    }

    /**
     * Solution 2: DFS
     *
     * Same as Solution 1, replaced descendants array with counts array, which is essentially descendants + 1. This
     * makes the code cleaner.
     */
    class Solution2 {
        public int[] sumOfDistancesInTree(int N, int[][] edges) {
            if (N == 1) {
                return new int[1];
            }
            List<Integer>[] tree = new ArrayList[N];
            for (int[] edge : edges) {
                if (tree[edge[0]] == null) {
                    tree[edge[0]] = new ArrayList<>();
                }
                if (tree[edge[1]] == null) {
                    tree[edge[1]] = new ArrayList<>();
                }
                tree[edge[0]].add(edge[1]);
                tree[edge[1]].add(edge[0]);
            }
            int[] ans = new int[N], counts = new int[N];
            firstPass(tree, 0, -1, ans, counts);
            secondPass(tree, 0, -1, ans, counts);
            return ans;
        }

        private void firstPass(List<Integer>[] tree, int cur, int from, int[] ans, int[] counts) {
            int res = 0;
            for (int next : tree[cur]) {
                if (from != next) {
                    firstPass(tree, next, cur, ans, counts);
                    counts[cur] += counts[next];
                    res += ans[next] + counts[next];
                }
            }
            ++counts[cur];
            ans[cur] = res;
        }

        private void secondPass(List<Integer>[] tree, int cur, int from, int[] ans, int[] counts) {
            if (from >= 0) {
                ans[cur] = ans[from] - counts[cur] + (ans.length - counts[cur]);
            }
            for (int next : tree[cur]) {
                if (from != next) {
                    secondPass(tree, next, cur, ans, counts);
                }
            }
        }
    }
}
