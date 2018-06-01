/**
 * LeetCode #7, easy
 *
 * Given a 32-bit signed integer, reverse digits of an integer.

 Example 1:

 Input: 123
 Output: 321
 Example 2:

 Input: -123
 Output: -321
 Example 3:

 Input: 120
 Output: 21

 Note:
 Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range:
 [−2^31,  2^31 − 1]. For the purpose of this problem, assume that your function returns 0 when the reversed integer
 overflows.
 */

package Math;

public class ReverseInteger {
    /**
     * Solution 1:
     *
     * x % 10 is the remainder (negative for negative x), x / 10 is all other digits. Remember to check for overflow.
     */
    class Solution1 {
        public int reverse(int x) {
            int res = 0;
            while (Math.abs(x) > 0) {
                int d = x % 10; // returns remainder rather than modulus for Java
                if (x > 0 && (Integer.MAX_VALUE - d) / 10 < res) return 0;
                if (x < 0 && (Integer.MIN_VALUE - d) / 10 > res) return 0;
                x /= 10;
                res = res * 10 + d;
            }
            return res;
        }
    }
}
