/**
 * LeetCode #25, hard
 *
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.

 k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.

 Example:

 Given this linked list: 1->2->3->4->5

 For k = 2, you should return: 2->1->4->3->5

 For k = 3, you should return: 3->2->1->4->5

 Note:

 Only constant extra memory is allowed.
 You may not alter the values in the list's nodes, only nodes itself may be changed.
 */

package LinkedList;

public class ReverseNodesInKGroup {
    /**
     * Definition for singly-linked list.
     */
        public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    /**
     * Solution 1: Loop, reverse segments one by one
     *
     * Use a helper function that reverse k nodes after the given prev node, and returns the new prev node for next
     * loop. The problem can then be broken down to n/k sub-problems, each of which is reverse just k nodes.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution1 {
        public ListNode reverseKGroup(ListNode head, int k) {
            ListNode sentinel = new ListNode(0), prev = sentinel;
            sentinel.next = head;
            while (prev != null) {
                prev = reverse(prev, k);
            }
            return sentinel.next;
        }

        /* Reverse k nodes after the given prev node, return the tail node (as next prev) */
        private ListNode reverse(ListNode prev, int k) {
            if (prev == null) return null;
            ListNode tail = prev;
            for (int i = 0; i < k; i++) {
                tail = tail.next;
                if (tail == null) return null;
            }
            ListNode cur = prev.next, newTail = cur; // new tail is the first node after prev
            while (cur != tail) {
                // put current node behind tail
                prev.next = cur.next;
                cur.next = tail.next;
                tail.next = cur;
                // move current node to next
                cur = prev.next;
            }
            return newTail; // the old tail is no longer the tail after reversion
        }
    }

    /**
     * Solution 2: Recursion
     *
     * Similar to Solution 1, reverse k nodes at a time. Recursive solution takes O(n/k) time so it does not meet the
     * requirement of the problem.
     *
     * Time complexity: O(n). Space complexity: O(n/k).
     */
    class Solution2 {
        public ListNode reverseKGroup(ListNode head, int k) {
            if (head == null) return null;
            ListNode tail = head;
            // find the valid tail
            for (int i = 0; i < k-1; i++) {
                tail = tail.next;
                if (tail == null) return head;
            }
            ListNode cur = head, next;
            while (cur != tail) {
                // put current node after the tail
                next = cur.next;
                cur.next = tail.next;
                tail.next = cur;
                cur = next;
            }
            // now head is the new tail after reversion
            head.next = reverseKGroup(head.next, k);
            return tail; // tail becomes the new head after reversion
        }
    }
}
