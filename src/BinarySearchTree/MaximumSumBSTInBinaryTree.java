/**
 * LeetCode #1373, hard
 *
 * Given a binary tree root, the task is to return the maximum sum of all keys of any sub-tree which is also a Binary
 * Search Tree (BST).
 *
 * Assume a BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 *
 * Example 1:
 *
 * Input: root = [1,4,3,2,4,2,5,null,null,null,null,null,null,4,6]
 * Output: 20
 * Explanation: Maximum sum in a valid Binary search tree is obtained in root node with key equal to 3.
 *
 * Example 2:
 *
 * Input: root = [4,3,null,1,2]
 * Output: 2
 * Explanation: Maximum sum in a valid Binary search tree is obtained in a single root node with key equal to 2.
 *
 * Example 3:
 *
 * Input: root = [-4,-2,-5]
 * Output: 0
 * Explanation: All values are negatives. Return an empty BST.
 *
 * Example 4:
 *
 * Input: root = [2,1,3]
 * Output: 6
 *
 * Example 5:
 *
 * Input: root = [5,4,8,3,null,6,3]
 * Output: 7
 *
 * Constraints:
 *
 * The given binary tree will have between 1 and 40000 nodes.
 * Each node's value is between [-4 * 10^4 , 4 * 10^4].
 */

public class MaximumSumBSTInBinaryTree {
    /**
     * Solution 1: Depth-first search
     *
     * DFS, return {min, max, sum} where min and max are ranges of the sub tree, if it is a BST, otherwise return a min
     * of Integer.MIN_VALUE. This is safe because the node's value is far less significant. Return null if node is null,
     * and remember to check for nulls.
     */
    class Solution1 {
        int res;

        public int maxSumBST(TreeNode root) {
            res = Integer.MIN_VALUE;
            dfs(root);
            return Math.max(0, res);
        }

        // return {min, max, sum}, or null if root is null
        private int[] dfs(TreeNode root) {
            if (root == null) {
                return null;
            }
            int[] left = dfs(root.left);
            int[] right = dfs(root.right);
            if ((left != null && left[0] == Integer.MIN_VALUE) ||
                (right != null && right[0] == Integer.MIN_VALUE)) {
                // left or right sub tree is not BST
                return new int[]{ Integer.MIN_VALUE, 0, 0 };
            }
            if ((left != null && left[1] >= root.val) ||
                (right != null && right[0] <= root.val)) {
                // left or right range overlap with root, not BST
                return new int[]{ Integer.MIN_VALUE, 0, 0 };
            }
            int sum = root.val, min = root.val, max = root.val;
            if (left != null) {
                sum += left[2];
                min = left[0];
            }
            if (right != null) {
                sum += right[2];
                max = right[1];
            }
            res = Math.max(res, sum);
            return new int[]{ min, max, sum };
        }
    }
}
