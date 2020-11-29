/**
 * LeetCode #438, medium
 *
 * Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.
 *
 * Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than
 * 20,100.
 *
 * The order of output does not matter.
 *
 * Example 1:
 *
 * Input:
 * s: "cbaebabacd" p: "abc"
 *
 * Output:
 * [0, 6]
 *
 * Explanation:
 * The substring with start index = 0 is "cba", which is an anagram of "abc".
 * The substring with start index = 6 is "bac", which is an anagram of "abc".
 *
 * Example 2:
 *
 * Input:
 * s: "abab" p: "ab"
 *
 * Output:
 * [0, 1, 2]
 *
 * Explanation:
 * The substring with start index = 0 is "ab", which is an anagram of "ab".
 * The substring with start index = 1 is "ba", which is an anagram of "ab".
 * The substring with start index = 2 is "ab", which is an anagram of "ab".
 */

public class FindAllAnagramsInAString {
    /**
     * Solution 1: Brutal force
     *
     * Simply test every substring of s with p's length to see if it is p's anagram. Use char arrays to save the cost
     * of a hashmap.
     *
     * Time complexity: O(n * m). Space complexity: O(1).
     */
    class Solution1 {
        public List<Integer> findAnagrams(String s, String p) {
            List<Integer> res = new ArrayList<>();
            char[] chars = s.toCharArray();
            int[] p_freq = new int[26];
            for (char c : p.toCharArray()) {
                p_freq[c-'a'] += 1;
            }
            for (int i = 0; i <= chars.length - p.length(); ++i) {
                int[] s_freq = new int[26];
                for (int j = 0; j < p.length(); ++j) {
                    s_freq[chars[i+j]-'a'] += 1;
                }
                boolean equal = true;
                for (int k = 0; k < 26; ++k) {
                    if (p_freq[k] != s_freq[k]) {
                        equal = false;
                        break;
                    }
                }
                if (equal) {
                    res.add(i);
                }
            }
            return res;
        }
    }

    /**
     * Solution 2: Sliding window
     *
     * Use a sliding window to capture substring and the character counts while moving.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution2 {
        public List<Integer> findAnagrams(String s, String p) {
            List<Integer> res = new ArrayList<>();
            if (s.length() < p.length()) {
                return res;
            }
            char[] s_chars = s.toCharArray();
            char[] p_chars = p.toCharArray();
            int[] s_freq = new int[26];
            int[] p_freq = new int[26];
            for (int i = 0; i < p_chars.length; ++i) {
                ++s_freq[s_chars[i]-'a'];
                ++p_freq[p_chars[i]-'a'];
            }
            for (int i = 0; i <= s_chars.length - p_chars.length; ++i) {
                if (isEqual(s_freq, p_freq)) {
                    res.add(i);
                }
                --s_freq[s_chars[i]-'a'];
                if (i + p_chars.length < s_chars.length) {
                    ++s_freq[s_chars[i + p_chars.length]-'a'];
                }
            }
            return res;
        }

        private boolean isEqual(int[] s, int[] p) {
            for (int i = 0; i < s.length; ++i) {
                if (s[i] != p[i]) {
                    return false;
                }
            }
            return true;
        }
    }
}