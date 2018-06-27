/**
 * LeetCode #75, medium
 *
 * Given an array with n objects colored red, white or blue, sort them in-place so that objects of the same color are
 * adjacent, with the colors in the order red, white and blue.

 Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.

 Note: You are not suppose to use the library's sort function for this problem.

 Example:

 Input: [2,0,2,1,1,0]
 Output: [0,0,1,1,2,2]
 Follow up:

 A rather straight forward solution is a two-pass algorithm using counting sort.
 First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's, then
 1's and followed by 2's.
 Could you come up with a one-pass algorithm using only constant space?
 */

package Array;

public class SortColors {
    /**
     * Solution 1: Bucket sort
     *
     * Straightforward bucket sort. Two passes.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution1 {
        public void sortColors(int[] nums) {
            int n0 = 0, n1 = 0;
            for (int num : nums) {
                if (num == 0) n0++;
                else if (num == 1) n1++;
            }
            int i = 0;
            for (; i < n0; i++) nums[i] = 0;
            for (; i < n0+n1; i++) nums[i] = 1; // beware of this n0+n1
            for (; i < nums.length; i++) nums[i] = 2;
        }
    }

    /**
     * Solution 2: Two pointers
     *
     * Two pointers to record end of zero sequence and start of two sequence. If we see an zero, append it to zero
     * sequence; vice versa for twos. If we see a one, keep going. Tricky part is we need to make sure current index
     * is no smaller than end of zero index. One pass.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution2 {
        public void sortColors(int[] nums) {
            int endOfZero = 0, startOfTwo = nums.length-1, cur = 0;
            while (cur <= startOfTwo) {
                if (nums[cur] == 0) {
                    swap(nums, endOfZero++, cur);
                    cur = endOfZero > cur ? endOfZero : cur; // index bound check
                } else if (nums[cur] == 1) {
                    cur++;
                } else {
                    swap(nums, startOfTwo--, cur);
                }
            }
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
