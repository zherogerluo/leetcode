/**
 * LeetCode #18, medium
 *
 * Given an array nums of n integers and an integer target, are there elements a, b, c, and d in nums such that a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.

 Note:

 The solution set must not contain duplicate quadruplets.

 Example:

 Given array nums = [1, 0, -1, 0, -2, 2], and target = 0.

 A solution set is:
 [
 [-1,  0, 0, 1],
 [-2, -1, 1, 2],
 [-2,  0, 0, 2]
 ]
 */

package Array;

import java.util.*;

public class FourSum {
    /**
     * Solution 1: Two pointers
     *
     * Two nested for loop reduces it to n^2 two sum problems. Beware of duplicates.
     *
     * Time complexity: O(n^3). Space complexity: O(1) not counting output.
     */
    class Solution1 {
        public List<List<Integer>> fourSum(int[] nums, int target) {
            List<List<Integer>> res = new ArrayList<>();
            Arrays.sort(nums);
            for (int i = 0; i < nums.length; i++) {
                if (i > 0 && nums[i] == nums[i-1]) continue; // skip duplicates
                for (int j = i+1; j < nums.length; j++) {
                    if (j > i+1 && nums[j] == nums[j-1]) continue; // skip duplicates
                    int t = target - nums[i] - nums[j], l = j+1, r = nums.length-1;
                    while (l < r) {
                        if (nums[l] + nums[r] < t) l++;
                        else if (nums[l] + nums[r] > t) r--;
                        else {
                            res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                            while (l < r && nums[l++] == nums[l]);
                            while (l < r && nums[r--] == nums[r]);
                        }
                    }
                }
            }
            return res;
        }
    }

    /**
     * Solution 2: Two pointers
     *
     * Optimized based on Solution 1. Skip cases where the max sum is too small or min sum is too large.
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        final int n = nums.length;
        for (int i = 0; i < n-3; i++) {
            if (i > 0 && nums[i] == nums[i-1]) continue; // skip duplicates
            if (nums[i] + nums[i+1] + nums[i+2] + nums[i+3] > target) break; // min sum is too large
            if (nums[i] + nums[n-3] + nums[n-2] + nums[n-1] < target) continue; // max sum is too small
            for (int j = i+1; j < n-2; j++) {
                if (j > i+1 && nums[j] == nums[j-1]) continue; // skip duplicates
                if (nums[i] + nums[j] + nums[j+1] + nums[j+2] > target) break; // min sum is too large
                if (nums[i] + nums[j] + nums[n-2] + nums[n-1] < target) continue; // max sum is too small
                int t = target - nums[i] - nums[j], l = j+1, r = n-1;
                while (l < r) {
                    if (nums[l] + nums[r] < t) l++;
                    else if (nums[l] + nums[r] > t) r--;
                    else {
                        res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        while (l < r && nums[l++] == nums[l]);
                        while (l < r && nums[r--] == nums[r]);
                    }
                }
            }
        }
        return res;
    }
}
