/**
 * LeetCode #73, medium
 *
 * Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.

 Example 1:

 Input:
 [
 [1,1,1],
 [1,0,1],
 [1,1,1]
 ]
 Output:
 [
 [1,0,1],
 [0,0,0],
 [1,0,1]
 ]
 Example 2:

 Input:
 [
 [0,1,2,0],
 [3,4,5,2],
 [1,3,1,5]
 ]
 Output:
 [
 [0,0,0,0],
 [0,4,5,0],
 [0,3,1,0]
 ]
 Follow up:

 A straight forward solution using O(mn) space is probably a bad idea.
 A simple improvement uses O(m + n) space, but still not the best solution.
 Could you devise a constant space solution?
 */

package Array;

public class SetMatrixZeroes {
    /**
     * Solution 1:
     *
     * Use the first zero's row and column to record rows and columns that has zeros. Alternatively we can use first
     * row and column, but will need a separate boolean because [0,0] can record only 0th row or 0th column depending
     * on personal choice.
     *
     * Time complexity: O(m * n). Space: O(1).
     */
    class Solution1 {
        public void setZeroes(int[][] matrix) {
            final int m = matrix.length, n = matrix[0].length;
            int ii = m, jj = n;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] == 0) {
                        if (ii == m) {
                            ii = i; jj = j;
                        } else {
                            matrix[ii][j] = 0;
                            matrix[i][jj] = 0;
                        }
                    }
                }
            }
            if (ii == m) return; // zero not found
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != ii && j != jj && (matrix[ii][j] == 0 || matrix[i][jj] == 0)) {
                        matrix[i][j] = 0;
                    }
                }
            }
            for (int i = 0; i < m; i++) matrix[i][jj] = 0;
            for (int j = 0; j < n; j++) matrix[ii][j] = 0;
        }
    }
}
