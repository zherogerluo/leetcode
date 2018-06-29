/**
 * LeetCode #84, hard
 *
 * Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the
 * area of largest rectangle in the histogram.
       __
     __

           __
 __      __
   __
 ____________

 Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].

       __
     __
     ////
     ////  __
 __  ////__
   __////
 ____////____

 The largest rectangle is shown in the shaded area, which has area = 10 unit.

 Example:

 Input: [2,1,5,6,2,3]
 Output: 10
 */

package Array;

import java.util.*;

public class LargestRectangleInHistogram {
    /**
     * Solution 1: Stack
     *
     * An area is formed with a range and a height, where the height must be minimum in this range and the heights at
     * two ends of the range must be further smaller than the height. We use a stack to record the index of heights
     * with unknown right edge (left edge index will be the next in stack). Whenever we find a height that is shorter
     * than the stack top height, we know we find a right edge for it, then we can calculate the corresponding area
     * and record it if it is max. Keep doing this until the stack top's height is smaller than current height (i.e.
     * current height no longer serves as a valid right edge for it). Then we push current index and move on until we
     * reach the end of array.
     *
     * Tricky part: The calculation of area involves height, range start and end, each with different index. An edge
     * case is when we reach the end of array and stack is not empty, this case can be merged to i < length case.
     * Another edge case is when stack is empty, the range start index can be assigned to be -1.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution1 {
        public int largestRectangleArea(int[] heights) {
            Stack<Integer> stack = new Stack<>();
            int res = 0;
            for (int i = 0; i <= heights.length; i++) {
                // i == heights.length case can be merged to other valid indexes as long as we carefully check it
                while (!stack.isEmpty() && (i == heights.length || heights[stack.peek()] > heights[i])) {
                    // note the index check above
                    int lowest = stack.pop();
                    int start = stack.isEmpty() ? -1 : stack.peek(); // note the treatment of start when stack is empty
                    // calculation of area, three different indexes, and note the -1L
                    res = Math.max(res, heights[lowest] * (i - 1 - start));
                }
                stack.push(i);
            }
            return res;
        }
    }
}
