/**
 * LeetCode #16, medium
 *
 * Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest to target. Return the sum of the three integers. You may assume that each input would have exactly one solution.

 Example:

 Given array nums = [-1, 2, 1, -4], and target = 1.

 The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 */

package Array;

import java.util.*;

public class ThreeSumClosest {
    /**
     * Solution 1: Two pointers
     *
     * Similar to three sum, except using a dist to keep track of the closest distance.
     *
     * Time complexity: O(n^2). Space complexity: O(1)
     */
    class Solution1 {
        public int threeSumClosest(int[] nums, int target) {
            int dist = Integer.MAX_VALUE;
            Arrays.sort(nums);
            for (int i = 0; i < nums.length; i++) {
                int l = i+1, r = nums.length-1;
                while (l < r) {
                    int d = nums[i] + nums[l] + nums[r] - target;
                    if (d < 0) l++;
                    else if (d > 0) r--;
                    else return target;
                    dist = Math.abs(dist) > Math.abs(d) ? d : dist;
                }
            }
            return dist + target;
        }
    }
}
