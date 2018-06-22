/**
 * LeetCode #48, medium
 *
 * You are given an n x n 2D matrix representing an image.

 Rotate the image by 90 degrees (clockwise).

 Note:

 You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. DO NOT allocate another 2D matrix and do the rotation.

 Example 1:

 Given input matrix =
 [
 [1,2,3],
 [4,5,6],
 [7,8,9]
 ],

 rotate the input matrix in-place such that it becomes:
 [
 [7,4,1],
 [8,5,2],
 [9,6,3]
 ]
 Example 2:

 Given input matrix =
 [
 [ 5, 1, 9,11],
 [ 2, 4, 8,10],
 [13, 3, 6, 7],
 [15,14,12,16]
 ],

 rotate the input matrix in-place such that it becomes:
 [
 [15,13, 2, 5],
 [14, 3, 4, 1],
 [12, 6, 8, 9],
 [16, 7,10,11]
 ]
 */

package Array;

public class RotateImage {
    /**
     * Solution 1: Flip and transpose, the clever way
     *
     * Image rotation can be broken down to two easier transformation: Flipping along y and transpose.
     *
     * Time complexity: O(n^2). Space complexity: O(1).
     */
    class Solution1 {
        public void rotate(int[][] matrix) {
            if (matrix == null || matrix.length <= 1) return;
            final int n = matrix.length;
            // flip along y then transpose
            for (int i = 0; i < n / 2; i++) {
                int[] temp = matrix[i];
                matrix[i] = matrix[n-i-1];
                matrix[n-1-i] = temp;
            }
            for (int i = 0; i < n; i++) {
                for (int j = i+1; j < n; j++) {
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = temp;
                }
            }
        }
    }

    /**
     * Solution 2: Rotate pixel by pixel, the dumb way
     *
     * Rotate each pixel one by one, two for loops go over 1/4 of the image with an inner for loop with 4 operations
     */
    class Solution2 {
        public void rotate(int[][] matrix) {
            if (matrix == null || matrix.length <= 1) return;
            final int n = matrix.length;
            for (int i = 0; i < n/2; i++) {
                for (int j = 0; j < (n+1)/2; j++) {
                    int idest, jdest, last = matrix[i][j];
                    for (int k = 0; k < 4; k++) {
                        idest = j; jdest = n-i-1;
                        // swap matrix[idest][jdest] with last value
                        int temp = matrix[idest][jdest];
                        matrix[idest][jdest] = last;
                        last = temp;
                        // next pair
                        i = idest; j = jdest;
                    }
                    // i and j should already be restored to initial values after this loop
                }
            }
        }
    }
}
