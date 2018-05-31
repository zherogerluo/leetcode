/**
 * LeetCode #2, medium
 *
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

 You may assume the two numbers do not contain any leading zero, except the number 0 itself.

 Example

 Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 Output: 7 -> 0 -> 8
 Explanation: 342 + 465 = 807.
 */

package LinkedList;

public class AddTwoNumbers {
    /**
     * Definition for singly-linked list.
     */
    public class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }
    }

    /**
     * Solution 1: Linked list traversal
     *
     * Traverse two linked lists at the same time, keeping record of the carry digit (0 or 1).
     *
     * Tricky part: one list node becomes null (check for nulls), BOTH list nodes become null
     * BUT carry digit is not zero. In these cases the loop should still go through. The ending
     * condition is that, both lists reach end AND carry digit is zero.
     *
     * Time complexity: O(n). Space complexity: O(1)
     */
    class Solution1 {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode sentinel = new ListNode(0), prev = sentinel;
            int carry = 0;
            while (l1 != null || l2 != null || carry != 0) { // note the carry digit condition
                int val = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + carry;
                carry = val / 10;
                val = val % 10;
                prev.next = new ListNode(val);
                prev = prev.next;
                if (l1 != null) l1 = l1.next; // check for null!
                if (l2 != null) l2 = l2.next; // check for null!
            }
            return sentinel.next;
        }
    }
}
