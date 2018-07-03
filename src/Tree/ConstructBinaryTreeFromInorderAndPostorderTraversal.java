/**
 * LeetCode #106, medium
 *
 * Given inorder and postorder traversal of a tree, construct the binary tree.

 Note:
 You may assume that duplicates do not exist in the tree.

 For example, given

 inorder = [9,3,15,20,7]
 postorder = [9,15,7,20,3]
 Return the following binary tree:

   3
  / \
 9  20
   /  \
  15  7
 */

package Tree;

public class ConstructBinaryTreeFromInorderAndPostorderTraversal {
    /**
     * Solution 1: Recursion
     *
     * This problem is almost exactly the same as Problem #105, except that we start from end of both arrays and
     * first build right child instead of left. Here is a recursive solution adapted that from Problem #105. Similar
     * thing can be done for the iterative solution and others.
     */
    class Solution1 {
        int i, j;

        public TreeNode buildTree(int[] inorder, int[] postorder) {
            i = inorder.length-1; j = postorder.length-1;
            return build(inorder, postorder, null);
        }

        private TreeNode build(int[] inorder, int[] postorder, TreeNode bound) {
            if (j < 0 || (bound != null && inorder[i] == bound.val)) return null;
            TreeNode root = new TreeNode(postorder[j--]);
            root.right = build(inorder, postorder, root);
            i--;
            root.left = build(inorder, postorder, bound);
            return root;
        }
    }
}
