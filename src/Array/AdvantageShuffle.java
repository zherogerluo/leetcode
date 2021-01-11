/**
 * LeetCode #870, medium
 *
 * Given two arrays A and B of equal size, the advantage of A with respect to B is the number of indices i for which
 * A[i] > B[i].
 *
 * Return any permutation of A that maximizes its advantage with respect to B.
 *
 * Example 1:
 *
 * Input: A = [2,7,11,15], B = [1,10,4,11]
 * Output: [2,11,7,15]
 *
 * Example 2:
 *
 * Input: A = [12,24,8,32], B = [13,25,32,11]
 * Output: [24,32,8,12]
 *
 * Note:
 *
 * 1 <= A.length = B.length <= 10000
 * 0 <= A[i] <= 10^9
 * 0 <= B[i] <= 10^9
 */

package Array;

import java.util.*;

public class AdvantageShuffle {
    /**
     * Solution 1:
     *
     * Greedy approach, the idea is to sort B and find minimum in A that beats B[i]. The tricky part is how we record
     * the numbers in A that have been used, and for B we sort indices not B itself.
     */
    class Solution1 {
        public int[] advantageCount(int[] A, int[] B) {
            final int n = A.length;
            Integer[] indicesB = new Integer[n];
            for (int i = 0; i < n; ++i) {
                indicesB[i] = i;
            }
            Arrays.sort(indicesB, Comparator.comparingInt(i -> B[i]));
            int[] sortedA = Arrays.copyOf(A, n);
            Arrays.sort(sortedA);
            int[] res = new int[n];
            int j = 0;
            for (int i = 0; i < n; ++i) {
                if (sortedA[i] > B[indicesB[j]]) {
                    res[indicesB[j]] = sortedA[i];
                    sortedA[i] = -1;
                    ++j;
                }
            }
            // fill rest of result with unused number in A
            for (int num : sortedA) {
                if (num != -1) {
                    res[indicesB[j++]] = num;
                }
            }
            return res;
        }
    }
}
