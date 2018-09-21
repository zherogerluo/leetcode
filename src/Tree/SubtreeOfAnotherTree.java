/**
 * LeetCode #572, easy
 *
 * Given two non-empty binary trees s and t, check whether tree t has exactly the same structure and node values with
 * a subtree of s. A subtree of s is a tree consists of a node in s and all of this node's descendants. The tree s
 * could also be considered as a subtree of itself.
 *
 * Example 1:
 * Given tree s:
 *      3
 *     / \
 *    4   5
 *   / \
 *  1   2
 * Given tree t:
 *    4
 *   / \
 *  1   2
 * Return true, because t has the same structure and node values with a subtree of s.
 *
 * Example 2:
 * Given tree s:
 *      3
 *     / \
 *    4   5
 *   / \
 *  1   2
 *     /
 *    0
 * Given tree t:
 *    4
 *   / \
 *  1   2
 * Return false.
 */

package Tree;

public class SubtreeOfAnotherTree {
    /**
     * Solution 1: Recursion
     *
     * A superset problem of Same Tree. If it is same tree, apparently they are mutually subtrees; Otherwise just
     * test if t is subtree of s.left or s.right. We need a utility function to check whether two trees are the same
     * or not.
     *
     * Time complexity: O(n^2). Space complexity: O(log(n)) average.
     */
    class Solution1 {
        public boolean isSubtree(TreeNode s, TreeNode t) {
            if (s == t) return true;
            if (s == null || t == null) return false;
            if (isSameTree(s, t)) return true;
            return isSubtree(s.left, t) || isSubtree(s.right, t);
        }

        private boolean isSameTree(TreeNode s, TreeNode t) {
            if (s == t) return true;
            if (s == null || t == null || s.val != t.val) return false;
            return isSameTree(s.left, t.left) && isSameTree(s.right, t.right);
        }
    }
}
