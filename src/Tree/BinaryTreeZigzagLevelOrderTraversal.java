/**
 * LeetCode #103, medium
 *
 * Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).

 For example:
 Given binary tree [3,9,20,null,null,15,7],
   3
  / \
 9  20
   /  \
  15  7
 return its zigzag level order traversal as:
 [
 [3],
 [20,9],
 [15,7]
 ]
 */

package Tree;

import java.util.*;

public class BinaryTreeZigzagLevelOrderTraversal {
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
     * Solution 1: Iterative, standard level-order traversal BFS
     *
     * Standard level-order traversal, except to add value to start or end of list depending on the reverse boolean.
     *
     * Time complexity: O(n). Space complexity: O(n) because queue size is n/2 for leaf level.
     */
    class Solution1 {
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) return res;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            boolean reverse = false;
            while (!queue.isEmpty()) {
                int size = queue.size();
                List<Integer> list = new LinkedList<>(); // LinkedList may perform better than ArrayList here
                for (int i = 0; i < size; i++) {
                    root = queue.poll();
                    if (reverse) list.add(0, root.val);
                    else list.add(root.val);
                    if (root.left != null) queue.offer(root.left);
                    if (root.right != null) queue.offer(root.right);
                }
                res.add(list);
                reverse = !reverse; // don't forget this
            }
            return res;
        }
    }

    /**
     * Solution 2: Iterative, level-order traversal, BFS, with deque
     *
     * Key to this solution is to use deque vs. queue in standard BFS, and alternate polling end. An important
     * invariant to note is, after each level is done, the node in deque is strictly in original level-order, not
     * reversed. The only thing that is reversed, is which end to poll from.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) return res;
            Deque<TreeNode> queue = new ArrayDeque<>();
            queue.offer(root);
            boolean reverse = false;
            while (!queue.isEmpty()) {
                int size = queue.size();
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    if (reverse) root = queue.pollLast();
                    else root = queue.pollFirst();
                    list.add(root.val);
                    if (reverse) {
                        if (root.right != null) queue.offerFirst(root.right);
                        if (root.left != null) queue.offerFirst(root.left);
                    } else {
                        if (root.left != null) queue.offerLast(root.left);
                        if (root.right != null) queue.offerLast(root.right);
                    }
                }
                res.add(list);
                reverse = !reverse;
            }
            return res;
        }
    }
}
