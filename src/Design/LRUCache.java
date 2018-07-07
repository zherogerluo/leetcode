/**
 * LeetCode #146, hard
 *
 * Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put.

 get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.

 Follow up:
 Could you do both operations in O(1) time complexity?

 Example:

 LRUCache cache = new LRUCache( 2 /* capacity *\/ );

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // returns 1
        cache.put(3, 3);    // evicts key 2
        cache.get(2);       // returns -1 (not found)
        cache.put(4, 4);    // evicts key 1
        cache.get(1);       // returns -1 (not found)
        cache.get(3);       // returns 3
        cache.get(4);       // returns 4
 */

package Design;

import java.util.*;

public class LRUCache {
    /**
     * Solution 1: Doubly-linked list and hash map
     *
     * We use DLL for O(1) node removal and insertion at head, and hash map for O(1) retrieval. A very useful trick
     * is to use circular DLL so that we don't have to deal with null nodes.
     */
    class LRUCache1 {
        class Node {
            int key, val; // need to store key in node so that it is easier to remove node from hash map
            Node prev, next;

            Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        Node sentinel; // circular doubly-linked list sentinel node
        Map<Integer, Node> map;
        int capacity, size;

        public LRUCache1(int capacity) {
            map = new HashMap<>();
            this.capacity = capacity;
            size = 0;
            sentinel = new Node(0, 0);
            sentinel.next = sentinel;
            sentinel.prev = sentinel;
        }

        public int get(int key) {
            if (map.containsKey(key)) {
                Node cur = map.get(key);
                promote(cur);
                return cur.val;
            }
            return -1;
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                Node cur = map.get(key);
                cur.val = value;
                promote(cur);
            } else {
                // evict node if size too large
                if (size == capacity) {
                    map.remove(remove(sentinel.prev).key);
                    size--;
                }
                // insert node
                Node node = new Node(key, value);
                map.put(key, node);
                insertAsHead(node);
                size++;
            }
        }

        private void insertAsHead(Node node) {
            Node head = sentinel.next;
            head.prev = node;
            node.next = head;
            node.prev = sentinel;
            sentinel.next = node;
        }

        private Node remove(Node node) {
            Node next = node.next, prev = node.prev;
            prev.next = next;
            next.prev = prev;
            return node;
        }

        private void promote(Node node) {
            insertAsHead(remove(node));
        }
    }

    /**
     * Solution 2: LinkedHashMap
     *
     * This is more like cheating using Java standard library. LinkedHashMap is implemented exactly with a linked
     * list and hash map. Tricky part is to promote node when calling get and put. It turns out that we need to
     * remove the node and re-insert it, so that it appears as the last inserted. This adds additional overhead, but
     * code is very concise.
     */
    class LRUCache2 {
        LinkedHashMap<Integer, Integer> map;
        int capacity;

        public LRUCache2(int capacity) {
            map = new LinkedHashMap<>();
            this.capacity = capacity;
        }

        public int get(int key) {
            if (map.containsKey(key)) {
                int val = map.remove(key);
                map.put(key, val);
                return val;
            }
            return -1;
        }

        public void put(int key, int value) {
            if (!map.containsKey(key)) {
                if (map.size() == capacity) map.remove(map.keySet().iterator().next());
            } else map.remove(key);
            map.put(key, value);
        }
    }
}
