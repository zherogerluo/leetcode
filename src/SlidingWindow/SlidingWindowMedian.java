/**
 * LeetCode #480, hard
 *
 * Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So
 * the median is the mean of the two middle value.
 *
 * Examples:
 * [2,3,4] , the median is 3
 *
 * [2,3], the median is (2 + 3) / 2 = 2.5
 *
 * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very
 * right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Your
 * job is to output the median array for each window in the original array.
 *
 * For example,
 * Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
 *
 * Window position                Median
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       1
 *  1 [3  -1  -3] 5  3  6  7       -1
 *  1  3 [-1  -3  5] 3  6  7       -1
 *  1  3  -1 [-3  5  3] 6  7       3
 *  1  3  -1  -3 [5  3  6] 7       5
 *  1  3  -1  -3  5 [3  6  7]      6
 * Therefore, return the median sliding window as [1,-1,-1,3,5,6].
 *
 * Note:
 * You may assume k is always valid, ie: k is always smaller than input array's size for non-empty array.
 * Answers within 10^-5 of the actual value will be accepted as correct.
 */

import java.util.*;

package SlidingWindow;

public class SlidingWindowMedian {
    /**
     * Solution 1: Heap
     *
     * Same idea as Find Median From Data Stream, use two heaps to store lower half and high half of the numbers, so
     * that the median can be obtained in O(1) time. The difference is that, for sliding window, we need to evict old
     * number but cannot do that efficiently using heap data structure. However, we can treat a number as "evicted" but
     * not actually removing it from the heap; We just need to ignore it when we see it at the heap head. Using this
     * idea, the solution is similar to those of Find Median From Data Stream: Add number and keep the heaps balanced,
     * except for this problem we balance heaps by remembering the relative size difference (because we cannot really
     * remove the number). Note if the to-be-evicted number is at either heap's head, we CAN actually remove it.
     *
     * Time complexity: O(n * log(k)): Heap operation is log(k) and we have at most c * n of them where c is constant.
     * Space complexity: O(n) not counting results.
     */
    class Solution1 {
        public double[] medianSlidingWindow(int[] nums, int k) {
            Queue<Integer> low = new PriorityQueue<>((i, j) -> Integer.compare(nums[j], nums[i]));
            Queue<Integer> high = new PriorityQueue<>(Comparator.comparingInt(i -> nums[i]));
            double[] res = new double[nums.length-k+1];
            // prepare initial window, low is no shorter than high
            for (int i = 0; i < k; ++i) {
                if (!low.isEmpty() && nums[i] > nums[low.peek()]) {
                    high.offer(i);
                } else {
                    low.offer(i);
                }
                while (high.size() > low.size()) {
                    low.offer(high.poll());
                }
                while (low.size() > high.size() + 1) {
                    high.offer(low.poll());
                }
            }
            res[0] = median(nums, low, high, k);
            // move window
            for (int i = k, j = 0; i < nums.length; ++i, ++j) {
                // d is the net diff of low size and high size in this iteration
                int d = 0, lowHead = nums[low.peek()];
                // add new num, use head of low as direction
                if (nums[i] <= lowHead) {
                    low.offer(i);
                    ++d;
                } else {
                    high.offer(i);
                    --d;
                }
                // "evict" old num
                // 1) it can be evicted for real
                if (low.peek() == j) {
                    low.poll();
                    --d;
                } else if (high.peek() == j) {
                    high.poll();
                    ++d;
                // 2) we know where it is but can't really evict it, bookkeeping the net diff
                } else if (nums[j] <= lowHead) {
                    --d;
                } else {
                    ++d;
                }
                // keep the two priority queues "balanced", d can only be +-2
                if (d == 2) {
                    high.offer(low.poll());
                }
                if (d == -2) {
                    low.offer(high.poll());
                }
                // purge two queues
                while (!low.isEmpty() && low.peek() <= j) {
                    low.poll();
                }
                while (!high.isEmpty() && high.peek() <= j) {
                    high.poll();
                }
                res[j+1] = median(nums, low, high, k);
            }
            return res;
        }

        private double median(int[] nums, Queue<Integer> low, Queue<Integer> high, int k) {
            return k % 2 == 0 ? 0.5 * nums[low.peek()] + 0.5 * nums[high.peek()] : nums[low.peek()];
        }
    }

