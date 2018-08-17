/**
 * LeetCode #371, easy
 *
 * Calculate the sum of two integers a and b, but you are not allowed to use the operator + and -.
 *
 * Example:
 * Given a = 1 and b = 2, return 3.
 */

package BitManipulation;

public class SumOfTwoIntegers {
    /**
     * Solution 1: Bit manipulation
     *
     * Calculate carry bits and plain sum bits separately using & and ^, then recursively add them together.
     *
     * For carry bits: 1 for 1 + 1 and 0 for all other case, so it can be done using & operator. Carry bits will be
     * imposed on higher position so we do a left shift.
     *
     * For plain sum bits (without carry): 0 for 0 + 0 and 1 + 1, and 1 for 0 + 1 and 1 + 0, which can be done using
     * ^ (XOR) operator.
     */
    class Solution1 {
        public int getSum(int a, int b) {
            if (b == 0) return a;
            if (a == 0) return b;
            int carry = (a & b) << 1;
            int rest = a ^ b;
            return getSum(carry, rest);
        }
    }
}
