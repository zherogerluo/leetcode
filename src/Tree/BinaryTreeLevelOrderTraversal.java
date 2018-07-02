/**
 * LeetCode #102, medium
 *
 * Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).

 For example:
 Given binary tree [3,9,20,null,null,15,7],
   3
  / \
 9  20
   /  \
  15  7
 return its level order traversal as:
 [
 [3],
 [9,20],
 [15,7]
 ]
 */

package Tree;

import java.util.*;

public class BinaryTreeLevelOrderTraversal {
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
     * Solution 1: Iterative, level-order traversal using a queue
     *
     * Typical level-order traversal, breadth-first-search. One thing to note is to record initial size of queue and
     * only poll this many nodes for one level.
     *
     * Time complexity: O(n). Space complexity: O(n) (queue size will be n/2 for leaf level).
     */
    class Solution1 {
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) return res;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    root = queue.poll();
                    list.add(root.val);
                    if (root.left != null) queue.offer(root.left);
                    if (root.right != null) queue.offer(root.right);
                }
                res.add(list);
            }
            return res;
        }
    }

    /**
     * Solution 2: Recursion, depth-first-search
     *
     * Do DFS and add the node to the given depth. This won't work if the required result is a single list (but we
     * can still concatenate lists to fulfill that). Not a standard queue-based BFS but good idea.
     *
     * Time complexity: O(n). Space complexity: O(h) where h is tree height.
     */
    class Solution2 {
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            levelTraverse(root, 0, res);
            return res;
        }

        /* Recursively add the root value to the list of nodes of given depth */
        private void levelTraverse(TreeNode root, int depth, List<List<Integer>> res) {
            if (root == null) return;
            if (res.size() == depth) res.add(new ArrayList<>());
            res.get(depth).add(root.val);
            levelTraverse(root.left, depth+1, res);
            levelTraverse(root.right, depth+1, res);
        }
    }
}
