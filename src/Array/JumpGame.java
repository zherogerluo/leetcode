/**
 * LeetCode #55, medium
 *
 * Given an array of non-negative integers, you are initially positioned at the first index of the array.

 Each element in the array represents your maximum jump length at that position.

 Determine if you are able to reach the last index.

 Example 1:

 Input: [2,3,1,1,4]
 Output: true
 Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
 Example 2:

 Input: [3,2,1,0,4]
 Output: false
 Explanation: You will always arrive at index 3 no matter what. Its maximum
 jump length is 0, which makes it impossible to reach the last index.
 */

package Array;

public class JumpGame {
    /**
     * Solution 1: Greedy
     *
     * Same as Jump Game II, record the farthest we can reach as we traverse the array. If that distance covers the
     * last index, return true. Important: If we come out of the farthest range, return false.
     */
    class Solution1 {
        public boolean canJump(int[] nums) {
            int farthest = 0;
            for (int i = 0; i < nums.length; i++) {
                if (i > farthest) return false; // this line is crucial
                farthest = Math.max(farthest, i + nums[i]);
                if (farthest >= nums.length-1) return true;
            }
            return true;
        }
    }
}
