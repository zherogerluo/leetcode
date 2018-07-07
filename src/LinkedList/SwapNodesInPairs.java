/**
 * LeetCode #24, medium
 *
 * Given a linked list, swap every two adjacent nodes and return its head.

 Example:

 Given 1->2->3->4, you should return the list as 2->1->4->3.
 Note:

 Your algorithm should use only constant extra space.
 You may not modify the values in the list's nodes, only nodes itself may be changed.
 */

package LinkedList;

public class SwapNodesInPairs {
    /**
     * Solution 1: Loop
     *
     * prev -> head -> tail -> after. Swap head and tail each time, and update all pointers. Check for nulls.
     * Recursion is out of the question because it will take O(n) space.
     *
     * Time complexity: O(n). Space complexity: O(1)
     */
    class Solution1 {
        public ListNode swapPairs(ListNode head) {
            if (head == null) return head; // check for null. takes care of n = 0 case
            ListNode sentinel = new ListNode(0);
            sentinel.next = head;
            ListNode prev = sentinel, tail = head.next;
            while (tail != null) { // takes care of n = 1 case
                head.next = tail.next;
                tail.next = head;
                prev.next = tail;
                prev = head;
                head = prev.next;
                tail = head == null ? null : head.next; // check for null
            }
            return sentinel.next;
        }
    }

    /**
     * Solution 2: Loop
     *
     * Another version. Slightly different from Solution 1. All pointers start from prev, which avoids doing many
     * null checks.
     */
    class Solution2 {
        public ListNode swapPairs(ListNode head) {
            ListNode sentinel = new ListNode(0), prev = sentinel;
            sentinel.next = head;
            ListNode tail = prev; // start from prev
            while (true) {
                for (int i = 0; i < 2; i++) {
                    tail = tail.next;
                    if (tail == null) return sentinel.next;
                }
                head = prev.next;
                // swap nodes
                head.next = tail.next;
                tail.next = head;
                prev.next = tail;
                // update all pointers
                prev = head;
                tail = prev;
            }
            // no return statement here, otherwise compiling error
        }
    }
}
