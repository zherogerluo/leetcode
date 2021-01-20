/**
 * LeetCode #1072, medium
 *
 * Given a matrix consisting of 0s and 1s, we may choose any number of columns in the matrix and flip every cell in that
 * column.  Flipping a cell changes the value of that cell from 0 to 1 or from 1 to 0.
 *
 * Return the maximum number of rows that have all values equal after some number of flips.
 *
 * Example 1:
 *
 * Input: [[0,1],[1,1]]
 * Output: 1
 * Explanation: After flipping no values, 1 row has all values equal.
 *
 * Example 2:
 *
 * Input: [[0,1],[1,0]]
 * Output: 2
 * Explanation: After flipping values in the first column, both rows have equal values.
 *
 * Example 3:
 *
 * Input: [[0,0,0],[0,0,1],[1,1,0]]
 * Output: 2
 * Explanation: After flipping values in the first two columns, the last two rows have equal values.
 *
 * Note:
 *
 * 1 <= matrix.length <= 300
 * 1 <= matrix[i].length <= 300
 * All matrix[i].length's are equal
 * matrix[i][j] is 0 or 1
 */

package HashTable;

import java.util.*;

public class FlipColumnsForMaximumNumberOfEqualRows {
    /**
     * Solution 1: Hashmap
     *
     * It is apparent that we need to count the number of same rows, and consider the flipped rows as they can also have
     * all equal values if the conjugate is converted to all equal, e.g. {0 1 0} and {1 0 1}. We use a bit mask to store
     * each row and use it as the key to the hashmap. Depending on the row length, multiple longs may be needed. The
     * result would be max of (the max of single row count, and the max of single row count and its complement's count).
     *
     * Time complexity: O(mn), m for row operations and n for initialization/hashing a row.
     * Space complexity: O(mn).
     */
    class Solution1 {
        class Row {
            int len;
            long[] bits;

            public Row(int[] row) {
                len = row.length;
                bits = new long[(len+63)/64];
                int i = 0;
                for (int bit : row) {
                    bits[i/64] = (bits[i/64] << 1) | bit;
                    ++i;
                }
            }

            public Row(Row other) {
                len = other.len;
                bits = Arrays.copyOf(other.bits, other.bits.length);
            }

            public Row flip() {
                Row res = new Row(this);
                for (int i = 0; i < bits.length; ++i) {
                    res.bits[i] = ~res.bits[i];
                }
                if (len % 64 != 0) {
                    res.bits[bits.length-1] &= ~(-1 << (len % 64));
                }
                return res;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || !(o instanceof Row)) {
                    return false;
                }
                Row other = (Row) o;
                return len == other.len && Arrays.equals(bits, other.bits);
            }

            @Override
            public int hashCode() {
                return Arrays.hashCode(bits);
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (long bit : bits) {
                    sb.append(Long.toBinaryString(bit));
                    sb.append(' ');
                }
                return sb.toString();
            }
        }
        public int maxEqualRowsAfterFlips(int[][] matrix) {
            Map<Row, Integer> map = new HashMap<>();
            for (int[] row : matrix) {
                Row key = new Row(row);
                map.put(key, map.getOrDefault(key, 0) + 1);
            }
            int res = 0;
            for (Row row : map.keySet()) {
                res = Math.max(res, map.get(row) + map.getOrDefault(row.flip(), 0));
            }
            return res;
        }
    }

    /**
     * Solution 2: Hashmap
     *
     * Difference from Solution 1: 1) Use string as hashmap key 2) Update res while building the hashmap
     */
    class Solution2 {
        public int maxEqualRowsAfterFlips(int[][] matrix) {
            Map<String, Integer> map = new HashMap<>();
            int res = 0;
            for (int[] row : matrix) {
                StringBuilder sb = new StringBuilder(), flip = new StringBuilder();
                for (int val : row) {
                    sb.append(val);
                    flip.append(1 - val);
                }
                String key = sb.toString(), flipped = flip.toString();
                int count = map.getOrDefault(key, 0) + 1;
                map.put(key, count);
                res = Math.max(res, count + map.getOrDefault(flipped, 0));
            }
            return res;
        }
    }
}
