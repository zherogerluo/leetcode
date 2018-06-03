/**
 * LeetCode #11, medium
 *
 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai). n vertical
 * lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines, which together
 * with x-axis forms a container, such that the container contains the most water.

 Note: You may not slant the container and n is at least 2.
 */

package Array;

public class ContainerWithMostWater {
    /**
     * Solution 1: Two pointers
     *
     * Start from left- and right-most walls and update the maximum area, and move the shorter wall closer every time
     * in hope to find a larger-area container.
     *
     * Time complexity: O(n). Space complexity: O(1)
     */
    class Solution1 {
        public int maxArea(int[] height) {
            if (height == null || height.length < 2) return 0;
            int left = 0, right = height.length-1, res = 0;
            while (left < right) {
                if (height[left] < height[right]) {
                    res = Math.max(res, height[left] * (right-left));
                    left++;
                } else {
                    res = Math.max(res, height[right] * (right-left));
                    right--;
                }
            }
            return res;
        }
    }
}
