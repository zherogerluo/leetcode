/**
 * LeetCode #279, medium
 *
 * Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which
 * sum to n.

 Example 1:

 Input: n = 12
 Output: 3
 Explanation: 12 = 4 + 4 + 4.
 Example 2:

 Input: n = 13
 Output: 2
 Explanation: 13 = 4 + 9.
 */

package Math;

public class PerfectSquares {
    /**
     * Solution 1: Dynamic programming
     *
     * For result of number k, we check number whose square is smaller than k, and check the result of the remainder.
     *
     * dp[k] = min(1 + dp[k - i * i] for i * i < k). Note that the formula is not dp[k] = min(dp[i] + dp[k-i]),
     * because this does not take care of perfect square numbers such as 4, 9, 16 ...
     *
     * Time complexity: O(n * sqrt(n)). Space complexity: O(n).
     */
    class Solution1 {
        public int numSquares(int n) {
            int[] dp = new int[n+1];
            for (int i = 1; i <= n; i++) {
                int min = i;
                for (int j = 1; j*j <= i; j++) {
                    min = Math.min(min, 1 + dp[i-j*j]);
                }
                dp[i] = min;
            }
            return dp[n];
        }
    }
}
