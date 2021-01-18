/**
 * LeetCode #922, easy
 *
 * Given an array A of non-negative integers, half of the integers in A are odd, and half of the integers are even.
 *
 * Sort the array so that whenever A[i] is odd, i is odd; and whenever A[i] is even, i is even.
 *
 * You may return any answer array that satisfies this condition.
 *
 * Example 1:
 *
 * Input: [4,2,5,7]
 * Output: [4,5,2,7]
 * Explanation: [4,7,2,5], [2,5,4,7], [2,7,4,5] would also have been accepted.
 *
 * Note:
 *
 * 2 <= A.length <= 20000
 * A.length % 2 == 0
 * 0 <= A[i] <= 1000
 */

package Array;

public class SortArrayByParityTwo {
    /**
     * Solution 1:
     *
     * Admittedly, an easier solution would be just iterating through the array and put even/odd numbers in the right
     * place in a new array. This solution uses the same storage, using two pointers to check even/odd indices and swap
     * them if not in the right place.
     */
    class Solution1 {
        public int[] sortArrayByParityII(int[] A) {
            int even = 0, odd = 1;
            while (even < A.length && odd < A.length) {
                if (A[even] % 2 == 0) {
                    even += 2;
                    continue;
                }
                if (A[odd] % 2 != 0) {
                    odd += 2;
                    continue;
                }
                int tmp = A[even];
                A[even] = A[odd];
                A[odd] = tmp;
            }
            return A;
        }
    }
}
