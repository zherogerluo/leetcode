/**
 * LeetCode #95, medium
 *
 * Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.
 *
 * Example:
 *
 * Input: 3
 * Output:
 * [
 *   [1,null,3,2],
 *   [3,2,null,1],
 *   [3,1,null,null,2],
 *   [2,1,3],
 *   [1,null,2,null,3]
 * ]
 * Explanation:
 * The above output corresponds to the 5 unique BST's shown below:
 *
 *    1         3     3      2      1
 *     \       /     /      / \      \
 *      3     2     1      1   3      2
 *     /     /       \                 \
 *    2     1         2                 3
 */

package Tree;

import java.util.*;

public class UniqueBinarySearchTreesTwo {
    /**
     * Solution 1: Brutal force recursion
     *
     * Split the range from some middle point m into [l, m-1], m and [m+1, r], take m as root, and recursively
     * generate trees on left and right sub-ranges. Since all nodes are created brand new, there will be some waste
     * of memory and time.
     *
     * There will be O(n!) unique BSTs, because for each new node n, we can put it at roughly n/2 places in the BSTs of
     * n-1.
     *
     * Time complexity: O(n!). Space complexity: O(n!).
     */
    class Solution1 {
        public List<TreeNode> generateTrees(int n) {
            if (n == 0) return new ArrayList<>();
            return generateTrees(1, n);
        }

        private List<TreeNode> generateTrees(int l, int r) {
            List<TreeNode> res = new ArrayList<>();
            if (l > r) {
                res.add(null);
                return res;
            }
            for (int m = l; m <= r; m++) {
                for (TreeNode left : generateTrees(l, m-1)) {
                    for (TreeNode right : generateTrees(m+1, r)) {
                        TreeNode root = new TreeNode(m);
                        root.left = left;
                        root.right = right;
                        res.add(root);
                    }
                }
            }
            return res;
        }
    }

    /**
     * Solution 2: Recursion with memoization, top-down DP
     *
     * Same as Solution 1 except using map to memoize results. Time and space complexity should still be O(n!)
     * though, despite DP prevents some redundant node creation.
     */
    class Solution2 {
        public List<TreeNode> generateTrees(int n) {
            if (n == 0) return new ArrayList<>();
            return generateTrees(1, n, new List[n+1][n+1]); // note the dimension here
        }

        private List<TreeNode> generateTrees(int l, int r, List<TreeNode>[][] memo) {
            List<TreeNode> res = new ArrayList<>();
            if (l > r) {
                res.add(null);
                return res;
            }
            if (memo[l][r] != null) return memo[l][r];
            for (int m = l; m <= r; m++) {
                for (TreeNode left : generateTrees(l, m-1, memo)) {
                    for (TreeNode right : generateTrees(m+1, r, memo)) {
                        TreeNode root = new TreeNode(m);
                        root.left = left;
                        root.right = right;
                        res.add(root);
                    }
                }
            }
            memo[l][r] = res;
            return res;
        }
    }
}
