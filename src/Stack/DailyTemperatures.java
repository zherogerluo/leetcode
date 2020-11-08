/**
 * LeetCode #739, medium
 *
 * Given a list of daily temperatures T, return a list such that, for each day in the input, tells you how many days
 * you would have to wait until a warmer temperature. If there is no future day for which this is possible, put 0
 * instead.
 *
 * For example, given the list of temperatures T = [73, 74, 75, 71, 69, 72, 76, 73], your output should be
 * [1, 1, 4, 2, 1, 1, 0, 0].
 *
 * Note: The length of temperatures will be in the range [1, 30000]. Each temperature will be an integer in the range
 * [30, 100].
 */

import java.util.*;

public class DailyTemperatures {
    /**
     * Solution 1: Priority queue
     *
     * Use priority queue to remember indices which do not have results yet, consume them as we iterate through the
     * remaining list - for temperature T[i], finalize all lower T[k] in the queue.
     *
     * Time complexity: O(n * log(n)). Space complexity: O(n).
     */
    class Solution1 {
        public int[] dailyTemperatures(int[] T) {
            int[] result = new int[T.length];
            Queue<Integer> queue = new PriorityQueue<>((a, b) -> Integer.compare(T[a], T[b]));
            for (int i = 0; i < T.length; ++i) {
                while (!queue.isEmpty() && T[queue.peek()] < T[i]) {
                    int index = queue.poll();
                    result[index] = i - index;
                }
                queue.offer(i);
            }
            return result;
        }
    }

    /**
     * Solution 2: Stack
     *
     * An important observation is that, for all the uphill index, the result is always 1, and the downhill result
     * depends on the uphill data on the right. We iterate from back, use a stack to remember uphill data, then consume
     * them when we are in downhill. This avoids sorting and takes O(n) time only.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        public int[] dailyTemperatures(int[] T) {
            int[] result = new int[T.length];
            Deque<Integer> stack = new ArrayDeque<>();
            for (int i = T.length - 2; i >= 0; --i) { // skip last element
                if (T[i] < T[i + 1]) {
                    // uphill
                    result[i] = 1;
                    stack.push(i + 1);
                } else {
                    // downhill (or level)
                    while (!stack.isEmpty() && T[i] >= T[stack.peek()]) {
                        stack.pop();
                    }
                    if (!stack.isEmpty()) {
                        result[i] = stack.peek() - i;
                    }
                }
            }
            return result;
        }
    }

    /**
     * Solution 3: Two pointers
     *
     * The idea is similar with Solution 2, but with a more important observation: We don't actually need a stack to
     * remember the possible higher temperature indices, instead we can use the available results to trace it down.
     * Just jump from k to k + result[k] to find the next higher temperature. If none can be found, result[k] is zero.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution3 {
        public int[] dailyTemperatures(int[] T) {
            int[] result = new int[T.length];
            for (int i = T.length - 2; i >= 0; --i) { // skip last element
                if (T[i] < T[i + 1]) {
                    // uphill
                    result[i] = 1;
                } else {
                    // downhill (or level)
                    int j = i + 1;
                    while (j < T.length) {
                        if (T[i] < T[j]) {
                            result[i] = j - i;
                            break;
                        } else if (result[j] == 0) {
                            result[i] = 0;
                            break;
                        }
                        j = j + result[j];
                    }
                }
            }
            return result;
        }
    }
}
