/**
 * LeetCode $454, medium
 *
 * Given four lists A, B, C, D of integer values, compute how many tuples (i, j, k, l) there are such that A[i] +
 * B[j] + C[k] + D[l] is zero.
 *
 * To make problem a bit easier, all A, B, C, D have same length of N where 0 ≤ N ≤ 500. All integers are in the
 * range of -228 to 228 - 1 and the result is guaranteed to be at most 231 - 1.
 *
 * Example:
 *
 * Input:
 * A = [ 1, 2]
 * B = [-2,-1]
 * C = [-1, 2]
 * D = [ 0, 2]
 *
 * Output:
 * 2
 *
 * Explanation:
 * The two tuples are:
 * 1. (0, 0, 0, 1) -> A[0] + B[0] + C[0] + D[1] = 1 + (-2) + (-1) + 2 = 0
 * 2. (1, 1, 0, 0) -> A[1] + B[1] + C[0] + D[0] = 2 + (-1) + (-1) + 0 = 0
 */

package HashTable;

import java.util.*;

public class FourSumTwo {
    /**
     * Solution 1: Two pointers
     *
     * Enumerate the sum of A and B, and C and D, so the problem reduces to two sum problem. Use sorting and two
     * pointers to solve the two sum problem, but remember to explicitly treat the duplicates: Need to count number
     * of duplicates in both arrays and multiply them before adding to result.
     *
     * Time complexity: O(n^2 * log(n)). Space complexity: O(n^2).
     */
    class Solution1 {
        public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
            final int n = A.length;
            int[] AB = new int[n * n], CD = new int[n * n];
            int k = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    AB[k] = A[i] + B[j];
                    CD[k] = C[i] + D[j];
                    k++;
                }
            }
            Arrays.sort(AB);
            Arrays.sort(CD);
            int res = 0, i = 0, j = CD.length - 1;
            while (i < AB.length && j >= 0) {
                if (AB[i] + CD[j] > 0) j--;
                else if (AB[i] + CD[j] < 0) i++;
                else {
                    int p = 1, q = 1;
                    while (++i < AB.length && AB[i-1] == AB[i]) p++;
                    while (--j >= 0 && CD[j+1] == CD[j]) q++;
                    res += p * q;
                }
            }
            return res;
        }
    }

    /**
     * Solution 2: Hash map
     *
     * Use hash map to store sum and count for A + B, and for every C + D just fetch the count of -(C + D) in the map.
     *
     * Time complexity: O(n^2). Space complexity: O(n^2).
     */
    class Solution2 {
        public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
            Map<Integer, Integer> count = new HashMap<>();
            for (int a : A) {
                for (int b : B) {
                    count.put(a + b, count.getOrDefault(a + b, 0) + 1);
                }
            }
            int res = 0;
            for (int c : C) {
                for (int d : D) {
                    res += count.getOrDefault(-(c + d), 0);
                }
            }
            return res;
        }
    }
}
