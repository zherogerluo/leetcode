/**
 * LeetCode #240, medium
 *
 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

 Integers in each row are sorted in ascending from left to right.
 Integers in each column are sorted in ascending from top to bottom.
 Example:

 Consider the following matrix:

 [
 [1,   4,  7, 11, 15],
 [2,   5,  8, 12, 19],
 [3,   6,  9, 16, 22],
 [10, 13, 14, 17, 24],
 [18, 21, 23, 26, 30]
 ]
 Given target = 5, return true.

 Given target = 20, return false.
 */

package BinarySearch;

public class SearchATwoDMatrixTwo {
    /**
     * Solution 1:
     *
     * It is easy to find that 0,0 and m-1,n-1 are minimum and maximum number in this matrix. We can start from
     * either 0,n-1 or m-1,0 and eliminate one row or column each time (divide and conquer).
     *
     * Time complexity: O(m+n). Space complexity: O(1).
     */
    class Solution1 {
        public boolean searchMatrix(int[][] matrix, int target) {
            int i = matrix.length-1, j = 0;
            while (i >= 0 && j < matrix[0].length) {
                if (matrix[i][j] > target) i--;
                else if (matrix[i][j] < target) j++;
                else return true;
            }
            return false;
        }
    }
}
