/**
 * LeetCode #145, hard
 *
 * Given a binary tree, return the postorder traversal of its nodes' values.

 Example:

 Input: [1,null,2,3]
 1
  \
   2
  /
 3

 Output: [3,2,1]

 Follow up: Recursive solution is trivial, could you do it iteratively?
 */

package Tree;

import java.util.*;

public class BinaryTreePostorderTraversal {
    /**
     * Solution 1: Recursion
     *
     * Trivial.
     *
     * Time complexity: O(n). Space complexity: O(n) for output list, O(log(n)) for call stack.
     */
    class Solution1 {
        public List<Integer> postorderTraversal(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            if (root == null) return res;
            res.addAll(postorderTraversal(root.left));
            res.addAll(postorderTraversal(root.right));
            res.add(root.val);
            return res;
        }
    }

    /**
     * Solution 2: Iterative using stack
     *
     * The idea is to do a inverted preorder traversal and reverse the result. Postorder: [left][right]root, the
     * reverse is root[right][left] which is exactly the inverted preorder traversal (right child first).
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        public List<Integer> postorderTraversal(TreeNode root) {
            List<Integer> res = new LinkedList<>(); // use linked list to improve performance to add to list head
            if (root == null) return res;
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                root = stack.pop();
                res.add(0, root.val); // add to list head (reverse the result)
                // push right after left, right comes out first (inverted preorder)
                if (root.left != null) stack.push(root.left);
                if (root.right != null) stack.push(root.right);
            }
            return res;
        }
    }
}
