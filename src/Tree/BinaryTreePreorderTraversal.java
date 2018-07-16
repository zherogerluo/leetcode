/**
 * LeetCode #144, medium
 *
 * Given a binary tree, return the preorder traversal of its nodes' values.

 Example:

 Input: [1,null,2,3]
 1
  \
   2
  /
 3

 Output: [1,2,3]

 Follow up: Recursive solution is trivial, could you do it iteratively?
 */

package Tree;

import java.util.*;

public class BinaryTreePreorderTraversal {
    /**
     * Solution 1: Recursion
     *
     * Trivial.
     *
     * Time complexity: O(n). Space complexity: O(n) for output list, O(log(n)) for call stack.
     */
    class Solution1 {
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            if (root == null) return res;
            res.add(root.val); // don't forget this line!
            res.addAll(preorderTraversal(root.left));
            res.addAll(preorderTraversal(root.right));
            return res;
        }
    }

    /**
     * Solution 2: Iterative using stack
     *
     * Pop new root from stack, record its value, and push the children in, right first, so that left child is popped
     * out first.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            if (root == null) return res;
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                root = stack.pop();
                res.add(root.val);
                if (root.right != null) stack.push(root.right);
                if (root.left != null) stack.push(root.left);
            }
            return res;
        }
    }

    public void test() {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(6);
        System.out.println(new Solution1().preorderTraversal(root));
        System.out.println(new Solution2().preorderTraversal(root));
    }

    public static void main(String[] args) {
        new BinaryTreePreorderTraversal().test();
    }
}
