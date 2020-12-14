/**
 * LeetCode #951, medium
 *
 * For a binary tree T, we can define a flip operation as follows: choose any node, and swap the left and right child
 * subtrees.
 *
 * A binary tree X is flip equivalent to a binary tree Y if and only if we can make X equal to Y after some number of
 * flip operations.
 *
 * Given the roots of two binary trees root1 and root2, return true if the two trees are flip equivelent or false
 * otherwise.
 *
 * Example 1:
 *
 * Flipped Trees Diagram
 * Input: root1 = [1,2,3,4,5,6,null,null,null,7,8], root2 = [1,3,2,null,6,4,5,null,null,null,null,8,7]
 * Output: true
 * Explanation: We flipped at nodes with values 1, 3, and 5.
 *
 * Example 2:
 *
 * Input: root1 = [], root2 = []
 * Output: true
 *
 * Example 3:
 *
 * Input: root1 = [], root2 = [1]
 * Output: false
 *
 * Example 4:
 *
 * Input: root1 = [0,null,1], root2 = []
 * Output: false
 *
 * Example 5:
 *
 * Input: root1 = [0,null,1], root2 = [0,1]
 * Output: true
 *
 * Constraints:
 *
 * The number of nodes in each tree is in the range [0, 100].
 * Each tree will have unique node values in the range [0, 99].
 */

package Tree;

import java.util.*;

public class FlipEquivalentBinaryTrees {
    /**
     * Solution 1: Recursive DFS
     *
     * Typical DFS recursion, the edge cases are a bit complicated.
     */
    class Solution1 {
        public boolean flipEquiv(TreeNode root1, TreeNode root2) {
            if (root1 == null && root2 == null) {
                return true;
            }
            if (root1 == null && root2 != null || root1 != null && root2 == null || root1.val != root2.val) {
                return false;
            }
            return flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right) ||
                    flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left);
        }
    }

    /**
     * Solution 2: Iterative BFS
     *
     * Typical BFS using two queues. Need to make sure the child nodes are inserted into each queue in right order
     * depending on whether the child nodes need to be flipped.
     */
    class Solution2 {
        public boolean flipEquiv(TreeNode root1, TreeNode root2) {
            if (root1 == null && root2 == null) {
                return true;
            }
            if (!isEqual(root1, root2)) {
                return false;
            }
            Deque<TreeNode> queue1 = new ArrayDeque<>(), queue2 = new ArrayDeque<>();
            queue1.offer(root1);
            queue2.offer(root2);
            while (!queue1.isEmpty() && !queue2.isEmpty()) {
                root1 = queue1.poll();
                root2 = queue2.poll();
                if (isEqual(root1.left, root2.left) && isEqual(root1.right, root2.right)) {
                    if (root1.left != null) queue1.offer(root1.left);
                    if (root1.right != null) queue1.offer(root1.right);
                    if (root2.left != null) queue2.offer(root2.left);
                    if (root2.right != null) queue2.offer(root2.right);
                } else if (isEqual(root1.left, root2.right) && isEqual(root1.right, root2.left)) {
                    if (root1.left != null) queue1.offer(root1.left);
                    if (root1.right != null) queue1.offer(root1.right);
                    if (root2.right != null) queue2.offer(root2.right);
                    if (root2.left != null) queue2.offer(root2.left);
                } else {
                    return false;
                }
            }
            return queue1.isEmpty() && queue2.isEmpty();
        }

        private boolean isEqual(TreeNode root1, TreeNode root2) {
            if (root1 == null || root2 == null) {
                return root1 == root2;
            }
            return root1.val == root2.val;
        }
    }
}
