/**
 * LeetCode #26, easy
 *
 * Given a sorted array nums, remove the duplicates in-place such that each element appear only once and return the new length.

 Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.

 Example 1:

 Given nums = [1,1,2],

 Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.

 It doesn't matter what you leave beyond the returned length.
 Example 2:

 Given nums = [0,0,1,1,1,2,2,3,3,4],

 Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3, and 4 respectively.

 It doesn't matter what values are set beyond the returned length.
 */

package Array;

public class RemoveDuplicatesFromSortedArray {
    /**
     * Solution 1: Two pointers
     *
     * Slow and fast pointers, fast detects duplicates and move forward, slow keeps track of the next unique number
     * to be written.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution1 {
        public int removeDuplicates(int[] nums) {
            int slow = 0, fast = 0;
            for (; fast < nums.length; fast++) {
                if (fast < nums.length-1 && nums[fast] == nums[fast+1]) continue;
                nums[slow++] = nums[fast];
            }
            return slow;
        }
    }
}
