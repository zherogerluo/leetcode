/**
 * LeetCode #77, medium
 *
 * Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.

 Example:

 Input: n = 4, k = 2
 Output:
 [
 [2,4],
 [3,4],
 [2,3],
 [1,2],
 [1,3],
 [1,4],
 ]
 */

package Backtracking;

import java.util.*;

public class Combinations {
    /**
     * Solution 1: Backtracking, depth-first-search
     *
     * Typical backtracking, use temp array to collect data and if meet requirements then add to result. Tricky part
     * is edge case needs to happen when i == k not i == k-1, and there is a performance improvement that we only
     * need to look at number smaller or equal to n-(k-1-i) because otherwise with incrementing m it will eventually
     * exceed n which is not something we want to search for.
     *
     * Time complexity: O(n! / k!), not sure. Space complexity: O(k) not counting result.
     */
    class Solution1 {
        public List<List<Integer>> combine(int n, int k) {
            List<List<Integer>> res = new ArrayList<>();
            combine(new int[k], 0, k, 1, n, res);
            return res;
        }

        private void combine(int[] temp, int i, int k, int n0, int n, List<List<Integer>> res) {
            if (i == k) { // beware of the right edge case condition
                List<Integer> list = new ArrayList<>();
                for (int num : temp) list.add(num);
                res.add(list);
            } else {
                for (int m = n0; m <= n-k+i+1; m++) { // set the tight right-side bound for performance
                    temp[i] = m;
                    combine(temp, i+1, k, m+1, n, res);
                }
            }
        }
    }
}
