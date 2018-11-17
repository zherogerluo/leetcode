/**
 * LeetCode #21, easy
 *
 * Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the
 * nodes of the first two lists.
 *
 * Example:
 *
 * Input: 1->2->4, 1->3->4
 * Output: 1->1->2->3->4->4
 */

package LinkedList;

public class MergeTwoSortedLists {
    /**
     * Solution 1:
     *
     * Typical merge, trivial.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    class Solution1 {
        public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
//            if (l1 == null) return l2;
//            if (l2 == null) return l1;
            // The above two corner cases are already taken care of by code below
            ListNode sentinel = new ListNode(0), prev = sentinel;
            while (l1 != null && l2 != null) {
                if (l1.val < l2.val) {
                    prev.next = l1;
                    l1 = l1.next;
                } else {
                    prev.next = l2;
                    l2 = l2.next;
                }
                prev = prev.next;
            }
            if (l1 != null) prev.next = l1;
            else prev.next = l2;
            return sentinel.next;
        }
    }
}
