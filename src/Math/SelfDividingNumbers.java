/**
 * LeetCode #728, easy
 *
 * A self-dividing number is a number that is divisible by every digit it contains.
 *
 * For example, 128 is a self-dividing number because 128 % 1 == 0, 128 % 2 == 0, and 128 % 8 == 0.
 *
 * Also, a self-dividing number is not allowed to contain the digit zero.
 *
 * Given a lower and upper number bound, output a list of every possible self dividing number, including the bounds
 * if possible.
 *
 * Example 1:
 * Input:
 * left = 1, right = 22
 * Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22]
 * Note:
 *
 * The boundaries of each input argument are 1 <= left <= right <= 10000.
 */

package Math;

import java.util.*;

public class SelfDividingNumbers {
    /**
     * Solution 1:
     *
     * Brutal force, test if each integer is self dividing. Need to take care of case when last digit is 0 (avoid
     * divide by zero error).
     */
    class Solution1 {
        public List<Integer> selfDividingNumbers(int left, int right) {
            List<Integer> res = new ArrayList<>();
            for (; left <= right; left++) {
                if (isSelfDividing(left)) res.add(left);
            }
            return res;
        }

        private boolean isSelfDividing(int n) {
            int remain = n;
            while (remain != 0) {
                int digit = remain % 10;
                if (digit == 0 || n % digit != 0) return false; // need to check whether last digit is zero
                remain = remain / 10;
            }
            return true;
        }
    }
}
