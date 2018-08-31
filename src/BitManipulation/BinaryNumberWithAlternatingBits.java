/**
 * LeetCode #693, easy
 *
 * Given a positive integer, check whether it has alternating bits: namely, if two adjacent bits will always have
 * different values.
 *
 * Example 1:
 * Input: 5
 * Output: True
 * Explanation:
 * The binary representation of 5 is: 101
 *
 * Example 2:
 * Input: 7
 * Output: False
 * Explanation:
 * The binary representation of 7 is: 111.
 *
 * Example 3:
 * Input: 11
 * Output: False
 * Explanation:
 * The binary representation of 11 is: 1011.
 *
 * Example 4:
 * Input: 10
 * Output: True
 * Explanation:
 * The binary representation of 10 is: 1010.
 */

package BitManipulation;

public class BinaryNumberWithAlternatingBits {
    /**
     * Solution 1: Loop
     *
     * Loop through bits and check with last seen bit.
     */
    class Solution1 {
        public boolean hasAlternatingBits(int n) {
            if (n == 0) return true;
            int last = n & 1;
            n = n >>> 1;
            while (n != 0) {
                int cur = n & 1;
                if (last == cur) return false;
                last = cur;
                n = n >>> 1;
            }
            return true;
        }
    }

    /**
     * Solution 2: Bit manipulation
     *
     * XOR with right shifted n, result should be 000...111...1, which is supposed to have all ones in lower bits. To
     * verify it, do an AND operation with n + 1: 000...1000...0 & 000...0111...1 == 0.
     */
    class Solution2 {
        public boolean hasAlternatingBits(int n) {
            return ((n ^= (n >> 1)) & (n + 1)) == 0;
        }
    }
}
