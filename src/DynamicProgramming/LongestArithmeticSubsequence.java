/**
 * LeetCode #1027, medium
 *
 * Given an array A of integers, return the length of the longest arithmetic subsequence in A.
 *
 * Recall that a subsequence of A is a list A[i_1], A[i_2], ..., A[i_k] with 0 <= i_1 < i_2 < ... < i_k <= A.length - 1,
 * and that a sequence B is arithmetic if B[i+1] - B[i] are all the same value (for 0 <= i < B.length - 1).
 *
 * Example 1:
 *
 * Input: A = [3,6,9,12]
 * Output: 4
 * Explanation:
 * The whole array is an arithmetic sequence with steps of length = 3.
 *
 * Example 2:
 *
 * Input: A = [9,4,7,2,10]
 * Output: 3
 * Explanation:
 * The longest arithmetic subsequence is [4,7,10].
 *
 * Example 3:
 *
 * Input: A = [20,1,15,3,10,5,8]
 * Output: 4
 * Explanation:
 * The longest arithmetic subsequence is [20,15,10,5].
 *
 * Constraints:
 *
 * 2 <= A.length <= 1000
 * 0 <= A[i] <= 500
 */

package DynamicProgramming;

import java.util.*;

public class LongestArithmeticSubsequence {
    /**
     * Solution 1:
     *
     * Use a map to remember all value-index, for every pair A[i] and A[j] try to find the next number that satisfies
     * arithmetic sequence requirement. Straightforward idea but there are multiple optimizations needed to pass
     * LeetCode tests, see comments in the code.
     *
     * Time complexity: O(n^3). Space complexity: O(n).
     */
    class Solution1 {
        public int longestArithSeqLength(int[] A) {
            int res = 2;
            Map<Integer, List<Integer>> indices = new HashMap<>();
            for (int i = 0; i < A.length; ++i) {
                if (indices.get(A[i]) == null) {
                    indices.put(A[i], new ArrayList<>());
                }
                List<Integer> cur = indices.get(A[i]);
                cur.add(i);
                // take care of the delta == 0 case
                res = Math.max(res, cur.size());
            }
            for (int i = 0; i < A.length-2; ++i) {
                // the remaining numbers are already less than current result
                if (A.length - i <= res) {
                    break;
                }
                for (int j = i+1; j < A.length-1; ++j) {
                    // the remaining numbers are already less than current result
                    if (A.length - j <= res - 1) {
                        break;
                    }
                    // result can't be more than the number of distinct values in the input array
                    if (res == indices.keySet().size()) {
                        return res;
                    }
                    int delta = A[j] - A[i];
                    // delta == 0 is already taken care of when we build the indices map
                    if (delta == 0) {
                        break;
                    }
                    int next = A[j] + delta;
                    int last_index = j;
                    int length = 2;
                    while (indices.containsKey(next)) {
                        List<Integer> list = indices.get(next);
                        int index = -1;
                        for (int k : list) {
                            // there could be duplicate values, just find the one that is immediately after last_index
                            if (k > last_index) {
                                index = k;
                                break;
                            }
                        }
                        if (index == -1) {
                            break;
                        }
                        ++length;
                        next += delta;
                        last_index = index;
                    }
                    res = Math.max(res, length);
                }
            }
            return res;
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * Hash map to remember delta-length pairs for subsequences ending at index i. Still do a nested for-loop to
     * enumerate all possible deltas at index i, update results based on results from index j.
     *
     * Time complexity: O(n^2). Space complexity: O(n^2), n hash maps and n possible deltas in each map.
     */
    class Solution2 {
        public int longestArithSeqLength(int[] A) {
            Map<Integer, Integer>[] dp = new HashMap[A.length];
            int max = 2;
            for (int i = 0; i < A.length; ++i) {
                dp[i] = new HashMap<>();
                for (int j = 0; j < i; ++j) {
                    int delta = A[i] - A[j];
                    int length = dp[j].getOrDefault(delta, 1) + 1;
                    dp[i].put(delta, length);
                    max = Math.max(max, length);
                }
            }
            return max;
        }
    }

    /**
     * Solution 3: Dynamic programming
     *
     * More straightforward DP idea: dp[i][j] = the longest arithmetic subsequence ending with index i and j. It should
     * always be dp[prev][i], but we need some way to find previous number. Here we use a map to remember it, but for it
     * to work, we also need to start j from i+1 to the end, instead from 0 to i, otherwise the previous number map
     * wouldn't be valid.
     *
     * Time complexity: O(n^2). Space complexity: O(n^2).
     */
    class Solution3 {
        public int longestArithSeqLength(int[] A) {
            int[][] dp = new int[A.length][A.length];
            Map<Integer, Integer> last = new HashMap<>();
            int max = 2;
            for (int i = 0; i < A.length; ++i) {
                for (int j = i+1; j < A.length; ++j) {
                    int prev = A[i] - (A[j] - A[i]);
                    if (last.containsKey(prev)) {
                        int prev_index = last.get(prev);
                        dp[i][j] = dp[prev_index][i] + 1;
                        max = Math.max(max, dp[i][j]);
                    } else {
                        dp[i][j] = 2;
                    }
                }
                last.put(A[i], i);
            }
            return max;
        }
    }
}
