/**
 * LeetCode #239, hard
 *
 * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the
 * very right. You can only see the k numbers in the window. Each time the sliding window moves right by one
 * position. Return the max sliding window.

 Example:

 Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3
 Output: [3,3,5,5,6,7]
 Explanation:

 Window position                Max
 ---------------               -----
 [1  3  -1] -3  5  3  6  7       3
  1 [3  -1  -3] 5  3  6  7       3
  1  3 [-1  -3  5] 3  6  7       5
  1  3  -1 [-3  5  3] 6  7       5
  1  3  -1  -3 [5  3  6] 7       6
  1  3  -1  -3  5 [3  6  7]      7
 Note:
 You may assume k is always valid, 1 ≤ k ≤ input array's size for non-empty array.

 Follow up:
 Could you solve it in linear time?
 */

package Heap;

import java.util.*;

public class SlidingWindowMaximum {
    /**
     * Solution: Heap
     *
     * Use a heap to store the index of numbers and put larger numbers in front. Put new number in heap and evict
     * index from heap top that is smaller than the left edge of the window.
     *
     * Time complexity: O(n * log(n)) because for each number we do at most two heap operations which costs O(log(n)).
     *
     * Space complexity: O(n) because heap could contain n numbers for worst case (e.g. ascending array).
     */
    class Solution1 {
        public int[] slidingWindowMaximum(int[] nums, int k) {
            Queue<Integer> queue = new PriorityQueue<>((a, b) -> nums[b] - nums[a]);
            int[] res = new int[nums.length - k + 1];
            for (int r = 0; r < nums.length; r++) {
                queue.offer(r);
                int l = r - k + 1;
                while (queue.peek() < l) queue.poll();
                if (l >= 0) res[l] = nums[queue.peek()];
            }
            return res;
        }
    }

    /**
     * Solution 2:
     */
    // TODO: Linear time solution.

    void test() {
        int[] nums = {1,3,-1,-3,5,3,6,7};
        int k = 3;
        System.out.println(Arrays.toString(new Solution1().slidingWindowMaximum(nums, 3)));
    }

    public static void main(String[] args) {
        new SlidingWindowMaximum().test();
    }
}
