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
        public int[] maxSlidingWindow(int[] nums, int k) {
            if (nums == null || nums.length == 0) return new int[0];
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
     * Solution 2: Two passes
     *
     * This solution is a bit tricky. We first partition the array into windows of size k, and use two arrays maxL
     * and maxR to store the maximum value in these windows when we traverse from left and from right. For example,
     * nums = [1,2,3,4,8,7,6,5,9,10] and k = 4, the partition is [1,2,3,4 | 8,7,6,5 | 9,10] and maxL = [1,2,3,4 | 8,
     * 8,8,8 | 9,10] and maxR = [4,4,4,4 | 8,7,6,5 | 10,10]. Then for a window starting at index i, the window will
     * be split by this partition into left and right parts, for example, i = 3 then [1,2,3,(4 | 8,7,6),5 | 9,10].
     * The max for this sliding window is either max of its left part, which is maxR[i], or max of its right part,
     * which is maxL[i+k-1]. So we just take max of maxL[i+k-1] and maxR[i] to get the answer.
     *
     * Very clever solution, but impossible to come up with at an interview.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        public int[] maxSlidingWindow(int[] nums, int k) {
            if (nums == null || nums.length == 0) return new int[0];
            final int n = nums.length;
            int[] maxL = new int[n], maxR = new int[n];
            for (int i = 0, max = Integer.MIN_VALUE; i < n; i++) {
                max = Math.max(max, nums[i]);
                maxL[i] = max;
                if ((i+1) % k == 0) max = Integer.MIN_VALUE;
            }
            for (int i = n-1, max = Integer.MIN_VALUE; i >= 0; i--) {
                max = Math.max(max, nums[i]);
                maxR[i] = max;
                if (i % k == 0) max = Integer.MIN_VALUE;
            }
            int[] res = new int[n-k+1];
            for (int i = 0; i < res.length; i++) {
                res[i] = Math.max(maxL[i+k-1], maxR[i]); // note the indexes
            }
            return res;
        }
    }

    /**
     * Solution 3: Deque
     *
     * We don't have to use a heap. Instead, use a deque as a monotonic queue. We evict indexes from head of deque
     * that falls out of this window, and we remove index from tail of deque that is smaller than current number (not
     * a possible candidate). This way, the deque will always store indexes whose corresponding values form a
     * descending order (hence monotonic here), so that the result[i] is simply the head number of this deque.
     *
     * Time complexity: O(n), every index will be offered and polled at most once. Space complexity: O(n).
     */
    class Solution3 {
        public int[] maxSlidingWindow(int[] nums, int k) {
            if (nums == null || nums.length == 0) return new int[0];
            Deque<Integer> deque = new ArrayDeque<>();
            int[] res = new int[nums.length-k+1];
            for (int i = 0; i < nums.length; i++) {
                while (!deque.isEmpty() && deque.peekFirst() < i-k+1) deque.pollFirst();
                while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) deque.pollLast();
                deque.offerLast(i);
                if (i-k+1 >= 0) res[i-k+1] = nums[deque.peekFirst()];
            }
            return res;
        }
    }

    void test() {
        int[] nums = {1,3,-1,-3,5,3,6,7};
        int k = 3;
        System.out.println(Arrays.toString(new Solution1().maxSlidingWindow(nums, 3)));
        System.out.println(Arrays.toString(new Solution2().maxSlidingWindow(nums, 3)));
    }

    public static void main(String[] args) {
        new SlidingWindowMaximum().test();
    }
}
