/**
 * LeetCode #41, hard
 *
 * Given an unsorted integer array, find the smallest missing positive integer.

 Example 1:

 Input: [1,2,0]
 Output: 3
 Example 2:

 Input: [3,4,-1,1]
 Output: 2
 Example 3:

 Input: [7,8,9,11,12]
 Output: 1
 Note:

 Your algorithm should run in O(n) time and uses constant extra space.
 */

package Array;

import java.util.*;

public class FirstMissingPositive {
    /**
     * Solution 1: Sorting
     *
     * Sort the array, and check positive numbers. If there is any missing leap, return the result.
     *
     * Tricky part: Must skip the non-positive numbers, AND the duplicated numbers.
     *
     * Time complexity: O(n * log(n)). Space complexity: O(1).f
     */
    class Solution1 {
        public int firstMissingPositive(int[] nums) {
            if (nums == null) return 1;
            Arrays.sort(nums);
            int num = 1;
            for (int i = 0; i < nums.length; i++) {
                if (i < nums.length-1 && nums[i+1] == nums[i]) continue;
                if (nums[i] > 0 && nums[i] != num++) return num - 1;
            }
            return num;
        }
    }

    /**
     * Solution 2: Bucket sort
     *
     * We can mathematically prove that the first missing positive will be no larger than nums.length + 1: Otherwise
     * we won't be able to fit smaller positive numbers in just nums.length buckets. So the idea here is to just bucket
     * sort positive numbers that are smaller than nums.length + 1, either in-place or with external O(n) storage.
     * Then we just check the sorted sequence one by one to find the first missing number.
     *
     * Trick part is to get the bucket sorting right. For in-place algorithm, we need to continuous swap numbers if
     * they are not in the right place, until it is, or the next number is out of range.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution2 {
        public int firstMissingPositive(int[] nums) {
            if (nums == null) return 1;
            for (int i = 0; i < nums.length; i++) {
                int k = nums[i] - 1, temp; // be careful with the definition of k: k is not i - 1
                                           // k can be understood as "the index where nums[i] belongs"
                while (k >= 0 && k < nums.length && nums[k] != k + 1) {
                    temp = nums[k] - 1;
                    nums[k] = k + 1;
                    k = temp;
                }
            }
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != i + 1) return i + 1;
            }
            return nums.length + 1;
        }
    }
}
