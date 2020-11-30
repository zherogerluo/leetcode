/**
 * LeetCode #581, medium
 *
 * Given an integer array nums, you need to find one continuous subarray that if you only sort this subarray in
 * ascending order, then the whole array will be sorted in ascending order.
 *
 * Return the shortest such subarray and output its length.
 *
 * Example 1:
 *
 * Input: nums = [2,6,4,8,10,9,15]
 * Output: 5
 * Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make the whole array sorted in ascending order.
 *
 * Example 2:
 *
 * Input: nums = [1,2,3,4]
 * Output: 0
 *
 * Example 3:
 *
 * Input: nums = [1]
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^4
 * -10^5 <= nums[i] <= 10^5
 */

public class ShortestUnsortedContinuousSubarray {
    /**
     * Solution 1: Sorting
     *
     * Sort and compare, straightforward, beware of special case of already sorted array, need to make sure to return 0.
     *
     * Time complexity: O(n * log(n)). Space complexity: O(n).
     */
    class Solution1 {
        public int findUnsortedSubarray(int[] nums) {
            int[] copy = Arrays.copyOf(nums, nums.length);
            Arrays.sort(copy);
            int start = 1, end = 0;
            for (int i = 0; i < nums.length; ++i) {
                if (nums[i] != copy[i]) {
                    start = i;
                    break;
                }
            }
            for (int i = nums.length - 1; i >= 0; --i) {
                if (nums[i] != copy[i]) {
                    end = i;
                    break;
                }
            }
            return end - start + 1;
        }
    }

    /**
     * Solution 2: Compare with max and min
     *
     * The idea is, if an array is already sorted, as we iterate, we should see nums[i] is always equal to the max (or
     * min if we start from right). We can easily get the start and end of unsorted range based on this observation.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution2 {
        public int findUnsortedSubarray(int[] nums) {
            int max = nums[0], min = nums[nums.length-1];
            int start = 1, end = 0;
            for (int i = 0; i < nums.length; ++i) {
                max = Math.max(max, nums[i]);
                if (nums[i] < max) {
                    end = i;
                }
            }
            for (int i = nums.length - 1; i >= 0; --i) {
                min = Math.min(min, nums[i]);
                if (nums[i] > min) {
                    start = i;
                }
            }
            return end - start + 1;
        }
    }
}