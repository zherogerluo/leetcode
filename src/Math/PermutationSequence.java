/**
 * LeetCode #60, medium
 *
 * The set [1,2,3,...,n] contains a total of n! unique permutations.
 *
 * By listing and labeling all of the permutations in order, we get the following sequence for n = 3:
 *
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 * Given n and k, return the kth permutation sequence.
 *
 * Note:
 *
 * Given n will be between 1 and 9 inclusive.
 * Given k will be between 1 and n! inclusive.
 *
 * Example 1:
 *
 * Input: n = 3, k = 3
 * Output: "213"
 *
 * Example 2:
 *
 * Input: n = 4, k = 9
 * Output: "2314"
 */

package Math;

public class PermutationSequence {
    /**
     * Solution 1:
     *
     * An observation is that, for 1*** to increment to 2***, k must be at lease 3!. We can thus compare k with i!
     * and determine digits one by one. To avoid repetitive calculation of factorial, calculate n! first then divide
     * to get (n-1)!, (n-2)!, etc.
     *
     * Time complexity: O(n^2). Note that finding the right number by index takes O(n) time.
     * Space complexity: O(n).
     */
    class Solution1 {
        public String getPermutation(int n, int k) {
            StringBuilder sb = new StringBuilder();
            int fac = 1;
            boolean[] used = new boolean[n];
            for (int i = 2; i <= n; i++) fac *= i; // calculate n!
            k--; // this is important for the last two digits to be correct - think about n = 2 and k = 2
            while (n > 0 && k >= 0) {
                fac /= n--; // factorial of n-1
                int index = k / fac; // this is how large the current digit needs to be
                for (int i = 0; i <= index; i++) {
                    if (used[i]) index++; // the digit is not simply index, but the index-th digit of unused numbers
                }
                used[index] = true;
                sb.append(index + 1);
                k -= k % fac;
            }
            return sb.toString();
        }
    }
}
