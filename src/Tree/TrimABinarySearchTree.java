/**
 * LeetCode #669, easy
 *
 * Given a binary search tree and the lowest and highest boundaries as L and R, trim the tree so that all its
 * elements lies in [L, R] (R >= L). You might need to change the root of the tree, so the result should return the
 * new root of the trimmed binary search tree.
 *
 * Example 1:
 * Input:
 *     1
 *    / \
 *   0   2
 *
 *   L = 1
 *   R = 2
 *
 * Output:
 *     1
 *       \
 *        2
 * Example 2:
 * Input:
 *     3
 *    / \
 *   0   4
 *    \
 *     2
 *    /
 *   1
 *
 *   L = 1
 *   R = 3
 *
 * Output:
 *       3
 *      /
 *    2
 *   /
 *  1
 */

package Tree;

public class TrimABinarySearchTree {
    /**
     * Solution 1: Recursion
     *
     * Recursively trim the tree. For the child nodes, update the range so that one side is the root value.
     *
     * Time complexity: O(n). Space complexity: O(log(n)) average.
     */
    class Solution1 {
        public TreeNode trimBST(TreeNode root, int L, int R) {
            if (root == null) return null;
            if (root.val < L) return trimBST(root.right, L, R);
            if (root.val > R) return trimBST(root.left, L, R);
            root.left = trimBST(root.left, L, root.val);
            root.right = trimBST(root.right, root.val, R);
            return root;
        }
    }

    /**
     * Solution 2: Iterative
     *
     * This iterative is nothing like using stack to traverse the tree in pre- or in-order. First, find a valid new
     * root, which is basically binary search in BST; Second, trim the left tree by doing this: If the left child is
     * smaller than L, simply get rid of it and its left branch, replace with the right branch and move on. The same
     * thing is done for right tree in the third loop.
     *
     * Think out of the box. This is far less as hard as you think it would be.
     */
    class Solution2 {
        public TreeNode trimBST(TreeNode root, int L, int R) {
            if (root == null) return root;
            // Find a valid root which is used to return.
            while (root.val < L || root.val > R) {
                if (root.val < L) root = root.right;
                else root = root.left;
            }
            TreeNode dummy = root;
            // Remove the invalid nodes from left subtree.
            while (dummy != null) {
                // If the left child is smaller than L, then we just keep the right subtree of it.
                while (dummy.left != null && dummy.left.val < L) dummy.left = dummy.left.right;
                dummy = dummy.left;
            }
            dummy = root;
            // Remove the invalid nodes from right subtree
            while (dummy != null) {
                // If the right child is bigger than R, then we just keep the left subtree of it.
                while (dummy.right != null && dummy.right.val > R) dummy.right = dummy.right.left;
                dummy = dummy.right;
            }
            return root;
        }
    }
}
