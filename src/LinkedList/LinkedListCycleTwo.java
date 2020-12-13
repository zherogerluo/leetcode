/**
 * LeetCode #142, medium
 *
 * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
 *
 * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously
 * following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is
 * connected to. Note that pos is not passed as a parameter.
 *
 * Notice that you should not modify the linked list.
 *
 * Example 1:
 *
 * Input: head = [3,2,0,-4], pos = 1
 * Output: tail connects to node index 1
 * Explanation: There is a cycle in the linked list, where tail connects to the second node.
 *
 * Example 2:
 *
 * Input: head = [1,2], pos = 0
 * Output: tail connects to node index 0
 * Explanation: There is a cycle in the linked list, where tail connects to the first node.
 *
 * Example 3:
 *
 * Input: head = [1], pos = -1
 * Output: no cycle
 * Explanation: There is no cycle in the linked list.
 *
 *
 * Constraints:
 *
 * The number of the nodes in the list is in the range [0, 104].
 * -105 <= Node.val <= 105
 * pos is -1 or a valid index in the linked-list.
 *
 * Follow up: Can you solve it using O(1) (i.e. constant) memory?
 */

package LinkedList;

public class LinkedListCycleTwo {
    /**
     * Solution 1: Two pointers
     *
     * Classic two pointers to find linked list cycle: Slow and fast that moves 2x as slow. When they meet, they
     * traveled x + y and 2x + 2y where x is the length between head and cycle entry, and y is the length between cycle
     * entry and the current position. It is easy to prove that both pointers are x away from the entry point.
     * Then reset one at head, move two at same 1x speed until they meet, the meeting node is the cycle entry.
     *
     * Time complexity: O(n). Space complexity: O(1).
     */
    public class Solution1 {
        public ListNode detectCycle(ListNode head) {
            ListNode slow = head, fast = head;
            while (true) {
                if (slow == null || fast == null) {
                    return null;
                }
                slow = slow.next;
                fast = fast.next;
                if (fast == null) {
                    return null;
                }
                fast = fast.next;
                if (slow == fast) {
                    break;
                }
            }
            fast = head;
            while (true) {
                if (slow == fast) {
                    break;
                }
                slow = slow.next;
                fast = fast.next;
            }
            return slow;
        }
    }
}