/**
 * LeetCode #324, medium
 *
 * Given an unsorted array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....

 Example 1:

 Input: nums = [1, 5, 1, 1, 6, 4]
 Output: One possible answer is [1, 4, 1, 5, 1, 6].
 Example 2:

 Input: nums = [1, 3, 2, 2, 3, 1]
 Output: One possible answer is [2, 3, 1, 3, 1, 2].
 Note:
 You may assume all input has valid answer.

 Follow Up:
 Can you do it in O(n) time and/or in-place with O(1) extra space?
 */

package Sort;

import java.util.*;

public class WiggleSortTwo {
    /**
     * Solution 1: Sorting
     *
     * Sort the array, put smaller half to even indexes and larger half to odd indexes. Tricky part is we have to
     * somehow reverse the two halves to make sure the duplicate elements in the middle are put far apart so that
     * they won't become neighbors.
     *
     * Time complexity: O(n * log(n)). Space complexity: O(n).
     */
    class Solution1 {
        public void wiggleSort(int[] nums) {
            final int n = nums.length;
            int[] copy = Arrays.copyOf(nums, n);
            Arrays.sort(copy);
            for (int i = 0, j = (n+1)/2, k = n-1; k >= 0; k--) {
                if (k % 2 == 0) nums[k] = copy[i++]; // even
                else nums[k] = copy[j++]; // odd
            }
        }
    }

    /**
     * Solution 2: Partitioning
     *
     * Sorting is overkill because we don't care whether smaller and larger halves are sorted or not. We just need to
     * partition it using the median. So we can use quick select to find median and partition the array in O(n) time.
     * Tricky part is to do a three-way partition so that the median duplicates can be grouped together, and iterate
     * from back so that they won't become neighbors (similar to Solution 1).
     *
     * Time complexity: O(n) average, O(n^2) worst case. Space complexity: O(n).
     */
    // TODO
}
