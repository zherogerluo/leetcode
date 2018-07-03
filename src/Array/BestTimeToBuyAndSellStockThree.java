/**
 * LeetCode #123, hard
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.

 Design an algorithm to find the maximum profit. You may complete at most two transactions.

 Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy
 again).

 Example 1:

 Input: [3,3,5,0,0,3,1,4]
 Output: 6
 Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
 Example 2:

 Input: [1,2,3,4,5]
 Output: 4
 Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
 Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
 engaging multiple transactions at the same time. You must sell before buying again.
 Example 3:

 Input: [7,6,4,3,1]
 Output: 0
 Explanation: In this case, no transaction is done, i.e. max profit = 0.
 */

package Array;

public class BestTimeToBuyAndSellStockThree {
    /**
     * Solution 1: Dynamic programming
     *
     * Two dimensional dynamic programming, dp[k][i] means the max profit at day i with exactly k transactions.
     * Formula: dp[k][i] = max(dp[k][i-1], dp[k-1][j] + prices[i] - prices[j] for j < i)
     *                   = max(dp[k][i-1], prices[i] - debt[j] where debt[j] = prices[j] - dp[k-1][j] for j < i)
     *                   = max(dp[k][i-1], prices[i] - min(debt[j] for j < i) where debt[j] = prices[j] - dp[k-1][j])
     *
     * Time complexity: O(k * n). If we use linear search every time for min debt, it will be O(k * n^2).
     * Space complexity: O(k * n).
     */
    class Solution1 {
        public int maxProfit(int[] prices) {
            // dp[k][i] = max(dp[k][i-1], dp[k-1][j] + prices[i] - prices[j] for j < i)
            if (prices == null || prices.length <= 1) return 0;
            int[][] dp = new int[3][prices.length];
            for (int k = 1; k < 3; k++) {
                int minDebt = Integer.MAX_VALUE;
                for (int i = 1; i < prices.length; i++) {
                    minDebt = Math.min(minDebt, prices[i-1] - dp[k-1][i-1]);
                    dp[k][i] = Math.max(dp[k][i-1], prices[i] - minDebt);
                }
            }
            return dp[2][prices.length-1];
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * Two one-dimensional DP, starting from left and right. fromLeft[i] stores the max profit on day i if we buy
     * first and sell next (from left to right), while fromRight[i] stores max profit on day i if we sell first and
     * buy next (carry a debt, from right to left). At a specific day i as splitting point, fromLeft takes care of the
     * first transaction and fromRight the second transaction. This idea is brilliant, easy to understand. However,
     * unlike Solution 1, it is hard to generalize this solution to the case of maximum k transactions.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        public int maxProfit(int[] prices) {
            if (prices == null || prices.length <= 1) return 0;
            int[] fromLeft = new int[prices.length], fromRight = new int[prices.length];
            int min = prices[0];
            for (int i = 1; i < prices.length; i++) {
                min = Math.min(min, prices[i]);
                fromLeft[i] = Math.max(fromLeft[i-1], prices[i] - min);
            }
            int max = prices[prices.length-1];
            for (int i = prices.length-2; i >= 0; i--) {
                max = Math.max(max, prices[i]);
                fromRight[i] = Math.max(fromRight[i+1], max - prices[i]);
            }
            int maxProfit = 0;
            for (int i = 0; i < prices.length; i++) maxProfit = Math.max(maxProfit, fromLeft[i] + fromRight[i]);
            return maxProfit;
        }
    }
}
