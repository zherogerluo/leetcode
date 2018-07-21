/**
 * LeetCode #258, easy
 *
 * Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.

 Example:

 Input: 38
 Output: 2
 Explanation: The process is like: 3 + 8 = 11, 1 + 1 = 2.
 Since 2 has only one digit, return it.
 Follow up:
 Could you do it without any loop/recursion in O(1) runtime?
 */

package Math;

public class AddDigits {
    /**
     * Solution 1: Recursive
     *
     * Sum all digits up, if not single digit, go on.
     */
    class Solution1 {
        public int addDigits(int num) {
            if (num < 10) return num;
            int sum = 0;
            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }
            return addDigits(sum);
        }
    }

    /**
     * Solution 2: Iterative
     *
     * Same idea as Solution 1.
     */
    class Solution2 {
        public int addDigits(int num) {
            int sum = num;
            while (sum >= 10) {
                num = sum;
                sum = 0;
                while (num > 0) {
                    sum += num % 10;
                    num /= 10;
                }
            }
            return sum;
        }
    }

    /**
     * Solution 3: Remainder
     *
     * num = a0 + a1 * 10 + a2 * 10^2 + ... + ak * 10^k + ...
     *
     * The first step is to calculate a0 + a1 + ... + ak. It can be done by just doing n % 9. Then for the new sum,
     * do the same thing on and on. This whole iterative process is equivalent of a single num % 9 operation.
     *
     * Tricky part: We need to explicitly deal with 0 and multiples of 9.
     */
    class Solution3 {
        public int addDigits(int num) {
            if (num != 0 && num % 9 == 0) return 9;
            return num % 9;
        }
    }
}
