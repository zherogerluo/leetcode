/**
 * LeetCode #98, medium
 *
 * Given a binary tree, determine if it is a valid binary search tree (BST).

 Assume a BST is defined as follows:

 The left subtree of a node contains only nodes with keys less than the node's key.
 The right subtree of a node contains only nodes with keys greater than the node's key.
 Both the left and right subtrees must also be binary search trees.
 Example 1:

 Input:
   2
  / \
 1  3
 Output: true
 Example 2:

   5
  / \
 1  4
   / \
  3  6
 Output: false
 Explanation: The input is: [5,1,4,null,null,3,6]. The root node's value
 is 5 but its right child's value is 4.
 */

package Tree;

import java.util.*;

public class ValidateBinarySearchTree {
    /**
     * Definition for a binary tree node.
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    /**
     * Solution 1: Recursion
     *
     * Helper function to validate a BST with given range, and split the range by root value and recursively call
     * helper on left and right children.
     *
     * Tricky part: Node value can be min and max int, so use min and max of long type as starting range.
     *
     * Time complexity: O(n). Space complexity: O(h).
     */
    class Solution1 {
        public boolean isValidBST(TreeNode root) {
            return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }

        private boolean isValidBST(TreeNode root, long leftBound, long rightBound) {
            if (root == null) return true;
            if (root.val <= leftBound || root.val >= rightBound) return false;
            return isValidBST(root.left, leftBound, root.val) && isValidBST(root.right, root.val, rightBound);
        }
    }

    /**
     * Solution 2: Iterative, in-order traversal
     *
     * In-order traversal, record last seen value and compare current value to last. Again the last value should
     * start with min of long type, not int type, otherwise cannot handle valid min int node.
     *
     * Time complexity: O(n). Space complexity: O(h).
     */
    class Solution2 {
        public boolean isValidBST(TreeNode root) {
            Stack<TreeNode> stack = new Stack<>();
            long last = Long.MIN_VALUE;
            while (root != null || !stack.isEmpty()) {
                while (root != null) {
                    stack.push(root);
                    root = root.left;
                }
                root = stack.pop();
                if (root.val <= last) return false;
                last = root.val;
                root = root.right;
            }
            return true;
        }
    }
}
