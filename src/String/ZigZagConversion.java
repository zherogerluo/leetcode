/**
 * LeetCode #6, medium
 *
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)

 P   A   H   N
 A P L S I I G
 Y   I   R
 And then read line by line: "PAHNAPLSIIGYIR"

 Write the code that will take a string and make this conversion given a number of rows:

 string convert(string s, int numRows);
 Example 1:

 Input: s = "PAYPALISHIRING", numRows = 3
 Output: "PAHNAPLSIIGYIR"
 Example 2:

 Input: s = "PAYPALISHIRING", numRows = 4
 Output: "PINALSIGYAHRPI"
 Explanation:

 P     I    N
 A   L S  I G
 Y A   H R
 P     I
 */

package String;

public class ZigZagConversion {
    /**
     * Solution 1: StringBuilder array
     *
     * Use numRows StringBuilders, assign chars to StringBuilders. If hit 0 or numRows-1 (boundaries), flip the
     * direction. Straightforward.
     *
     * Time complexity: O(n). Space complexity: O(k), where k = numRows.
     */
    class Solution1 {
        public String convert(String s, int numRows) {
            if (s == null) return null;
            if (numRows <= 1 || s.length() <= numRows) return s;
            StringBuilder[] sbs = new StringBuilder[numRows];
            for (int i = 0; i < numRows; i++) sbs[i] = new StringBuilder();
            int sbIndex = 0, direction = 1;
            for (char c : s.toCharArray()) {
                sbs[sbIndex].append(c);
                sbIndex += direction;
                if (sbIndex == 0 || sbIndex == numRows-1) direction = -direction;
            }
            for (int i = 1; i < numRows; i++) {
                sbs[0].append(sbs[i]);
            }
            return sbs[0].toString();
        }
    }
}
