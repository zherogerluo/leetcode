/**
 * LeetCode #1209, medium
 *
 * Given a string s, a k duplicate removal consists of choosing k adjacent and equal letters from s and removing them
 * causing the left and the right side of the deleted substring to concatenate together.
 *
 * We repeatedly make k duplicate removals on s until we no longer can.
 *
 * Return the final string after all such duplicate removals have been made.
 *
 * It is guaranteed that the answer is unique.
 *
 * Example 1:
 *
 * Input: s = "abcd", k = 2
 * Output: "abcd"
 * Explanation: There's nothing to delete.
 *
 * Example 2:
 *
 * Input: s = "deeedbbcccbdaa", k = 3
 * Output: "aa"
 * Explanation:
 * First delete "eee" and "ccc", get "ddbbbdaa"
 * Then delete "bbb", get "dddaa"
 * Finally delete "ddd", get "aa"
 *
 * Example 3:
 *
 * Input: s = "pbbcggttciiippooaais", k = 2
 * Output: "ps"
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * 2 <= k <= 10^4
 * s only contains lower case English letters.
 */

import java.util.*;

public class RemoveAllAdjacentDuplicatesInStringTwo {
    /**
     * Solution 1: Stack
     *
     * It is natural to approach this problem using stack: Push characters into stack until we see k of them, pop all
     * out and continue until the end. To achieve this we also need to remember the counts.
     */
    class Solution1 {
        public String removeDuplicates(String s, int k) {
            Deque<Character> chars = new ArrayDeque<>();
            Deque<Integer> counts = new ArrayDeque<>();
            for (char c : s.toCharArray()) {
                if (!chars.isEmpty() && chars.peek() == c && counts.peek() == k-1) {
                    chars.pop();
                    counts.pop();
                } else if (!chars.isEmpty() && chars.peek() == c) {
                    counts.push(counts.pop() + 1);
                } else {
                    counts.push(1);
                    chars.push(c);
                }
            }
            StringBuilder sb = new StringBuilder();
            while (!chars.isEmpty()) {
                char c = chars.removeLast();
                for (int i = counts.removeLast(); i > 0; --i) {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
    }

    /**
     * Solution 2: Stack
     *
     * Variant of Solution 1 using array as stack.
     */
    class Solution2 {
        public String removeDuplicates(String s, int k) {
            char[] chars = s.toCharArray();
            int[] counts = new int[s.length()];
            int head = -1;
            for (char c : chars) {
                if (head != -1 && chars[head] == c && counts[head] == k-1) {
                    --head;
                } else if (head != -1 && chars[head] == c) {
                    ++counts[head];
                } else {
                    ++head;
                    counts[head] = 1;
                    chars[head] = c;
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <= head; ++i) {
                for (int j = counts[i]; j > 0; --j) {
                    sb.append(chars[i]);
                }
            }
            return sb.toString();
        }
    }
}
