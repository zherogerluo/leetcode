/**
 * LeetCode #879, hard
 *
 * There is a group of n members, and a list of various crimes they could commit. The ith crime generates a profit[i]
 * and requires group[i] members to participate in it. If a member participates in one crime, that member can't
 * participate in another crime.
 *
 * Let's call a profitable scheme any subset of these crimes that generates at least minProfit profit, and the total
 * number of members participating in that subset of crimes is at most n.
 *
 * Return the number of schemes that can be chosen. Since the answer may be very large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: n = 5, minProfit = 3, group = [2,2], profit = [2,3]
 * Output: 2
 * Explanation: To make a profit of at least 3, the group could either commit crimes 0 and 1, or just crime 1.
 * In total, there are 2 schemes.
 *
 * Example 2:
 *
 * Input: n = 10, minProfit = 5, group = [2,3,5], profit = [6,7,8]
 * Output: 7
 * Explanation: To make a profit of at least 5, the group could commit any crimes, as long as they commit one.
 * There are 7 possible schemes: (0), (1), (2), (0,1), (0,2), (1,2), and (0,1,2).
 *
 * Constraints:
 *
 * 1 <= n <= 100
 * 0 <= minProfit <= 100
 * 1 <= group.length <= 100
 * 1 <= group[i] <= 100
 * profit.length == group.length
 * 0 <= profit[i] <= 100
 */

package DynamicProgramming;

import java.util.*;

public class ProfitableSchemes {
    /**
     * Solution 1: Depth-first search with memoization
     *
     * This is a knapsack problem, subtree is pick/not pick the ith crime. One tricky part is that if current profit is
     * already over minProfit, then the results are the same. Note the index is always increasing, do not go back and
     * revisit crime < i because we've already done that.
     *
     * Time complexity: O(len(group) * n * P). Space complexity: O(len(group) * n * P).
     */
    class Solution1 {
        final int MOD = 1000000007;

        public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
            return (minProfit == 0 ? 1 : 0) + solve(n, minProfit, 0, group, profit, new int[group.length][n+1][minProfit+1]);
        }

        private int solve(int n, int target, int i, int[] group, int[] profit, int[][][] memo) {
            if (i == group.length) {
                return 0;
            }
            target = Math.max(0, target);
            if (memo[i][n][target] != 0) {
                return memo[i][n][target] - 1;
            }
            int res = 0;
            // binary branching, not a for loop
            if (n >= group[i]) {
                res += (target - profit[i] <= 0) ? 1 : 0;
                res = (res + solve(n-group[i], target-profit[i], i+1, group, profit, memo)) % MOD;
            }
            res = (res + solve(n, target, i+1, group, profit, memo)) % MOD;
            memo[i][n][target] = res + 1;
            return res;
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * We could use a two dimensional memo matrix because we only needed i-1 results for i, however we need to do the
     * loop backwards to ensure the older values are updated later. Boundary condition is dp[0] filled with 1 which
     * means the ways are always one if minProfit requirement is zero - group can always take no crimes and fulfill it.
     *
     * Time complexity: O(len(group) * n * P). Space complexity: O(n * P).
     */
    class Solution2 {
        final int MOD = 1000000007;

        public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
            // dp[i][target][n]: ways ends at i =
            //     dp[i-1][target][n] (not taking ith crime) +
            //     dp[i-1][target-profit[i]][n-group[i]] (taking ith crime)
            int[][] dp = new int[minProfit+1][n+1];
            Arrays.fill(dp[0], 1);
            for (int k = 0; k < group.length; ++k) {
                for (int i = minProfit; i >= 0; --i) {
                    for (int j = n; j >= 0; --j) {
                        if (j >= group[k]) {
                            dp[i][j] = (dp[i][j] + dp[Math.max(0, i-profit[k])][j-group[k]]) % MOD;
                        }
                    }
                }
            }
            return dp[minProfit][n];
        }
    }
}
