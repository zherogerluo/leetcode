/**
 * LeetCode #17, medium
 *
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number
 * could represent.

 A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any
 letters.

 Example:

 Input: "23"
 Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 Note:

 Although the above answer is in lexicographical order, your answer could be in any order you want.
 */

package String;

import java.util.*;

public class LetterCombinationsOfAPhoneNumber {
    /**
     * Solution 1: Queue
     *
     * Use a queue to store all sequences seen so far. For the next digit, append all possible chars to each of the
     * seen sequence and feed it to the queue. Do this until no more digit to consume.
     *
     * Tricky part: When polling queue, it is important not to poll those just been offered in the current loop.
     */
    class Solution1 {
        public List<String> letterCombinations(String digits) {
            if (digits == null || digits.length() == 0) return new ArrayList<String>();
            String[] map = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
            Queue<String> queue = new LinkedList<>();
            queue.add("");
            for (char c : digits.toCharArray()) {
                int d = c - '0', size = queue.size();
                while (size-- > 0) {
                    String s = queue.poll();
                    for (char c1 : map[d].toCharArray()) {
                        queue.offer(s + c1);
                    }
                }
            }
            return (List<String>) queue;
        }
    }
}
