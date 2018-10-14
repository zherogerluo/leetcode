/**
 * LeetCode #322, medium
 *
 * You are given coins of different denominations and a total amount of money amount. Write a function to compute the
 * fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any
 * combination of the coins, return -1.
 *
 * Example 1:
 *
 * Input: coins = [1, 2, 5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 *
 * Example 2:
 *
 * Input: coins = [2], amount = 3
 * Output: -1
 *
 * Note:
 * You may assume that you have an infinite number of each kind of coin.
 */

package DynamicProgramming;

import java.util.*;

public class CoinChange {
    /**
     * Solution 1: DFS with memoization
     *
     * DFS to search for combinations, use memoization to reduce cost. Top-down DP.
     *
     * Time complexity: O(n * k) where k is amount. Space complexity: O(k).
     */
    class Solution1 {
        public int coinChange(int[] coins, int amount) {
            int result = coinChange(coins, amount, new int[amount+1]);
            return result == Integer.MAX_VALUE ? -1 : result;
        }

        private int coinChange(int[] coins, int amount, int[] memo) { // no need to keep track of count here
            if (amount == 0) return 0;
            if (amount < 0) return Integer.MAX_VALUE;
            if (memo[amount] != 0) return memo[amount];
            int result = Integer.MAX_VALUE;
            for (int coin : coins) {
                result = Math.min(result, coinChange(coins, amount-coin, memo));
            }
            if (result != Integer.MAX_VALUE) result++; // prevent overflow
            memo[amount] = result;
            return result;
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * Explicit DP, same idea as Solution 1.
     */
    class Solution2 {
        public int coinChange(int[] coins, int amount) {
            int[] results = new int[amount+1];
            Arrays.fill(results, Integer.MAX_VALUE);
            results[0] = 0;
            for (int i = 1; i <= amount; i++) {
                for (int coin : coins) {
                    int remain = i-coin;
                    if (remain >= 0 && results[remain] != Integer.MAX_VALUE) { // test index bound
                        results[i] = Math.min(results[i], results[remain]);
                    }
                }
                if (results[i] != Integer.MAX_VALUE) results[i]++; // this line should be outside of inner loop
            }
            return results[amount] == Integer.MAX_VALUE ? -1 : results[amount];
        }
    }

    /**
     * Solution 3: Greedy
     *
     * Sort array, start from big coin to small, greedily search for result by testing whether remaining amount can be
     * divided by coin value. This algorithm alone is costly, must keep track of min count so far to prune search
     * branches. Not sure about time complexity.
     */
    class Solution3 {
        public int coinChange(int[] coins, int amount) {
            Arrays.sort(coins);
            int[] result = new int[]{Integer.MAX_VALUE};
            coinChange(coins, amount, coins.length - 1, 0, result);
            return result[0] == Integer.MAX_VALUE ? -1 : result[0];
        }

        private void coinChange(int[] coins, int amount, int i, int count, int[] min) {
            if (amount < 0 || i < 0) return;
            if (amount % coins[i] == 0) {
                min[0] = Math.min(min[0], count + amount / coins[i]);
                return;
            }
            for (int f = amount/coins[i]; f >= 0 && count + f < min[0]; f--) {
                coinChange(coins, amount - f * coins[i], i - 1, count + f, min);
            }
        }
    }
}
