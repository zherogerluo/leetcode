/**
 * LeetCode #113, medium
 *
 * Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.
 *
 * Note: A leaf is a node with no children.
 *
 * Example:
 *
 * Given the below binary tree and sum = 22,
 *
 *       5
 *      / \
 *     4   8
 *    /   / \
 *   11  13  4
 *  /  \    / \
 * 7    2  5   1
 * Return:
 *
 * [
 *    [5,4,11,2],
 *    [5,8,4,5]
 * ]
 */

package Tree;

import java.util.*;

public class PathSumTwo {
    /**
     * Solution 1: Recursion, DFS
     *
     * Same as Path Sum, except using a temporary list to store current path so far, add node and remove node in the
     * beginning and ending of the helper method.
     *
     * Time complexity: O(n). Space complexity: O(log(n)) average, not including result.
     */
    class Solution1 {
        public List<List<Integer>> pathSum(TreeNode root, int sum) {
            List<List<Integer>> res = new ArrayList<>();
            pathSum(root, sum, res, new ArrayList<>(), 0);
            return res;
        }

        private void pathSum(TreeNode root, int sum, List<List<Integer>> res, List<Integer> curList, int curSum) {
            if (root == null) return;
            curSum += root.val;
            curList.add(root.val);
            if (root.left == null && root.right == null && curSum == sum) {
                res.add(new ArrayList<>(curList));
            }
            pathSum(root.left, sum, res, curList, curSum);
            pathSum(root.right, sum, res, curList, curSum);
            curList.remove(curList.size()-1);
        }
    }
}
