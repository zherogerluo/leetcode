/**
 * LeetCode #160, easy
 *
 * Write a program to find the node at which the intersection of two singly linked lists begins.
 *
 * Example 1:
 *
 * Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
 * Output: Reference of the node with value = 8
 * Input Explanation: The intersected node's value is 8 (note that this must not be 0 if the two lists intersect). From
 * the head of A, it reads as [4,1,8,4,5]. From the head of B, it reads as [5,6,1,8,4,5]. There are 2 nodes before the
 * intersected node in A; There are 3 nodes before the intersected node in B.
 *
 * Example 2:
 *
 * Input: intersectVal = 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
 * Output: Reference of the node with value = 2
 * Input Explanation: The intersected node's value is 2 (note that this must not be 0 if the two lists intersect). From
 * the head of A, it reads as [1,9,1,2,4]. From the head of B, it reads as [3,2,4]. There are 3 nodes before the
 * intersected node in A; There are 1 node before the intersected node in B.
 *
 * Example 3:
 *
 * Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
 * Output: null
 * Input Explanation: From the head of A, it reads as [2,6,4]. From the head of B, it reads as [1,5]. Since the two
 * lists do not intersect, intersectVal must be 0, while skipA and skipB can be arbitrary values.
 * Explanation: The two lists do not intersect, so return null.
 *
 * Notes:
 *
 * If the two linked lists have no intersection at all, return null.
 * The linked lists must retain their original structure after the function returns.
 * You may assume there are no cycles anywhere in the entire linked structure.
 * Each value on each linked list is in the range [1, 10^9].
 * Your code should preferably run in O(n) time and use only O(1) memory.
 */

package LinkedList;

import java.util.*;

public class IntersectionOfTwoLinkedLists {
    /**
     * Solution 1: Hash map
     *
     * Use hash map to remember visited nodes of list A, then iterate through list B until we find a match.
     *
     * Time complexity: O(m+n). Space complexity: O(m).
     */
    public class Solution1 {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            Set<ListNode> set = new HashSet<>();
            while (headA != null) {
                set.add(headA);
                headA = headA.next;
            }
            while (headB != null) {
                if (set.contains(headB)) {
                    return headB;
                }
                headB = headB.next;
            }
            return null;
        }
    }

    /**
     * Solution 2: Two pointers, fast and slow
     *
     * Connect head and tail of one of the lists, this problem reduces to finding the entry node of a list with a
     * circle. Beware of the case where two lists are not intersecting at all.
     *
     * Time complexity: O(m+n). Space complexity: O(1).
     */
    public class Solution2 {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            if (headA == null || headB == null) {
                return null;
            }
            ListNode fast = headB, slow = headB;
            do {
                slow = slow.next == null ? headA : slow.next;
                fast = fast.next == null ? headA : fast.next;
                fast = fast.next == null ? headA : fast.next;
            } while (fast != slow);
            fast = headB;
            while (true) {
                if (fast == null || fast == slow) {
                    break;
                }
                slow = slow.next == null ? headA : slow.next;
                fast = fast.next;
            }
            return fast;
        }
    }

    /**
     * Solution 3: Two pointers, rotating
     *
     * Start from head of each list, when reaching end, switch to head of the other list. It is guaranteed that the two
     * pointers are going to meet at intersection in the second pass. Subtle and non-trivial to come up with.
     *
     * Time complexity: O(m+n). Space complexity: O(1).
     */
    public class Solution3 {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            ListNode a = headA, b = headB;
            while (true) {
                if (a == b) {
                    break;
                }
                a = a == null ? headB : a.next;
                b = b == null ? headA : b.next;
            }
            return a;
        }
    }
}