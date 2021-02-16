/**
 * LeetCode #1007, medium
 *
 * In a row of dominoes, A[i] and B[i] represent the top and bottom halves of the ith domino.  (A domino is a tile with
 * two numbers from 1 to 6 - one on each half of the tile.)
 *
 * We may rotate the ith domino, so that A[i] and B[i] swap values.
 *
 * Return the minimum number of rotations so that all the values in A are the same, or all the values in B are the same.
 *
 * If it cannot be done, return -1.
 *
 * Example 1:
 *
 * Input: A = [2,1,2,4,2,2], B = [5,2,6,2,3,2]
 * Output: 2
 * Explanation:
 * The first figure represents the dominoes as given by A and B: before we do any rotations.
 * If we rotate the second and fourth dominoes, we can make every value in the top row equal to 2, as indicated by the
 * second figure.
 *
 * Example 2:
 *
 * Input: A = [3,5,1,2,3], B = [3,6,3,3,4]
 * Output: -1
 * Explanation:
 * In this case, it is not possible to rotate the dominoes to make one row of values equal.
 *
 * Constraints:
 *
 * 2 <= A.length == B.length <= 2 * 10^4
 * 1 <= A[i], B[i] <= 6
 */

package Array;

public class MinimumDominoRotationsForEqualRow {
    /**
     * Solution 1:
     *
     * Enumerate each number to be target, then count rotations from A and B.
     */
    class Solution1 {
        public int minDominoRotations(int[] A, int[] B) {
            final int n = A.length;
            int[] freq = new int[7];
            for (int a : A) ++freq[a];
            for (int b : B) ++freq[b];
            int res = Integer.MAX_VALUE;
            for (int num = 1; num <= 6; ++num) {
                if (freq[num] < n) continue;
                int rotA = 0, rotB = 0;
                boolean valid = true;
                for (int i = 0; i < n; ++i) {
                    if (A[i] != num && B[i] != num) {
                        valid = false;
                        break;
                    }
                    rotA += A[i] == num ? 0 : 1;
                    rotB += B[i] == num ? 0 : 1;
                }
                if (valid) {
                    res = Math.min(res, Math.min(rotA, rotB));
                }
            }
            return res == Integer.MAX_VALUE ? -1 : res;
        }
    }

    /**
     * Solution 2:
     *
     * Same as Solution 1 but not calculating frequency, which is not very beneficial.
     */
    class Solution2 {
        public int minDominoRotations(int[] A, int[] B) {
            final int n = A.length;
            int res = Integer.MAX_VALUE;
            for (int num = 1; num <= 6; ++num) {
                int rotA = 0, rotB = 0;
                boolean valid = true;
                for (int i = 0; i < n; ++i) {
                    if (A[i] != num && B[i] != num) {
                        valid = false;
                        break;
                    }
                    rotA += A[i] == num ? 0 : 1;
                    rotB += B[i] == num ? 0 : 1;
                }
                if (valid) {
                    res = Math.min(res, Math.min(rotA, rotB));
                }
            }
            return res == Integer.MAX_VALUE ? -1 : res;
        }
    }
}
