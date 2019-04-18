/**
 * LeetCode #61, medium
 *
 * Given a linked list, rotate the list to the right by k places, where k is non-negative.
 *
 * Example 1:
 *
 * Input: 1->2->3->4->5->NULL, k = 2
 * Output: 4->5->1->2->3->NULL
 * Explanation:
 * rotate 1 steps to the right: 5->1->2->3->4->NULL
 * rotate 2 steps to the right: 4->5->1->2->3->NULL
 *
 * Example 2:
 *
 * Input: 0->1->2->NULL, k = 4
 * Output: 2->0->1->NULL
 * Explanation:
 * rotate 1 steps to the right: 2->0->1->NULL
 * rotate 2 steps to the right: 1->2->0->NULL
 * rotate 3 steps to the right: 0->1->2->NULL
 * rotate 4 steps to the right: 2->0->1->NULL
 */

package LinkedList;

public class RotateList {
    /**
     * Solution 1: Two pointers
     *
     * Use two pointers to find the tail node and the kth node before it, which is the new head. Then simply
     * concatenate to get new list. Note that we need to optimize for large k by counting list length and take mod.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution1 {
        public ListNode rotateRight(ListNode head, int k) {
            if (head == null) return null;
            // optimize for very large k
            ListNode node = head;
            int n = 0;
            while (node != null) {
                n++;
                node = node.next;
            }
            k = k % n;
            // two pointers with k distance
            ListNode tail = head;
            for (int i = 0; i < k; i++) {
                tail = tail.next;
                if (tail == null) tail = head; // guarantees tail != null
            }
            // find new head
            ListNode sentinel = head;
            while (tail.next != null) {
                sentinel = sentinel.next; tail = tail.next;
            }
            ListNode newHead = sentinel.next == null ? head : sentinel.next;
            // modify list
            tail.next = head; // this line must come before next line for k = 0 to be correct
            sentinel.next = null;
            return newHead;
        }
    }

    /**
     * Solution 2: Circular list
     *
     * Variant of Solution 1. When approached tail node, simply make the list circular. The tail node then becomes
     * sentinel node for old list. March from there by n-k node and we get the new sentinel node.
     */
    class Solution2 {
        public ListNode rotateRight(ListNode head, int k) {
            if (head == null) return null;
            // optimize for very large k
            ListNode node = head;
            int n = 1;
            while (node.next != null) {
                n++;
                node = node.next;
            }
            k = k % n;
            if (k == 0) return head;
            node.next = head; // make it circular
            for (int i = 0; i < n-k; i++) node = node.next;
            head = node.next;
            node.next = null;
            return head;
        }
    }
}
