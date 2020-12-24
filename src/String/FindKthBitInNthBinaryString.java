/**
 * LeetCode #1545, medium
 *
 * Given two positive integers n and k, the binary string  Sn is formed as follows:
 *
 * S1 = "0"
 * Si = Si-1 + "1" + reverse(invert(Si-1)) for i > 1
 * Where + denotes the concatenation operation, reverse(x) returns the reversed string x, and invert(x) inverts all the
 * bits in x (0 changes to 1 and 1 changes to 0).
 *
 * For example, the first 4 strings in the above sequence are:
 *
 * S1 = "0"
 * S2 = "011"
 * S3 = "0111001"
 * S4 = "011100110110001"
 * Return the kth bit in Sn. It is guaranteed that k is valid for the given n.
 *
 * Example 1:
 *
 * Input: n = 3, k = 1
 * Output: "0"
 * Explanation: S3 is "0111001". The first bit is "0".
 *
 * Example 2:
 *
 * Input: n = 4, k = 11
 * Output: "1"
 * Explanation: S4 is "011100110110001". The 11th bit is "1".
 *
 * Example 3:
 *
 * Input: n = 1, k = 1
 * Output: "0"
 *
 * Example 4:
 *
 * Input: n = 2, k = 3
 * Output: "1"
 *
 * Constraints:
 *
 * 1 <= n <= 20
 * 1 <= k <= 2^n - 1
 */

package String;

public class FindKthBitInNthBinaryString {
    /**
     * Solution 1: Calculate Sn
     *
     * Very slow.
     */
    class Solution1 {
        public char findKthBit(int n, int k) {
            return getS(n).charAt(k-1);
        }

        private String getS(int n) {
            if (n == 1) {
                return "0";
            }
            String s = getS(n-1);
            StringBuilder sb = new StringBuilder(s);
            sb.append('1');
            for (int i = s.length()-1; i >= 0; --i) {
                sb.append((char)('0' + '1' - s.charAt(i)));
            }
            return sb.toString();
        }
    }

    /**
     * Solution 2: Recursion
     *
     * We don't have to calculate Sn. By observing the construction of Sn, one can easily see that it reduces to n-1
     * case in two ways: Either the straightforward n-1 case if k falls in the first half, or the reverse of the
     * inverse of n-1 case if k falls in the second half.
     *
     * Also note that the number of digits An = 2^n - 1:
     * An = 2 * An-1 + 1
     * An + 1 = 2 * (An-1 + 1)
     * An + 1 = 2^(n-1) * (A1 + 1) = 2^n
     */
    class Solution2 {
        public char findKthBit(int n, int k) {
            if (n == 1 && k == 1) {
                return '0';
            }
            int digits = (int)(Math.pow(2, n)) - 1;
            int prev_digits = (digits - 1) / 2;
            if (k == prev_digits + 1) {
                return '1';
            }
            if (k <= prev_digits) {
                return findKthBit(n-1, k);
            }
            return (char)('0' + '1' - findKthBit(n-1, digits-k+1));
        }
    }
}
