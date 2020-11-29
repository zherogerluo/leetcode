/**
 * LeetCode #309, medium
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 *
 * Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and
 * sell one share of the stock multiple times) with the following restrictions:
 *
 * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 * After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
 *
 * Example:
 *
 * Input: [1,2,3,0,2]
 * Output: 3
 *
 * Explanation: transactions = [buy, sell, cooldown, buy, sell]
 */

public class BestTimeToBuyAndSellStockWithCooldown {
    /**
     * Solution 1:
     *
     * Break it down to multiple scenarios. The straightforward cases are uphill and downhill cases, where we delay sale
     * (merge transaction with previous one) and delay purchase. The complicated case is a "V" shape, where we have to
     * look back as many as three days to calculate the max profit. See the code for more comments.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution1 {
        public int maxProfit(int[] prices) {
            if (prices == null || prices.length <= 1) {
                return 0;
            }
            int[] profit = new int[prices.length];
            if (prices[1] > prices[0]) {
                profit[1] = prices[1] - prices[0];
            }
            for (int i = 2; i < prices.length; ++i) {
                if (prices[i] <= prices[i-1]) {
                    // price goes down, we don't want to sell, profit stays same
                    profit[i] = profit[i-1];
                } else {
                    // price goes up, we can buy or already bought
                    int this_profit = prices[i] - prices[i-1];
                    if (prices[i-1] >= prices[i-2]) {
                        // previous price went up, merge the transactions
                        profit[i] = profit[i-1] + this_profit;
                    } else {
                        // we see a "V" shaped price, take the max of multiple scenarios:
                        // 1. transactions are merged
                        // 2. take this profit and drop pre-cooldown profit
                        // 3. take pre-cooldown profit and drop this profit
                        int profit_1 = profit[i-2] + prices[i-1] - prices[i-2] + this_profit;
                        int profit_2 = this_profit + (i >= 3 ? profit[i-3] : 0);
                        int profit_3 = profit[i-1];
                        profit[i] = Math.max(Math.max(profit_1, profit_2), profit_3);
                    }
                }
            }
            return profit[prices.length-1];
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * Two DP arrays: buy[i] = max profit if we 1) buy on or 2) bought and rest till day i, sell[i] similar meaning.
     * On day i if we can buy, the max profit would be either we rest (buy[i-1]), or buy after we sell at least two days
     * ago (sell[i-2] - prices[i]). Array sell is updated similarly except that we can sell right after we buy, so the
     * i-2 becomes i-1. The initial condition for buy[1] is a little tricky, we can either buy on day one or two so it
     * is the max of both. We don't need to initialize buy[0] because it is never used.
     *
     * This solution is tricky and hard to understand to the bottom.
     *
     * Time complexity: O(n). Space complexity: O(n) but can be trivially reduced to O(1) since we only need i-2 and
     * i-1 data.
     */
    class Solution2 {
        public int maxProfit(int[] prices) {
            if (prices == null || prices.length <= 1) {
                return 0;
            }
            // max profit if we buy or already bought at day i
            int[] buy = new int[prices.length];
            // max profit if we sell or already sold at day i
            int[] sell = new int[prices.length];
            // we either buy on day one or two
            buy[1] = Math.max(-prices[0], -prices[1]);
            // we cannot sell on day one, can only sell on day two
            sell[1] = Math.max(0, prices[1] - prices[0]);
            for (int i = 2; i < prices.length; ++i) {
                // not sell[i-1] because of the cooldown
                buy[i] = Math.max(buy[i-1], sell[i-2] - prices[i]);
                sell[i] = Math.max(sell[i-1], buy[i-1] + prices[i]);
            }
            return sell[prices.length-1];
        }
    }

    /**
     * Solution 3: State machine
     *
     * There is a nice state machine explanation of the DP solution:
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/75928/Share-my-DP-solution-(By-State-Machine-Thinking)
     */
}