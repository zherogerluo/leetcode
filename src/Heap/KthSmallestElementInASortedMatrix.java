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
     * Time complexity: O(k * log(n)). Space complexity: O(n).
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
     *
     * Binary search on values. We calculate mid value based on low and high, and count the number of entries smaller
     * or equal to it. If count < k, we update low, otherwise update high. Every time we cut the searching range by
     * half, and for each search, there is a clever trick to do it in O(n). When searching row by row from first to
     * last, we can certainly do a binary search for mid for every row, which would lead to O(n * log(n)) time.
     * However, note that the numbers are sorted not only in rows, but also in columns. This means that, if we find
     * mid and position k for this row, we don't have to search for index > k in the next row, because the number at
     * k in next row would be larger than that of this row for sure. In this case, we can just keep a single index
     * marking the right-hand-side boundary of our search, and while we go from top row to bottom row, this index
     * would only shrink, so the entire search would be completed when the index goes to 0, i.e. finished in O(n) time.
     *
     * Another trick thing to note is that, how is the return value low guaranteed to be the value in the matrix? It
     * relies on the precondition that such kth smallest element exists. If low is smaller than target, the count
     * would always be smaller than k, and low would keep incrementing until it sits on the kth smallest element and
     * won't go beyond that, and the binary search loop ends when high goes below low.
     *
     * Time complexity: O(n * log(max - min)), which is effectively O(n) because the range of int is a constant, thus
     * the log value. Space complexity: O(1).
     */
    class Solution2 {
        public int kthSmallest(int[][] matrix, int k) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0 || k <= 0)
                return Integer.MIN_VALUE;
            final int m = matrix.length, n = matrix[0].length;
            int low = matrix[0][0], high = matrix[m-1][n-1];
            while (low <= high) {
                int mid = low + (high - low) / 2;
                int count = 0;
                for (int i = 0, j = n-1; i < m && j >= 0; i++) {
                    while (j >= 0 && matrix[i][j] > mid) j--;
                    count += j + 1;
                }
                if (count < k) low = mid + 1;
                else high = mid - 1;
            }
            return low;
        }
    }
}
