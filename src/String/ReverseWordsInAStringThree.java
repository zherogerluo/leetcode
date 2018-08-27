/**
 * LeetCode #557, easy
 *
 * Given a string, you need to reverse the order of characters in each word within a sentence while still preserving
 * whitespace and initial word order.
 *
 * Example 1:
 * Input: "Let's take LeetCode contest"
 * Output: "s'teL ekat edoCteeL tsetnoc"
 *
 * Note: In the string, each word is separated by single space and there will not be any extra space in the string.
 */

package String;

public class ReverseWordsInAStringThree {
    /**
     * Solution 1:
     *
     * Split string and reverse each one.
     */
    class Solution1 {
        public String reverseWords(String s) {
            StringBuilder sb = new StringBuilder();
            String[] words = s.split(" ");
            for (String word : words) {
                for (int i = word.length() - 1; i >= 0; i--) {
                    sb.append(word.charAt(i));
                }
                sb.append(' ');
            }
            return sb.toString().trim();
        }
    }
}
