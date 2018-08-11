/**
 * LeetCode #328, medium
 *
 * Given a singly linked list, group all odd nodes together followed by the even nodes. Please note here we are
 * talking about the node number and not the value in the nodes.

 You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time complexity.

 Example 1:

 Input: 1->2->3->4->5->NULL
 Output: 1->3->5->2->4->NULL
 Example 2:

 Input: 2->1->3->5->6->4->7->NULL
 Output: 2->3->6->7->1->5->4->NULL
 Note:

 The relative order inside both the even and odd groups should remain as it was in the input.
 The first node is considered odd, the second node even and so on ...
 */

package LinkedList;

public class OddEvenLinkedList {
    /**
     * Solution 1: Linked list removal and insertion
     *
     * For every odd node, remove it and insert it to the end of odd sublist.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution1 {
        public ListNode oddEvenList(ListNode head) {
            if (head == null) return null;
            ListNode odd = head, even = head.next;
            while (even != null) {
                insertAfter(odd, removeNext(even));
                odd = odd.next;
                even = even.next;
            }
            return head;
        }

        private ListNode removeNext(ListNode prev) {
            ListNode toRemove = prev.next;
            if (toRemove == null) return null;
            prev.next = toRemove.next;
            toRemove.next = null;
            return toRemove;
        }

        private void insertAfter(ListNode prev, ListNode toInsert) {
            if (toInsert == null) return;
            ListNode next = prev.next;
            prev.next = toInsert;
            toInsert.next = next;
        }
    }
}
