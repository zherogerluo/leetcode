/**
 * LeetCode #430, medium
 *
 * You are given a doubly linked list which in addition to the next and previous pointers, it could have a child
 * pointer, which may or may not point to a separate doubly linked list. These child lists may have one or more children
 * of their own, and so on, to produce a multilevel data structure, as shown in the example below.
 *
 * Flatten the list so that all the nodes appear in a single-level, doubly linked list. You are given the head of the
 * first level of the list.
 *
 * Example 1:
 *
 * Input: head = [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
 * Output: [1,2,3,7,8,11,12,9,10,4,5,6]
 * Explanation:
 * The multilevel linked list in the input is as follows:
 *
 * After flattening the multilevel linked list it becomes:
 *
 * Example 2:
 *
 * Input: head = [1,2,null,3]
 * Output: [1,3,2]
 * Explanation:
 * The input multilevel linked list is as follows:
 *
 *   1---2---NULL
 *   |
 *   3---NULL
 * Example 3:
 *
 * Input: head = []
 * Output: []
 *
 * How multilevel linked list is represented in test case:
 *
 * We use the multilevel linked list from Example 1 above:
 *
 *  1---2---3---4---5---6--NULL
 *          |
 *          7---8---9---10--NULL
 *              |
 *              11--12--NULL
 * The serialization of each level is as follows:
 *
 * [1,2,3,4,5,6,null]
 * [7,8,9,10,null]
 * [11,12,null]
 * To serialize all levels together we will add nulls in each level to signify no node connects to the upper node of the
 * previous level. The serialization becomes:
 *
 * [1,2,3,4,5,6,null]
 * [null,null,7,8,9,10,null]
 * [null,11,12,null]
 * Merging the serialization of each level and removing trailing nulls we obtain:
 *
 * [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
 *
 * Constraints:
 *
 * The number of Nodes will not exceed 1000.
 * 1 <= Node.val <= 10^5
 */

package LinkedList;

import java.util.*;

public class FlattenAMultilevelDoublyLinkedList {
    /**
     * Solution 1: Recursive
     *
     * DFS approach, note the helper needs to return the tail so that it can connect with the next node.
     */
    class Solution1 {
        public Node flatten(Node head) {
            if (head == null) return null;
            helper(head);
            return head;
        }

        // flatten and return the tail, head is not null
        private Node helper(Node head) {
            while (head.next != null && head.child == null) {
                head = head.next;
            }
            if (head.child == null) {
                // then head.next must be null
                return head;
            }
            // have a valid child, head.next can still be null
            Node tail = helper(head.child);
            Node old_next = head.next;
            tail.next = old_next;
            if (old_next != null) old_next.prev = tail;
            head.next = head.child;
            head.child.prev = head;
            head.child = null;
            return old_next == null ? tail : helper(old_next);
        }
    }

    /**
     * Solution 2: Iterative
     *
     * Use stack to push next when encountering child. Keep track of "prev", the tail of flattened list which serves as
     * previous node of the next node in the stack. Note the usage of sentinel, makes code clean and concise.
     */
    class Solution2 {
        public Node flatten(Node head) {
            if (head == null) return head;
            Node sentinel = new Node(), prev = sentinel;
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                prev.next = head;
                head.prev = prev;
                while (head != null) {
                    if (head.child != null) {
                        if (head.next != null) {
                            stack.push(head.next);
                        }
                        head.next = head.child;
                        head.child.prev = head;
                        Node child = head.child;
                        head.child = null;
                        head = child;
                    } else {
                        if (head.next == null) {
                            prev = head;
                        }
                        head = head.next;
                    }
                }
            }
            // nullify head.prev to make it a valid doubly-linked list
            sentinel.next.prev = null;
            return sentinel.next;
        }
    }
}
