/**
 * LeetCode #1305, medium
 *
 * Given two binary search trees root1 and root2.
 *
 * Return a list containing all the integers from both trees sorted in ascending order.
 *
 * Example 1:
 *
 * Input: root1 = [2,1,4], root2 = [1,0,3]
 * Output: [0,1,1,2,3,4]
 *
 * Example 2:
 *
 * Input: root1 = [0,-10,10], root2 = [5,1,7,0,2]
 * Output: [-10,0,0,1,2,5,7,10]
 *
 * Example 3:
 *
 * Input: root1 = [], root2 = [5,1,7,0,2]
 * Output: [0,1,2,5,7]
 *
 * Example 4:
 *
 * Input: root1 = [0,-10,10], root2 = []
 * Output: [-10,0,10]
 *
 * Example 5:
 *
 * Input: root1 = [1,null,8], root2 = [8,1]
 * Output: [1,1,8,8]
 *
 * Constraints:
 *
 * Each tree has at most 5000 nodes.
 * Each node's value is between [-10^5, 10^5].
 */

package Tree;

import java.util.*;

public class AllElementsInTwoBinarySearchTrees {
    /**
     * Solution 1: Iterative
     *
     * Iterative in-order traversal using two stacks, put the smaller one into the result. This is very much like the
     * merge sort algorithm. Note that we need to keep doing the loop until both stacks are empty to take advantage
     * the in-order logic and not duplicate code after the loop to take care any remaining items in the stacks.
     *
     * Time complexity: O(n). Space complexity: O(log(n)).
     */
    class Solution1 {
        public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
            List<Integer> res = new ArrayList<>();
            Deque<TreeNode> small = new ArrayDeque<>(), large = new ArrayDeque<>();
            while (root1 != null) {
                small.push(root1);
                root1 = root1.left;
            }
            while (root2 != null) {
                large.push(root2);
                root2 = root2.left;
            }
            while (!small.isEmpty() || !large.isEmpty()) { // note the condition
                // swap stacks when one is empty or val is flipped
                if (small.isEmpty() ||
                        (!large.isEmpty() && small.peek().val > large.peek().val)) {
                    Deque<TreeNode> tmp = small;
                    small = large;
                    large = tmp;
                }
                // ordinary in-order traversal
                TreeNode node = small.pop();
                res.add(node.val);
                node = node.right;
                while (node != null) {
                    small.push(node);
                    node = node.left;
                }
            }
            return res;
        }
    }

    /**
     * Solution 2: Recursive
     *
     * Recursive version of in-order traversal. Idea is to use two lists, one stores the already traversed numbers and
     * the other store the finalized results. When reaching a node, we go to left, compare the value with candidates
     * and move anything that is smaller into the final results, add the value of self, and continue with right branch.
     * Tricky to come up with but the idea is really naive and simple.
     *
     * Time complexity: O(n). Space complexity: O(log(n)).
     */
    class Solution2 {
        public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
            LinkedList<Integer> res = new LinkedList<>(), tmp = new LinkedList<>();
            inorder(root1, res, tmp); // effectively doing a normal in-order on root1 because res is empty
            inorder(root2, tmp, res);
            res.addAll(tmp); // pick up any remaining ones
            return res;
        }

        private void inorder(TreeNode root, LinkedList<Integer> candidates, LinkedList<Integer> finalized) {
            if (root == null) {
                return;
            }
            inorder(root.left, candidates, finalized);
            while (!candidates.isEmpty() && candidates.peekFirst() <= root.val) {
                finalized.offerLast(candidates.pollFirst());
            }
            finalized.offerLast(root.val);
            inorder(root.right, candidates, finalized);
        }
    }
}
