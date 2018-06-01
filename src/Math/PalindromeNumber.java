/**
 * LeetCode #9, easy
 *
 * Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.

 Example 1:

 Input: 121
 Output: true
 Example 2:

 Input: -121
 Output: false
 Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
 Example 3:

 Input: 10
 Output: false
 Explanation: Reads 01 from right to left. Therefore it is not a palindrome.

 Follow up:
 Could you solve it without converting the integer to a string?
 */

package Math;

public class PalindromeNumber {
    /**
     * Solution 1:
     *
     * Calculate reverse until reverse is larger than x, then compare reverse vs x.
     *
     * Tricky part #1:
     *
     * Stop calculating reverse once it is larger than x. Compare reverse and x outside of loop.
     *
     * Tricky part #2:
     *
     * Palindrome can be odd or even lengths.
     *
     * Tricky part #3:
     *
     * Beware of corner cases, especially cases like "12210"!
     */
    class Solution1 {
        public boolean isPalindrome(int x) {
            if (x < 0) return false;
            if (x < 10) return true;
            if (x % 10 == 0) return false;
            int reverse = 0;
            while (reverse < x) {
                reverse = reverse * 10 + x % 10;
                x = x / 10;
            }
            return x == reverse || reverse / 10 == x;
        }
    }
}
