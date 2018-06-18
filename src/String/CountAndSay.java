/**
 * LeetCode #38, easy
 *
 * The count-and-say sequence is the sequence of integers with the first five terms as following:

 1.     1
 2.     11
 3.     21
 4.     1211
 5.     111221
 1 is read off as "one 1" or 11.
 11 is read off as "two 1s" or 21.
 21 is read off as "one 2, then one 1" or 1211.
 Given an integer n, generate the nth term of the count-and-say sequence.

 Note: Each term of the sequence of integers will be represented as a string.

 Example 1:

 Input: 1
 Output: "1"
 Example 2:

 Input: 4
 Output: "1211"
 */

package String;

public class CountAndSay {
    /**
     * Solution 1: Recursion
     *
     * Count char and append count + char to output. Recursive.
     */
    class Solution1 {
        public String countAndSay(int n) {
            if (n == 1) return "1";
            String s = countAndSay(n-1);
            char[] cs = s.toCharArray();
            char last = cs[0];
            int count = 0;
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < cs.length; i++) {
                if (cs[i] != last) {
                    res.append(count).append(last);
                    count = 1;
                    last = cs[i];
                } else {
                    count++;
                }
            }
            return res.append(count).append(last).toString();
        }
    }
}
