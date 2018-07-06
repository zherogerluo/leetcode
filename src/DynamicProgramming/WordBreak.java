/**
 * LeetCode #139, medium
 *
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be
 * segmented into a space-separated sequence of one or more dictionary words.

 Note:

 The same word in the dictionary may be reused multiple times in the segmentation.
 You may assume the dictionary does not contain duplicate words.
 Example 1:

 Input: s = "leetcode", wordDict = ["leet", "code"]
 Output: true
 Explanation: Return true because "leetcode" can be segmented as "leet code".
 Example 2:

 Input: s = "applepenapple", wordDict = ["apple", "pen"]
 Output: true
 Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 Note that you are allowed to reuse a dictionary word.
 Example 3:

 Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 Output: false
 */

package DynamicProgramming;

import java.util.*;

public class WordBreak {
    /**
     * Solution 1: Recursive DFS with memo (top-to-bottom DP - recursion with memoization)
     *
     * For a given string, iterate through and check substring(0, i), if it is in dictionary, check the remaining
     * substring, on and on. Use a memo hash map to store seen results.
     *
     * Instead of iterating through the string, we can instead iterate through the dictionary and check if string
     * starts with a word in the dictionary. This is more efficient if dictionary size is small.
     *
     * Important note: Don't use a set to store results, because false result is still valuable and needs to be cached.
     *
     * Time complexity: O(n^3)? Space complexity: O(n).
     */
    class Solution1 {
        public boolean wordBreak(String s, List<String> wordDict) {
            Set<String> dict = new HashSet<>(wordDict);
            Map<String, Boolean> memo = new HashMap<>();
            return search(s, dict, memo);
        }

        private boolean search(String s, Set<String> dict, Map<String, Boolean> memo) {
            if (dict.contains(s)) return true;
            if (memo.containsKey(s)) return memo.get(s);
            boolean res = false;
            for (int i = 1; i <= s.length(); i++) {
                if (dict.contains(s.substring(0, i))) {
                    res = res || search(s.substring(i, s.length()), dict, memo);
                    // if (res) break; // don't need to break here because result will be cached
                }
            }
            memo.put(s, res);
            return res;
        }
    }

    /**
     * Solution 2: Recursive DFS with memo (top-to-bottom DP - recursion with memoization)
     *
     * Same as Solution 1 except iterating through dictionary.
     */
    class Solution2 {
        public boolean wordBreak(String s, List<String> wordDict) {
            Set<String> dict = new HashSet<>(wordDict);
            Map<String, Boolean> memo = new HashMap<>();
            return search(s, dict, memo);
        }

        private boolean search(String s, Set<String> dict, Map<String, Boolean> memo) {
            if (dict.contains(s)) return true;
            if (memo.containsKey(s)) return memo.get(s);
            boolean res = false;
            for (String word : dict) {
                if (s.startsWith(word)) {
                    res = res || search(s.substring(word.length()), dict, memo);
                    // if (res) break; // don't need to break here because result will be cached
                }
            }
            memo.put(s, res);
            return res;
        }
    }

    /**
     * Solution 3: Dynamic programming
     *
     * Solution 1 and 2 are essentially doing dynamic programming on memo recursively. Using explicit DP, dp[i] is
     * defined as whether we can break the substring from 0 to i (exclusive). Thus, dp[i] will be true if there
     * exists a j < i where dp[j] is true and the substring between j and i is a valid word.
     *
     * Again, we can loop through dictionary instead of all DP elements before i.
     */
    class Solution3 {
        public boolean wordBreak(String s, List<String> wordDict) {
            Set<String> dict = new HashSet<>(wordDict);
            boolean[] dp = new boolean[s.length()+1];
            dp[0] = true;
            for (int i = 1; i < dp.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (dp[j] && dict.contains(s.substring(j, i))) {
                        dp[i] = true;
                        break;
                    }
                }
            }
            return dp[dp.length-1];
        }
    }

    /**
     * Solution 4: Dynamic programming
     *
     * Same as Solution 3 except iterating through dictionary.
     */
    class Solution4 {
        public boolean wordBreak(String s, List<String> wordDict) {
            Set<String> dict = new HashSet<>(wordDict);
            boolean[] dp = new boolean[s.length()+1];
            dp[0] = true;
            for (int i = 1; i < dp.length; i++) {
                for (String word : dict) {
                    int start = i - word.length();
                    if (start >= 0 && dp[start] && s.substring(start, i).equals(word)) {
                        dp[i] = true;
                        break;
                    }
                }
            }
            return dp[dp.length-1];
        }
    }
}
