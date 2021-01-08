/**
 * LeetCode #698, medium
 *
 * Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k
 * non-empty subsets whose sums are all equal.
 *
 * Example 1:
 *
 * Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 * Output: True
 * Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
 *
 * Note:
 *
 * 1 <= k <= len(nums) <= 16.
 * 0 < nums[i] < 10000.
 */

package DynamicProgramming;

import java.util.*;

public class PartitionToKEqualSumSubsets {
    /**
     * Solution 1: Depth-first search
     *
     * It is easy to come up with the idea that we need to calculate sum of each subset, which is total sum / k. We can
     * then DFS until all numbers are used. Once a target is met, immediately start the next level DFS.
     */
    class Solution1 {
        public boolean canPartitionKSubsets(int[] nums, int k) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            if (sum % k != 0) {
                return false;
            }
            sum /= k;
            // critical optimization: 1) sorting 2) testing largest number
            Arrays.sort(nums);
            if (nums[nums.length-1] > sum) {
                return false;
            }
            return helper(nums, new boolean[nums.length], sum, sum);
        }

        private boolean helper(int[] nums, boolean[] used, int target, final int sum) {
            if (target < 0) {
                return false;
            }
            boolean allUsed = true;
            for (int i = nums.length-1; i >= 0; --i) {
                if (used[i]) {
                    continue;
                }
                allUsed = false;
                used[i] = true;
                boolean res = false;
                if (nums[i] == target) {
                    // immediately start the next round searching
                    res = helper(nums, used, sum, sum);
                } else if (nums[i] < target) {
                    res = helper(nums, used, target-nums[i], sum);
                }
                used[i] = false;
                if (res) {
                    return true;
                }
            }
            return allUsed;
        }
    }
}
