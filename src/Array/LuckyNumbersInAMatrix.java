/**
 * LeetCode #1380, easy
 *
 * Given a m * n matrix of distinct numbers, return all lucky numbers in the matrix in any order.
 *
 * A lucky number is an element of the matrix such that it is the minimum element in its row and maximum in its column.
 *
 * Example 1:
 *
 * Input: matrix = [[3,7,8],[9,11,13],[15,16,17]]
 * Output: [15]
 * Explanation: 15 is the only lucky number since it is the minimum in its row and the maximum in its column
 *
 * Example 2:
 *
 * Input: matrix = [[1,10,4,2],[9,3,8,7],[15,16,17,12]]
 * Output: [12]
 * Explanation: 12 is the only lucky number since it is the minimum in its row and the maximum in its column.
 *
 * Example 3:
 *
 * Input: matrix = [[7,8],[1,2]]
 * Output: [7]
 *
 * Constraints:
 *
 * m == mat.length
 * n == mat[i].length
 * 1 <= n, m <= 50
 * 1 <= matrix[i][j] <= 10^5.
 * All elements in the matrix are distinct.
 */

package Array;

import java.util.*;

public class LuckyNumbersInAMatrix {
    /**
     * Solution 1:
     *
     * Memorize min and max values in first pass, then find the matching number in second pass. Trivial
     */
    class Solution1 {
        public List<Integer> luckyNumbers (int[][] matrix) {
            final int m = matrix.length, n = matrix[0].length;
            int[] row = new int[m], col = new int[n];
            Arrays.fill(row, Integer.MAX_VALUE);
            Arrays.fill(col, Integer.MIN_VALUE);
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    row[i] = Math.min(row[i], matrix[i][j]);
                    col[j] = Math.max(col[j], matrix[i][j]);
                }
            }
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (row[i] == col[j]) {
                        res.add(row[i]);
                    }
                }
            }
            return res;
        }
    }
}
