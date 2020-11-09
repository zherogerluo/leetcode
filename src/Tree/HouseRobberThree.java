/**
 * LeetCode #337, medium
 *
 * The thief has found himself a new place for his thievery again. There is only one entrance to this area, called the
 * "root." Besides the root, each house has one and only one parent house. After a tour, the smart thief realized that
 * "all houses in this place forms a binary tree". It will automatically contact the police if two directly-linked
 * houses were broken into on the same night.
 *
 * Determine the maximum amount of money the thief can rob tonight without alerting the police.
 *
 * Example 1:
 * Input: [3,2,3,null,3,null,1]
 *
 *      3
 *     / \
 *    2   3
 *     \   \
 *      3   1
 *
 * Output: 7
 * Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
 *
 * Example 2:
 * Input: [3,4,5,1,3,null,1]
 *
 *      3
 *     / \
 *    4   5
 *   / \   \
 *  1   3   1
 *
 * Output: 9
 * Explanation: Maximum amount of money the thief can rob = 4 + 5 = 9.
 */

public class HouseRobberThree {
    /**
     * Solution 1: Depth-first search
     *
     * Typical DFS, with boolean to decide to include this node or not. This algorithm sucks big time since it is
     * actually O(2^n) time as we branch to robbed and not robbed at every node. There are a lot of cases that can be
     * pruned, e.g. rob only root and not all other nodes, which is clearly not the solution.
     *
     * Time complexity: O(2^n). Space complexity: O(log(n)).
     */
    class Solution1 {
        public int rob(TreeNode root) {
            return rob(root, true);
        }

        private int rob(TreeNode root, boolean can_rob) {
            if (root == null) {
                return 0;
            }
            int robbed = 0;
            if (can_rob) {
                robbed = root.val + rob(root.left, false) + rob(root.right, false);
            }
            int not_robbed = rob(root.left, true) + rob(root.right, true);
            return Math.max(robbed, not_robbed);
        }
    }

    /**
     * Solution 2: Depth-first search
     *
     * Now the helper returns both results for robbing root as result[0], or not robbing root as result[1]. We can
     * recursively calculate results based on the child node results: Result for robbing root is the root value plus
     * not robbing results for both left and right branches, however the result for not robbing root is a bit trickier:
     * We need to calculate the max of robbing and not robbing since we don't really know which one is bigger.
     *
     * Time complexity: O(n). Space complexity: O(log(n)).
     */
    class Solution2 {
        public int rob(TreeNode root) {
            int[] result = helper(root);
            return Math.max(result[0], result[1]);
        }

        private int[] helper(TreeNode root) {
            if (root == null) {
                return new int[]{ 0, 0 };
            }
            int[] left = helper(root.left), right = helper(root.right);
            return new int[]{ root.val + left[1] + right[1],
                              Math.max(left[0], left[1]) + Math.max(right[0], right[1]) };
        }
    }
}
