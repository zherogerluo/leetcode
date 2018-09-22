/**
 * LeetCode #543, easy
 *
 * Given a binary tree, you need to compute the length of the diameter of the tree. The diameter of a binary tree is
 * the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.
 *
 * Example:
 * Given a binary tree
 *           1
 *          / \
 *         2   3
 *        / \
 *       4   5
 * Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].
 *
 * Note: The length of path between two nodes is represented by the number of edges between them.
 */

package Tree;

public class DiameterOfBinaryTree {
    /**
     * Solution 1: Recursion
     *
     * This problem is essentially finding the max depth of a tree. During this process, the diameter of current
     * subtree can be calculated as a side product. The idea is to recursively calculate max depth while keeping
     * track of current max diameter, which can be updated to be sum of left and right depths plus two. The path of
     * diameter has to be rooted somewhere, and the max depth search recursively go through all the nodes, meaning
     * that the diameter is guaranteed to be found for some root. Without the part of updating diameter, this
     * solutino is merely max depth problem.
     *
     * Time complexity: O(n). Space complexity: O(log(n)) average.
     */
    class Solution1 {
        public int diameterOfBinaryTree(TreeNode root) {
            int[] max = new int[1];
            maxDepth(root, max);
            return max[0];
        }

        private int maxDepth(TreeNode root, int[] max) {
            if (root == null) return -1;
            int left = maxDepth(root.left, max), right = maxDepth(root.right, max);
            max[0] = Math.max(max[0], left + right + 2); // update max with length of path passing this root
            return Math.max(left, right) + 1;
        }
    }
}
