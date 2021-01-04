/**
 * LeetCode #938, easy
 *
 * Given the root node of a binary search tree, return the sum of values of all nodes with a value in the range [low, high].
 *
 * Example 1:
 *      10
 *     /  \
 *    5   15
 *  /  \    \
 * 3   7    18
 * Input: root = [10,5,15,3,7,null,18], low = 7, high = 15
 * Output: 32
 *
 * Example 2:
 *           10
 *         /   \
 *       5      15
 *     /  \    /  \
 *    3    7  13   18
 *   /    /
 *  1    6
 * Input: root = [10,5,15,3,7,13,18,1,null,6], low = 6, high = 10
 * Output: 23
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [1, 2 * 10^4].
 * 1 <= Node.val <= 10^5
 * 1 <= low <= high <= 10^5
 * All Node.val are unique.
 */

package Tree;

public class RangeSumOfBST {
    /**
     * Solution 1: Recursion
     */
    class Solution1 {
        public int rangeSumBST(TreeNode root, int low, int high) {
            if (root == null) {
                return 0;
            }
            int res = 0;
            if (root.val >= low && root.val <= high) {
                res += root.val;
            }
            if (root.val > low) {
                res += rangeSumBST(root.left, low, high);
            }
            if (root.val < high) {
                res += rangeSumBST(root.right, low, high);
            }
            return res;
        }
    }
}
