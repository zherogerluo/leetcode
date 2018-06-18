/**
 * LeetCode #34, medium
 *
 * Given an array of integers nums sorted in ascending order, find the starting and ending position of a given target value.

 Your algorithm's runtime complexity must be in the order of O(log n).

 If the target is not found in the array, return [-1, -1].

 Example 1:

 Input: nums = [5,7,7,8,8,10], target = 8
 Output: [3,4]
 Example 2:

 Input: nums = [5,7,7,8,8,10], target = 6
 Output: [-1,-1]
 */

package Array;

public class SearchForARange {
    /**
     * Solution 1: Modified binary search
     *
     * Modified binary search for left or right edge. Add two more cases when nums[mid] == target.
     *
     * Time complexity: O(log(n)). Space complexity: O(1)
     */
    class Solution1 {
        public int[] searchRange(int[] nums, int target) {
            return new int[]{searchEdge(nums, target, true), searchEdge(nums, target, false)};
        }

        private int searchEdge(int[] nums, int target, boolean searchLeft) {
            int left = 0, right = nums.length-1, mid;
            while (left <= right) {
                mid = left + (right - left) / 2;
                if (nums[mid] < target) left = mid + 1;
                else if (nums[mid] > target) right = mid - 1;
                else {
                    if (searchLeft) {
                        if (mid == 0 || nums[mid-1] < nums[mid]) return mid;
                        else right = mid - 1;
                    } else {
                        if (mid == nums.length-1 || nums[mid+1] > nums[mid]) return mid;
                        else left = mid + 1;
                    }
                }
            }
            return -1;
        }
    }

    /**
     * Solution 2: Binary search for halves
     *
     * Search for target +- 0.5, return two indexes i and j, then the result should be [i, j-1].
     *
     * Tricky part: Check for bound for returned index. Check if the target was found.
     */
    class Solution {
        public int[] searchRange(int[] nums, int target) {
            if (nums == null || nums.length == 0) return new int[]{-1, -1};
            int left = search(nums, target-0.5), right = search(nums, target+0.5);
            if (left >= nums.length || nums[left] != target) return new int[]{-1, -1}; // these checks are necessary
            return new int[]{left, right-1};
        }

        /* Returns the index of smallest num larger than target. Prerequisite: target is not an integer.
         * This implementation guarantees to return the index that is to the immediate right of target. */
        private int search(int[] nums, double target) {
            int left = 0, right = nums.length-1, mid;
            while (left <= right) {
                mid = left + (right - left) / 2;
                if (nums[mid] < target) left = mid + 1;
                else right = mid - 1;
                // no need to consider nums[mid] == target case because it won't be.
            }
            return left;
        }
    }
}
