/**
 * LeetCode #105, medium
 *
 * Given preorder and inorder traversal of a tree, construct the binary tree.

 Note:
 You may assume that duplicates do not exist in the tree.

 For example, given

 preorder = [3,9,20,15,7]
 inorder = [9,3,15,20,7]
 Return the following binary tree:

   3
  / \
 9  20
   /  \
  15  7
 */

package Tree;

import java.util.*;

public class ConstructBinaryTreeFromPreorderAndInorderTraversal {
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
     * Solution 1: Divide and conquer
     *
     * Preorder: root[left][right]. Inorder: [left]root[right]. We know that the starting element in preorder is the
     * root, we then search for it in inorder and split both arrays to two parts and recursively build trees on them.
     *
     * Tricky part: The indexes after splitting need to be taken care of very cautiously.
     *
     * Time complexity: O(n^2) because of linear search. Space complexity: O(h) because of call stack.
     */
    class Solution1 {
        public TreeNode buildTree(int[] preorder, int[] inorder) {
            return buildTree(preorder, 0, preorder.length, inorder, 0, inorder.length);
        }

        private TreeNode buildTree(int[] preorder, int pstart, int pend, int[] inorder, int istart, int iend) {
            if (pstart >= pend || istart >= iend) return null;
            TreeNode root = new TreeNode(preorder[pstart]);
            int i = search(inorder, preorder[pstart], istart, iend);
            // the new pstart and pend is associated with istart as well
            root.left = buildTree(preorder, pstart+1, pstart+i+1-istart, inorder, istart, i);
            root.right = buildTree(preorder, pstart+i+1-istart, pend, inorder, i+1, iend);
            return root;
        }

        private int search(int[] nums, int target, int start, int end) {
            for (; start < end; start++) {
                if (nums[start] == target) return start;
            }
            return -1;
        }
    }

    /**
     * Solution 2: Divide and conquer, using hash map
     *
     * Same as Solution 1 except using a hash map for O(1) index look up in inorder.
     *
     * Time complexity: O(n). Space complexity: O(h).
     */
    class Solution2 {
        public TreeNode buildTree(int[] preorder, int[] inorder) {
            Map<Integer, Integer> inorderIndexMap = new HashMap<>();
            for (int i = 0; i < inorder.length; i++) inorderIndexMap.put(inorder[i], i);
            return buildTree(preorder, 0, preorder.length, inorder, 0, inorder.length, inorderIndexMap);
        }

        private TreeNode buildTree(int[] preorder, int pstart, int pend, int[] inorder, int istart, int iend,
                                   Map<Integer, Integer> inorderIndexMap) {
            if (pstart >= pend || istart >= iend) return null;
            TreeNode root = new TreeNode(preorder[pstart]);
            int i = inorderIndexMap.get(root.val);
            root.left = buildTree(preorder, pstart+1, pstart+i+1-istart, inorder, istart, i, inorderIndexMap);
            root.right = buildTree(preorder, pstart+i+1-istart, pend, inorder, i+1, iend, inorderIndexMap);
            return root;
        }
    }

    /**
     * Solution 3: Recursion
     *
     * Cleanest solution so far, use simple recursion and global indexes. Key trick is to add a bound as argument to
     * specify when to stop when building this (sub)tree. If the preorder value is equal to the bound value, we know
     * we reached the bound and returns null. Otherwise, we build the root, build the left subtree with root itself
     * as the bound, and increment inorder index (very important) and build the right subtree with null bound. Null
     * bound here means don't check the bound.
     *
     * Tricky part: Usage of this bound. Increment inorder index to skip the root value in inorder array.
     *
     * Time complexity: O(n). Space complexity: O(h).
     */
    class Solution3 {
        int i, j;

        public TreeNode buildTree(int[] preorder, int[] inorder) {
            i = 0; j = 0;
            return build(preorder, inorder, null);
        }

        private TreeNode build(int[] preorder, int[] inorder, TreeNode bound) {
            if (i == preorder.length || (bound != null && bound.val == inorder[j])) return null;
            TreeNode root = new TreeNode(preorder[i++]);
            root.left = build(preorder, inorder, root);
            j++;
            root.right = build(preorder, inorder, bound);
            return root;
        }
    }

    /**
     * Solution 4: Iterative using stack
     *
     * Similar to in-order traversal. We put new node in stack and append to left child until preorder and inorder
     * matches, then we pop the node as we increment inorder index and compare the node value to inorder value. If no
     * match, we know there must be a right branch, then we make a right child from preorder and redo the above on
     * the right child, until we reach to the end.
     *
     * Tricky part: 1) Make sure to push the leftmost matching node to the stack as well, for easier code writing.
     *              2) While popping out nodes, stop at the last node whose value matches the inorder value.
     *              3) Check for array bound when trying to move to the right child
     *              4) Pre-processing: Set i = 1 and push the root to stack before the main loop.
     *
     * Time complexity: O(n). Space complexity: O(h).
     */
    class Solution4 {
        public TreeNode buildTree(int[] preorder, int[] inorder) {
            if (preorder == null || inorder == null || preorder.length == 0 || inorder.length == 0) return null;
            Stack<TreeNode> stack = new Stack<>();
            // pre-processing
            int i = 1, j = 0;
            TreeNode root = new TreeNode(preorder[0]), cur = root;
            stack.push(root);
            while (i < preorder.length) {
                // check value against node, not preorder[i], so that the leftmost node can be pushed in as well
                // alternatively we can do: while (preorder[i-1] != inorder[j])
                while (cur.val != inorder[j]) {
                    cur.left = new TreeNode(preorder[i++]);
                    cur = cur.left;
                    stack.push(cur);
                }
                // check inorder value against the stack top node, not cur
                while (!stack.isEmpty() && stack.peek().val == inorder[j]) {
                    cur = stack.pop();
                    j++;
                }
                if (i < preorder.length) { // check for array out of bound
                    cur.right = new TreeNode(preorder[i++]);
                    cur = cur.right;
                    stack.push(cur); // don't forget to push it to stack
                }
            }
            return root;
        }
    }
}
