/**
 * LeetCode #96, medium
 *
 * Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?
 *
 * Example:
 *
 * Input: 3
 * Output: 5
 * Explanation:
 * Given n = 3, there are a total of 5 unique BST's:
 *
 *    1         3     3      2      1
 *     \       /     /      / \      \
 *      3     2     1      1   3      2
 *     /     /       \                 \
 *    2     1         2                 3
 */

package Tree;

public class UniqueBinarySearchTrees {
    /**
     * Solution 1: Recursion with memoization, top-down DP
     *
     * Split range n into l and n-l-1:
     *
     *       root
     *      /    \
     * l nodes   n-l-1 nodes
     *
     * Then recursively count number of trees and do sum(mul(f(l), f(n-l-1)) for all l from 0 to n-1. Use memoization
     * to avoid duplicate cases. Beware of bounds!
     *
     * Time complexity: O(n^2). Space complexity: O(n).
     */
    class Solution1 {
        public int numTrees(int n) {
            return numTrees(n, new int[n+1]); // note the array dimension here
        }

        private int numTrees(int n, int[] memo) {
            if (n <= 1) return 1;
            if (memo[n] != 0) return memo[n];
            int count = 0;
            for (int l = 0; l < n; l++) { // not l <= n
                count += numTrees(l, memo) * numTrees(n-l-1, memo); // note the right formula is sum of mul
            }
            memo[n] = count;
            return count;
        }
    }

    /**
     * Solution 2: Dynamic programming, bottom-up
     *
     * Explicit DP, array counts[i] stores number of unique BSTs for i. The formula is:
     *
     * counts[i] = sum(counts[j] * counts[i-j-1]) for j smaller than i. Here j means for i as root, there are j nodes
     * in the left branch. Apparently j can only be 0 to i-1.
     */
    class Solution2 {
        public int numTrees(int n) {
            if (n <= 1) return 1;
            int[] counts = new int[n+1];
            counts[0] = 1;
            counts[1] = 1;
            for (int i = 2; i <= n; i++) {
                for (int j = 0; j < i; j++) {
                    counts[i] += counts[j] * counts[i-j-1];
                }
            }
            return counts[n];
        }
    }
}
