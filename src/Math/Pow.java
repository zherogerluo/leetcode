/**
 * LeetCode #50, medium
 *
 * Implement pow(x, n), which calculates x raised to the power n (x**n).

 Example 1:

 Input: 2.00000, 10
 Output: 1024.00000
 Example 2:

 Input: 2.10000, 3
 Output: 9.26100
 Example 3:

 Input: 2.00000, -2
 Output: 0.25000
 Explanation: 2-2 = 1/22 = 1/4 = 0.25
 Note:

 -100.0 < x < 100.0
 n is a 32-bit signed integer, within the range [−2**31, 2**31 − 1]
 */

package Math;

public class Pow {
    /**
     * Solution 1: Binary search
     *
     * pow(x, n) can be reduced to d * d where d = pow(x, n/2). Beware of so many corner cases and overflow.
     *
     * Time complexity: O(log(n)). Space complexity: O(log(n)) because of recursion. Can be reduced to O(1) space
     * using iteration.
     */
    class Solution {
        public double myPow(double x, int n) {
            if (x == 0 && n < 0) return Double.POSITIVE_INFINITY;
            if (n == 0) return 1;
            if (n == 1) return x;
            if (x == 0) return 0;
            if (x == 1) return 1;
            if (x == -1) return n%2 == 0 ? 1 : -1;
            if (n < 0) return 1.0 / myPow(x, -(n+1)) / x;
            double d = myPow(x, n/2);
            return n % 2 == 0 ? d*d : d*d*x;
        }
    }
}
