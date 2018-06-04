/**
 * LeetCode #15, medium
 *
 * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

 Note:

 The solution set must not contain duplicate triplets.

 Example:

 Given array nums = [-1, 0, 1, 2, -1, -4],

 A solution set is:
 [
 [-1, 0, 1],
 [-1, -1, 2]
 ]
 */

package Array;

import java.util.*;

public class ThreeSum {
    /**
     * Solution 1: Two pointers
     *
     * For each num in nums, perform two sum search where target is -num.
     *
     * Tricky part: Skip the duplicates, and be careful with out of bound array access!
     *
     * Time complexity: O(n^2). Space complexity: O(1) (not counting results)
     */
    class Solution1 {
        public List<List<Integer>> threeSum(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            Arrays.sort(nums);
            for (int i = 0; i < nums.length; i++) {
                // reduce to two sum
                int left = i+1, right = nums.length-1, target = -nums[i];
                while (left < right) {
                    if (nums[left] + nums[right] < target) left++;
                    else if (nums[left] + nums[right] > target) right--;
                    else {
                        /* duplicates only need to be handled when a triplet is found */
                        res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                        // skip duplicates
                        while (left < right && nums[left++] == nums[left]);
                        while (left < right && nums[right--] == nums[right]);
                    }
                }
                // skip duplicates
                while (i < nums.length-1 && nums[i+1] == nums[i]) i++;
            }
            return res;
        }
    }

    /**
     * Solution 2: Two pointers
     *
     * Same as Solution 1, except using a hash set to store results and remove duplicates. Performance hit.
     */
    class Solution2 {
        public List<List<Integer>> threeSum(int[] nums) {
            Set<List<Integer>> res = new HashSet<>();
            Arrays.sort(nums);
            for (int i = 0; i < nums.length; i++) {
                // reduce to two sum
                int left = i+1, right = nums.length-1, target = -nums[i];
                while (left < right) {
                    if (nums[left] + nums[right] < target) left++;
                    else if (nums[left] + nums[right] > target) right--;
                    else res.add(Arrays.asList(nums[i], nums[left++], nums[right--]));
                }
            }
            return new ArrayList<List<Integer>>(res);
        }
    }
}
