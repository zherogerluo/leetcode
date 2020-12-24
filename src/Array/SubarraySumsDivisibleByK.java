/**
 * LeetCode #974, medium
 *
 * Given an array A of integers, return the number of (contiguous, non-empty) subarrays that have a sum divisible by K.
 *
 * Example 1:
 *
 * Input: A = [4,5,0,-2,-3,1], K = 5
 * Output: 7
 * Explanation: There are 7 subarrays with a sum divisible by K = 5:
 * [4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
 *
 * Note:
 *
 * 1 <= A.length <= 30000
 * -10000 <= A[i] <= 10000
 * 2 <= K <= 10000
 */

package Array;

import java.util.*;

public class SubarraySumsDivisibleByK {
    /**
     * Solution 1:
     *
     * The general idea for subarray problem is to convert it to accumulated array diff problem. One straightforward
     * idea is to calculate accumulated sum, then for every i-j pair we can calculate the sum of subarray from i to j
     * using sum[j] - sum[i]. However this is O(n^2) time which does not pass LeetCode testing.
     *
     * Since the problem is asking for divisible sum only, we can just calculate and store mod(K) as we calculate the
     * accumulated sum. For a subarray i-j to be divisible, sum[i] and sum[j] must have the same mod(k), which can be
     * achieved by remembering mod(k) with a hash map. Note the mod cannot be remainder (%).
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution1 {
        public int subarraysDivByK(int[] A, int K) {
            Map<Integer, Integer> mod = new HashMap<>();
            int[] sum = new int[A.length+1];
            mod.put(0, 1); // makes sure we count the case when sum[i] is divisible by K
            int res = 0;
            for (int i = 1; i <= A.length; ++i) {
                sum[i] = Math.floorMod(sum[i-1] + A[i-1], K);
                res += mod.getOrDefault(sum[i], 0);
                mod.put(sum[i], mod.getOrDefault(sum[i], 0) + 1);
            }
            return res;
        }
    }

    /**
     * Solution 2:
     *
     * Same as Solution 1 except for using int[] for hash map.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        public int subarraysDivByK(int[] A, int K) {
            int[] mod = new int[K];
            int[] sum = new int[A.length+1];
            mod[0] = 1; // makes sure we count the case when sum[i] is divisible by K
            int res = 0;
            for (int i = 1; i <= A.length; ++i) {
                sum[i] = Math.floorMod(sum[i-1] + A[i-1], K);
                res += mod[sum[i]];
                ++mod[sum[i]];
            }
            return res;
        }
    }

    /**
     * Solution 3:
     *
     * Same as Solution 2 except that we don't need a sum array, instead just need a sum variable.
     */
    class Solution3 {
        public int subarraysDivByK(int[] A, int K) {
            int[] mod = new int[K];
            mod[0] = 1;
            int sum = 0, res = 0;
            for (int i = 1; i <= A.length; ++i) {
                sum = Math.floorMod(sum + A[i-1], K);
                res += mod[sum];
                ++mod[sum];
            }
            return res;
        }
    }
}
