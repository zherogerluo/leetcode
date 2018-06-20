/**
 * LeetCode #45, hard
 *
 * Given an array of non-negative integers, you are initially positioned at the first index of the array.

 Each element in the array represents your maximum jump length at that position.

 Your goal is to reach the last index in the minimum number of jumps.

 Example:

 Input: [2,3,1,1,4]
 Output: 2
 Explanation: The minimum number of jumps to reach the last index is 2.
 Jump 1 step from index 0 to 1, then 3 steps to the last index.
 Note:

 You can assume that you can always reach the last index.
 */

package Array;

public class JumpGameTwo {
    /**
     * Solution 1: Breadth-first search
     *
     * This algorithm may not look like a BFS but deep in its core it implements the BFS idea. We loop through the
     * array, keep track of the farthest index we can jump to if the current index is within the previous farthest
     * range. If we get out of the previous range, increment the jump count because we know currently we need an
     * additional jump to get here, meanwhile update the previous farthest range. It is BFS because we visit the
     * indexes level by level, although some are skipped because they are visited in previous levels. The BFS nodes
     * are continuous indexes so we don't need to keep a queue, probably another reason it does not look like BFS.
     *
     * Tricky part: Update the jump count only if i gets out of range, otherwise will miss count by 1 if previous
     * farthest range covers exactly till the ending index.
     *
     * Important note: Do not even attempt DFS because you know it is going to be bad!!!
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution1 {
        public int jump(int[] nums) {
            int jump = 0, prevFarthest = 0, curFarthest = 0;
            for (int i = 0; i < nums.length; i++) {
                if (i > prevFarthest) { // tricky part: cannot write i == prevFarthest
                    jump++;
                    prevFarthest = curFarthest;
                }
                curFarthest = Math.max(curFarthest, i + nums[i]);
            }
            return jump;
        }
    }
}
