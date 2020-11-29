/**
 * LeetCode #437, medium
 *
 * You are given a binary tree in which each node contains an integer value.
 *
 * Find the number of paths that sum to a given value.
 *
 * The path does not need to start or end at the root or a leaf, but it must go downwards (traveling only from parent
 * nodes to child nodes).
 *
 * The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.
 *
 * Example:
 *
 * root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
 *
 *       10
 *      /  \
 *     5   -3
 *    / \    \
 *   3   2   11
 *  / \   \
 * 3  -2   1
 *
 * Return 3. The paths that sum to 8 are:
 *
 * 1.  5 -> 3
 * 2.  5 -> 2 -> 1
 * 3. -3 -> 11
 */

import java.util.*;

public class PathSumThree {
    /**
     * Solution 1: Brutal force DFS
     *
     * DFS, with a boolean to mark whether or not to start a path at current node.
     *
     * Time complexity: O(2^n). Space complexity: O(log(n))
     */
    class Solution1 {
        int res = 0;

        public int pathSum(TreeNode root, int sum) {
            res = 0;
            helper(root, sum, false);
            return res;
        }

        private void helper(TreeNode root, int remaining, boolean started) {
            if (root == null) {
                return;
            }
            if (root.val == remaining) {
                ++res;
            }
            if (!started) {
                helper(root.left, remaining, started);
                helper(root.right, remaining, started);
            }
            helper(root.left, remaining - root.val, true);
            helper(root.right, remaining - root.val, true);
        }
    }

    /**
     * Solution 2: Traverse and remember
     *
     * Traverse the entire tree and remember all the paths and their frequencies starting at a node. Theoretically it
     * is O(n) but in reality it has big overhead because of n hash maps so it is pretty slow.
     *
     * Time complexity: O(n). Space complexity: O(log(n)).
     */
    class Solution2 {
        int res = 0;

        public int pathSum(TreeNode root, int sum) {
            res = 0;
            helper(root, new HashMap<>(), sum);
            return res;
        }

        private void helper(TreeNode root, Map<TreeNode, Map<Integer, Integer>> count_map, int global_sum) {
            if (root == null) {
                return;
            }
            Map<Integer, Integer> map = new HashMap<>();
            map.put(root.val, 1);
            helper(root.left, count_map, global_sum);
            helper(root.right, count_map, global_sum);
            if (root.left != null) {
                Map<Integer, Integer> left = count_map.get(root.left);
                for (int sum : left.keySet()) {
                    int new_sum = sum + root.val;
                    int count = left.get(sum);
                    map.put(new_sum, map.getOrDefault(new_sum, 0) + count);
                }
            }
            if (root.right != null) {
                Map<Integer, Integer> right = count_map.get(root.right);
                for (int sum : right.keySet()) {
                    int new_sum = sum + root.val;
                    int count = right.get(sum);
                    map.put(new_sum, map.getOrDefault(new_sum, 0) + count);
                }
            }
            res += map.getOrDefault(global_sum, 0);
            count_map.put(root, map);
        }
    }

    /**
     * Solution 3: Path difference
     *
     * The most optimal solution. Idea is that, the paths we are looking for can be obtained by taking difference
     * (subtract) between two paths starting from tree root, which we refer to as "full path". We use a working map to
     * remember all full path length and frequency ending at current node, and also keep track of current sum of full
     * path. We search the needed diff in the map, if there is a match, we know we can get a required partial path
     * starting from the end of the searched full path.
     *
     * Again the most important idea behind this is taking the diff.
     *
     * One tricky point is to initialize the map with { 0 : 1 } because if we happen to have a full path sum equal to
     * required sum and the diff is zero, we need to get 1 back from the search to increment the result.
     *
     * Time complexity: O(n). Space complexity: O(log(n)).
     */
    class Solution3 {
        int res = 0;

        public int pathSum(TreeNode root, int sum) {
            res = 0;
            Map<Integer, Integer> paths = new HashMap<>();
            // account for the case when pre_sum == sum
            paths.put(0, 1);
            helper(root, paths, 0, sum);
            return res;
        }

        private void helper(TreeNode root, Map<Integer, Integer> paths, int pre_sum, final int sum) {
            if (root == null) {
                return;
            }
            // sum of current path from tree root
            pre_sum += root.val;
            // increment result if diff between current sum and remembered sum equals target sum
            res += paths.getOrDefault(pre_sum - sum, 0);
            // remember this path sum
            paths.put(pre_sum, paths.getOrDefault(pre_sum, 0) + 1);
            helper(root.left, paths, pre_sum, sum);
            helper(root.right, paths, pre_sum, sum);
            // forget this path sum as we go back to other branches of the tree
            paths.put(pre_sum, paths.get(pre_sum) - 1);
        }
    }
}