/**
 * LeetCode #148, medium
 *
 * Sort a linked list in O(n log n) time using constant space complexity.

 Example 1:

 Input: 4->2->1->3
 Output: 1->2->3->4
 Example 2:

 Input: -1->5->3->4->0
 Output: -1->0->3->4->5
 */

package LinkedList;

public class SortList {
    /**
     * Solution 1: Merge sort
     *
     * Typical merge sort. Tricky part is when deciding two list heads, use slow and fast pointers, but initialize
     * fast pointer as head.next so that the list can be broken down to two equal length pieces. Alternatively, we
     * can write a helper sorting function that takes length as input, and directly forward to length/2 node as
     * second head.
     *
     * Time complexity: O(n * log(n)). Space complexity: O(log(n)).
     */
    class Solution1 {
        public ListNode sortList(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode slow = head, fast = head.next; // initialize fast as next node to slow
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            ListNode head2 = slow.next;
            slow.next = null; // break into two lists
            head = sortList(head);
            head2 = sortList(head2);
            return merge(head, head2);
        }

        private ListNode merge(ListNode head1, ListNode head2) {
            ListNode sentinel = new ListNode(0), prev = sentinel;
            while (head1 != null && head2 != null) {
                if (head1.val <= head2.val) {
                    prev.next = head1;
                    head1 = head1.next;
                } else {
                    prev.next = head2;
                    head2 = head2.next;
                }
                prev = prev.next;
            }
            if (head1 == null) prev.next = head2;
            else prev.next = head1;
            return sentinel.next;
        }
    }
}
