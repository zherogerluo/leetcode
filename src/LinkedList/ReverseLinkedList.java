/**
 * LeetCode #206, easy
 *
 * Reverse a singly linked list.

 Example:

 Input: 1->2->3->4->5->NULL
 Output: 5->4->3->2->1->NULL
 Follow up:

 A linked list can be reversed either iteratively or recursively. Could you implement both?
 */

package LinkedList;

public class ReverseLinkedList {
    /**
     * Solution 1: Iterative
     *
     * Reverse from front, and insert the reversed front sublist after head.
     *
     * Before:                                       After:
     * head -> ... -> tail -> newHead -> next ...    newHead -> head -> ... -> tail -> next -> ...
     */
    class Solution1 {
        public ListNode reverseList(ListNode head) {
            if (head == null) return null;
            ListNode tail = head, newHead = head.next;
            while (newHead != null) {
                tail.next = newHead.next;
                newHead.next = head;
                head = newHead;
                newHead = tail.next;
            }
            return head;
        }
    }

    /**
     * Solution 2: Recursive
     *
     * Recursively reverse the list starting next to head, and append head to the end of list. To do this we need to
     * also keep track of the node before current tail (subtail).
     */
    class Solution2 {
        public ListNode reverseList(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode tail = head, subtail = head.next;
            ListNode newHead = reverseList(head.next);
            subtail.next = tail;
            tail.next = null; // don't forget to put and end after the tail
            return newHead;
        }
    }

    /**
     * Solution 3: Iterative
     *
     * Start another new linked list, put head of original list to the head of new list one by one. In the end, the
     * new list will be a reversed original list.
     */
    class Solution3 {
        public ListNode reverseList(ListNode head) {
            ListNode newHead = null;
            while (head != null) {
                ListNode next = head.next;
                head.next = newHead;
                newHead = head;
                head = next;
            }
            return newHead;
        }
    }
}
