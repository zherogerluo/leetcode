/**
 * LeetCode #31, medium
 *
 * Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.

 If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).

 The replacement must be in-place and use only constant extra memory.

 Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.

 1,2,3 → 1,3,2
 3,2,1 → 1,2,3
 1,1,5 → 1,5,1
 */

package Array;

public class NextPermutation {
    /**
     * Solution 1:
     *
     * The next permutation is just the next number with smallest increment. The digit being incremented must have
     * been followed by a sequence that is already largest, i.e. a decreasing sequence. The algorithm is to find the
     * first peak, then we know that the left number needs to increment. Then we find the number from its right that is
     * just larger than it, swap them, then sort the rest in ascending order.
     *
     * Tricky part: Take care of the case when the sequence is already largest (descending order).
     *
     * Time complexity: O(n), three passes. Note that for the sorting in the last part, we can use two pointers
     * technique instead of any O(n * log(n)) sort, because we already know it is in descending order.
     *
     * Space complexity: O(1).
     */
    class Solution1 {
        public void nextPermutation(int[] nums) {
            if (nums == null || nums.length <= 1) return;
            int peak = nums.length-1;
            // starting from end of array, find the first peak position
            while (peak > 0) {
                if (nums[peak-1] < nums[peak]) break;
                peak--;
            }
            // swap the number in front of the peak with one after peak that is immediately larger
            // it guarantees the smallest increment, thus the next permutation
            if (peak > 0) { // this line takes care of the case when nums is descending (e.g. [8, 7, 6, 5])
                for (int i = nums.length-1; i > 0; i--) {
                    if (nums[i] > nums[peak-1]) {
                        swap(nums, i, peak-1);
                        break;
                    }
                }
            }
            // sort the array from peak to the end
            int i = peak, j = nums.length-1;
            while (i < j) swap(nums, i++, j--);
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
