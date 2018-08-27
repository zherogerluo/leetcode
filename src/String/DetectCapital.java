/**
 * LeetCode #520, easy
 *
 * Given a word, you need to judge whether the usage of capitals in it is right or not.
 *
 * We define the usage of capitals in a word to be right when one of the following cases holds:
 *
 * All letters in this word are capitals, like "USA".
 * All letters in this word are not capitals, like "leetcode".
 * Only the first letter in this word is capital if it has more than one letter, like "Google".
 * Otherwise, we define that this word doesn't use capitals in a right way.
 * Example 1:
 * Input: "USA"
 * Output: True
 * Example 2:
 * Input: "FlaG"
 * Output: False
 * Note: The input will be a non-empty word consisting of uppercase and lowercase latin letters.
 */

package String;

public class DetectCapital {
    /**
     * Solution 1:
     *
     * Count char by char. Trivial.
     */
    class Solution1 {
        public boolean detectCapitalUse(String word) {
            int count = 0;
            boolean firstCapital = false;
            char[] cs = word.toCharArray();
            for (int i = 0; i < cs.length; i++) {
                char c = cs[i];
                if (c >= 'A' && c <= 'Z') {
                    count++;
                    if (i == 0) firstCapital = true;
                }
            }
            if (count == 0 || count == word.length()) return true;
            if (count == 1 && firstCapital) return true;
            return false;
        }
    }

    /**
     * Solution 2:
     *
     * Use Java library methods.
     */
    class Solution2 {
        public boolean detectCapitalUse(String word) {
            return word.equals(word.toUpperCase()) ||
                    word.equals(word.toLowerCase()) ||
                    Character.isUpperCase(word.charAt(0)) &&
                            word.substring(1).equals(word.substring(1).toLowerCase());
        }
    }
}
