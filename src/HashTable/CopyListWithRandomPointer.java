/**
 * LeetCode #138, medium
 *
 * A linked list is given such that each node contains an additional random pointer which could point to any node in
 * the list or null.

 Return a deep copy of the list.
 */

package HashTable;

import java.util.*;

public class CopyListWithRandomPointer {
    /**
     * Definition for singly-linked list with a random pointer.
     */
    class RandomListNode {
        int label;
        RandomListNode next, random;
        RandomListNode(int x) { this.label = x; }
    }

    /**
     * Solution 1: Hash map
     *
     * Copy the linked list normally, and point the random pointer to the old random node. While copying, use a hash
     * map to store the 1-1 map between old and copied new nodes. Then in a second pass through the copied linked
     * list, update the random pointer using the hash map so that it points to the copied new node corresponding to
     * the old random node.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution1 {
        public RandomListNode copyRandomList(RandomListNode head) {
            RandomListNode sentinel = new RandomListNode(0), cur = sentinel;
            Map<RandomListNode, RandomListNode> map = new HashMap<>();
            // copy the linked list
            while (head != null) {
                cur.next = new RandomListNode(head.label);
                cur = cur.next;
                cur.random = head.random; // same random pointer
                map.put(head, cur); // store old -> new mapping
                head = head.next;
            }
            cur = sentinel.next;
            // update the random pointers in the copied new linked list
            while (cur != null) {
                cur.random = map.get(cur.random);
                cur = cur.next;
            }
            return sentinel.next;
        }
    }
}
