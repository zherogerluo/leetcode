/**
 * LeetCode #122, easy
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.

 Design an algorithm to find the maximum profit. You may complete as many transactions as you like (i.e., buy one and
 sell one share of the stock multiple times).

 Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy
 again).

 Example 1:

 Input: [7,1,5,3,6,4]
 Output: 7
 Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
 Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
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

public class BestTimeToBuyAndSellStockTwo {
    /**
     * Solution 1:
     *
     * If we want to get max profit, we must buy at local low and sell at next high. If price goes down, we need to
     * sell at last high and look for next low to buy in. If price goes up, we hold the stock.
     *
     * Tricky part: We only need to compare price with high point, if goes down, we sell immediately. Otherwise we
     * update high price (hold the stock). Another tricky part is to collect profit when we reach the end of array.
     */
    class Solution1 {
        public int maxProfit(int[] prices) {
            if (prices == null || prices.length <= 1) return 0;
            int profit = 0, low = prices[0], high = low;
            for (int price : prices) {
                if (price > high) high = price; // no need to compare with low
                else { // This involves price below low as well. In any case we would want to sell and update prices
                    profit += high - low;
                    low = price;
                    high = price;
                }
            }
            return profit + high - low; // don't forget to collect remaining profit
        }
    }

    /**
     * Solution 2: Greedy
     *
     * The algorithm in Solution 1 can be further simplified given unlimited number of transactions. If price rises,
     * we simply buy the stock on the day before and sell it today, like we are god of stock market. This is
     * basically breaking down one transaction into single-day transactions yet still results in the same profit.
     * Excellent example of out-of-box thinking.
     */
    class Solution2 {
        public int maxProfit(int[] prices) {
            if (prices == null) return 0;
            int profit = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i-1]) profit = profit + prices[i] - prices[i-1];
            }
            return profit;
        }
    }
}
