/**
 * Given a binary tree, return the inorder traversal of its nodes' values.

 Example:

 Input: [1,null,2,3]
 1
  \
  2
  /
 3

 Output: [1,3,2]
 Follow up: Recursive solution is trivial, could you do it iteratively?
 */

package Tree;

import java.util.*;

public class BinaryTreeInorderTraversal {
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
     * Solution 1: Recursion. Easy.
     *
     * Time complexity: O(n). Space complexity: O(n) (call stack).
     */
    class Solution1 {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            if (root == null) return res;
            res.addAll(inorderTraversal(root.left));
            res.add(root.val);
            res.addAll(inorderTraversal(root.right));
            return res;
        }
    }

    /**
     * Solution 2: Iterative, with stack
     *
     * First, trace all the way to the leftmost node and push them all to stack. Then, pop them one-by-one and record
     * its value. If the popped node has right child, then go to right child and again push its left child to stack.
     * Do this until stack is empty.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            TreeNode cur = root;
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            while (!stack.isEmpty()) {
                cur = stack.pop();
                res.add(cur.val);
                if (cur.right != null) {
                    cur = cur.right;
                    while (cur != null) {
                        stack.push(cur);
                        cur = cur.left;
                    }
                }
            }
            return res;
        }
    }

    /**
     * Solution 3: Iterative, with stack
     *
     * Similar to Solution 2, with some arrangement to combine the two while loops. This is slightly less
     * straightforward as Solution 2 though.
     */
    class Solution3 {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            while (root != null || !stack.isEmpty()) {
                while (root != null) {
                    stack.push(root);
                    root = root.left;
                }
                root = stack.pop();
                res.add(root.val);
                root = root.right;
            }
            return res;
        }
    }

    /**
     * Solution 4: Iterative, with stack and hash table
     *
     * This solution implements the original inorder traversal the best: traverse left -> root -> right order. For
     * each node, push right, self and left to the stack, and record in hash table that it has been "visited" (or
     * more appropriate, "expanded"). For the popped node, if it has been visited, then add value to result,
     * otherwise "expand" it.
     *
     * This method can be easily modified to do pre- or post-order traversal in iterative fashion.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution4 {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            if (root == null) return res;
            Stack<TreeNode> stack = new Stack<>();
            Set<TreeNode> visited = new HashSet<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                root = stack.pop();
                if (visited.contains(root)) res.add(root.val);
                else {
                    visited.add(root);
                    if (root.right != null) stack.push(root.right);
                    stack.push(root);
                    if (root.left != null) stack.push(root.left);
                }
            }
            return res;
        }
    }

    /**
     * Solution 5: Morris traversal
     */
    // TODO
}
