/**
 * LeetCode #33, medium
 *
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

 (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).

 You are given a target value to search. If found in the array return its index, otherwise return -1.

 You may assume no duplicate exists in the array.

 Your algorithm's runtime complexity must be in the order of O(log n).

 Example 1:

 Input: nums = [4,5,6,7,0,1,2], target = 0
 Output: 4
 Example 2:

 Input: nums = [4,5,6,7,0,1,2], target = 3
 Output: -1
 */

package Array;

public class SearchInRotatedSortedArray {
    /**
     * Solution 1: Binary search
     *
     * Similar to binary search, but needs to consider more cases. Basically one can split the case to two parts:
     * left half is in order and right half is in order, then for each part, the problem reduces to normal binary
     * search.
     *
     * Tricky part: Be careful when partitioning left and right part, remember mid is always leaning to the left.
     * Consider the test case [2,1] when working on this part!
     *
     * Time complexity: O(log(n)). Space complexity: O(1).
     */
    class Solution1 {
        public int search(int[] nums, int target) {
            if(nums == null || nums.length == 0) return -1;
            int n = nums.length;
            int left = 0, right = n - 1, mid;
            while (left <= right) {
                mid = left + (right - left) / 2; // avoids overflow
                if (nums[mid] == target) return mid;
                if (nums[mid] < nums[right]) { // If we want to compare mid to left here, we need to write:
                                               // nums[mid] >= nums[left] because mid is leaning to left and could be
                                               // equal to left. Be very careful here. Consider test case [2,1]
                    // right part in order
                    if (nums[mid] < target && target <= nums[right]) left = mid + 1;
                    else right = mid - 1;
                } else {
                    if (nums[left] <= target && target < nums[mid]) right = mid - 1;
                    else left = mid + 1;
                }
            }
            return -1;
        }
    }
}
