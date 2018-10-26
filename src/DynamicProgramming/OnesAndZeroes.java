/**
 * LeetCode #474, medium
 *
 * In the computer world, use restricted resource you have to generate maximum benefit is what we always want to pursue.
 *
 * For now, suppose you are a dominator of m 0s and n 1s respectively. On the other hand, there is an array with
 * strings consisting of only 0s and 1s.
 *
 * Now your task is to find the maximum number of strings that you can form with given m 0s and n 1s. Each 0 and 1
 * can be used at most once.
 *
 * Note:
 * The given numbers of 0s and 1s will both not exceed 100
 * The size of given string array won't exceed 600.
 * Example 1:
 * Input: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3
 * Output: 4
 *
 * Explanation: This are totally 4 strings can be formed by the using of 5 0s and 3 1s, which are “10,”0001”,”1”,”0”
 *
 * Example 2:
 * Input: Array = {"10", "0", "1"}, m = 1, n = 1
 * Output: 2
 *
 * Explanation: You could form "10", but then you'd have nothing left. Better form "0" and "1".
 */

package DynamicProgramming;

import java.util.*;

public class OnesAndZeroes {
    /**
     * Solution 1: DFS with memoization, top-down DP
     *
     * Knapsack problem but with additional dimension. Use a three dimensional memo array to remember the results at
     * given index, m, and n. In each DFS step, the result is simply the maximum of "1 + using this string" and "not
     * using this string". Note that for m < 0 or n < 0 base case, we need to return -1 as it invalidates the parent
     * call and should be considered "owning" one. Moreover this case is the strongest case that should come before
     * all other base cases.
     *
     * Time complexity: O(k * m * n) where k is the length of strs. Space complexity: O(k * m * n).
     */
    class Solution1 {
        public int findMaxForm(String[] strs, int m, int n) {
            int[] zeros = new int[strs.length], ones = new int[strs.length];
            for (int i = 0; i < strs.length; i++) {
                for (char c : strs[i].toCharArray()) {
                    if (c == '0') zeros[i]++;
                    else ones[i]++;
                }
            }
            int[][][] memo = new int[strs.length][m+1][n+1];
            for (int i = 0; i < strs.length; i++) {
                for (int j = 0; j <= m; j++) {
                    Arrays.fill(memo[i][j], -1);
                }
            }
            return findMaxForm(zeros, ones, 0, m, n, memo);
        }

        private int findMaxForm(int[] zeros, int[] ones, int index, int m, int n, int[][][] memo) {
            if (m < 0 || n < 0) return -1; // this should come before the next
            if (index >= zeros.length) return 0;
            if (memo[index][m][n] != -1) return memo[index][m][n];
            int result = Math.max(1 + findMaxForm(zeros, ones, index+1, m-zeros[index],
                    n-ones[index], memo),
                    findMaxForm(zeros, ones, index+1, m, n, memo));
            memo[index][m][n] = result;
            return result;
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * Bottom-up version of Solution 1. Similar to Problem 416, we can reduce the DP array by one dimension, because
     * the results are built on old one. dp[i][j] is defined as the max number of forms found with given zero
     * budget i and one budget j. The formula is:
     *
     * dp[i][j] = max(last dp[i][j] meaning we don't use current string, 1 + dp[i-zeros][j-ones] meaning we use the
     * current string but budget gets reduced)
     *
     * Same as in Problem 416, we need to iterate from back of the DP array in order to avoid using the just updated
     * value. We want to always use values in the last pass.
     *
     * Time complexity: O(k * m * n) where k is the length of strs. Space complexity: O(m * n).
     */
    class Solution2 {
        public int findMaxForm(String[] strs, int m, int n) {
            int[][] dp = new int[m+1][n+1];
            for (String s : strs) {
                int zeros = 0, ones = 0;
                for (char c : s.toCharArray()) {
                    if (c == '0') zeros++;
                    else ones++;
                }
                for (int i = m; i >= 0; i--) { // iterate from the back
                    for (int j = n; j >= 0; j--) { // iterate from the back
                        if (i - zeros >= 0 && j - ones >= 0) { // check for index bound
                            dp[i][j] = Math.max(dp[i][j], 1 + dp[i - zeros][j - ones]);
                        }
                    }
                }
            }
            return dp[m][n];
        }
    }
}
