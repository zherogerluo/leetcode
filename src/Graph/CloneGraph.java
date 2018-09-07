/**
 * LeetCode #133, medium
 *
 * Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.
 */

package Graph;

import java.util.*;

public class CloneGraph {
    /**
     * Definition for undirected graph.
     */
    class UndirectedGraphNode {
        int label;
        List<UndirectedGraphNode> neighbors;
        UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
    }

    /**
     * Solution 1: Recursive DFS with map
     *
     * Clone nodes in DFS fashion, and keep a map of (original : copy) pairs. While copying nodes, always look up in
     * the map first (cloned); If not cloned, clone it and recursively clone its neighbors.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    public class Solution1 {
        public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
            return cloneNode(node, new HashMap<>());
        }

        private UndirectedGraphNode cloneNode(UndirectedGraphNode node,
                                              Map<UndirectedGraphNode, UndirectedGraphNode> cloned) {
            if (node == null) return null;
            if (cloned.containsKey(node)) return cloned.get(node);
            UndirectedGraphNode newNode = new UndirectedGraphNode(node.label);
            cloned.put(node, newNode);
            for (UndirectedGraphNode neighbor : node.neighbors) {
                newNode.neighbors.add(cloneNode(neighbor, cloned));
            }
            return newNode;
        }
    }

    /**
     * Solution 2: Iterative BFS with map
     *
     * Same idea as Solution 1. Map contains (original : copy) pairs, and queue contains original nodes that are not
     * been visited (i.e. cloned counterparts have no neighbors BUT itself has been cloned). For each node polled from
     * queue, we need to visit their neighbors by creating copies and add them to map and queue, and finally link
     * copied neighbors to copied node.
     *
     * The invariant for the queue is very important.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    public class Solution2 {
        public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
            if (node == null) return null;
            Map<UndirectedGraphNode, UndirectedGraphNode> cloned = new HashMap<>();
            Queue<UndirectedGraphNode> queue = new LinkedList<>();
            queue.offer(node);
            cloned.put(node, new UndirectedGraphNode(node.label)); // need to put in cloned node before the loop
            while (!queue.isEmpty()) {
                UndirectedGraphNode original = queue.poll(), copy = cloned.get(original);
                for (UndirectedGraphNode neighbor : original.neighbors) {
                    if (!cloned.containsKey(neighbor)) {
                        cloned.put(neighbor, new UndirectedGraphNode(neighbor.label));
                        queue.offer(neighbor);
                    }
                    copy.neighbors.add(cloned.get(neighbor));
                }
            }
            return cloned.get(node);
        }
    }
}
