/**
 * LeetCode #518, medium
 *
 * You are given coins of different denominations and a total amount of money. Write a function to compute the number
 * of combinations that make up that amount. You may assume that you have infinite number of each kind of coin.
 *
 * Note: You can assume that
 *
 * 0 <= amount <= 5000
 * 1 <= coin <= 5000
 * the number of coins is less than 500
 * the answer is guaranteed to fit into signed 32-bit integer
 *
 *
 * Example 1:
 *
 * Input: amount = 5, coins = [1, 2, 5]
 * Output: 4
 * Explanation: there are four ways to make up the amount:
 * 5=5
 * 5=2+2+1
 * 5=2+1+1+1
 * 5=1+1+1+1+1
 *
 *
 * Example 2:
 *
 * Input: amount = 3, coins = [2]
 * Output: 0
 * Explanation: the amount of 3 cannot be made up just with coins of 2.
 *
 *
 * Example 3:
 *
 * Input: amount = 10, coins = [10]
 * Output: 1
 */

package DynamicProgramming;

import java.util.*;

public class CoinChangeTwo {
    /**
     * Solution 1: DFS with memoization, top-down DP
     *
     * Typical DFS + memoization. Note that we need a two dimensional memo[i][j] to remember the number of combinations
     * for amount == j using only coins less or equal to index == i. We need to sort the coins and go from largest
     * to smallest coin, DFS to either same level or next level with reduced amount.
     *
     * Time complexity: O(n * k). Space complexity: O(n * k).
     */
    class Solution {
        public int change(int amount, int[] coins) {
            // Arrays.sort(coins); // no need to sort the coins
            return change(amount, coins, 0, new int[coins.length][amount+1]);
        }

        private int change(int amount, int[] coins, int index, int[][] memo) {
            if (amount == 0) return 1;
            if (amount < 0 || index >= coins.length) return 0;
            if (memo[index][amount] != 0) return memo[index][amount] == -1 ? 0 : memo[index][amount];
            int result = change(amount - coins[index], coins, index, memo) +
                         change(amount, coins, index + 1, memo);
            memo[index][amount] = result == 0 ? -1 : result;
            return result;
        }
    }

    /**
     * Solution 2: DFS with memoization
     *
     * Same as Solution 1, except use iteration for a coin instead of recursion. However it is slower than Solution 1
     * due to more method calls despite results being cached.
     */
    class Solution2 {
        public int change(int amount, int[] coins) {
            // Arrays.sort(coins); // no need to sort the coins
            return change(amount, coins, 0, new int[coins.length][amount+1]);
        }

        private int change(int amount, int[] coins, int index, int[][] memo) {
            if (amount == 0) return 1;
            if (amount < 0 || index >= coins.length) return 0;
            if (memo[index][amount] != 0) return memo[index][amount] == -1 ? 0 : memo[index][amount];
            int result = 0;
            for (int f = amount / coins[index]; f >= 0; f--) { // iterate through all possible frequencies
                result += change(amount - f * coins[index], coins, index + 1, memo);
            }
            memo[index][amount] = result == 0 ? -1 : result;
            return result;
        }
    }

    /**
     * Solution 3: Dynamic programming
     *
     * Bottom-up DP. Same as solution 1, memo[i][j] means the number of combinations for amount == j using only coins
     * less or equal to index == i.
     *
     * memo[i][j] = memo[i][j-coin] + memo[i-1][j] (must use this coin + not used)
     *
     * Time complexity: O(n * k). Space complexity: O(n * k).
     */
    class Solution3 {
        public int change(int amount, int[] coins) {
            if (amount == 0) return 1;
            if (coins == null || coins.length == 0) return 0;
            int[][] memo = new int[coins.length][amount+1];
            for (int i = 0; i < coins.length; i++) {
                memo[i][0] = 1;
                for (int j = 1; j <= amount; j++) {
                    int remain = j - coins[i];
                    if (remain >= 0) memo[i][j] += memo[i][remain];
                    if (i > 0) memo[i][j] += memo[i-1][j];
                }
            }
            return memo[coins.length-1][amount];
        }
    }

    /**
     * Solution 4: Dynamic programming
     *
     * Same as Solution 3, but uses only two memo arrays: One for last round, one for this round. Note that DP for
     * this problem really only cares about the most recent two passes.
     *
     * Time complexity: O(n * k). Space complexity: O(k).
     */
    class Solution4 {
        public int change(int amount, int[] coins) {
            if (amount == 0) return 1;
            if (coins == null || coins.length == 0) return 0;
            int[] last = new int[amount+1], cur = new int[amount+1];
            for (int a = 0; a <= amount; a += coins[0]) last[a] = 1; // pre-processing for the first coin
            for (int coin : coins) { // no need to index the array
                cur[0] = 1;
                for (int a = 1; a <= amount; a++) {
                    int remain = a - coin;
                    if (remain >= 0) cur[a] += cur[remain];
                    cur[a] += last[a];
                }
                // swap to arrays
                int[] tmp = last;
                last = cur;
                cur = tmp;
                Arrays.fill(cur, 0); // reinitialize, O(n)
            }
            return last[amount];
        }
    }

    /**
     * Solution 5: Dynamic programming
     *
     * Most elegant DP using only one DP array. From Solution 4, it is not hard to see that current result is built
     * upon last result by adding repeatedly array entry cur[remain]; What was left in the last array was inherited
     * unchanged (cur[a] += last[a]). So we only need to use one single DP array and repeatedly work on it. Fastest
     * solution.
     *
     * Time complexity: O(n * k). Space complexity: O(k).
     */
    class Solution5 {
        public int change(int amount, int[] coins) {
            int dp[] = new int[amount + 1];
            dp[0] = 1; // crucial initialization
            for (int coin : coins) {
                for (int i = coin; i <= amount; i++) {
                    dp[i] += dp[i - coin]; // last pass results were implicitly inherited
                }
            }
            return dp[amount];
        }
    }
}
