/**
 * LeetCode #131, medium
 *
 * Given a string s, partition s such that every substring of the partition is a palindrome.

 Return all possible palindrome partitioning of s.

 Example:

 Input: "aab"
 Output:
 [
 ["aa","b"],
 ["a","a","b"]
 ]
 */

package Backtracking;

import java.util.*;

public class PalindromePartitioning {
    /**
     * Solution 1: DFS
     *
     * Iterate through the string, when we see a palindrome, we do DFS to see if we can partition the remaining
     * substring. If we do, add this palindrome to all the result and return the new result list. Remember to use a
     * memo array to store all known results to prevent redundant work. In this sense, this problem can be also
     * solved using DP.
     *
     * Time complexity: Might be O(n * 2^n) since we would have 2^n palindrome partitions the worst case, and we
     * need to store n of them. The method isPalindrome also takes O(n) each time.
     *
     * Space complexity: O(n * 2^n) the worst case
     */
    class Solution1 {
        public List<List<String>> partition(String s) {
            List[] memo = new List[s.length()+1];
            List<List<String>> list = new ArrayList<>();
            list.add(new ArrayList<>());
            memo[s.length()] = list;
            return partition(s, 0, s.length(), memo);
        }

        private List<List<String>> partition(String s, int start, int end, List<List<String>>[] memo) {
            List<List<String>> res;
            if (memo[start] != null) return memo[start];
            res = new ArrayList<>();
            for (int i = start; i < end; i++) {
                if (isPalindrome(s, start, i)) {
                    for (List<String> l : partition(s, i+1, end, memo)) {
                        List<String> list = new ArrayList<>();
                        list.add(s.substring(start, i+1));
                        list.addAll(l);
                        res.add(list);
                    }
                }
            }
            memo[start] = res;
            return res;
        }

        private boolean isPalindrome(String s, int i, int j) {
            while (i < j) {
                if (s.charAt(i++) != s.charAt(j--)) return false;
            }
            return true;
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * Same as Solution 1, except that we build memo array explicitly. Definition of DP array element changed
     * slightly: dp[i] stores all partitions ending at i exclusively. No explicit DP formula.
     */
    class Solution2 {
        public List<List<String>> partition(String s) {
            List<List<String>>[] dp = new List[s.length()+1];
            dp[0] = new ArrayList<>();
            dp[0].add(new ArrayList<>());
            for (int end = 1; end < dp.length; end++) {
                List<List<String>> res = new ArrayList<>();
                for (int start = 0; start < end; start++) {
                    if (isPalindrome(s, start, end-1)) {
                        for (List<String> prev : dp[start]) {
                            List<String> cur = new ArrayList<>(prev);
                            cur.add(s.substring(start, end));
                            res.add(cur);
                        }
                    }
                }
                dp[end] = res;
            }
            return dp[s.length()];
        }

        private boolean isPalindrome(String s, int i, int j) {
            while (i < j) {
                if (s.charAt(i++) != s.charAt(j--)) return false;
            }
            return true;
        }
    }
}
