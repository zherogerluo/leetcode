/**
 * LeetCode #42, hard
 *
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much
 * water it is able to trap after raining.

 The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water
 (blue section) are being trapped. Thanks Marcos for contributing this image!

 Example:

 Input: [0,1,0,2,1,0,1,3,2,1,2,1]
 Output: 6
 */

package Array;

import java.util.*;

public class TrappingRainWater {
    /**
     * Solution 1: Stack
     *
     * Use a stack to remember index of walls. For a new wall, pop the stack until we see a higher wall, for each
     * popped wall, we collect water between it and the current wall.
     *
     * This algorithm will always reflect the actual water collected, i.e. result at index i is always valid for the
     * sub-array height[0-i]. One way of illustrating this algorithm is that, every time we collect water, we fill
     * concrete to replace the void. So every wall in the stack represents a concrete wall that extends all the way
     * back to the next index in the stack.
     *
     * Tricky part: In the water collection process, we need to remember the height of the last floor. The water is
     * collected layer by layer.
     *
     * Time complexity: O(n) because every number is visited at most two times. Space complexity: O(n).
     */
    class Solution1 {
        public int trap(int[] height) {
            if (height == null || height.length <= 2) return 0;
            Deque<Integer> stack = new ArrayDeque<>();
            int water = 0;
            for (int i = 0; i < height.length; i++) {
                if (height[i] == 0) continue; // this line is not necessary but is expected to improve performance
                int floor = 0;
                while (!stack.isEmpty()) {
                    int j = stack.peek(), ceiling = Math.min(height[i], height[j]);
                    water += (i - j - 1) * (ceiling - floor);
                    floor = ceiling; // remember the floor
                    if (height[j] <= height[i]) stack.pop();
                    else break; // if the next wall in stack is higher, we stop collecting water.
                }
                stack.push(i);
            }
            return water;
        }
    }

    /**
     * Solution 2: Two pointers
     *
     * Two pointers start from left and right, record the max they have seen. Advance the pointer whose side has
     * lower max, and collect a column of water each time, since the other side has a higher max wall height, this
     * column of water is guaranteed.
     *
     * This algorithm is very much like the one in "Container with Most Water" problem.
     *
     * Time complexity: O(n), one pass. Space complexity: O(1).
     */
    class Solution2 {
        public int trap(int[] height) {
            if (height == null || height.length <= 2) return 0;
            int left = 0, right = height.length-1;
            int maxL = height[left], maxR = height[right];
            int water = 0;
            while (left < right) {
                if (maxL <= maxR) {
                    left++;
                    if (height[left] >= maxL) maxL = height[left];
                    else water += maxL - height[left];
                } else {
                    right--;
                    if (height[right] >= maxR) maxR = height[right];
                    else water += maxR - height[right];
                }
            }
            return water;
        }
    }
}
