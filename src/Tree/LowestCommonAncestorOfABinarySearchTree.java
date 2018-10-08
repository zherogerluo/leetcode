/**
 * LeetCode #235, easy
 *
 * Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.
 *
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q
 * as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
 *
 * Given binary search tree:  root = [6,2,8,0,4,7,9,null,null,3,5]
 *
 *         _______6______
 *        /              \
 *     ___2__          ___8__
 *    /      \        /      \
 *    0      _4       7       9
 *          /  \
 *          3   5
 * Example 1:
 *
 * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
 * Output: 6
 * Explanation: The LCA of nodes 2 and 8 is 6.
 *
 * Example 2:
 *
 * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
 * Output: 2
 * Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA
 * definition.
 *
 * Note:
 *
 * All of the nodes' values will be unique.
 * p and q are different and both values will exist in the BST.
 */

package Tree;

public class LowestCommonAncestorOfABinarySearchTree {
    /**
     * Solution 1: Recursion
     *
     * Much like Solution 2 of finding LCA of a general binary tree.
     *
     * If p and q are on different size of root, then their LCA must be the root. If they are on same side, we can
     * traverse on step deeper into left or right child. Base case is when root is null or value equals either p or q.
     */
    class Solution1 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (p.val > q.val) return lowestCommonAncestor(root, q, p); // make sure p is smaller
            if (root == null || root == p || root == q) return root; // base cases
            if (root.val > p.val && root.val < q.val) return root;
            if (root.val < p.val) return lowestCommonAncestor(root.right, p, q);
            else return lowestCommonAncestor(root.left, p, q);
        }
    }

    /**
     * Solution 2: Recursion
     *
     * Same idea as Solution 1 but much simpler implementation. Noticeable change: Because it is a BST, We don't need
     * to check for nulls because we know exactly where to search and won't go to null branches; No need to make p <
     * q, we can simply compare root value with min and max of both p and q and decide whether to go to left or
     * right, otherwise we return root. Elegant solution.
     */
    class Solution2 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root.val < Math.min(p.val, q.val)) return lowestCommonAncestor(root.right, p, q);
            else if (root.val > Math.max(p.val, q.val)) return lowestCommonAncestor(root.left, p, q);
            else return root;
        }
    }

    /**
     * Solution 3: Iteration
     *
     * Surprisingly concise iterative solution.
     */
    class Solution3 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            while ((root.val - p.val) * (root.val - q.val) > 0) {
                root = root.val < Math.min(p.val, q.val) ? root.right : root.left;
            }
            return root;
        }
    }
}
