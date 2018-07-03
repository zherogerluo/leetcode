/**
 * LeetCode #108, easy
 *
 * Given an array where elements are sorted in ascending order, convert it to a height balanced BST.

 For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees
 of every node never differ by more than 1.

 Example:

 Given the sorted array: [-10,-3,0,5,9],

 One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:

       0
     /  \
   -3   9
   /   /
 -10  5
 */

package Tree;

public class ConvertSortedArrayToBinarySearchTree {
    /**
     * Solution 1: Recursion, divide and conquer
     *
     * Divide range by half to make sure the BST is balanced. Build tree with mid value as the root, and divide to
     * two subtree problems.
     *
     * Tricky part: Remember to take care of null nodes.
     *
     * Time complexity: O(n). Space complexity: O(log(n)) (for a balanced tree, h = log(n)).
     */
    class Solution {
        public TreeNode sortedArrayToBST(int[] nums) {
            return build(nums, 0, nums.length-1);
        }

        private TreeNode build(int[] nums, int start, int end) {
            if (start == end) return new TreeNode(nums[start]);
            if (start > end) return null; // don't forget the null nodes
            int mid = start + (end - start) / 2;
            TreeNode root  = new TreeNode(nums[mid]);
            root.left = build(nums, start, mid-1);
            root.right = build(nums, mid+1, end);
            return root;
        }
    }
}
