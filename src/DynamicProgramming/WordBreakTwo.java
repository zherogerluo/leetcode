/**
 * LeetCode #140, hard
 *
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add spaces in s to
 * construct a sentence where each word is a valid dictionary word. Return all such possible sentences.

 Note:

 The same word in the dictionary may be reused multiple times in the segmentation.
 You may assume the dictionary does not contain duplicate words.
 Example 1:

 Input:
 s = "catsanddog"
 wordDict = ["cat", "cats", "and", "sand", "dog"]
 Output:
 [
 "cats and dog",
 "cat sand dog"
 ]
 Example 2:

 Input:
 s = "pineapplepenapple"
 wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
 Output:
 [
 "pine apple pen apple",
 "pineapple pen apple",
 "pine applepen apple"
 ]
 Explanation: Note that you are allowed to reuse a dictionary word.
 Example 3:

 Input:
 s = "catsandog"
 wordDict = ["cats", "dog", "sand", "and", "cat"]
 Output:
 []
 */

package DynamicProgramming;

import java.util.*;

public class WordBreakTwo {
    /**
     * Solution 1: Recursive DFS with memo, top-down DP
     *
     * Similar to Solution 2 in Word Break (Problem #139), except that we store results in memo. Tricky part is to
     * deal with the last word without adding a trailing zero.
     *
     * Important note is that, bottom-up DP will cause memory exceeding limit, because explicit DP array stores
     * results while scanning through array without caring about the rest, while this top-down DP is recursive like
     * DFS and will only start cache after it has gone through the entire string. For case such as
     * "aaaaaaaaaaaaaaaaaaaaaaab" with dictionary of just "a", bottom-up DP will store all kinds of huge results for
     * 'a's which are useless. The top-down DP will DFS to the end, detect that there is no valid break, and return
     * empty list without storing garbage intermediate results.
     */
    class Solution1 {
        public List<String> wordBreak(String s, List<String> wordDict) {
            return wordBreak(s, new HashSet<>(wordDict), new HashMap<>());
        }

        private List<String> wordBreak(String s, Set<String> dict, Map<String, List<String>> memo) {
            if (memo.containsKey(s)) return memo.get(s);
            List<String> res = new ArrayList<>();
            if (s.length() == 0) return res;
            for (String word : dict) {
                if (s.startsWith(word)) {
                    if (s.length() == word.length()) res.add(word);
                    for (String subRes : wordBreak(s.substring(word.length()), dict, memo)) {
                        res.add(word + " " + subRes);
                    }
                }
            }
            memo.put(s, res);
            return res;
        }
    }
}
