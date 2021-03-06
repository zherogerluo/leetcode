/**
 * LeetCode #404, easy
 *
 * Find the sum of all left leaves in a given binary tree.
 *
 * Example:
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * There are two left leaves in the binary tree, with values 9 and 15 respectively. Return 24.
 */

package Tree;

public class SumOfLeftLeaves {
    /**
     * Solution 1: Recursion
     */
    class Solution1 {
        public int sumOfLeftLeaves(TreeNode root) {
            if (root == null) return 0;
            int res = 0;
            if (root.left != null && root.left.left == null && root.left.right == null) {
                res += root.left.val;
            } else {
                res += sumOfLeftLeaves(root.left);
            }
            res += sumOfLeftLeaves(root.right);
            return res;
        }
    }
}
