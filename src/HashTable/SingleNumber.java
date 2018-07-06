/**
 * LeetCode #136, easy
 *
 * Given a non-empty array of integers, every element appears twice except for one. Find that single one.

 Note:

 Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

 Example 1:

 Input: [2,2,1]
 Output: 1
 Example 2:

 Input: [4,1,2,1,2]
 Output: 4
 */

package HashTable;

import java.util.*;

public class SingleNumber {
    /**
     * Solution 1: XOR
     *
     * x ^ 0 = x, x ^ x = 0, and XOR sequence can be exchanged. So we XOR everything, what is left is the single one.
     */
    class Solution1 {
        public int singleNumber(int[] nums) {
            int res = 0;
            for (int num : nums) res = res ^ num;
            return res;
        }
    }

    /**
     * Solution 2: Hash table
     *
     * Use a hash set to store numbers that appeared once. Remove it if it appears again.
     */
    class Solution2 {
        public int singleNumber(int[] nums) {
            Set<Integer> set = new HashSet<>();
            for (int num : nums) {
                if (set.contains(num)) set.remove(num);
                else set.add(num);
            }
            return set.iterator().next();
        }
    }

    /**
     * Solution 3: Sorting
     *
     * Sort the array and check the pairs. Return the first element in the pair if the pair contains different
     * numbers. Note we need to return the last element if the loop ends (the array length is expected to be odd).
     */
    class Solution3 {
        public int singleNumber(int[] nums) {
            Arrays.sort(nums);
            for (int i = 1; i < nums.length; i += 2) {
                if (nums[i] != nums[i-1]) return nums[i-1];
            }
            return nums[nums.length-1];
        }
    }
}
