/**
 * LeetCode #53, easy
 *
 * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest
 * sum and return its sum.

 Example:

 Input: [-2,1,-3,4,-1,2,1,-5,4],
 Output: 6
 Explanation: [4,-1,2,1] has the largest sum = 6.
 Follow up:

 If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which
 is more subtle.
 */

package Array;

public class MaximumSubarray {
    /**
     * Solution 1: Dynamic programming
     *
     * For each number in the array, find the sum of maximum subarray ending at this number. This can be done by
     * looking at such sum at previous index: If it is positive, then the new sum is the last sum plus current number
     * (regardless if it positive or not), otherwise the new sum is just this number itself (adding a negative last
     * sum will only make the new sum smaller). This is DP without an explicit DP array because all we need for index
     * i is just the result at index i-1.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution1 {
        public int maxSubArray(int[] nums) {
            int lastMax = 0, res = nums[0];
            for (int num : nums) {
                int curMax;
                if (lastMax > 0) curMax = lastMax + num;
                else curMax = num;
                res = Math.max(res, curMax);
                lastMax = curMax;
            }
            return res;
        }
    }

    /**
     * Solution 2: Integration
     *
     * Prepare an integration array, where each number is the sum of all previous numbers in nums array. Then the
     * problem transforms to: Find the lowest and highest number in this integral and the result will be highest -
     * lowest.
     *
     * A very important note is that the lowest number is not necessary one of the array element. In fact, the lowest
     * number must be min(0, min in integral), simply because the integral should start from zero.
     *
     * Time complexity: O(n). Space complexity: O(1) if integral is calculated in-place.
     */
    class Solution2 {
        public int maxSubArray(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            for (int i = 1; i < nums.length; i++) {
                nums[i] += nums[i-1];
            }
            int low = 0, res = nums[0];
            for (int i = 0; i < nums.length; i++) {
                res = Math.max(res, nums[i] - low); // Tricky part: this line has to come before the next line
                if (nums[i] < low) low = nums[i];
            }
            return res;
        }
    }
}
