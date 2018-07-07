/**
 * LeetCode #152, medium
 *
 * Given an integer array nums, find the contiguous subarray within an array (containing at least one number) which
 * has the largest product.

 Example 1:

 Input: [2,3,-2,4]
 Output: 6
 Explanation: [2,3] has the largest product 6.
 Example 2:

 Input: [-2,0,-1]
 Output: 0
 Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 */

package Array;

public class MaximumProductSubarray {
    /**
     * Solution 1: Dynamic programming
     *
     * This problem is very similar to Maximun Subarray (Problem #53) and can be similarly solved using DP. The
     * tricky part is, unlike maximum sum, the maximum product can be achieved in multiple different ways: Negative
     * times negative, positive times positive (zero can be take care of automatically). So we need to keep track of
     * two values: both current max and min product. For each iteration, we also need to calculate two candidates:
     * curMax * num and curMin * num, and compare against num itself to decide new curMax and curMin, and update max
     * according to curMax.
     *
     * Note: Don't attempt to update curMax and curMin using reference to themselves like curMin = min(curMin, value)
     * because whatever updated will affect the next causing it to be amplified!
     *
     * Note: Don't initialize values to max or min integer - causes overflow.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution1 {
        public int maxProduct(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            int max = nums[0], curMax = nums[0], curMin = nums[0]; // don't initialize to Integer.MIN_VALUE - overflow
            for (int i = 1; i < nums.length; i++) {
                int n1 = curMax * nums[i], n2 = curMin * nums[i]; // need to calculate candidates separately
                curMax = Math.max(Math.max(n1, n2), nums[i]); // don't update value using itself
                curMin = Math.min(Math.min(n1, n2), nums[i]);
                max = Math.max(max, curMax);
            }
            return max;
        }
    }

    /**
     * Solution 2: Accumulation
     *
     * Similar to the integration solution for Maximum Subarray problem, but much more trickier when updating max
     * value. Basically one has to check if the accumulated product is positive or negative or zero. If positive, this
     * product is candidate for max; If negative, we need to keep track of the last negative product with smallest
     * absolution value, which we call "base" here, and product/base will be the candidate; If zero, we reset the
     * base and start fresh.
     *
     * There underlies an important observation that the absolute value of the product will only increase for all
     * non-zero subarray, because abs of each number has to be at lease 1. So the base is either 1, or the first
     * negative product we see.
     *
     * Don't forget to deal with zero case explicitly.
     *
     * Time complexity: O(n). Space complexity: O(1) if accumulated product is calculated in-place.
     */
    class Solution {
        public int maxProduct(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            for (int i = 1; i < nums.length; i++) {
                if (nums[i-1] != 0) nums[i] *= nums[i-1]; // skip zero value
            }
            int max = nums[0];
            int base = 1;
            for (int i = 0; i < nums.length; i++) { // must start from zero index to deal with nums[0]
                if (nums[i] > 0) max = Math.max(max, nums[i]);
                else if (nums[i] < 0) {
                    max = Math.max(max, nums[i]/base);
                    if (base == 1) base = nums[i]; // set base for just once
                } else {
                    max = Math.max(max, 0);
                    base = 1; // reset base
                }
            }
            return max;
        }
    }
}
