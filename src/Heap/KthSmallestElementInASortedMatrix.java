/**
 * LeetCode #378, medium
 *
 * Given a n x n matrix where each of the rows and columns are sorted in ascending order, find the kth smallest
 * element in the matrix.
 *
 * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
 *
 * Example:
 *
 * matrix = [
 *    [ 1,  5,  9],
 *    [10, 11, 13],
 *    [12, 13, 15]
 * ],
 * k = 8,
 *
 * return 13.
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ n^2.
 */

package Heap;

import java.util.*;

public class KthSmallestElementInASortedMatrix {
    /**
     * Solution 1: Heap
     *
     * The idea is similar to merge sorted linked list. Treat each row or column as a sorted list, use a min heap to
     * store the heads of lists, poll and advance list one at a time until k is reached. Tricky part is to keep index
     * pairs in the heap in order to locate the head and compare. Another trick is to use column as lists in the hope
     * that more cache hits can be achieved (not sure though). Or, use whichever is shorter so that heap size can be
     * minimized?
     *
     * Time complexity: O(k * log(m)). Space complexity: O(m).
     */
    class Solution1 {
        public int kthSmallest(int[][] matrix, int k) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0 || k <= 0)
                return Integer.MIN_VALUE;
            final int m = matrix.length, n = matrix[0].length;
            Queue<int[]> pq = new PriorityQueue<>((a, b) -> matrix[a[0]][a[1]] - matrix[b[0]][b[1]]);
            for (int j = 0; j < n; j++) pq.offer(new int[]{0, j});
            while (!pq.isEmpty()) {
                int[] ij = pq.poll();
                if (--k == 0) return matrix[ij[0]][ij[1]];
                if (ij[0] < m - 1) pq.offer(new int[]{ij[0] + 1, ij[1]});
            }
            return Integer.MIN_VALUE;
        }
    }

    /**
     * Solution 2: Binary search
     */
    // TODO
}
