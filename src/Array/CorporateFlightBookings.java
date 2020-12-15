/**
 * LeetCode #1109, medium
 *
 * There are n flights, and they are labeled from 1 to n.
 *
 * We have a list of flight bookings.  The i-th booking bookings[i] = [i, j, k] means that we booked k seats from
 * flights labeled i to j inclusive.
 *
 * Return an array answer of length n, representing the number of seats booked on each flight in order of their label.
 *
 * Example 1:
 *
 * Input: bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
 * Output: [10,55,45,25,25]
 *
 * Constraints:
 *
 * 1 <= bookings.length <= 20000
 * 1 <= bookings[i][0] <= bookings[i][1] <= n <= 20000
 * 1 <= bookings[i][2] <= 10000
 */

package Array;

public class CorporateFlightBookings {
    /**
     * Solution 1: Brutal force
     *
     * Time complexity: O(n^2). Space complexity: O(1).
     */
    class Solution1 {
        public int[] corpFlightBookings(int[][] bookings, int n) {
            int[] res = new int[n];
            for (int[] booking : bookings) {
                for (int i = booking[0] - 1; i < booking[1]; ++i) {
                    res[i] += booking[2];
                }
            }
            return res;
        }
    }

    /**
     * Solution 2: Rolling sum
     *
     * By observing the sample results, we can see that the number of seats gets incremented at beginning of booking
     * and decremented at the end of booking. Thus we can calculate the diff at booking start/end, then the results
     * will be the accumulation of the diff.
     *
     * Time complexity: O(n) given the constraints on the number of bookings.
     * Space complexity: O(n).
     */
    class Solution2 {
        public int[] corpFlightBookings(int[][] bookings, int n) {
            int[] diff = new int[n];
            for (int[] booking : bookings) {
                diff[booking[0]-1] += booking[2];
                if (booking[1] < n) diff[booking[1]] -= booking[2];
            }
            for (int i = 1; i < n; ++i) {
                diff[i] += diff[i-1];
            }
            return diff;
        }
    }
}
