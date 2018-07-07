/**
 * LeetCode #19, medium
 *
 * Given a linked list, remove the n-th node from the end of list and return its head.

 Example:

 Given linked list: 1->2->3->4->5, and n = 2.

 After removing the second node from the end, the linked list becomes 1->2->3->5.
 Note:

 Given n will always be valid.

 Follow up:

 Could you do this in one pass?
 */

package LinkedList;

public class RemoveNthNodeFromEndOfList {
    /**
     * Solution 1: Two pointers
     *
     * Keep a fast and slow pointers which has a distance of n. Remove the slow's next node.
     *
     * Trick part: Use a sentinel node to deal with deleting head. Otherwise, need to explicitly consider the case
     * that distance is n-1.
     *
     * Time complexity: O(n). Space complexity: O(1)
     */
    class Solution1 {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode sentinel = new ListNode(0), prev = sentinel;
            sentinel.next = head;
            while (head != null) {
                head = head.next;
                if (n-- > 0) continue;
                else prev = prev.next;
            }
            if (n <= 0) prev.next = prev.next.next;
            return sentinel.next;
        }
    }
}
