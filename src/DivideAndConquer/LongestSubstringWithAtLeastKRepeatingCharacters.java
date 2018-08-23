/**
 * LeetCode #395, medium
 *
 * Find the length of the longest substring T of a given string (consists of lowercase letters only) such that every
 * character in T appears no less than k times.
 *
 * Example 1:
 *
 * Input:
 * s = "aaabb", k = 3
 *
 * Output:
 * 3
 *
 * The longest substring is "aaa", as 'a' is repeated 3 times.
 * Example 2:
 *
 * Input:
 * s = "ababbc", k = 2
 *
 * Output:
 * 5
 *
 * The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
 */

package DivideAndConquer;

import java.util.*;

public class LongestSubstringWithAtLeastKRepeatingCharacters {
    /**
     * Solution 1: Brutal force
     *
     * For every substring, count the appearances of all letters, and loop through count to see if all counts are no
     * less than k. If so, update the result.
     *
     * Time complexity: O(n^2). Space complexity: O(1) (count array is fixed length).
     */
    class Solution1 {
        public int longestSubstring(String s, int k) {
            int res = 0;
            for (int i = 0; i < s.length(); i++) {
                int[] count = new int[26];
                for (int j = i; j < s.length(); j++) {
                    count[s.charAt(j)-'a'] += 1;
                    boolean valid = true;
                    for (int p = 0; p < 26; p++) {
                        valid = valid && (count[p] == 0 || count[p] >= k); // need to consider zero count here
                    }
                    if (valid) res = Math.max(res, j - i + 1);
                }
            }
            return res;
        }
    }

    /**
     * Solution 2: Divide and conquer
     *
     * Count the letters, and split the string at positions where the letter does not appear at least k times, and do
     * the same thing recursively for every split string. Note that at each level of recursive call, at least one
     * letter will be used to split the string, therefore the split string will not have that letter. Ultimately we
     * will have at most 26 levels of recursive calls, so this solution should take O(n) time.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution2 {
        public int longestSubstring(String s, int k) {
            return longestSubstring(s, 0, s.length(), k);
        }

        public int longestSubstring(String s, int start, int end, int k) {
            if (end - start < k) return 0;
            int res = 0;
            int[] count = new int[26];
            for (int i = start; i < end; i++) {
                count[s.charAt(i)-'a'] += 1;
            }
            boolean valid = true;
            for (int i = 0; i < count.length; i++) {
                valid = valid && (count[i] == 0 || count[i] >= k);
            }
            if (valid) return end - start;
            for (int i = start; i <= end; i++) {
                if (i == end || count[s.charAt(i)-'a'] < k) {
                    res = Math.max(res, longestSubstring(s, start, i, k));
                    start = i + 1;
                }
            }
            return res;
        }
    }

    /**
     * Solution 3: Two pointers
     *
     * Define a target such that the substring must have this many unique letters that appear no less than k times.
     * Such target could range from 1 to 26, so we loop through every possibility. In each loop, use two pointers
     * technique to keep track of the current number of unique letters and number of letters that appeared at least k
     * times. If the unique count is no larger than the target, we increment the fast pointer, otherwise we increment
     * the slow pointer to reduce the unique count to let it match target. We test if both metrics are equal to
     * target, if so, update the result.
     *
     * This solution is not easy to come up with, and is not very efficient either, despite it runs in O(n) time. One
     * pitfall is that, the possible target range might not run to 26 and its max is limited by the input string
     * itself. To optimize on this, we count the letters in string first to get the max possible unique count. Also
     * we can add some corner cases like k <= 1 to improve speed.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution3 {
        public int longestSubstring(String s, int k) {
            if (k <= 1) return s.length(); // corner case

            // optimization, get the upper bound of number of unique letters
            int[] count = new int[26];
            for (char c : s.toCharArray()) count[c-'a']++;
            int maxUnique = 0;
            for (int cnt : count) maxUnique += (cnt > 0 ? 1 : 0);

            int res = 0;
            for (int target = 1; target <= maxUnique; target++) {
                Arrays.fill(count, 0);
                int i = 0, j = 0, unique = 0, noLessThanK = 0;
                while (j < s.length()) {
                    if (unique <= target) { // note the <= sign, advance fast pointer even when
                                            // unique already met target
                        int index = s.charAt(j) - 'a';
                        if (count[index] == 0) unique++;
                        count[index]++;
                        if (count[index] == k) noLessThanK++;
                        j++;
                    } else {
                        int index = s.charAt(i) - 'a';
                        if (count[index] == k) noLessThanK--;
                        count[index]--;
                        if (count[index] == 0) unique--;
                        i++;
                    }
                    if (unique == target && noLessThanK == target) {
                        res = Math.max(res, j - i);
                    }
                }
            }
            return res;
        }
    }
}
