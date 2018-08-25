/**
 * LeetCode #460, hard
 *
 * Design and implement a data structure for Least Frequently Used (LFU) cache. It should support the following
 * operations: get and put.
 *
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 * put(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity,
 * it should invalidate the least frequently used item before inserting a new item. For the purpose of this problem,
 * when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be
 * evicted.
 *
 * Follow up:
 * Could you do both operations in O(1) time complexity?
 *
 * Example:
 *
 LFUCache cache = new LFUCache( 2 (capacity) );

 cache.put(1, 1);
 cache.put(2, 2);
 cache.get(1);       // returns 1
 cache.put(3, 3);    // evicts key 2
 cache.get(2);       // returns -1 (not found)
 cache.get(3);       // returns 3.
 cache.put(4, 4);    // evicts key 1.
 cache.get(1);       // returns -1 (not found)
 cache.get(3);       // returns 3
 cache.get(4);       // returns 4
 */

package Design;

import java.util.*;

public class LFUCache {
    /**
     * Solution 1: Hash map + doubly linked list
     *
     * Use a hash map to store key-value data, and a single doubly linked list to store all nodes with ascending
     * frequency, and another hash map to keep track of the tail node of a certain frequency. Once a node is touched,
     * promote the node to next frequency, and doubly linked list guarantees O(1) time for this operation.
     *
     * There are many tricky parts in the code that can cause all kinds of different bugs. Not recommended. Instead,
     * we can maintain a count-DLList map (multiple doubly linked lists) to simplify the implementation and reduce the
     * risk of writing bugs. See Solution 2.
     *
     * Time complexity: O(1). Space complexity: O(n).
     */
    class LFUCache1 {
        /* Doubly linked list node */
        class Node {
            int key, val, freq;
            Node prev, next;

            Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        Map<Integer, Node> data; // key-value pair
        Map<Integer, Node> tails; // tail of sublist whose freq equals key
        Node sentinel; // sentinel of doubly linked list, circular
        int capacity, size;

        public LFUCache1(int capacity) {
            this.capacity = capacity;
            data = new HashMap<>();
            tails = new HashMap<>();
            sentinel = new Node(0, 0);
            // create circular structure
            sentinel.prev = sentinel;
            sentinel.next = sentinel;
        }

        public int get(int key) {
            Node node = data.getOrDefault(key, null);
            if (node == null) return -1;
            // promote node to next freq, or the end of list if no higher freq
            promote(node);
            // System.out.print("get(" + key + "): ");
            // print();
            return node.val;
        }

        public void put(int key, int value) {
            if (capacity == 0) return; // corner case
            Node node = null;
            if (data.containsKey(key)) {
                node = data.get(key);
                node.val = value; // don't forget to update value here!
            } else {
                size++; // increment size only when it's not in data
                if (size > capacity) {
                    size--;
                    // evict the first node in the doubly linked list
                    Node toRemove = sentinel.next;
                    remove(toRemove);
                    data.remove(toRemove.key);
                }
                node = new Node(key, value);
                data.put(key, node);
                insertAfter(sentinel, node); // put the node in
            }
            promote(node); // then promote
            // System.out.print("put(" + key + ", " + value + "): ");
            // print();
        }

        /* promote existing node to next freq */
        private void promote(Node node) {
            Node prev = remove(node);
            // look for places to put it, could be next freq, this freq, or its old place
            Node insertPoint = tails.getOrDefault(node.freq + 1, tails.getOrDefault(node.freq, prev));
            insertAfter(insertPoint, node);
            node.freq++;
            tails.put(node.freq, node); // don't forget to update the tail node
        }

        /* Insert the node toInsert after Node prev */
        private void insertAfter(Node prev, Node toInsert) {
            toInsert.prev = prev;
            toInsert.next = prev.next;
            prev.next.prev = toInsert;
            prev.next = toInsert;
        }

