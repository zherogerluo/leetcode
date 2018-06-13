/**
 * LeetCode #30, hard
 *
 * You are given a string, s, and a list of words, words, that are all of the same length. Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening characters.

 Example 1:

 Input:
 s = "barfoothefoobarman",
 words = ["foo","bar"]
 Output: [0,9]
 Explanation: Substrings starting at index 0 and 9 are "barfoor" and "foobar" respectively.
 The output order does not matter, returning [9,0] is fine too.
 Example 2:

 Input:
 s = "wordgoodstudentgoodword",
 words = ["word","student"]
 Output: []
 */

package HashTable;

import java.util.*;

public class SubstringWithConcatenationOfAllWords {
    /**
     * Solution 1: Hash map and two pointers
     *
     * Basic idea is to keep a slow and fast pointers, fast pointer scans word and record its position in the hash
     * map, if the word has been seen and appeared after the slow pointer, then this substring between slow and fast
     * is not valid, in this case we forward the slow pointer until the last appearance of current word.
     *
     * Many tricky parts need attention. A major one is that, the starting position of slow/fast pointer could range
     * from 0 to word length - 1 (consider "wordneed" and "ordneed" where we want to find "need"). For every new
     * starting position, we make a new working copy of the hash map.
     *
     * Time complexity: O(mn) where k is the word length. We have m out-most loop (m starting points), for each of which
     * we need to loop through all n/m words, where for each word we need to calculate hash code which takes O(m)
     * time. However note that the hash code of string is cached after calculation, so the future hashcode() takes
     * O(1). If the string has many repeating words then the best case run time could be more close to O(n).
     * Comparing string in hash table bucket is O(1) if strings are the same, or O(m) if they are different. Actually
     * the substring method takes O(m) time after Java 7 update 6.
     *
     * Space complexity: O(mn) for hash map storage
     */
    class Solution1 {
        public List<Integer> findSubstring(String s, String[] words) {
            List<Integer> res = new ArrayList<>();
            if (words == null || words.length == 0) return res;
            int wordLen = words[0].length();
            Map<String, Integer> map0 = new HashMap<>(); // word and its count
            for (String word: words) {
                if (word.length() != wordLen) return res;
                map0.put(word, map0.getOrDefault(word, 0) + 1);
            }
            for (int k = 0; k < wordLen; k++) { // note this outer loop
                int start = k;
                Map<String, Integer> map = new HashMap<>(map0); // copying is necessary. safer way is to rebuild it.
                for (int i = start; i <= s.length() - wordLen; i += wordLen) {
                    String cur = s.substring(i, i + wordLen);
                    if (!map.containsKey(cur)) {
                        for (int j = start; j < i; j += wordLen) {
                            String visited = s.substring(j, j + wordLen);
                            map.replace(visited, map.get(visited) + 1);
                        }
                        start = i + wordLen;
                    } else {
                        int count = map.get(cur) - 1;
                        while (count < 0) {
                            String visited = s.substring(start, start + wordLen);
                            if (cur.equals(visited)) count++;
                            else map.replace(visited, map.get(visited) + 1);
                            start += wordLen;
                        } // not elegant
                        map.put(cur, count);
                        if (i + wordLen - start == wordLen * words.length) res.add(start); // not elegant
                    }
                }
            }
            return res;
        }
    }
}
