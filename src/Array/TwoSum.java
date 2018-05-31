/**
 * LeetCode #1, easy
 *
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.

 You may assume that each input would have exactly one solution, and you may not use the same element twice.

 Example:

 Given nums = [2, 7, 11, 15], target = 9,

 Because nums[0] + nums[1] = 2 + 7 = 9,
 return [0, 1].
 */

package Array;

import java.util.*;

public class TwoSum {
    /**
     * Solution 1: Sorting
     *
     * Sort array, then use two pointers technique:
     * If sum is too small, advance left pointer;
     * If sum is too large, advance right pointer;
     * Otherwise sum is equal to target, return indexes.
     *
     * Tricky part is that the return values need to be indices, so the sorting has to be done on a separate
     * index array, which is sorted based on values in nums array. Alternatively, one can use a map to keep
     * track of value-index pairs, and in the final step do a look up to get the indices.
     *
     * Time complexity: O(n * log(n)). Space complexity: O(n)
     */
    class Solution1 {
        public int[] twoSum(int[] nums, int target) {
            final int n = nums.length;
            Integer[] indexes = new Integer[n];
            for (int i = 0; i < n; i++) indexes[i] = i;
            Arrays.sort(indexes, new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return nums[a] - nums[b];
                }
            });
            int l = 0, r = n - 1;
            while (l < r) {
                if (nums[indexes[l]] + nums[indexes[r]] < target) l++;
                else if (nums[indexes[l]] + nums[indexes[r]] > target) r--;
                else return new int[]{indexes[l], indexes[r]};
            }
            return null;
        }
    }

    /**
     * Solution 2: Hash map
     *
     * Use a hash map to record value-index pair.
     * Iterate through the array, and check if target - current num is in the hash map.
     * If so, we are done, return the indices. If not, continue.
     *
     * One trick is to build the map as you go - just one pass. If one number needs the second later one
     * to form a solution, this algorithm guarantees that this solution will get visited -
     * when iterating to the second number.
     *
     * Time complexity: O(n). Space complexity: O(n)
     */
    class Solution2 {
        public int[] twoSum(int[] nums, int target) {
            final int n = nums.length;
            HashMap<Integer, Integer> map = new HashMap<>();
            // build map during iteration
            for (int i = 0; i < n; i++) {
                int rem = target - nums[i];
                if (map.containsKey(rem)) return new int[]{map.get(rem), i};
                else map.put(nums[i], i);
            }
            return null;
        }
    }
}
