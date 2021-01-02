/**
 * LeetCode #1031, medium
 *
 * Given an array A of non-negative integers, return the maximum sum of elements in two non-overlapping (contiguous)
 * subarrays, which have lengths L and M.  (For clarification, the L-length subarray could occur before or after the
 * M-length subarray.)
 *
 * Formally, return the largest V for which V = (A[i] + A[i+1] + ... + A[i+L-1]) + (A[j] + A[j+1] + ... + A[j+M-1])
 * and either:
 *
 * 0 <= i < i + L - 1 < j < j + M - 1 < A.length, or
 * 0 <= j < j + M - 1 < i < i + L - 1 < A.length.
 *
 * Example 1:
 *
 * Input: A = [0,6,5,2,2,5,1,9,4], L = 1, M = 2
 * Output: 20
 * Explanation: One choice of subarrays is [9] with length 1, and [6,5] with length 2.
 *
 * Example 2:
 *
 * Input: A = [3,8,1,3,2,1,8,9,0], L = 3, M = 2
 * Output: 29
 * Explanation: One choice of subarrays is [3,8,1] with length 3, and [8,9] with length 2.
 *
 * Example 3:
 *
 * Input: A = [2,1,5,6,0,9,5,0,3,8], L = 4, M = 3
 * Output: 31
 * Explanation: One choice of subarrays is [5,6,0,9] with length 4, and [3,8] with length 3.
 *
 * Note:
 *
 * L >= 1
 * M >= 1
 * L + M <= A.length <= 1000
 * 0 <= A[i] <= 1000
 */

package Array;

public class MaximumSumOfTwoNonOverlappingSubarrays {
    /**
     * Solution 1: Brutal force, sliding window
     *
     * Time complexity: O(n^2). Space complexity: O(1).
     */
    class Solution1 {
        public int maxSumTwoNoOverlap(int[] A, int L, int M) {
            int sum_L = 0, res = 0;;
            for (int i = 0; i < L; ++i) {
                sum_L += A[i];
            }
            for (int i = L; i <= A.length; ++i) {
                int sum_M = 0;
                for (int j = 0; j < M; ++j) {
                    sum_M += A[j];
                }
                for (int j = M; j <= A.length; ++j) {
                    if (j-M >= i || j <= i-L) {
                        res = Math.max(res, sum_L + sum_M);
                    }
                    // move the window
                    if (j < A.length) {
                        sum_M = sum_M + A[j] - A[j-M];
                    }
                }
                // move the window
                if (i < A.length) {
                    sum_L = sum_L + A[i] - A[i-L];
                }
            }
            return res;
        }
    }

    /**
     * Solution 2:
     *
     * Reduce to L+M and M+L cases which can be handled by the same algorithm. Use two sliding windows attached, we
     * check sum of max sum of window 1 + rolling sum of window 2.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution2 {
        public int maxSumTwoNoOverlap(int[] A, int L, int M) {
            return Math.max(helper(A, L, M), helper(A, M, L));
        }

        private int helper(int[] A, int L, int M) {
            int sumL = 0, sumM = 0, maxL = 0, res = 0;
            for (int i = 0; i < A.length; ++i) {
                sumM += A[i];
                if (i - M >= 0) {
                    sumM -= A[i-M];
                    sumL += A[i-M];
                }
                if (i - M - L >= 0) {
                    sumL -= A[i-M-L];
                }
                maxL = Math.max(maxL, sumL);
                res = Math.max(res, maxL + sumM);
            }
            return res;
        }
    }
}
