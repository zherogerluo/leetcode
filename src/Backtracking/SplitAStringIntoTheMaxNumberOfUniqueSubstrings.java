/**
 * LeetCode #1593, medium
 *
 * Given a string s, return the maximum number of unique substrings that the given string can be split into.
 *
 * You can split string s into any list of non-empty substrings, where the concatenation of the substrings forms the
 * original string. However, you must split the substrings such that all of them are unique.
 *
 * A substring is a contiguous sequence of characters within a string.
 *
 * Example 1:
 *
 * Input: s = "ababccc"
 * Output: 5
 * Explanation: One way to split maximally is ['a', 'b', 'ab', 'c', 'cc']. Splitting like ['a', 'b', 'a', 'b', 'c',
 * 'cc'] is not valid as you have 'a' and 'b' multiple times.
 *
 * Example 2:
 *
 * Input: s = "aba"
 * Output: 2
 * Explanation: One way to split maximally is ['a', 'ba'].
 *
 * Example 3:
 *
 * Input: s = "aa"
 * Output: 1
 * Explanation: It is impossible to split the string any further.
 *
 * Constraints:
 *
 * 1 <= s.length <= 16
 * s contains only lower case English letters.
 */

import java.util.*;

public class SplitAStringIntoTheMaxNumberOfUniqueSubstrings {
    /**
     * Solution 1: Depth-first search
     *
     * General idea is to use a hash set to track seen substrings, and split the rest into substrings that haven't been
     * seen. Return values are tricky.
     *
     * Time complexity: O(n^2). Space complexity: O(n).
     */
    class Solution1 {
        public int maxUniqueSplit(String s) {
            return split(s.toCharArray(), 0, new HashSet<>());
        }

        private int split(char[] chars, int start, Set<String> set) {
            if (start == chars.length) {
                // no way to split an empty string, consider the case of "a"
                return 0;
            }
            int max = 0; // if there is valid split, max should be non-zero
            for (int end = start+1; end <= chars.length; ++end) {
                String s = new String(chars, start, end-start);
                if (set.contains(s)) {
                    continue;
                }
                set.add(s);
                max = Math.max(max, 1 + split(chars, end, set));
                set.remove(s);
            }
            return max;
        }
    }

    /**
     * Solution 2: DFS
     *
     * It makes more sense to utilize backtracking, i.e. update result only when we are done, so that we don't bother
     * with the tricky return value. This also enables us to do pruning to significantly improve performance for cases
     * like "abcdefghijklmn".
     */
    class Solution2 {
        int max;

        public int maxUniqueSplit(String s) {
            max = 1;
            split(s.toCharArray(), 0, new HashSet<>());
            return max;
        }

        private void split(char[] chars, int start, Set<String> set) {
            if (start == chars.length) {
                max = Math.max(max, set.size());
                return;
            }
            // pruning, no point to try if we can't beat current max even by splitting the rest into single char
            if (chars.length - start + set.size() <= max) {
                return;
            }
            for (int end = start+1; end <= chars.length; ++end) {
                String s = new String(chars, start, end-start);
                if (set.contains(s)) {
                    continue;
                }
                set.add(s);
                split(chars, end, set);
                set.remove(s);
            }
        }
    }
}
