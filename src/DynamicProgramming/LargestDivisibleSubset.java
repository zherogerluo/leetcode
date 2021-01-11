/**
 * LeetCode #368, medium
 *
 * Given a set of distinct positive integers nums, return the largest subset answer such that every pair (answer[i],
 * answer[j]) of elements in this subset satisfies:
 *
 * answer[i] % answer[j] == 0, or
 * answer[j] % answer[i] == 0
 * If there are multiple solutions, return any of them.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3]
 * Output: [1,2]
 * Explanation: [1,3] is also accepted.
 *
 * Example 2:
 *
 * Input: nums = [1,2,4,8]
 * Output: [1,2,4,8]
 *
 * Constraints:
 *
 * 1 <= nums.length <= 1000
 * 1 <= nums[i] <= 2 * 109
 * All the integers in nums are unique.
 */

package DynamicProgramming;

import java.util.*;

public class LargestDivisibleSubset {
    /**
     * Solution 1: Depth-first search with memoization
     *
     * This problem is almost exactly the same as the longest increasing sequence, except that the operator is mod (%)
     * rather than compare (<). Basically the subproblem we solve is the largest divisible subset starting from i. We
     * don't have to memorize the set itself, rather we memorize the next number index and the total length of the set.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution1 {
        public List<Integer> largestDivisibleSubset(int[] nums) {
            // memo[i] = {next index, max length}
            Arrays.sort(nums);
            int[][] memo = new int[nums.length][2];
            List<Integer> res = new ArrayList<>();
            int max = 0, idx = -1;
            for (int i = 0; i < nums.length; ++i) {
                int[] pair = helper(nums, i, memo);
                if (max < pair[1]) {
                    max = pair[1];
                    idx = i;
                }
            }
            while (idx != -1) {
                res.add(nums[idx]);
                idx = memo[idx][0];
            }
            return res;
        }

        private int[] helper(int[] nums, int i, int[][] memo) {
            if (memo[i][1] != 0) {
                return memo[i];
            }
            int max = 0, idx = -1;
            for (int j = i+1; j < nums.length; ++j) {
                if (nums[j] % nums[i] == 0) {
                    int[] pair = helper(nums, j, memo);
                    if (max < pair[1]) {
                        max = pair[1];
                        idx = j;
                    }
                }
            }
            memo[i][0] = idx;
            memo[i][1] = max + 1;
            return memo[i];
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * For this solution, the subproblem is the longest divisible subset ENDING at index i inclusively, so the nested
     * loop starts from zero and ends at i. Note the optimization in the termination condition in the inner loop
     * nums[j] * 2 <= nums[i] because numbers between nums[i] / 2 and nums[i] cannot possibly divide nums[i].
     */
    class Solution2 {
        public List<Integer> largestDivisibleSubset(int[] nums) {
            Arrays.sort(nums);
            int[] len = new int[nums.length], prev = new int[nums.length];
            int max = 0, end = -1;
            for (int i = 0; i < nums.length; ++i) {
                int maxLen = 0, prevIdx = -1;
                for (int j = 0; j < i && nums[j] * 2 <= nums[i]; ++j) {
                    if (nums[i] % nums[j] == 0) {
                        if (maxLen < len[j]) {
                            maxLen = len[j];
                            prevIdx = j;
                        }
                    }
                }
                len[i] = maxLen + 1;
                prev[i] = prevIdx;
                if (len[i] > max) {
                    max = len[i];
                    end = i;
                }
            }
            List<Integer> res = new ArrayList<>();
            while (end != -1) {
                res.add(nums[end]);
                end = prev[end];
            }
            return res;
        }
    }
}