    /**
     * Solution 2: Heap
     *
     * Nicer version of Solution 1, with 1) more concise logic to add new number 2) use pseudo priority queue size
     * instead of delta, which is easier to read.
     *
     * Note: We need to purge the queue after every poll to make the solution correct.
     */
    class Solution2 {
        public double[] medianSlidingWindow(int[] nums, int k) {
            Queue<Integer> low = new PriorityQueue<>((i, j) -> Integer.compare(nums[j], nums[i]));
            Queue<Integer> high = new PriorityQueue<>(Comparator.comparingInt(i -> nums[i]));
            double[] res = new double[nums.length-k+1];
            // prepare initial window, low is no shorter than high
            for (int i = 0; i < k; ++i) {
                high.offer(i);
                low.offer(high.poll());
                while (low.size() > high.size() + 1) {
                    high.offer(low.poll());
                }
            }
            res[0] = median(nums, low, high, k);
            // move window
            for (int i = k, j = 0; i < nums.length; ++i, ++j) {
                int szLow = 0, szHigh = 0;
                if (low.peek() == j) {
                    purge(low, j);
                    --szLow;
                } else if (high.peek() == j) {
                    purge(high, j);
                    --szHigh;
                } else if (nums[j] <= nums[low.peek()]) {
                    --szLow;
                } else {
                    --szHigh;
                }
                high.offer(i);
                low.offer(high.poll());
                purge(high, j);
                while (szLow-- > szHigh++) {
                    high.offer(low.poll());
                    purge(low, j);
                }
                res[j+1] = median(nums, low, high, k);
            }
            return res;
        }

        private double median(int[] nums, Queue<Integer> low, Queue<Integer> high, int k) {
            return k % 2 == 0 ? 0.5 * nums[low.peek()] + 0.5 * nums[high.peek()] : nums[low.peek()];
        }

        private void purge(Queue<Integer> pq, int cutoff) {
            while (!pq.isEmpty() && pq.peek() <= cutoff) {
                pq.poll();
            }
        }
    }

    /**
     * Solution 3: Heap
     *
     * Heap cannot do an efficient removal but still doable in linear time.
     *
     * Time complexity: O(n * k). Space complexity: O(k).
     */
    class Solution3 {
        public double[] medianSlidingWindow(int[] nums, int k) {
            Queue<Integer> low = new PriorityQueue<>((i, j) -> Integer.compare(nums[j], nums[i]));
            Queue<Integer> high = new PriorityQueue<>(Comparator.comparingInt(i -> nums[i]));
            double[] res = new double[nums.length-k+1];
            for (int i = 0; i < nums.length; ++i) {
                if (i-k >= 0) {
                    if (!low.remove(i-k)) {
                        high.remove(i-k);
                    }
                }
                high.offer(i);
                low.offer(high.poll());
                while (low.size() > high.size() + 1) {
                    high.offer(low.poll());
                }
                if (i-k+1 >= 0) {
                    res[i-k+1] = median(nums, low, high, k);
                }
            }
            return res;
        }

        private double median(int[] nums, Queue<Integer> low, Queue<Integer> high, int k) {
            return k % 2 == 0 ? 0.5 * nums[low.peek()] + 0.5 * nums[high.peek()] : nums[low.peek()];
        }
    }

    /**
     * Solution 4: Bubble sort
     *
     * Sort the window, when moving, replace old with new and bubble sort for one pass.
     *
     * Time complexity: O(n * k). Space complexity: O(k).
     */
    class Solution4 {
        public double[] medianSlidingWindow(int[] nums, int k) {
            double[] res = new double[nums.length-k+1];
            Integer[] window = new Integer[k];
            for (int i = 0; i < k; ++i) {
                window[i] = i;
            }
            Arrays.sort(window, Comparator.comparingInt(i -> nums[i]));
            res[0] = median(nums, window, k);
            for (int i = k, j = 0; i < nums.length; ++i, ++j) {
                int p = 0;
                // replace old num with new
                for (; p < k; ++p) {
                    if (window[p] == j) {
                        window[p] = i;
                        break;
                    }
                }
                // buuble sort
                while (p > 0 && nums[window[p]] < nums[window[p-1]]) {
                    swap(window, p, p-1);
                    --p;
                }
                while (p < k-1 && nums[window[p]] > nums[window[p+1]]) {
                    swap(window, p, p+1);
                    ++p;
                }
                res[j+1] = median(nums, window, k);
            }
            return res;
        }

        private double median(int[] nums, Integer[] window, int k) {
            return 0.5 * nums[window[(k-1)/2]] + 0.5 * nums[window[k/2]];
        }

        private void swap(Integer[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}
