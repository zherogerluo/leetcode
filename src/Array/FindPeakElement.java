/**
 * LeetCode #162, medium
 *
 * A peak element is an element that is greater than its neighbors.

 Given an input array nums, where nums[i] ≠ nums[i+1], find a peak element and return its index.

 The array may contain multiple peaks, in that case return the index to any one of the peaks is fine.

 You may imagine that nums[-1] = nums[n] = -∞.

 Example 1:

 Input: nums = [1,2,3,1]
 Output: 2
 Explanation: 3 is a peak element and your function should return the index number 2.
 Example 2:

 Input: nums = [1,2,1,3,5,6,4]
 Output: 1 or 5
 Explanation: Your function can return either index number 1 where the peak element is 2,
 or index number 5 where the peak element is 6.
 Note:

 Your solution should be in logarithmic complexity.
 */

package Array;

public class FindPeakElement {
    /**
     * Solution 1: Naive O(n) solution
     *
     * Trivial, remember to check for bounds
     */
    class Solution1 {
        public int findPeakElement(int[] nums) {
            for (int i = 0; i < nums.length; i++) {
                if ((i == 0 || nums[i] > nums[i-1]) && (i == nums.length-1) || nums[i] > nums[i+1]) {
                    return i;
                }
            }
            return -1;
        }
    }

    /**
     * Solution 2: More elegant O(n) solution
     *
     * Check only for downward trend, because there underlies an implicit assumption that the array was always in
     * rising trend if this check fails.
     */
    class Solution {
        public int findPeakElement(int[] nums) {
            for (int i = 0; i < nums.length-1; i++){
                if (nums[i] > nums[i+1]) return i;
            }
            return nums.length-1;
        }
    }

    /**
     * Solution 3: Naive O(log(n)) solution
     *
     * Binary search, if slope at mid is positive then there must be a peak at right side, vice versa for left side.
     * Intermediate value theorem for f'(x).
     */
    class Solution3 {
        public int findPeakElement(int[] nums) {
            if (nums == null || nums.length == 0) return -1;
            if (nums.length == 1) return 0;
            int left = 0, right = nums.length-1, mid;
            while (left <= right) {
                mid = left + (right - left) / 2;
                if (mid > 0 && nums[mid-1] > nums[mid]) right = mid - 1;
                else if (mid < nums.length-1 && nums[mid+1] > nums[mid]) left = mid + 1;
                else return mid;
            }
            return -1;
        }
    }

    /**
     * Solution 4: More elegant O(log(n)) solution
     *
     * Binary search, check only the right side num.
     */
    class Solution4 {
        public int findPeakElement(int[] nums) {
            int left = 0, right = nums.length-1, mid;
            while (left < right) {
                mid = left + (right - left) / 2;
                if (nums[mid+1] > nums[mid]) left = mid + 1;
                else right = mid;
            }
            return left;
        }
    }
}
