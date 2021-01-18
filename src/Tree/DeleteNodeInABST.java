/**
 * LeetCode #450, medium
 *
 * Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root node
 * reference (possibly updated) of the BST.
 *
 * Basically, the deletion can be divided into two stages:
 *
 * Search for a node to remove.
 * If the node is found, delete the node.
 * Follow up: Can you solve it with time complexity O(height of tree)?
 *
 * Example 1:
 *
 * Input: root = [5,3,6,2,4,null,7], key = 3
 * Output: [5,4,6,2,null,null,7]
 * Explanation: Given key to delete is 3. So we find the node with value 3 and delete it.
 * One valid answer is [5,4,6,2,null,null,7], shown in the above BST.
 * Please notice that another valid answer is [5,2,6,null,4,null,7] and it's also accepted.
 *
 * Example 2:
 *
 * Input: root = [5,3,6,2,4,null,7], key = 0
 * Output: [5,3,6,2,4,null,7]
 * Explanation: The tree does not contain a node with value = 0.
 *
 * Example 3:
 *
 * Input: root = [], key = 0
 * Output: []
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [0, 10^4].
 * -10^5 <= Node.val <= 10^5
 * Each node has a unique value.
 * root is a valid binary search tree.
 * -10^5 <= key <= 10^5
 */

package Tree;

public class DeleteNodeInABST {
    /**
     * Solution 1:
     *
     * Simple search the node and replace it with one of its children. If both children are not null, append one to the
     * leaf of the other such that it forms a valid BST.
     *
     * Note: 1) The target may not exist in the given BST 2) The target node may be root, which does not have a parent.
     */
    class Solution1 {
        public TreeNode deleteNode(TreeNode root, int key) {
            TreeNode parent = null, toRemove = root;
            while (toRemove != null && toRemove.val != key) {
                parent = toRemove;
                if (key < toRemove.val) {
                    toRemove = toRemove.left;
                } else {
                    toRemove = toRemove.right;
                }
            }
            if (toRemove == null) {
                return root;
            }
            TreeNode toReplace = null;
            if (toRemove.left != null && toRemove.right != null) {
                toReplace = toRemove.right;
                TreeNode tmp = toRemove.right;
                while (tmp.left != null) {
                    tmp = tmp.left;
                }
                tmp.left = toRemove.left;
            } else if (toRemove.left != null) {
                toReplace = toRemove.left;
            } else if (toRemove.right != null) {
                toReplace = toRemove.right;
            }
            if (parent == null) {
                return toReplace;
            }
            if (key < parent.val) {
                parent.left = toReplace;
            } else {
                parent.right = toReplace;
            }
            return root;
        }
    }
}
