/**
 * LeetCode #799, medium
 *
 * We stack glasses in a pyramid, where the first row has 1 glass, the second row has 2 glasses, and so on until the
 * 100th row.  Each glass holds one cup of champagne.
 *
 * Then, some champagne is poured into the first glass at the top.  When the topmost glass is full, any excess liquid
 * poured will fall equally to the glass immediately to the left and right of it.  When those glasses become full, any
 * excess champagne will fall equally to the left and right of those glasses, and so on.  (A glass at the bottom row has
 * its excess champagne fall on the floor.)
 *
 * For example, after one cup of champagne is poured, the top most glass is full.  After two cups of champagne are
 * poured, the two glasses on the second row are half full.  After three cups of champagne are poured, those two cups
 * become full - there are 3 full glasses total now.  After four cups of champagne are poured, the third row has the
 * middle glass half full, and the two outside glasses are a quarter full, as pictured below.
 *
 * Now after pouring some non-negative integer cups of champagne, return how full the jth glass in the ith row is (both
 * i and j are 0-indexed.)
 *
 * Example 1:
 *
 * Input: poured = 1, query_row = 1, query_glass = 1
 * Output: 0.00000
 * Explanation: We poured 1 cup of champagne to the top glass of the tower (which is indexed as (0, 0)). There will be
 * no excess liquid so all the glasses under the top glass will remain empty.
 *
 * Example 2:
 *
 * Input: poured = 2, query_row = 1, query_glass = 1
 * Output: 0.50000
 * Explanation: We poured 2 cups of champagne to the top glass of the tower (which is indexed as (0, 0)). There is one
 * cup of excess liquid. The glass indexed as (1, 0) and the glass indexed as (1, 1) will share the excess liquid
 * equally, and each will get half cup of champagne.
 *
 * Example 3:
 *
 * Input: poured = 100000009, query_row = 33, query_glass = 17
 * Output: 1.00000
 *
 * Constraints:
 *
 * 0 <= poured <= 10^9
 * 0 <= query_glass <= query_row < 100
 */

package DynamicProgramming;

public class ChampagneTower {
    /**
     * Solution 1: Dynamic programming
     *
     * Use a two dimensional array to store fullness (including the spilled amount) and work the way down. Should be
     * straightforward but note 1) the index trick to start from 1 to avoid boundary check 2) the use of Math.max and
     * .min to bound result.
     *
     * Time complexity: O(n^2). Space complexity: O(n^2) but can be easily reduced to O(n) since we only need i-1.
     */
    class Solution1 {
        public double champagneTower(int poured, int query_row, int query_glass) {
            double filled[][] = new double[query_row+1][query_row+2];
            filled[0][1] = poured;
            for (int i = 1; i <= query_row; ++i) {
                for (int j = 1; j <= i+1; ++j) {
                    filled[i][j] = (Math.max(0.0, filled[i-1][j] - 1) + Math.max(0.0, filled[i-1][j-1] - 1)) / 2;
                }
            }
            return Math.min(1.0, filled[query_row][query_glass+1]);
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * Clever trick to use only one-dimensional DP array: March from right to left, assign to right but increment left,
     * so all the interior buckets receive two increments, and it avoids overwriting value that needs to be used in the
     * next step.
     */
    class Solution2 {
        public double champagneTower(int poured, int query_row, int query_glass) {
            double filled[] = new double[query_row+2];
            filled[1] = poured;
            for (int i = 1; i <= query_row; ++i) {
                for (int j = i; j >= 1; --j) {
                    double excess = Math.max(0.0, filled[j] - 1);
                    filled[j] = excess / 2;
                    filled[j+1] += excess / 2;
                }
            }
            return Math.min(1.0, filled[query_glass+1]);
        }
    }
}
