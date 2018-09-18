/**
 * LeetCode #538, easy
 *
 * Given a Binary Search Tree (BST), convert it to a Greater Tree such that every key of the original BST is changed
 * to the original key plus sum of all keys greater than the original key in BST.
 *
 * Example:
 *
 * Input: The root of a Binary Search Tree like this:
 *               5
 *             /   \
 *            2     13
 *
 * Output: The root of a Greater Tree like this:
 *              18
 *             /   \
 *           20     13
 */

package Tree;

import java.util.*;

public class ConvertBSTToGreaterTree {
    /**
     * Solution 1: Recursive
     *
     * The basic idea is to do a reverse-order traversal and keep track of sum seen so far and assign the sum to
     * current node. We can do this by either using a global (to class) variable, or passing sum as an argument. The
     * latter is preferred because it is "stateless", meaning that the result of method call does not rely on the
     * current state of the class, which might be altered by some other object thus resulting in wrong output.
     *
     * Time complexity: O(n). Space complexity: O(log(n)) average.
     */
    class Solution1 {
        public TreeNode convertBST(TreeNode root) {
            reverseOrder(root, 0);
            return root;
        }

        /* Performs reverse-order traverse and returns current sum. Parameter sum is the sum seen so far. */
        private int reverseOrder(TreeNode root, int sum) {
            if (root == null) return sum;
            root.val += reverseOrder(root.right, sum);
            return reverseOrder(root.left, root.val); // root value becomes the new sum
        }
    }

    /**
     * Solution 2: Iterative
     *
     * Same idea as Solution 1. Iteratively perform reverse-order traversal using stack.
     */
    class Solution {
        public TreeNode convertBST(TreeNode root) {
            Stack<TreeNode> stack = new Stack<>();
            TreeNode cur = root;
            int sum = 0;
            while (cur != null || !stack.isEmpty()) {
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.right; // note the reverse-order here
                } else {
                    cur = stack.pop();
                    sum += cur.val; // update sum
                    cur.val = sum;
                    cur = cur.left; // note the reverse-order here
                }
            }
            return root;
        }
    }
}
