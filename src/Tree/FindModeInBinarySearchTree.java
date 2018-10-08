/**
 * LeetCode #501, easy
 *
 * Given a binary search tree (BST) with duplicates, find all the mode(s) (the most frequently occurred element) in
 * the given BST.
 *
 * Assume a BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys less than or equal to the node's key.
 * The right subtree of a node contains only nodes with keys greater than or equal to the node's key.
 * Both the left and right subtrees must also be binary search trees.
 *
 *
 * For example:
 * Given BST [1,null,2,2],
 *
 *    1
 *     \
 *      2
 *     /
 *    2
 *
 *
 * return [2].
 *
 * Note: If a tree has more than one mode, you can return them in any order.
 *
 * Follow up: Could you do that without using any extra space? (Assume that the implicit stack space incurred due to
 * recursion does not count).
 */

package Tree;

import java.util.*;

public class FindModeInBinarySearchTree {
    /**
     * Solution 1: In-order traversal
     *
     * Traverse the BST in order, keep track of last value, current count and max count. Once current count reaches
     * max, add value to the result; If current count exceeds max count, clear the result and add value to result.
     *
     * Time complexity: O(n). Space complexity: O(n) worst case (no duplicates)
     */
    class Solution1 {
        public int[] findMode(TreeNode root) {
            List<Integer> mode = new ArrayList<>();
            inorder(root, mode, new int[]{Integer.MIN_VALUE}, new int[]{0}, new int[]{0});
            int[] result = new int[mode.size()];
            int i = 0;
            for (int m : mode) result[i++] = m;
            return result;
        }

        private void inorder(TreeNode root, List<Integer> mode, int[] last, int[] curCount, int[] maxCount) {
            if (root == null) return;
            inorder(root.left, mode, last, curCount, maxCount);
            if (root.val == last[0]) curCount[0]++;
            else curCount[0] = 1;
            if (curCount[0] == maxCount[0]) mode.add(root.val);
            else if (curCount[0] > maxCount[0]) {
                mode.clear();
                mode.add(root.val);
                maxCount[0] = curCount[0];
            }
            // else do nothing
            last[0] = root.val;
            inorder(root.right, mode, last, curCount, maxCount);
        }
    }
}
