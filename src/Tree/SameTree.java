/**
 * LeetCode #100, easy
 *
 * Given two binary trees, write a function to check if they are the same or not.
 *
 * Two binary trees are considered the same if they are structurally identical and the nodes have the same value.
 *
 * Example 1:
 *
 * Input:     1         1
 *           / \       / \
 *          2   3     2   3
 *
 *         [1,2,3],   [1,2,3]
 *
 * Output: true
 *
 * Example 2:
 *
 * Input:     1         1
 *           /           \
 *          2             2
 *
 *         [1,2],     [1,null,2]
 *
 * Output: false
 *
 * Example 3:
 *
 * Input:     1         1
 *           / \       / \
 *          2   1     1   2
 *
 *         [1,2,1],   [1,1,2]
 *
 * Output: false
 */

package Tree;

import java.util.*;

public class SameTree {
    /**
     * Solution 1: Recursive
     *
     * Simple recursive pre-order traversal.
     *
     * Time complexity: O(n). Space complexity: O(log(n)) average.
     */
    class Solution1 {
        public boolean isSameTree(TreeNode p, TreeNode q) {
            if (p == null && q == null) return true;
            if (p == null || q == null || p.val != q.val) return false;
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
    }

    /**
     * Soluion 2: Iterative
     *
     * Iterative pre-order traversal using stack. Note we can use just a single stack, just need to separate the left
     * and right nodes.
     */
    class Solution2 {
        public boolean isSameTree(TreeNode p, TreeNode q) {
            Stack<TreeNode> stack = new Stack<>();
            stack.push(p);
            stack.push(q);
            while (!stack.isEmpty()) {
                q = stack.pop();
                p = stack.pop();
                if (p == null && q == null) continue;
                else if (p == null || q == null || p.val != q.val) return false;
                stack.push(p.right);
                stack.push(q.right);
                stack.push(p.left);
                stack.push(q.left);
            }
            return true;
        }
    }
}
