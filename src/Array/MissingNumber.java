/**
 * LeetCode #268, easy
 *
 * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.

 Example 1:

 Input: [3,0,1]
 Output: 2
 Example 2:

 Input: [9,6,4,2,3,5,7,0,1]
 Output: 8
 Note:
 Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space
 complexity?
 */

package Array;

import java.util.*;

public class MissingNumber {
    /**
     * Solution 1: XOR
     *
     * XOR from 1 to n, then XOR all numbers, whatever left is the missing one. It takes advantage of n XOR n == 0
     * and 0 XOR n == n.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution1 {
        public int missingNumber(int[] nums) {
            final int n = nums.length;
            int res = 0;
            for (int i = 0; i <= n; i++) res ^= i;
            for (int num : nums) res ^= num;
            return res;
        }
    }

    /**
     * Solution 2: Sorting
     *
     * Sort and compare value with index. Note we need to return length of nums after the loop.
     */
    class Solution2 {
        public int missingNumber(int[] nums) {
            Arrays.sort(nums);
            for (int i = 0; i < nums.length; i++) {
                if (i != nums[i]) return i;
            }
            return nums.length; // note here
        }
    }

    /**
     * Solution 3: Map
     *
     * Use an array as map, record number if it presents. Loop through map and report index whose value is not correct.
     *
     * Tricky part: Need to initialize map to -1 to deal with 0.
     */
    class Solution3 {
        public int missingNumber(int[] nums) {
            int[] map = new int[nums.length + 1];
            Arrays.fill(map, -1);
            for (int num : nums) {
                map[num] = num;
            }
            for (int i = 0; i < map.length; i++) {
                if (map[i] == -1) return i;
            }
            return -1;
        }
    }
}
