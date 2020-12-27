/**
 * LeetCode #673, medium
 *
 * Given an integer array nums, return the number of longest increasing subsequences.
 *
 * Notice that the sequence has to be strictly increasing.
 *
 * Example 1:
 *
 * Input: nums = [1,3,5,4,7]
 * Output: 2
 * Explanation: The two longest increasing subsequences are [1, 3, 4, 7] and [1, 3, 5, 7].
 *
 * Example 2:
 *
 * Input: nums = [2,2,2,2,2]
 * Output: 5
 * Explanation: The length of longest continuous increasing subsequence is 1, and there are 5 subsequences' length is 1,
 * so output 5.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 2000
 * -10^6 <= nums[i] <= 10^6
 */

package DynamicProgramming;

import java.util.*;

public class NumberOfLongestIncreasingSubsequence {
    /**
     * Solution 1: Depth-first search with memoization
     *
     * Typical DFS, note we need to do n helper in the main func to cover cases like [2,2,2,2,2]. memo[i] = { current
     * max length, count of max length }.
     */
    class Solution1 {
        public int findNumberOfLIS(int[] nums) {
            int max_len = 0, count = 0;
            int[][] memo = new int[nums.length][2];
            for (int i = 0; i < nums.length; ++i) {
                int[] res = helper(nums, i, memo);
                if (res[0] > max_len) {
                    max_len = res[0];
                    count = res[1];
                } else if (res[0] == max_len) {
                    count += res[1];
                }
            }
            return count;
        }

        private int[] helper(int[] nums, int i, int[][] memo) {
            if (memo[i][0] != 0) {
                return memo[i];
            }
            int max_len = 1, count = 1;
            for (int j = i+1; j < nums.length; ++j) {
                if (nums[j] > nums[i]) {
                    int[] res = helper(nums, j, memo);
                    int len = res[0] + 1;
                    if (len > max_len) {
                        max_len = len;
                        count = res[1];
                    } else if (len == max_len) {
                        count += res[1];
                    }
                }
            }
            memo[i][0] = max_len;
            memo[i][1] = count;
            return memo[i];
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * DP translation of Solution 1, use two arrays lengths and counts to memorize stuff. The difference is that the
     * nested loop starts at 0 and ends at i exclusive.
     */
    class Solution2 {
        public int findNumberOfLIS(int[] nums) {
            int[] lengths = new int[nums.length], counts = new int[nums.length];
            Arrays.fill(counts, 1);
            for (int i = 0; i < nums.length; ++i) {
                int len = 0, cnt = 1;
                for (int j = 0; j < i; ++j) {
                    if (nums[j] < nums[i]) {
                        if (lengths[j] > len) {
                            len = lengths[j];
                            cnt = counts[j];
                        } else if (lengths[j] == len) {
                            cnt += counts[j];
                        }
                    }
                }
                lengths[i] = len + 1;
                counts[i] = cnt;
            }
            int len = 0, cnt = 1;
            for (int i = 0; i < nums.length; ++i) {
                if (lengths[i] > len) {
                    len = lengths[i];
                    cnt = counts[i];
                } else if (lengths[i] == len) {
                    cnt += counts[i];
                }
            }
            return cnt;
        }
    }
}
