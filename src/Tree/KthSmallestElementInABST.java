/**
 * LeetCode #230, medium
 *
 * Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.

 Note:
 You may assume k is always valid, 1 ≤ k ≤ BST's total elements.

 Example 1:

 Input: root = [3,1,4,null,2], k = 1
   3
  / \
 1   4
  \
   2
 Output: 1

 Example 2:

 Input: root = [5,3,6,2,4,null,null,1], k = 3
       5
      / \
     3   6
    / \
   2   4
  /
 1
 Output: 3

 Follow up:
 What if the BST is modified (insert/delete operations) often and you need to find the kth smallest frequently? How
 would you optimize the kthSmallest routine?
 */

package Tree;

import java.util.*;

public class KthSmallestElementInABST {
    /**
     * Solution 1: Recursion, inorder traversal
     *
     * Recursive inorder traversal with count being recorded. If count reaches k, just return the node value.
     *
     * Time complexity: Average O(k * log(n)), worst O(nk). Space complexity: Average O(log(n)), worst O(n).
     */
    class Solution1 {
        int count = 0;

        public int kthSmallest(TreeNode root, int k) {
            int res = Integer.MIN_VALUE;
            if (root == null) return res;
            res = kthSmallest(root.left, k);
            if (res != Integer.MIN_VALUE) return res;
            count++;
            if (count == k) return root.val;
            return kthSmallest(root.right, k);
        }
    }

    /**
     * Solution 2: Iterative, inorder traversal
     *
     * Iterative inorder traversal. If count reaches k, return the node value.
     */
    class Solution2 {
        public int kthSmallest(TreeNode root, int k) {
            int count = 0;
            Stack<TreeNode> stack = new Stack<>();
            while (root != null || !stack.isEmpty()) {
                while (root != null) {
                    stack.push(root);
                    root = root.left;
                }
                root = stack.pop();
                count++;
                if (count == k) return root.val;
                root = root.right; // no null check here
            }
            return Integer.MIN_VALUE;
        }
    }
}
