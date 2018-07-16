/**
 * LeetCode #236, medium
 *
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

 According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as
 the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”

 Given the following binary tree:  root = [3,5,1,6,2,0,8,null,null,7,4]

        _______3______
       /              \
   ___5__          ___1__
  /      \        /      \
 6      _2       0       8
       /  \
      7   4
 Example 1:

 Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
 Output: 3
 Explanation: The LCA of of nodes 5 and 1 is 3.
 Example 2:

 Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
 Output: 5
 Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself
 according to the LCA definition.
 Note:

 All of the nodes' values will be unique.
 p and q are different and both values will exist in the binary tree.
 */

package Tree;

import java.util.*;

public class LowestCommonAncestorOfABinaryTree {
    /**
     * Solution 1: Recursive DFS
     *
     * Use DFS to find path to target nodes, and iterate through both paths until we see a different node.
     *
     * Time complexity: O(n). Space complexity: O(log(n)).
     */
    class Solution1 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            List<TreeNode> pathP = new ArrayList<>();
            List<TreeNode> pathQ = new ArrayList<>();
            findPath(root, p, pathP);
            findPath(root, q, pathQ);
            Set<TreeNode> setP = new HashSet<>(pathP);
            TreeNode res = root;
            // trace down one path until a node is not present in the other path - diverging node
            for (TreeNode cur : pathQ) {
                if (setP.contains(cur)) res = cur;
                else break;
            }
            return res;
        }

        /* Returns true if we found a path to target node from the root. */
        private boolean findPath(TreeNode root, TreeNode target, List<TreeNode> path) {
            if (root == null) return false;
            path.add(root);
            if (root == target) return true;
            boolean found = findPath(root.left, target, path) ||
                            findPath(root.right, target, path);
            if (!found) path.remove(path.size()-1); // remove the marked node only if not found
            return found;
        }
    }

    /**
     * Solution 2: Recursive DFS
     *
     * This solution is a bit difficult to understand. Basically this function will return the LCA, or any one of p
     * and q if it is found. For each root, it searches left and right branch. If both returns non-null, it means
     * that each branch contains one of p and q, so LCA is root. Otherwise if one branch returns non-null while the
     * other returns null, that means both p and q lands in the non-null branch and the returned node is the LCA.
     *
     * The more appropriate name for this function should be lowestCommonAncestorOrTargetNode. The validity of this
     * algorithm relies heavily on the underlying assumption that we are looking for LCA of two nodes which are
     * guaranteed to be present in the tree.
     *
     * Time complexity: O(n). Space complexity: O(log(n)).
     */
    class Solution2 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null || root == p || root == q) return root;
            TreeNode left = lowestCommonAncestor(root.left, p, q);
            TreeNode right = lowestCommonAncestor(root.right, p, q);
            if (left != null && right != null) return root; // both are non-null: p and q are on different branch
            return left != null ? left : right; // one is non-null: LCA is found
        }
    }
}
