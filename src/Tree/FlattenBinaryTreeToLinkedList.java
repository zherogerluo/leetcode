/**
 * LeetCode #114, medium
 *
 * Given a binary tree, flatten it to a linked list in-place.
 *
 * For example, given the following tree:
 *
 *     1
 *    / \
 *   2   5
 *  / \   \
 * 3   4   6
 * The flattened tree should look like:
 *
 * 1
 *  \
 *   2
 *    \
 *     3
 *      \
 *       4
 *        \
 *         5
 *          \
 *           6
 */

package Tree;

import java.util.*;

public class FlattenBinaryTreeToLinkedList {
    /**
     * Solution 1: Recursion, naive
     *
     * Just recursively flatten left and right branches, then use a loop to find the non-null tail of flattened left
     * branch and concatenate with the flattened right branch. The while loop takes O(log(n)) time, so the total time
     * complexity is expected to be O(n * log(n)).
     *
     * Time complexity: O(n * log(n)). Space complexity: O(log(n)). Both average.
     */
    class Solution1 {
        public void flatten(TreeNode root) {
            if (root == null) return;
            flatten(root.left);
            flatten(root.right);
            if (root.left == null) return;
            TreeNode left = root.left;
            while (left.right != null) left = left.right; // find the tail of flattened left branch
            left.right = root.right; // concatenate with right branch
            root.right = root.left;
            root.left = null; // don't forget to set left branch to null
        }
    }

    /**
     * Solution 2: Recursion, pre-order traversal
     *
     * Modify the recursive method to return the tail node (could be null) of the flattened tree. This way, left and
     * right concatenation takes O(1) time. Note that after flattening, the head nodes of flattened tree will stay in
     * place (root.left and root.right).
     *
     * This solution is essentially pre-order traversal.
     *
     * Time complexity: O(n). Space complexity: O(log(n)) average.
     */
    class Solution2 {
        public void flatten(TreeNode root) {
            helper(root);
        }

        /* Flattens the given tree and returns the tail node */
        private TreeNode helper(TreeNode root) {
            if (root == null) return null;
            TreeNode leftTail = helper(root.left), rightTail = helper(root.right);
            if (leftTail == null && rightTail == null) return root;
            if (leftTail == null) return rightTail;
            leftTail.right = root.right;
            root.right = root.left;
            root.left = null;
            return rightTail == null ? leftTail : rightTail;
        }
    }

    /**
     * Solution 3: Recursion, post-order traversal
     *
     * This solution is very similar to Solution 2 in Problem 538 Convert BST to Greater Tree. The basic idea is to
     * perform post-order traversal and flatten tree during the process, and continuously keep track of current
     * flattened tree head (similar to building sum in Problem 538).
     *
     * This solution looks more elegant than Solution 2, but harder to come up with.
     */
    class Solution3 {
        public void flatten(TreeNode root) {
            flatten(root, null);
        }

        private TreeNode flatten(TreeNode root, TreeNode head) {
            if (root == null) return head; // if null, we don't update the flattened result
            head = flatten(root.right, head); // flatten right branch and update the head
            head = flatten(root.left, head); // flatten left branch and update the head
            root.right = head; // put head to the right branch
            root.left = null; // nullify left branch
            return root; // now this tree has been flattened, return the root as new flattened head
        }
    }

    /**
     * Solution 4: Iterative with stack, top-down approach
     *
     * This solution is essentially a pre-order traversal and building the flattened tree top-down in the traversal.
     * By definition in the problem, the flattened tree is the pre-order traversal of the tree, so it is natural to
     * use pre-order traversal. Here, the stack stores nodes that have not been visited, as we traverse, the popped
     * node should be connect with the next to-be-visited node on its right branch, and the left branch should be set
     * as null.
     */
    class Solution4 {
        public void flatten(TreeNode root) {
            if (root == null) return;
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                root = stack.pop(); // current node being visited
                if (root.right != null) stack.push(root.right);
                if (root.left != null) stack.push(root.left);
                if (!stack.isEmpty()) root.right = stack.peek(); // connect current node to next to-be-visited node
                root.left = null; // don't forget to nullify left child
            }
        }
    }

    /**
     * Solution 5: Iterative, constant space
     *
     * Probably the most elegant solution. The idea is similar to Solution 1 and 2 - append right branch to the end of
     * left branch. The difference is that, Solution 1 or 2 does this after flattening both left and right branches,
     * while this solution does this without worrying about flattening first. It simply finds the right most node in
     * the left branch, and append right branch to it, then marches down from root to right child as next root, and
     * repeatedly does this until reaching the end. Comparing with Solution 1 and 2, this is a top-down approach,
     * finishing parent tree first then the subtrees. Very clever, easy to come up with and write up, and most
     * importantly, it is O(1) space.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution5 {
        public void flatten(TreeNode root) {
            while (root != null) {
                TreeNode left = root.left;
                if (left != null) {
                    while (left.right != null) left = left.right; // find the right most non-null node in left branch
                    left.right = root.right; // attach right branch to it
                    root.right = root.left; // move it to the right
                    root.left = null; // don't forget to nullify left child
                } // if left child is null, do nothing
                root = root.right; // march down to right child as next root
            }
        }
    }
}
