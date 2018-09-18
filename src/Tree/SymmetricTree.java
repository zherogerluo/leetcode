/**
 * LeetCode #101, easy
 *
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 *
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
 *
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 * But the following [1,2,2,null,3,null,3] is not:
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 * Note:
 * Bonus points if you could solve it both recursively and iteratively.
 */

package Tree;

import java.util.*;

public class SymmetricTree {
    /**
     * Solution 1: Recursive DFS
     *
     * This problem is pretty much the same as Problem 100 Same Tree, except that we are comparing left with right
     * and right with left.
     *
     * Time complexity: O(n). Space complexity: O(log(n)) average.
     */
    class Solution1 {
        public boolean isSymmetric(TreeNode root) {
            if (root == null) return true;
            return isSymmetric(root.left, root.right);
        }

        private boolean isSymmetric(TreeNode left, TreeNode right) {
            if (left == null && right == null) return true;
            if (left == null || right == null || left.val != right.val) return false;
            return isSymmetric(left.right, right.left) && isSymmetric(left.left, right.right);
        }
    }

    /**
     * Solution 2: Iterative DFS
     *
     * Iterative version of Solution 1.
     *
     * Note BFS solution can be coded similarly, just need to change stack to queue.
     */
    class Solution2 {
        public boolean isSymmetric(TreeNode root) {
            if (root == null) return true;
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root.left);
            stack.push(root.right);
            while (!stack.isEmpty()) {
                TreeNode right = stack.pop(), left = stack.pop();
                if (left == null && right == null) continue;
                if (left == null || right == null || left.val != right.val) return false;
                stack.push(left.left);
                stack.push(right.right);
                stack.push(left.right);
                stack.push(right.left);
            }
            return true;
        }
    }
}
