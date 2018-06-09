/**
 * LeetCode #23, hard
 *
 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

 Example:

 Input:
 [
 1->4->5,
 1->3->4,
 2->6
 ]
 Output: 1->1->2->3->4->4->5->6
 */

package LinkedList;

import java.util.*;

public class MergeKSortedLists {
    /**
     * Definition for singly-linked list.
     */
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    /**
     * Solution 1: Priority queue (heap)
     *
     * Keep a min heap that stores the head nodes for all lists. Each time poll the small list one and add to the
     * result list.
     *
     * Time complexity: O(n * log(k)). Space complexity: O(k)
     */
    class Solution1 {
        public ListNode mergeKLists(ListNode[] lists) {
            ListNode sentinel = new ListNode(0), prev = sentinel;
            PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
            for (ListNode node : lists) {
                if (node != null) pq.add(node);
            }
            while (!pq.isEmpty()) {
                ListNode cur = pq.poll();
                prev.next = cur;
                if (cur.next != null) pq.offer(cur.next);
                prev = prev.next;
            }
            return sentinel.next;
        }
    }

    /**
     * Solution 2: Divide and conquer
     *
     * Similar to merge sort. Divide array in halves recursively, and merge them from bottom up.
     *
     * Time complexity: O(n * log(k)). Space complexity: O(log(k))
     */
    class Solution2 {
        public ListNode mergeKLists(ListNode[] lists) {
            if (lists == null || lists.length == 0) return null;
            return merge(lists, 0, lists.length-1);
        }

        private ListNode merge(ListNode[] lists, int l, int r) {
            if (l == r) return lists[l];
            int mid = (l + r) / 2;
            ListNode list1 = merge(lists, l, mid);
            ListNode list2 = merge(lists, mid+1, r);
            ListNode sentinel = new ListNode(0), prev = sentinel;
            while (list1 != null && list2 != null) {
                if (list1.val < list2.val) {
                    prev.next = list1;
                    list1 = list1.next;
                } else {
                    prev.next = list2;
                    list2 = list2.next;
                }
                prev = prev.next; // don't forget this line
            }
            if (list1 != null) prev.next = list1;
            if (list2 != null) prev.next = list2;
            return sentinel.next;
        }
    }
}
