/**
 * LeetCode #1191, medium
 *
 * Given an integer array arr and an integer k, modify the array by repeating it k times.
 *
 * For example, if arr = [1, 2] and k = 3 then the modified array will be [1, 2, 1, 2, 1, 2].
 *
 * Return the maximum sub-array sum in the modified array. Note that the length of the sub-array can be 0 and its sum
 * in that case is 0.
 *
 * As the answer can be very large, return the answer modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: arr = [1,2], k = 3
 * Output: 9
 *
 * Example 2:
 *
 * Input: arr = [1,-2,1], k = 5
 * Output: 2
 *
 * Example 3:
 *
 * Input: arr = [-1,-2], k = 7
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^5
 * 1 <= k <= 10^5
 * -10^4 <= arr[i] <= 10^4
 */

package DynamicProgramming;

public class KConcatenationMaximumSum {
    /**
     * Solution 1: Kadane's algorithm
     *
     * A variant problem of finding max sum of sub array, using Kadane's algorithm by calculating accumulated sum. Key
     * point is 1) account for the max sum when loop across the end into the next copy 2) account for sum of multiple
     * full copies only if the sum of array is greater than zero.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution1 {
        public int kConcatenationMaxSum(int[] arr, int k) {
            final int MOD = 1000000007;
            int[] sum = new int[arr.length*2];
            int max = 0, min = 0;
            for (int i = 0; i < arr.length; ++i) {
                sum[i] = (i == 0 ? 0 : sum[i-1]) + arr[i];
                min = Math.min(min, sum[i]);
                max = Math.max(max, sum[i] - min);
            }
            if (k == 1) {
                return Math.floorMod(max, MOD);
            }
            // loop over to the array head
            for (int i = arr.length; i < sum.length; ++i) {
                sum[i] = sum[i-1] + arr[i-arr.length];
                min = Math.min(min, sum[i]);
                max = Math.max(max, sum[i] - min);
            }
            if (sum[arr.length-1] > 0) {
                for (int i = 0; i < k-2; ++i) {
                    max = Math.floorMod(max, MOD);
                    max += sum[arr.length-1];
                }
            }
            return Math.floorMod(max, MOD);
        }
    }

    /**
     * Solution 2:
     *
     * Same as Solution 1 except for using sum variable instead of array, as we don't need to keep sum older than the
     * last one.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution2 {
        public int kConcatenationMaxSum(int[] arr, int k) {
            final int MOD = 1000000007;
            int sum = arr[0];
            int max = arr[0], min = Math.min(arr[0], 0);
            for (int i = 1; i < arr.length; ++i) {
                sum += arr[i];
                min = Math.min(min, sum);
                max = Math.max(max, sum - min);
            }
            if (k == 1) {
                return Math.floorMod(max, MOD);
            }
            // loop over to the array head
            int sum_all = sum;
            for (int i = 0; i < arr.length; ++i) {
                sum += arr[i];
                min = Math.min(min, sum);
                max = Math.max(max, sum - min);
            }
            if (sum_all > 0) {
                for (int i = 0; i < k-2; ++i) {
                    max = Math.floorMod(max, MOD);
                    max += sum_all;
                }
            }
            return Math.floorMod(max, MOD);
        }
    }
}
