/**
 * LeetCode #338, medium
 *
 * Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num calculate the number of 1's in
 * their binary representation and return them as an array.
 *
 * Example 1:
 * Input: 2
 * Output: [0,1,1]
 *
 * Example 2:
 * Input: 5
 * Output: [0,1,1,2,1,2]
 *
 * Follow up:
 * It is very easy to come up with a solution with run time O(n*sizeof(integer)). But can you do it in linear time O(n)
 * /possibly in a single pass?
 * Space complexity should be O(n).
 * Can you do it like a boss? Do it without using any builtin function like __builtin_popcount in c++ or in any other
 * language.
 */

public class CountingBits {
    /**
     * Solution 1:
     *
     * Simply count bits.
     */
    class Solution1 {
        public int[] countBits(int num) {
            int[] result = new int[num + 1];
            for (int i = 0; i <= num; i++) {
                result[i] = countBitsForNum(i);
            }
            return result;
        }

        private int countBitsForNum(int num) {
            int count = 0;
            while (num != 0) {
                count += num & 1;
                num >>= 1;
            }
            return count;
        }
    }

    /**
     * Solution 2: Dynamic programming
     *
     * Reuse previous results, number of bits of k is 1 + number of bits of k1 where k1 is k with highest bit reset to
     * zero. Results between 2^m and 2^m+1 is 1 + results between 2^(m-1) and 2^m. We can keep track of 2^m as the base
     * number to calculate the index for accessing previous result.
     */
    class Solution2 {
        public int[] countBits(int num) {
            int[] result = new int[num + 1];
            int base = 0, next_base = 1;
            for (int i = 1; i <= num; i++) {
                if (i == next_base) {
                    result[i] = 1;
                    base = next_base;
                    next_base = next_base * 2;
                } else {
                    result[i] = 1 + result[i - base];
                }
            }
            return result;
        }
    }
}