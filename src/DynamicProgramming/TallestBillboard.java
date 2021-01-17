/**
 * LeetCode #956, hard
 *
 * You are installing a billboard and want it to have the largest height.  The billboard will have two steel supports,
 * one on each side.  Each steel support must be an equal height.
 *
 * You have a collection of rods which can be welded together.  For example, if you have rods of lengths 1, 2, and 3,
 * you can weld them together to make a support of length 6.
 *
 * Return the largest possible height of your billboard installation.  If you cannot support the billboard, return 0.
 *
 * Example 1:
 *
 * Input: [1,2,3,6]
 * Output: 6
 * Explanation: We have two disjoint subsets {1,2,3} and {6}, which have the same sum = 6.
 *
 * Example 2:
 *
 * Input: [1,2,3,4,5,6]
 * Output: 10
 * Explanation: We have two disjoint subsets {2,3,5} and {4,6}, which have the same sum = 10.
 *
 * Example 3:
 *
 * Input: [1,2]
 * Output: 0
 * Explanation: The billboard cannot be supported, so we return 0.
 *
 * Note:
 *
 * 0 <= rods.length <= 20
 * 1 <= rods[i] <= 1000
 * The sum of rods is at most 5000.
 */

import java.util.*;

package DynamicProgramming;

public class TallestBillboard {
    /**
     * Solution 1: Depth-first search
     *
     * Keep track of sum of each group as we consider the i-th rod: Sums might not change if we don't use this rod, or
     * either can increment if we put this rod in this group. Whenever we see an equal sum, update the result.
     *
     * This idea is relatively easy to come up with, however there are a few optimizations we need to think about:
     * 1) Sort the array reversely to be greedy.
     * 2) Terminate if the remaining rods cannot fill the gap between two groups. This requires us to also keep track
     * of sum of remaining rods.
     * 3) Update result if remaining rods can exactly fill the gap, and also terminate because there is only one way to
     * make the sums equal, which is assigning all the remaining rods to the smaller group.
     * 4) Terminate if the final results cannot be better than current result even we pick all the remaining rods.
     *
     * Note we don't need to use a list to remember the selected rods, which will never be used.
     *
     * Time complexity: O(3^n). Space complexity: O(n).
     */
    class Solution1 {
        int res;

        public int tallestBillboard(int[] rods) {
            Arrays.sort(rods);
            for (int i = 0, j = rods.length-1; i < j; ++i, --j) {
                int tmp = rods[i];
                rods[i] = rods[j];
                rods[j] = tmp;
            }
            res = 0;
            int total = 0;
            for (int rod : rods) {
                total += rod;
            }
            solve(rods, 0, 0, 0, total);
            return res;
        }

        private void solve(int[] rods, int i, int sum1, int sum2, int rest) {
            if (i == rods.length || Math.abs(sum1 - sum2) > rest || sum1 + sum2 + rest <= res * 2) {
                return;
            }
            if (Math.abs(sum1 - sum2) == rest) {
                res = Math.max(res, Math.max(sum1, sum2));
                return;
            }
            if (Math.abs(sum1 - sum2) == rods[i]) {
                res = Math.max(res, Math.max(sum1, sum2));
            }
            rest -= rods[i];
            solve(rods, i+1, sum1 + rods[i], sum2, rest);
            solve(rods, i+1, sum1, sum2 + rods[i], rest);
            solve(rods, i+1, sum1, sum2, rest);
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * The subproblem is calculating max height for each possible delta between two groups for subarray ending at i.
     * Since the result is sparse, we update the next row for every valid result in current row. Note that dp[i][0] is
     * always a valid result even if it is zero, which means we did not select any previous rod.
     *
     * There are three cases for dp[i][j]:
     * 1) Do not add next rod, simply carry over the result to dp[i+1][j].
     * 2) Add rod to larger group, the new delta is greater, and the max height is greater.
     * 3) Add rod to smaller group, the new delta is smaller, and the max height can be either the same or greater
     * depending on the next rod length.
     *
     * Time complexity: O(n * total). Space complexity: O(n * total) but can be potentially reduced to O(n) as we only
     * needed the (i-1)-th row for the i-th row.
     */
    class Solution2 {
        public int tallestBillboard(int[] rods) {
            if (rods.length <= 1) {
                return 0;
            }
            int total = 0;
            for (int rod : rods) {
                total += rod;
            }
            int[][] dp = new int[rods.length][total+1];
            dp[0][rods[0]] = rods[0];
            for (int i = 1; i < rods.length; ++i) {
                for (int j = 0; j <= total; ++j) {
                    // note j == 0 is a valid case
                    if (j != 0 && dp[i-1][j] == 0) {
                        continue;
                    }
                    // not adding new rod
                    dp[i][j] = Math.max(dp[i][j], dp[i-1][j]);
                    // rod is added to larger group
                    dp[i][j+rods[i]] = dp[i-1][j] + rods[i];
                    // rod is added to smaller group
                    dp[i][Math.abs(j-rods[i])] =
                            Math.max(dp[i][Math.abs(j-rods[i])], dp[i-1][j] + Math.max(rods[i]-j, 0));
                }
            }
            return dp[rods.length-1][0];
        }
    }
}
