/**
 * LeetCode #857, hard
 *
 * There are N workers.  The i-th worker has a quality[i] and a minimum wage expectation wage[i].
 *
 * Now we want to hire exactly K workers to form a paid group.  When hiring a group of K workers, we must pay them
 * according to the following rules:
 *
 * 1. Every worker in the paid group should be paid in the ratio of their quality compared to other workers in the paid
 * group.
 * 2. Every worker in the paid group must be paid at least their minimum wage expectation.
 *
 * Return the least amount of money needed to form a paid group satisfying the above conditions.
 *
 * Example 1:
 *
 * Input: quality = [10,20,5], wage = [70,50,30], K = 2
 * Output: 105.00000
 * Explanation: We pay 70 to 0-th worker and 35 to 2-th worker.
 *
 * Example 2:
 *
 * Input: quality = [3,1,10,10,1], wage = [4,8,2,2,7], K = 3
 * Output: 30.66667
 * Explanation: We pay 4 to 0-th worker, 13.33333 to 2-th and 3-th workers seperately.
 *
 * Note:
 *
 * 1 <= K <= N <= 10000, where N = quality.length = wage.length
 * 1 <= quality[i] <= 10000
 * 1 <= wage[i] <= 10000
 * Answers within 10^-5 of the correct answer will be considered correct.
 */

import java.util.*;

public class MinimumCostToHireKWorkers {
    /**
     * Solution 1: Heap
     *
     * It is apparent that the ratio of wage to quality, which we call unit wage, must be as small as possible to
     * minimize pay. Therefore we sort the unit wage, iterate starting from the smallest and build the group. The final
     * paid amount is unit wage * total quality of the group. As we consider the i-th worker, the unit wage for the
     * group is always the i-th (largest) because of the rules, however it might bring the total quality down if we add
     * it to the group and kick out the previous max. This can be achieved using a max heap. We keep updating the
     * least money paid when we update the heap, until we finish iterating the entire array.
     *
     * Time complexity: O(n * log(n)) for sorting. Heap operations take O(n * log(K)) time.
     * Space complexity: O(n).
     */
    class Solution1 {
        public double mincostToHireWorkers(int[] quality, int[] wage, int K) {
            final int n = quality.length;
            double[] unitWage = new double[n];
            Integer[] idx = new Integer[n];
            for (int i = 0; i < n; ++i) {
                unitWage[i] = (double) wage[i] / quality[i];
                idx[i] = i;
            }
            Arrays.sort(idx, Comparator.comparingDouble(i -> unitWage[i]));
            Queue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
            // first K workers as initial group
            int minQualK = 0;
            for (int i = 0; i < K; ++i) {
                pq.offer(quality[idx[i]]);
                minQualK += quality[idx[i]];
            }
            double minCost = unitWage[idx[K-1]] * minQualK;
            // keep finding worker that could bring down the total cost
            for (int i = K; i < n; ++i) {
                if (pq.peek() > quality[idx[i]]) {
                    // new worker can bring total quality down therefore potentially reduce total cost, update result
                    minQualK = minQualK - pq.poll() + quality[idx[i]];
                    pq.offer(quality[idx[i]]);
                    minCost = Math.min(minCost, unitWage[idx[i]] * minQualK);
                }
            }
            return minCost;
        }
    }
}
