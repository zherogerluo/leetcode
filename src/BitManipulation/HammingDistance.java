/**
 * LeetCode #461, easy
 *
 * The Hamming distance between two integers is the number of positions at which the corresponding bits are different.
 *
 * Given two integers x and y, calculate the Hamming distance.
 *
 * Note:
 * 0 ≤ x, y < 231.
 *
 * Example:
 *
 * Input: x = 1, y = 4
 *
 * Output: 2
 *
 * Explanation:
 * 1   (0 0 0 1)
 * 4   (0 1 0 0)
 *        ↑   ↑
 *
 * The above arrows point to positions where the corresponding bits are different.
 */

package BitManipulation;

public class HammingDistance {
    /**
     * Solution 1: XOR and count bits
     *
     * Time complexity: O(1). Space complexity: O(1).
     */
    class Solution1 {
        public int hammingDistance(int x, int y) {
            int res = 0, xor = x ^ y;
            // count bit 1 in xor
            while (xor != 0) {
                res += xor & 1;
                xor = xor >>> 1;
            }
            return res;
        }
    }
}
