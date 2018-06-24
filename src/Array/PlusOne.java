/**
 * LeetCode #66, easy
 *
 * Given a non-empty array of digits representing a non-negative integer, plus one to the integer.

 The digits are stored such that the most significant digit is at the head of the list, and each element in the array contain a single digit.

 You may assume the integer does not contain any leading zero, except the number 0 itself.

 Example 1:

 Input: [1,2,3]
 Output: [1,2,4]
 Explanation: The array represents the integer 123.
 Example 2:

 Input: [4,3,2,1]
 Output: [4,3,2,2]
 Explanation: The array represents the integer 4321.
 */

package Array;

public class PlusOne {
    /**
     * Solution 1:
     *
     * Add digit from back, keep track of carry over digit, and allocate new array if necessary.
     */
    class Solution1 {
        public int[] plusOne(int[] digits) {
            int carry = 1;
            for (int i = digits.length-1; i >= 0; i--) {
                int sum = digits[i] + carry;
                carry = sum / 10;
                digits[i] = sum % 10;
            }
            if (carry != 0) {
                int[] res = new int[digits.length+1];
                System.arraycopy(digits, 0, res, 1, digits.length);
                res[0] = carry;
                return res;
            }
            return digits;
        }
    }
}
