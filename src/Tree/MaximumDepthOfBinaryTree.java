/**
 * LeetCode #104, easy
 *
 * Given a binary tree, find its maximum depth.

 The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

 Note: A leaf is a node with no children.

 Example:

 Given binary tree [3,9,20,null,null,15,7],

   3
  / \
 9  20
   /  \
  15  7
 return its depth = 3.
 */

package Tree;

import java.util.*;

public class MaximumDepthOfBinaryTree {
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
     * Solution 1: Recursion, DFS,
     *
     * Trivial solution.
     */
    class Solution1 {
        public int maxDepth(TreeNode root) {
            if (root == null) return 0;
            return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
        }
    }

    /**
     * Solution 2: Iterative, BFS (level-order)
     *
     * Standard BFS, increment depth for every level. Important note is to poll queue by its initial size.
     */
    class Solution2 {
        public int maxDepth(TreeNode root) {
            if (root == null) return 0;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            int depth = 0;
            while (!queue.isEmpty()) {
                depth++;
                int size = queue.size();
                for (int i = 0; i < size; i++) { // must have this loop to consolidate a single level polling together
                    root = queue.poll();
                    if (root.left != null) queue.offer(root.left);
                    if (root.right != null) queue.offer(root.right);
                }
            }
            return depth;
        }
    }

    /**
     * Solution 3: Iterative, DFS using stack
     *
     * Use two stacks to record nodes and corresponding depths. This is basically simulating the recursive solution.
     */
    class Solution3 {
        public int maxDepth(TreeNode root) {
            if (root == null) return 0;
            Stack<TreeNode> nodes = new Stack<>();
            Stack<Integer> depths = new Stack<>();
            nodes.push(root);
            depths.push(1);
            int res = 1;
            while (!nodes.isEmpty()) {
                root = nodes.pop();
                int depth = depths.pop();
                res = Math.max(res, depth);
                if (root.left != null) {
                    nodes.push(root.left);
                    depths.push(depth + 1);
                }
                if (root.right != null) {
                    nodes.push(root.right);
                    depths.push(depth + 1);
                }
            }
            return res;
        }
    }
}
