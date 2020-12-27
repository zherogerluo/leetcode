/**
 * LeetCode #377, medium
 *
 * Given an integer array with all positive numbers and no duplicates, find the number of possible combinations that
 * add up to a positive integer target.
 *
 * Example:
 *
 * nums = [1, 2, 3]
 * target = 4
 *
 * The possible combination ways are:
 * (1, 1, 1, 1)
 * (1, 1, 2)
 * (1, 2, 1)
 * (1, 3)
 * (2, 1, 1)
 * (2, 2)
 * (3, 1)
 *
 * Note that different sequences are counted as different combinations.
 *
 * Therefore the output is 7.
 *
 * Follow up:
 * What if negative numbers are allowed in the given array?
 * How does it change the problem?
 * What limitation we need to add to the question to allow negative numbers?
 */

package DynamicProgramming;

import java.util.*;

public class CombinationSumFour {
    /**
     * Solution 1: DFS with memoization
     *
     * Typical solution. Note that we need to accumulate result rather than replace.
     */
    class Solution1 {
        public int combinationSum4(int[] nums, int target) {
            int[] memo = new int[target+1];
            Arrays.fill(memo, -1);
            return helper(nums, target, memo);
        }

        private int helper(int[] nums, int target, int[] memo) {
            if (target == 0) {
                return 1;
            }
            if (target < 0) {
                return 0;
            }
            if (memo[target] != -1) {
                return memo[target];
            }
            int res = 0;
            for (int num : nums) {
                res += helper(nums, target - num, memo);
            }
            if (memo[target] == -1) {
                memo[target] = res;
            } else {
                memo[target] += res;
            }
            return res;
        }
    }

    /**
     * Solution 2: Dynamic programming, bottom-up.
     */
    class Solution2 {
        public int combinationSum4(int[] nums, int target) {
            int[] memo = new int[target+1];
            memo[0] = 1;
            for (int t = 1; t <= target; ++t) {
                for (int num : nums) {
                    int delta = t - num;
                    if (delta >= 0) {
                        memo[t] += memo[delta];
                    }
                }
            }
            return memo[target];
        }
    }
}
