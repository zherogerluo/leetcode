/**
 * LeetCode #112, easy
 *
 * Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values
 * along the path equals the given sum.
 *
 * Note: A leaf is a node with no children.
 *
 * Example:
 *
 * Given the below binary tree and sum = 22,
 *
 *       5
 *      / \
 *     4   8
 *    /   / \
 *   11  13  4
 *  /  \      \
 * 7    2      1
 * return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
 */

package Tree;

public class PathSum {
    /**
     * Solution 1: Recursion, DFS
     *
     * DFS to the leaf node, return true if sum is equal to target. Note that the search cannot terminate if cur >
     * sum, because there is no precondition that node values are all positive.
     *
     * Time complexity: O(n). Space complexity: O(log(n)) average.
     */
    class Solution1 {
        public boolean hasPathSum(TreeNode root, int sum) {
            return hasPathSum(root, 0, sum);
        }

        private boolean hasPathSum(TreeNode root, int cur, int sum) {
            if (root == null) return false;
            cur += root.val;
            if (root.left == null && root.right == null) return cur == sum;
            return hasPathSum(root.left, cur, sum) || hasPathSum(root.right, cur, sum);
        }
    }
}
