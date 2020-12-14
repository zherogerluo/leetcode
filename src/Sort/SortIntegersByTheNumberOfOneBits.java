/**
 * LeetCode #1356, easy
 *
 * Given an integer array arr. You have to sort the integers in the array in ascending order by the number of 1's in
 * their binary representation and in case of two or more integers have the same number of 1's you have to sort them
 * in ascending order.
 *
 * Return the sorted array.
 *
 * Example 1:
 *
 * Input: arr = [0,1,2,3,4,5,6,7,8]
 * Output: [0,1,2,4,8,3,5,6,7]
 * Explantion: [0] is the only integer with 0 bits.
 * [1,2,4,8] all have 1 bit.
 * [3,5,6] have 2 bits.
 * [7] has 3 bits.
 * The sorted array by bits is [0,1,2,4,8,3,5,6,7]
 *
 * Example 2:
 *
 * Input: arr = [1024,512,256,128,64,32,16,8,4,2,1]
 * Output: [1,2,4,8,16,32,64,128,256,512,1024]
 * Explantion: All integers have 1 bit in the binary representation, you should just sort them in ascending order.
 *
 * Example 3:
 *
 * Input: arr = [10000,10000]
 * Output: [10000,10000]
 *
 * Example 4:
 *
 * Input: arr = [2,3,5,7,11,13,17,19]
 * Output: [2,3,5,17,7,11,13,19]
 *
 * Example 5:
 *
 * Input: arr = [10,100,1000,10000]
 * Output: [10,100,10000,1000]
 *
 * Constraints:
 *
 * 1 <= arr.length <= 500
 * 0 <= arr[i] <= 10^4
 */

import java.util.*;

public class SortIntegersByTheNumberOfOneBits {
    /**
     * Solution 1: Sort
     *
     * Trivial, sort with customized comparator
     */
    class Solution1 {
        public int[] sortByBits(int[] arr) {
            Integer[] copy = Arrays.stream(arr).boxed().toArray(Integer[]::new);
            Arrays.sort(copy, (a, b) -> {
                int bits_a = 0, bits_b = 0, copy_a = a, copy_b = b;
                while (copy_a != 0 || copy_b != 0) {
                    bits_a += copy_a & 1;
                    bits_b += copy_b & 1;
                    // don't modify original numbers
                    copy_a >>= 1;
                    copy_b >>= 1;
                }
                return bits_a == bits_b ? Integer.compare(a, b) : (bits_a - bits_b);
            });
            return Arrays.stream(copy).mapToInt(Integer::intValue).toArray();
        }
    }

    /**
     * Solution 2: Sort
     *
     * Use a n x 2 to remember bits count and sort the rows.
     */
    class Solution2 {
        public int[] sortByBits(int[] arr) {
            int[][] num_bits = new int[arr.length][2];
            for (int i = 0; i < arr.length; ++i) {
                int num = arr[i], bits = 0;
                while (num != 0) {
                    bits += num & 1;
                    num >>= 1;
                }
                num_bits[i][0] = arr[i];
                num_bits[i][1] = bits;
            }
            Arrays.sort(num_bits, (a, b) -> {
                return a[1] == b[1] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]);
            });
            int[] res = new int[arr.length];
            for (int i = 0; i < arr.length; ++i) {
                res[i] = num_bits[i][0];
            }
            return res;
        }
    }

    /**
     * Solution 3: Encoding
     *
     * Encode the number with bits together as bits * multiplier + number. Since the number has an upper limit, we can
     * choose a multiplier of, for example, 10^5. Sort the encoded numbers in natural order and we can get the results
     * by decoding them.
     */
    class Solution3 {
        public int[] sortByBits(int[] arr) {
            int[] encoded = new int[arr.length];
            for (int i = 0; i < arr.length; ++i) {
                int num = arr[i], bits = 0;
                while (num != 0) {
                    bits += num & 1;
                    num >>= 1;
                }
                encoded[i] = bits * 100000 + arr[i];
            }
            Arrays.sort(encoded);
            for (int i = 0; i < arr.length; ++i) {
                encoded[i] %= 100000;
            }
            return encoded;
        }
    }
}
