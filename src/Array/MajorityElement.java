/**
 * LeetCode #169, easy
 *
 * Given an array of size n, find the majority element. The majority element is the element that appears more than
 * ⌊n/2⌋ times.

 You may assume that the array is non-empty and the majority element always exist in the array.

 Example 1:

 Input: [3,2,3]
 Output: 3
 Example 2:

 Input: [2,2,1,1,1,2,2]
 Output: 2
 */

package Array;

import java.util.*;

public class MajorityElement {
    /**
     * Solution 1: Sorting
     *
     * Time complexity: O(n * log(n)). Space complexity: Depends on sorting algorithm
     */
    class Solution1 {
        public int majorityElement(int[] nums) {
            Arrays.sort(nums);
            return nums[nums.length/2];
        }
    }

    /**
     * Solution 2: Hash map
     *
     * Use a hash map to store count. If count is larger than n/2, return the number.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        public int majorityElement(int[] nums) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int num : nums) {
                int count = map.getOrDefault(num, 0) + 1;
                if (count > nums.length/2) return num;
                map.put(num, count);
            }
            return nums[nums.length-1];
        }
    }

    /**
     * Solution 3:
     *
     * Since the major element must appear more than n/2 times, we use a counter to keep track of the count of major.
     * If count becomes zero, it means at this point there are as many as half other elements, and the major element
     * shifts to current number. It guarantees that no element would appear more than half of numbers visited so far,
     * thus for the remaining subarray, the major element still holds, i.e. it will still appear more than half times
     * for the rest subarray.
     *
     * Very tricky to understand and heavily relies on the assumption that major element appears MORE than n/2.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution {
        public int majorityElement(int[] nums) {
            int major = nums[0], count = 1; // initialize count as 1
            for (int i = 1; i < nums.length;i++) {
                if (count == 0) {
                    count = 1;
                    major = nums[i];
                } else if (major == nums[i]) {
                    count++;
                } else {
                    count--;
                }
            }
            return major;
        }
    }
}
