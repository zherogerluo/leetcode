/**
 * LeetCode #863, medium
 *
 * We are given a binary tree (with root node root), a target node, and an integer value K.
 *
 * Return a list of the values of all nodes that have a distance K from the target node.  The answer can be returned
 * in any order.
 *
 * Example 1:
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, K = 2
 *
 * Output: [7,4,1]
 *
 * Explanation:
 * The nodes that are a distance 2 from the target node (with value 5)
 * have values 7, 4, and 1.
 *
 *      3
 *    /    \
 *   5      1
 *  / \    / \
 * 6   2  0   8
 *    / \
 *   7   4
 *
 * Note that the inputs "root" and "target" are actually TreeNodes.
 * The descriptions of the inputs above are just serializations of these objects.
 */

package Tree;

import java.util.*;

public class AllNodesDistanceKInBinaryTree {
    /**
     * Solution 1: DFS
     *
     * First find a path from root to target, then find nodes that are distance K from target, distance K-2 from next
     * in the path, distance K-3 from the path, etc. Tricky things are that we need to include node in the path that
     * is distance K away as well, and that we need to search the other branch that is not along the path.
     *
     * Time complexity: O(n). Space complexity: O(log(n)) average (call stack).
     */
    class Solution1 {
        public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
            List<Integer> res = new ArrayList<>();
            LinkedList<TreeNode> path = new LinkedList<>();
            findReversePath(root, target, path);
            // search from target
            search(target, K, res);
            // search along the path
            int curDist = 1; // distance between current node and the target
            TreeNode prev = target; // node last searched
            for (TreeNode node : path) {
                if (curDist == K) res.add(node.val); // the node along the path itself is a candidate
                TreeNode toSearch = node.left == prev ? node.right : node.left; // avoid last searched node
                search(toSearch, K - curDist - 1, res); // search the other branch
                prev = node; // don't forget to update the previous searched node
                curDist++;
            }
            return res;
        }

        /* Returns a reversed path from target (exclusive) to root (inclusive) */
        private boolean findReversePath(TreeNode root, TreeNode target, LinkedList<TreeNode> path) {
            if (root == null) return false;
            if (root == target) return true; // exclude the target node
            path.addFirst(root);
            boolean found = findReversePath(root.left, target, path) || findReversePath(root.right, target, path);
            if (!found) path.removeFirst();
            return found;
        }

        /* Searches the children of given root that is within given distance away, and append their values to res */
        private void search(TreeNode root, int distance, List<Integer> res) {
            if (root == null) return;
            if (distance == 0) res.add(root.val);
            else {
                search(root.left, distance-1, res);
                search(root.right, distance-1, res);
            }
        }
    }

    /**
     * Solution 2: Graph BFS
     *
     * The idea is to convert the tree into an undirected graph, then the distance K search would be a
     * straightforward BFS.
     */
    // TODO
}
