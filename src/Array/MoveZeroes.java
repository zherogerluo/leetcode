/**
 * LeetCode #283, easy
 *
 * Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the
 * non-zero elements.

 Example:

 Input: [0,1,0,3,12]
 Output: [1,3,12,0,0]
 Note:

 You must do this in-place without making a copy of the array.
 Minimize the total number of operations.
 */

package Array;

public class MoveZeroes {
    /**
     * Solution 1: Two pointers
     *
     * Keep a slow pointer, and copy non-zero numbers to slow pointer location. Then fill remaining array with zeros.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution1 {
        public void moveZeroes(int[] nums) {
            int index = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != 0) {
                    nums[index] = nums[i];
                    index++;
                }
            }
            // fill remaining array with zeros
            for (int i = index; i < nums.length; i++) {
                nums[i] = 0;
            }
        }
    }

    /**
     * Solution 2: Two pointers
     *
     * Similar idea, except to change fast pointer number to zeros at the same time when copying it to slow pointer
     * location. Remember to check for array bound.
     */
    class Solution2 {
        public void moveZeroes(int[] nums) {
            int zero = 0;
            while (zero < nums.length && nums[zero] != 0) zero++; // check for bound here
            for (int i = zero; i < nums.length; i++) {
                if (nums[i] != 0) {
                    nums[zero++] = nums[i];
                    nums[i] = 0;
                }
            }
        }
    }
}
