/**
 * LeetCode #1297, medium
 *
 * Given a string s, return the maximum number of ocurrences of any substring under the following rules:
 *
 * The number of unique characters in the substring must be less than or equal to maxLetters.
 * The substring size must be between minSize and maxSize inclusive.
 *
 * Example 1:
 *
 * Input: s = "aababcaab", maxLetters = 2, minSize = 3, maxSize = 4
 * Output: 2
 * Explanation: Substring "aab" has 2 ocurrences in the original string.
 * It satisfies the conditions, 2 unique letters and size 3 (between minSize and maxSize).
 *
 * Example 2:
 *
 * Input: s = "aaaa", maxLetters = 1, minSize = 3, maxSize = 3
 * Output: 2
 * Explanation: Substring "aaa" occur 2 times in the string. It can overlap.
 *
 * Example 3:
 *
 * Input: s = "aabcabcab", maxLetters = 2, minSize = 2, maxSize = 3
 * Output: 3
 *
 * Example 4:
 *
 * Input: s = "abcde", maxLetters = 2, minSize = 3, maxSize = 3
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * 1 <= maxLetters <= 26
 * 1 <= minSize <= maxSize <= min(26, s.length)
 * s only contains lowercase English letters.
 */

package String;

import java.util.*;

public class MaximumNumberOfOccurrencesOfASubstring {
    /**
     * Solution 1: Sliding window
     *
     * Sliding window, use map to remember occurrences of substring. Note that we only need to consider substring of
     * length minSize and not longer, because longer substrings cases can be reduce to shorter substrings by just
     * taking substring of it.
     */
    class Solution1 {
        public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
            int[] freq = new int[26];
            int letters = 0;
            int max = 0;
            Map<String, Integer> map = new HashMap<>();
            for (int i = 0; i < s.length(); ++i) {
                int j = s.charAt(i) - 'a';
                if (freq[j] == 0) {
                    ++letters;
                }
                ++freq[j];
                if (i >= minSize) {
                    int k = s.charAt(i - minSize) - 'a';
                    --freq[k];
                    if (freq[k] == 0) {
                        --letters;
                    }
                }
                if (i >= minSize - 1 && letters <= maxLetters) {
                    String sub = s.substring(i - minSize + 1, i + 1);
                    int count = map.getOrDefault(sub, 0) + 1;
                    max = Math.max(max, count);
                    map.put(sub, count);
                }
            }
            return max;
        }
    }
}
