package DynamicProgramming;

import java.util.*;

public class NumberOfSetsOfKNonOverlappingLineSegments {
    /**
     * Solution 1: Dynamic programming, bottom-up
     *
     * To get to dp[k][n], we consider two cases: 1) Draw one line from zero, which could extend to any length less than
     * n, and this effectively reduces to drawing k-1 lines for the rest, i.e. dp[k-1][j] for j < n. Note to take care
     * the edge case d[k-1][0], the boundary condition must be 1. 2) Don't draw a line, this reduces to dp[k][n-1].
     *
     * Time complexity: O(n * k). Space complexity: O(n).
     */
    class Solution1 {
        public int numberOfSets(int n, int k) {
            // dp[k][n] = sum(dp[k-1][j] for j < n) + dp[k][n-1]
            if (n - 1 < k) {
                return 0;
            }
            final int MOD = 1000000007;
            int[] dp = new int[n+1];
            // note this boundary condition
            Arrays.fill(dp, 1);
            for (int kk = 1; kk <= k; ++kk) {
                int sum = 0;
                // nn can start from 1, the kk initial value is to prune some zero cases that are not needed
                for (int nn = kk; nn <= n; ++nn) {
                    // need to update sum here so it takes old value
                    sum = Math.floorMod(sum + dp[nn], MOD);
                    if (nn - 1 < kk) {
                        // not enough points, can't draw
                        dp[nn] = 0;
                    } else if (nn - 1 == kk) {
                        // can draw exactly kk lines
                        dp[nn] = 1;
                    } else {
                        // sum already has the old value, need to deduct it
                        dp[nn] = Math.floorMod(sum - dp[nn] + dp[nn-1], MOD);
                    }
                }
            }
            return Math.floorMod(dp[n], MOD);
        }
    }

    /**
     * Solution 2: Depth-first search with memoization
     *
     * Top-down DP translated from Solution 1, note the edge case of k == 0: Need to return 1 to account for k = 1 case
     * when it calls k == 0.
     *
     * Note this is significantly slower than Solution 1, being actually O(n^2 * k) time because of the redundant sum
     * calculation in the for-loop. There should be a way to optimize this but not given here.
     *
     * Time complexity: O(n^2 * k). Space complexity: O(n * k).
     */
    class Solution2 {
        public int numberOfSets(int n, int k) {
            if (n - 1 < k) {
                return 0;
            }
            int[][] memo = new int[n+1][k+1];
            return helper(n, k, memo);
        }

        private int helper(int n, int k, int[][] memo) {
            if (n - 1 < k) {
                return 0;
            }
            if (k == 0 || n - 1 == k) {
                return 1;
            }
            if (memo[n][k] > 0) {
                return memo[n][k] - 1;
            }
            final int MOD = 1000000007;
            int res = 0;
            for (int nn = k; nn < n; ++nn) {
                res = Math.floorMod(res + helper(nn, k-1, memo), MOD);
            }
            res = Math.floorMod(res + helper(n-1, k, memo), MOD);
            memo[n][k] = res + 1;
            return res;
        }
    }

    /**
     * Solution 3: Math
     *
     * It can be proved that it is equivalent to draw k segments that can share endpoints for n points and to draw k
     * segments that cannot share endpoints for n + k - 1 points. Simply by adding one more point after all but last
     * segment, we can clearly see that the claim is true. Then the problem reduces to picking 2 * k points within
     * n + k - 1 points, which can be calculated by C(n+k-1, 2*k).
     */
}
