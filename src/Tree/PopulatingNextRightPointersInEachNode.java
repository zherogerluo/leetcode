/**
 * LeetCode #116, medium
 *
 * Given a binary tree

 struct TreeLinkNode {
 TreeLinkNode *left;
 TreeLinkNode *right;
 TreeLinkNode *next;
 }
 Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should
 be set to NULL.

 Initially, all next pointers are set to NULL.

 Note:

 You may only use constant extra space.
 Recursive approach is fine, implicit stack space does not count as extra space for this problem.
 You may assume that it is a perfect binary tree (ie, all leaves are at the same level, and every parent has two
 children).
 Example:

 Given the following perfect binary tree,

      1
    /  \
   2    3
  / \  / \
 4  5  6  7
 After calling your function, the tree should look like:

      1 -> NULL
    /  \
   2 -> 3 -> NULL
  / \  / \
 4->5->6->7 -> NULL
 */

package Tree;

import java.util.*;

public class PopulatingNextRightPointersInEachNode {
    /**
     * Definition for binary tree with next pointer.
     */
    public class TreeLinkNode {
        int val;
        TreeLinkNode left, right, next;
        TreeLinkNode(int x) { val = x; }
    }

    /**
     * Solution 1: Recursion, DFS
     *
     * Do a pre-order traversal, for every node, connect its left and right children, AND connect its right child to
     * its next node's left child. Check for nulls.
     */
    class Solution1 {
        public void connect(TreeLinkNode root) {
            if (root == null) return;
            if (root.left != null) root.left.next = root.right;
            if (root.next != null && root.right != null) root.right.next = root.next.left;
            connect(root.left);
            connect(root.right);
        }
    }

    /**
     * Solution 2: Iterative, BFS
     *
     * Do a level-order traversal, and create links for each level. Tricky part is to create only k-1 links for a
     * level with k nodes, but don't do it without visiting the last node, because we still need to traverse last
     * node's children.
     */
    class Solution2 {
        public void connect(TreeLinkNode root) {
            if (root == null) return;
            Queue<TreeLinkNode> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) { // don't do a size-1 loop here
                    TreeLinkNode cur = queue.poll();
                    if (i < size-1) cur.next = queue.peek(); // create link for only size-1 nodes in this level
                    if (cur.left != null) queue.offer(cur.left);
                    if (cur.right != null) queue.offer(cur.right);
                }
            }
        }
    }
}
