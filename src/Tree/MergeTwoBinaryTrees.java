/**
 * LeetCode #617, easy
 *
 * Given two binary trees and imagine that when you put one of them to cover the other, some nodes of the two trees
 * are overlapped while the others are not.
 *
 * You need to merge them into a new binary tree. The merge rule is that if two nodes overlap, then sum node values
 * up as the new value of the merged node. Otherwise, the NOT null node will be used as the node of new tree.
 *
 * Example 1:
 * Input:
 * 	Tree 1                     Tree 2
 *           1                         2
 *          / \                       / \
 *         3   2                     1   3
 *        /                           \   \
 *       5                             4   7
 * Output:
 * Merged tree:
 * 	     3
 * 	    / \
 * 	   4   5
 * 	  / \   \
 * 	 5   4   7
 * Note: The merging process must start from the root nodes of both trees.
 */

package Tree;

import java.util.*;

public class MergeTwoBinaryTrees {
    /**
     * Solution 1: Recursion
     *
     * Recursively merge root nodes. No need to explicitly deal with t1 == null and t2 == null case.
     *
     * Time complexity: O(n). Space complexity: O(log(n)) average.
     */
    class Solution1 {
        public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
            if (t1 == null) return t2;
            if (t2 == null) return t1;
            TreeNode root = new TreeNode(t1.val + t2.val);
            root.left = mergeTrees(t1.left, t2.left);
            root.right = mergeTrees(t1.right, t2.right);
            return root;
        }
    }

    /**
     * Solution 2: Iterative DFS preorder traversal
     *
     * Use a stack to traverse two trees in preorder, and merge nodes if neither is null. Note this solution modifies
     * t1 instead of creating a new tree, which is harder to write. Level order BFS traversal is alike.
     */
    class Solution2 {
        public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
            if (t1 == null) return t2;
            if (t2 == null) return t1;
            Stack<TreeNode> stack = new Stack<>();
            stack.push(t1);
            stack.push(t2);
            while (!stack.isEmpty()) {
                TreeNode n2 = stack.pop(), n1 = stack.pop();
                n1.val += n2.val;
                if (n1.left == null) n1.left = n2.left;
                else if (n1.left != null && n2.left != null) {
                    stack.push(n1.left);
                    stack.push(n2.left);
                }
                if (n1.right == null) n1.right = n2.right;
                else if (n1.right != null && n2.right != null) {
                    stack.push(n1.right);
                    stack.push(n2.right);
                }
            }
            return t1;
        }
    }
}
