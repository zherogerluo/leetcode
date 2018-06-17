/**
 * LeetCode #593, medium
 *
 * Given the coordinates of four points in 2D space, return whether the four points could construct a square.

 The coordinate (x,y) of a point is represented by an integer array with two integers.

 Example:
 Input: p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,1]
 Output: True
 Note:

 All the input integers are in the range [-10000, 10000].
 A valid square has four equal sides with positive length and four equal angles (90-degree angles).
 Input points have no order.
 */

package Math;

import java.util.*;

public class ValidSquare {
    /**
     * Solution 1: Vectors
     *
     * Pick p1 as starting point, calculate three vectors. If the four points make a square, then 1) the shortest two
     * vectors should have same length and 2) are orthogonal to each other, and 3) the longer vector is equal to the
     * vector sum of the shorter two.
     */
    class Solution1 {
        public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
            if (p1 == null || p2 == null || p2 == null || p4 == null) return false;
            if (p1.length != 2 || p2.length != 2 || p3.length != 2 || p4.length != 2) return false;
            int[][] vectors = new int[3][2];
            vectors[0] = vectorize(p1, p2);
            vectors[1] = vectorize(p1, p3);
            vectors[2] = vectorize(p1, p4);
            Comparator<int[]> comparator = new Comparator<int[]>() {
                @Override
                public int compare(int[] a, int[] b) {
                    return vecLen(a) - vecLen(b);
                }
            };
            Arrays.sort(vectors, comparator);
            if (vecLen(vectors[0]) == 0) return false;
            if (vecLen(vectors[0]) != vecLen(vectors[1])) return false;
            if (dotProduct(vectors[0], vectors[1]) != 0) return false;
            if (!vecEqual(vecSum(vectors[0], vectors[1]), vectors[2])) return false;
            return true;
        }

        private int[] vectorize(int[] a, int[] b) {
            int[] res = new int[2];
            res[0] = b[0] - a[0];
            res[1] = b[1] - a[1];
            return res;
        }

        private boolean vecEqual(int[] a, int[] b) {
            return a[0] == b[0] && a[1] == b[1];
        }

        private int vecLen(int[] a) {
            return a[0]*a[0] + a[1]*a[1];
        }

        private int dotProduct(int[] a, int[] b) {
            return a[0] * b[0] + a[1] * b[1];
        }

        private int[] vecSum(int[] a, int[] b) {
            int[] res = new int[2];
            res[0] = a[0] + b[0];
            res[1] = a[1] + b[1];
            return res;
        }
    }

    /**
     * Solution 2: Compare lengths
     *
     * Assume points are arranged in circular fashion, then they form a square only if sides are equal and diagonals
     * are equal as well. In the main method just enumerates all possibilities of p1-p4 forming circular patterns.
     */
    class Solution2 {
        public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
            return check(p1, p2, p3, p4) || check(p1, p2, p4, p3) || check(p1, p3, p2, p4);
        }

        /* Assume p1 - p4 are arranged counter-clock or clock-wise, returns true if they form a square */
        private boolean check(int[] p1, int[] p2, int[] p3, int[] p4) {
            return dist(p1, p2) != 0 && dist(p1, p2) == dist(p2, p3) && dist(p2, p3) == dist(p3, p4) &&
                    dist(p3, p4) == dist(p4, p1) && dist(p1, p3) == dist(p2, p4);
        }

        private int dist(int[] a, int[] b) {
            return (b[0] - a[0]) * (b[0] - a[0]) + (b[1] - a[1]) * (b[1] - a[1]);
        }
    }

    /**
     * Solution 3: Cheating
     *
     * Calculate all six distances. To form a square, four shorter ones must be equal, and two longer ones must be
     * equal, and the longer one must be sqrt(2) times shorter one (2 times larger for dist defined below). This is
     * not mathematically rigorous, but given the inputs are all integers, it works.
     */
    class Solution3 {
        public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
            int[] allDist = new int[]{dist(p1, p2), dist(p1, p3), dist(p1, p4), dist(p2, p3), dist(p2, p4), dist(p3, p4)};
            Arrays.sort(allDist);
            return allDist[0] != 0 && allDist[0] == allDist[3] && allDist[4] == allDist[5] && allDist[0] * 2 == allDist[4];
        }

        private int dist(int[] a, int[] b) {
            return (b[0] - a[0]) * (b[0] - a[0]) + (b[1] - a[1]) * (b[1] - a[1]);
        }
    }
}
