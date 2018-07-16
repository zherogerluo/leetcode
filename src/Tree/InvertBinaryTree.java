/**
 * LeetCode #226, easy
 *
 * Invert a binary tree.

 Example:

 Input:

        4
      /   \
    2      7
  /  \   /  \
 1   3  6   9
 Output:

        4
      /   \
    7      2
  /  \   /  \
 9   6  3   1

 Trivia:
 This problem was inspired by this original tweet by Max Howell:

 Google: 90% of our engineers use the software you wrote (Homebrew), but you can’t invert a binary tree on a
 whiteboard so f*** off.
 */

package Tree;

import java.util.*;

public class InvertBinaryTree {
    /**
     * Solution 1: Recursion.
     *
     * Trivial. But those seemingly trivial solutions are where carelessness happens!
     */
    class Solution1 {
        public TreeNode invertTree(TreeNode root) {
            if (root == null) return root;
            TreeNode temp = root.left;
            // don't forget the recursive calls!
            root.left = invertTree(root.right);
            root.right = invertTree(temp);
            return root;
        }
    }

    /**
     * Solution 2: Iterative, preorder (DFS)
     *
     * Just do an ordinary preorder traversal and invert children for each node. We can do the same thing using BFS.
     */
    class Solution2 {
        public TreeNode invertTree(TreeNode root) {
            if (root == null) return root;
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                TreeNode cur = stack.pop();
                TreeNode temp = cur.left;
                cur.left = cur.right;
                cur.right = temp;
                if (cur.right != null) stack.push(cur.right);
                if (cur.left != null) stack.push(cur.left);
            }
            return root;
        }
    }
}