        /* Remove the node and return its previous node */
        private Node remove(Node toRemove) {
            // next block is tricky and necessary
            if (tails.get(toRemove.freq) == toRemove) {
                // this is the tail of this freq
                if (toRemove.prev.freq == toRemove.freq) {
                    // there is other node with the same freq
                    tails.put(toRemove.freq, toRemove.prev);
                } else {
                    // this is the only node with this freq
                    tails.remove(toRemove.freq);
                }
            }
            Node prev = toRemove.prev;
            toRemove.prev.next = toRemove.next;
            toRemove.next.prev = toRemove.prev;
            toRemove.prev = null;
            toRemove.next = null;
            return prev;
        }

        /* print the doubly linked list to help with endless debugging */
        private void print() {
            Node head = sentinel.next;
            while (head != sentinel) {
                System.out.print(head.key + "f" + head.freq + " -> ");
                head = head.next;
            }
            System.out.println();
        }
    }

    /**
     * Solution 2:
     *
     * Use multiple lists rather than one, slightly easier than Solution 1. Tricky part is to update minFreq, if
     * putting down a new node, minFreq is always 1.
     */
    class LFUCache2 {
        /* Doubly linked list node */
        class Node {
            int key, val, freq;
            Node prev, next;

            Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        Map<Integer, Node> data; // key-value pair
        Map<Integer, Node> lists; // sentinel nodes of lists with given freq
        int capacity, size, minFreq;

        public LFUCache2(int capacity) {
            this.capacity = capacity;
            data = new HashMap<>();
            lists = new HashMap<>();
        }

        public int get(int key) {
            Node node = data.getOrDefault(key, null);
            if (node == null) return -1;
            // promote node to next freq, or the end of list if no higher freq
            promote(node);
            return node.val;
        }

        public void put(int key, int value) {
            if (capacity == 0) return;
            Node node = null;
            if (data.containsKey(key)) {
                node = data.get(key);
                node.val = value;
            } else {
                if (size == capacity) {
                    // evict the first node in the doubly linked list
                    Node toRemove = lists.get(minFreq).next;
                    remove(toRemove);
                    data.remove(toRemove.key);
                } else size++;
                node = new Node(key, value);
                data.put(key, node);
                minFreq = 1;
            }
            promote(node);
        }

        /* promote node to next freq */
        private void promote(Node node) {
            node = remove(node);
            if (node.freq == minFreq && !lists.containsKey(node.freq)) minFreq++;
            node.freq++;
            Node sentinel = lists.getOrDefault(node.freq, null);
            if (sentinel == null) {
                sentinel = new Node(0, 0);
                sentinel.next = sentinel;
                sentinel.prev = sentinel;
                lists.put(node.freq, sentinel);
            }
            insertAfter(sentinel.prev, node);
        }

        /* Insert the node toInsert after Node prev */
        private void insertAfter(Node prev, Node toInsert) {
            toInsert.prev = prev;
            toInsert.next = prev.next;
            prev.next.prev = toInsert;
            prev.next = toInsert;
        }

        /* Remove the node and return it self */
        private Node remove(Node toRemove) {
            if (toRemove.prev == null || toRemove.next == null) return toRemove;
            toRemove.prev.next = toRemove.next;
            toRemove.next.prev = toRemove.prev;
            toRemove.prev = null;
            toRemove.next = null;
            Node sentinel = lists.get(toRemove.freq);
            if (sentinel.next == sentinel) lists.remove(toRemove.freq);
            return toRemove;
        }
    }

    /**
     * Solution 3:
     *
     * Wrapped linked list operations in a separate class, more elegant implementation.
     */
    class LFUCache3 {
        /* Doubly linked list node */
        class Node {
            int key, val, freq;
            Node prev, next;

            Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        class DLList {
            Node sentinel;
            int size;

            DLList() {
                sentinel = new Node(0, 0);
                sentinel.prev = sentinel;
                sentinel.next = sentinel;
            }

            /* Insert the node toInsert to last position */
            void insertLast(Node toInsert) {
                toInsert.prev = sentinel.prev;
                toInsert.next = sentinel;
                sentinel.prev.next = toInsert;
                sentinel.prev = toInsert;
                size++;
            }

            /* Remove the node and return it self */
            Node remove(Node toRemove) {
                if (toRemove.prev == null || toRemove.next == null) return toRemove;
                toRemove.prev.next = toRemove.next;
                toRemove.next.prev = toRemove.prev;
                toRemove.prev = null;
                toRemove.next = null;
                size--;
                return toRemove;
            }

            /* Remove the first node and return it self */
            Node removeFirst() {
                if (size == 0) return null;
                return remove(sentinel.next);
            }

            int size() {
                return size;
            }
        }

