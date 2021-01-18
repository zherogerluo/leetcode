/**
 * LeetCode #216, medium
 *
 * Find all valid combinations of k numbers that sum up to n such that the following conditions are true:
 *
 * Only numbers 1 through 9 are used.
 * Each number is used at most once.
 * Return a list of all possible valid combinations. The list must not contain the same combination twice, and the combinations may be returned in any order.
 *
 * Example 1:
 *
 * Input: k = 3, n = 7
 * Output: [[1,2,4]]
 * Explanation:
 * 1 + 2 + 4 = 7
 * There are no other valid combinations.
 *
 * Example 2:
 *
 * Input: k = 3, n = 9
 * Output: [[1,2,6],[1,3,5],[2,3,4]]
 * Explanation:
 * 1 + 2 + 6 = 9
 * 1 + 3 + 5 = 9
 * 2 + 3 + 4 = 9
 * There are no other valid combinations.
 *
 * Example 3:
 *
 * Input: k = 4, n = 1
 * Output: []
 * Explanation: There are no valid combinations. [1,2,1] is not valid because 1 is used twice.
 *
 * Example 4:
 *
 * Input: k = 3, n = 2
 * Output: []
 * Explanation: There are no valid combinations.
 *
 * Example 5:
 *
 * Input: k = 9, n = 45
 * Output: [[1,2,3,4,5,6,7,8,9]]
 * Explanation:
 * 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 = 45
 * There are no other valid combinations.
 *
 * Constraints:
 *
 * 2 <= k <= 9
 * 1 <= n <= 60
 */

package Array;

import java.util.*;

public class CombinationSumThree {
    /**
     * Solution 1: Backtracking
     *
     * This is a knapsack problem, use binary branching is the best: Pick or not pick number i. Note the edge cases:
     * k and n must all be zero to be a valid case. Also terminate if i is out of range.
     */
    class Solution1 {
        public List<List<Integer>> combinationSum3(int k, int n) {
            List<List<Integer>> res = new ArrayList<>();
            solve(k, n, 1, new ArrayList<>(), res);
            return res;
        }

        private void solve(int k, int n, int i, List<Integer> l, List<List<Integer>> res) {
            if (k == 0 && n == 0) {
                res.add(new ArrayList<>(l));
                return;
            }
            if (k == 0 || i > 9) {
                return;
            }
            l.add(i);
            solve(k-1, n-i, i+1, l, res);
            l.remove(l.size()-1);
            solve(k, n, i+1, l, res);
        }
    }

    /**
     * Solution 2: Backtracking
     *
     * Use for loop as wider branching, but note it needs to start at i, otherwise we have duplicates. Edge case is the
     * same as Solution 1 (written in different way though) but no need to consider i out of range because it is in the
     * for-loop condition.
     *
     * Don't use edge case of k == 1 and add the last n to list. It is error prone: Easy to forget to remove the last
     * n before returning, resulting in wrong answers!
     */
    class Solution2 {
        public List<List<Integer>> combinationSum3(int k, int n) {
            List<List<Integer>> res = new ArrayList<>();
            solve(k, n, 1, new ArrayList<>(), res);
            return res;
        }

        private void solve(int k, int n, int i, List<Integer> l, List<List<Integer>> res) {
            // don't use k == 1 as edge case because it is error prone
            if (k == 0) {
                if (n == 0) {
                    res.add(new ArrayList<>(l));
                }
                return;
            }
            for (; i <= 9 && i <= n; ++i) {
                l.add(i);
                solve(k-1, n-i, i+1, l, res);
                l.remove(l.size()-1);
            }
        }
    }
}
