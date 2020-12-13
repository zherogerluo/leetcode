/**
 * LeetCode #1685, medium
 *
 * You are given an integer array nums sorted in non-decreasing order.
 *
 * Build and return an integer array result with the same length as nums such that result[i] is equal to the summation
 * of absolute differences between nums[i] and all the other elements in the array.
 *
 * In other words, result[i] is equal to sum(|nums[i]-nums[j]|) where 0 <= j < nums.length and j != i (0-indexed).
 *
 * Example 1:
 *
 * Input: nums = [2,3,5]
 * Output: [4,3,5]
 * Explanation: Assuming the arrays are 0-indexed, then
 * result[0] = |2-2| + |2-3| + |2-5| = 0 + 1 + 3 = 4,
 * result[1] = |3-2| + |3-3| + |3-5| = 1 + 0 + 2 = 3,
 * result[2] = |5-2| + |5-3| + |5-5| = 3 + 2 + 0 = 5.
 *
 * Example 2:
 *
 * Input: nums = [1,4,6,8,10]
 * Output: [24,15,13,15,21]
 *
 * Constraints:
 *
 * 2 <= nums.length <= 10^5
 * 1 <= nums[i] <= nums[i + 1] <= 10^4
 */

package Math;

public class SumOfAbsoluteDifferencesInASortedArray {
    /**
     * Solution 1: Simple math
     *
     * If we have i-1 result, we can calculate i result by simply adding/subtracting a delta for each number on its
     * left/right.
     */
    class Solution1 {
        public int[] getSumAbsoluteDifferences(int[] nums) {
            final int n = nums.length;
            int[] res = new int[n];
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            res[0] = sum - nums[0] * n;
            for (int i = 1; i < n; ++i) {
                int delta = nums[i] - nums[i-1];
                res[i] = res[i-1] + delta * (2 * i - n); // + delta * (i - 1) - delta * (n - 1 - i)
            }
            return res;
        }
    }
}
