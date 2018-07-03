/**
 * LeetCode #124, hard
 *
 * Given a non-empty binary tree, find the maximum path sum.

 For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree along
 the parent-child connections. The path must contain at least one node and does not need to go through the root.

 Example 1:

 Input: [1,2,3]

   1
  / \
 2   3

 Output: 6
 Example 2:

 Input: [-10,9,20,null,null,15,7]

  -10
  / \
 9  20
   /  \
 15   7

 Output: 42
 */

package Tree;

public class BinaryTreeMaximumPathSum {
    /**
     * Solution 1: Recursion, DFS
     *
     * The max sum path should involve at least one node as root, it could be root itself alone, or root + left max,
     * or root + right max, or root + left and right max both. The idea is to calculate the max single branch for
     * each child as we do a traversal, and update the max path sum for this node as root.
     */
    class Solution1 {
        int res;

        public int maxPathSum(TreeNode root) {
            res = Integer.MIN_VALUE;
            maxBranch(root);
            return res;
        }

        /* Returns the max sum of single branch rooted at root, and update the max path sum found for this root. */
        private int maxBranch(TreeNode root) {
            if (root == null) return 0;
            // throw away the negative result, we don't need it
            int leftMax = Math.max(0, maxBranch(root.left)), rightMax = Math.max(0, maxBranch(root.right));
            res = Math.max(res, root.val + leftMax + rightMax); // update result
            return root.val + Math.max(leftMax, rightMax); // return the max branch sum for this root
        }
    }
}