        Map<Integer, Node> data; // key-value pair
        Map<Integer, DLList> lists; // freq-list map
        int capacity, size, minFreq;

        public LFUCache3(int capacity) {
            this.capacity = capacity;
            data = new HashMap<>();
            lists = new HashMap<>();
        }

        public int get(int key) {
            Node node = data.getOrDefault(key, null);
            if (node == null) return -1;
            // promote node to next freq, or the end of list if no higher freq
            promote(node);
            return node.val;
        }

        public void put(int key, int value) {
            if (capacity == 0) return;
            Node node = null;
            if (data.containsKey(key)) {
                node = data.get(key);
                node.val = value;
            } else {
                if (size == capacity) {
                    // evict the first node in the doubly linked list
                    DLList list = lists.get(minFreq);
                    data.remove(list.removeFirst().key);
                    if (list.size() == 0) lists.remove(minFreq);
                } else size++;
                node = new Node(key, value);
                data.put(key, node);
                minFreq = 0; // note the initialization here, it will be later increment to 1 in promote(node)
            }
            promote(node);
        }

        /* promote node to next freq */
        private void promote(Node node) {
            DLList list = lists.get(node.freq);
            if (list != null) {
                list.remove(node);
                if (list.size() == 0) lists.remove(node.freq);
            }
            if (!lists.containsKey(minFreq)) minFreq++;
            node.freq++;
            lists.putIfAbsent(node.freq, new DLList());
            lists.get(node.freq).insertLast(node);
        }
    }

    /**
     * Solution 4: Hash map and linked hash set
     *
     * Same idea, implemented using Java LinkedHashSet. Basically replace doubly linked list with linked hash set,
     * similar to the solution in LRU cache problem.
     */
    class LFUCache4 {
        Map<Integer, Integer> data; // key-value pair
        Map<Integer, Integer> freqs; // key-freq pair
        Map<Integer, Set<Integer>> linkedSets; // freq-linked hash sets
        int capacity, size, minFreq;

        public LFUCache4(int capacity) {
            this.capacity = capacity;
            data = new HashMap<>();
            freqs = new HashMap<>();
            linkedSets = new HashMap<>();
        }

        public int get(int key) {
            if (!data.containsKey(key)) return -1;
            promote(key);
            return data.get(key);
        }

        public void put(int key, int value) {
            if (capacity == 0) return;
            if (!data.containsKey(key)) {
                if (size == capacity) {
                    // evict the first node in the linked set
                    Set<Integer> set = linkedSets.get(minFreq);
                    Integer keyToRemove = set.iterator().next(); // least recently used
                    set.remove(keyToRemove);
                    freqs.remove(keyToRemove); // don't forget this line!
                    data.remove(keyToRemove);
                    if (set.size() == 0) linkedSets.remove(minFreq);
                } else size++;
                minFreq = 0;
            }
            data.put(key, value);
            promote(key);
        }

        /* promote key to next freq */
        private void promote(int key) {
            int freq = freqs.getOrDefault(key, 0);
            Set<Integer> set = linkedSets.get(freq);
            if (set != null) {
                set.remove(key);
                if (set.size() == 0) linkedSets.remove(freq);
            }
            if (!linkedSets.containsKey(minFreq)) minFreq++;
            freq++;
            linkedSets.putIfAbsent(freq, new LinkedHashSet<>());
            linkedSets.get(freq).add(key);
            freqs.put(key, freq);
        }
    }
}
