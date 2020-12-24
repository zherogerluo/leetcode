/**
 * LeetCode #941, easy
 *
 * Given an array of integers arr, return true if and only if it is a valid mountain array.
 *
 * Recall that arr is a mountain array if and only if:
 *
 * arr.length >= 3
 * There exists some i with 0 < i < arr.length - 1 such that:
 * arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
 * arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
 *
 * Example 1:
 *
 * Input: arr = [2,1]
 * Output: false
 *
 * Example 2:
 *
 * Input: arr = [3,5,5]
 * Output: false
 *
 * Example 3:
 *
 * Input: arr = [0,3,2,1]
 * Output: true
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^4
 * 0 <= arr[i] <= 10^4
 */

/* Easy problem but also very easy to get it wrong on corner cases */

package Array;

public class ValidMountainArray {
    /**
     * Solution 1: Count peaks
     *
     * Count peaks, a few places to pay attention: 1) Boundaries 2) Zero peak (monotonically increasing/decreasing)
     */
    class Solution1 {
        public boolean validMountainArray(int[] arr) {
            if (arr.length < 3) {
                return false;
            }
            // Need to make sure boundaries are okay
            if (arr[0] >= arr[1] || arr[arr.length-2] <= arr[arr.length-1]) {
                return false;
            }
            int num_peaks = 0;
            for (int i = 1; i <= arr.length-2; ++i) {
                if (arr[i] == arr[i-1]) {
                    return false;
                }
                if (arr[i-1] < arr[i] && arr[i] > arr[i+1]) {
                    ++num_peaks;
                    if (num_peaks == 2) {
                        return false;
                    }
                }
            }
            // num_peaks can be zero
            return num_peaks == 1;
        }
    }

    /**
     * Solution 2: Walk the mountain
     *
     * Back to the definition, we walk through the mountain and only do one climb and one descend. If it is a mountain
     * array, we should be able to reach the end. One thing to note is to abort if we didn't move at all or already
     * walked all the way through at the end of climb, which means it is monotonically increasing or decreasing.
     *
     * This is a beautiful idea. The key to come up with it is to go back read the peak definition.
     */
    class Solution2 {
        public boolean validMountainArray(int[] arr) {
            int i = 0;
            while (i < arr.length-1 && arr[i] < arr[i+1]) {
                ++i;
            }
            if (i == 0 || i == arr.length-1) {
                return false;
            }
            while (i < arr.length-1 && arr[i] > arr[i+1]) {
                ++i;
            }
            return i == arr.length-1;
        }
    }
}
