/**
 * LeetCode #406, medium
 *
 * Suppose you have a random list of people standing in a queue. Each person is described by a pair of integers (h, k),
 * where h is the height of the person and k is the number of people in front of this person who have a height greater
 * than or equal to h. Write an algorithm to reconstruct the queue.
 *
 * Note:
 * The number of people is less than 1,100.
 *
 * Example
 *
 * Input:
 * [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
 *
 * Output:
 * [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
 */

import java.util.*;

public class QueueReconstructionByHeight {
    /**
     * Solution 1:
     *
     * The most important observation is that k is only dependent on taller h in front of that person. Based on this,
     * we can sort people from tall to short, with increasing k. Within the same height group, it is guaranteed that
     * the member orders are the same as the final result (i.e. (h, k) appears in front of (h, k+1)). We iterate
     * through the list and insert members based on their k as the result index.
     *
     * Time complexity: O(n^2). Space complexity: O(n)
     */
    class Solution1 {
        public int[][] reconstructQueue(int[][] people) {
            List<int[]> sorted = new ArrayList<>();
            for (int[] p : people) {
                sorted.add(p);
            }
            // sort from tall to short, but keep k increasing
            sorted.sort((a, b) -> { return a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]; });
            // reposition from tall to short since only the taller ones could affect k
            List<int[]> queue = new LinkedList<>();
            for (int[] p : sorted) {
                int ge_count = 0;
                ListIterator<int[]> it = queue.listIterator();
                while (it.hasNext()) {
                    if (ge_count == p[1]) {
                        break;
                    }
                    int[] q = it.next();
                    if (q[0] >= p[0]) {
                        ge_count += 1;
                    }
                }
                if (ge_count == p[1]) {
                    it.add(p);
                }
            }
            // collect result
            int[][] result = new int[people.length][];
            int k = 0;
            for (int[] p : queue) {
                result[k++] = p;
            }
            return result;
        }
    }

    /**
     * Solution 2:
     *
     * Same as Solution 1 with optimization and use of Java List APIs
     */
    class Solution2 {
        public int[][] reconstructQueue(int[][] people) {
            // sort from tall to short, but keep k increasing
            Arrays.sort(people, (a, b) -> { return a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]; });
            List<int[]> list = new ArrayList<>();
            for (int[] p : people) {
                list.add(p[1], p);
            }
            return list.toArray(new int[list.size()][2]);
        }
    }
}
