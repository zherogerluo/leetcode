/**
 * LeetCode #129, medium
 *
 * Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.
 *
 * An example is the root-to-leaf path 1->2->3 which represents the number 123.
 *
 * Find the total sum of all root-to-leaf numbers.
 *
 * Note: A leaf is a node with no children.
 *
 * Example:
 *
 * Input: [1,2,3]
 *     1
 *    / \
 *   2   3
 * Output: 25
 * Explanation:
 * The root-to-leaf path 1->2 represents the number 12.
 * The root-to-leaf path 1->3 represents the number 13.
 * Therefore, sum = 12 + 13 = 25.
 * Example 2:
 *
 * Input: [4,9,0,5,1]
 *     4
 *    / \
 *   9   0
 *  / \
 * 5   1
 * Output: 1026
 * Explanation:
 * The root-to-leaf path 4->9->5 represents the number 495.
 * The root-to-leaf path 4->9->1 represents the number 491.
 * The root-to-leaf path 4->0 represents the number 40.
 * Therefore, sum = 495 + 491 + 40 = 1026.
 */

package Tree;

public class SumRootToLeafNumbers {
    /**
     * Solution 1: Recursion, DFS
     *
     * DFS and carry current sum, once reach the end of the number, add sum to the result. Note that the ending
     * condition is two null children instead of null self.
     *
     * Time complexity: O(n). Space complexity: O(log(n)) average.
     */
    class Solution1 {
        public int sumNumbers(TreeNode root) {
            int[] res = new int[1];
            sum(root, 0, res);
            return res[0];
        }

        private void sum(TreeNode root, int cur, int[] res) {
            if (root == null) return;
            // root is a number, update current sum first
            cur = cur * 10 + root.val;
            if (root.left == null && root.right == null) {
                // end of a number, include in the result
                res[0] += cur;
                return;
            }
            // not end of a number, continue with left and right branches
            sum(root.left, cur, res);
            sum(root.right, cur, res);
        }
    }
}
