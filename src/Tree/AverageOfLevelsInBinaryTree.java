/**
 * LeetCode #637, easy
 *
 * Given a non-empty binary tree, return the average value of the nodes on each level in the form of an array.
 * Example 1:
 * Input:
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * Output: [3, 14.5, 11]
 * Explanation:
 * The average value of nodes on level 0 is 3,  on level 1 is 14.5, and on level 2 is 11. Hence return [3, 14.5, 11].
 * Note:
 * The range of node's value is in the range of 32-bit signed integer.
 */

package Tree;

import java.util.*;

public class AverageOfLevelsInBinaryTree {
    /**
     * Solution 1: Level-order traversal (BFS)
     *
     * Typical level order traversal using queue. For each level, poll the nodes in this level, sum and count to get
     * average. Note that sum needs to be either long or double type to avoid overflow.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution1 {
        public List<Double> averageOfLevels(TreeNode root) {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            List<Double> res = new ArrayList<>();
            while (!queue.isEmpty()) {
                int size = queue.size();
                double sum = 0;
                for (int i = 0; i < size; i++) {
                    TreeNode node = queue.poll();
                    sum += node.val;
                    if (node.left != null) queue.offer(node.left);
                    if (node.right != null) queue.offer(node.right);
                }
                res.add(sum / size);
            }
            return res;
        }
    }

    /**
     * Solution 2: Recursive pre-order traversal (DFS)
     *
     * Recursively add node value to a temporary list of lists according to the level, and post-process the temporary
     * list to get the final average values. Again sum needs to be long or double.
     *
     * Time complexity: O(n). Space complexity: O(log(n)) average.
     */
    class Solution2 {
        public List<Double> averageOfLevels(TreeNode root) {
            List<List<Integer>> lists = new ArrayList<>();
            collectVals(root, lists, 0);
            List<Double> res = new ArrayList<>();
            for (List<Integer> list : lists) {
                double sum = 0;
                for (int val : list) sum += val;
                res.add(sum / list.size());
            }
            return res;
        }

        /* Collect tree node values into given lists according to the level of the node */
        private void collectVals(TreeNode root, List<List<Integer>> lists, int level) {
            if (root == null) return;
            if (lists.size() == level) lists.add(new ArrayList<>()); // don't forget to initialize new list
            lists.get(level).add(root.val);
            collectVals(root.left, lists, level + 1);
            collectVals(root.right, lists, level + 1);
        }
    }
}
