/**
 * LeetCode #332, medium
 *
 * Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], reconstruct the
 * itinerary in order. All of the tickets belong to a man who departs from JFK. Thus, the itinerary must begin with JFK.
 *
 * Note:
 *
 * If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when
 * read as a single string. For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
 * All airports are represented by three capital letters (IATA code).
 * You may assume all tickets form at least one valid itinerary.
 *
 * Example 1:
 *
 * Input: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
 * Output: ["JFK", "MUC", "LHR", "SFO", "SJC"]
 *
 * Example 2:
 *
 * Input: [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
 * Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
 * Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"].
 *              But it is larger in lexical order.
 */

package Graph;

import java.util.*;

public class ReconstructItinerary {
    /**
     * Solution 1: DFS backtracking
     *
     * Use DFS to traverse the graph and consume edges. Once all edges are consumed, the itinerary is found.
     * Otherwise restore the temporary data and proceed with other paths.
     *
     * In this solution, graph stores edges rather than neighbors, because the graph traversal should allow visiting
     * a single node multiple times, but not for edges. We also need to sort edges based on the lexical order of
     * destination node.
     *
     * Time complexity: O(E^?). Space complexity: O(E).
     */
    class Solution1 {
        public List<String> findItinerary(String[][] tickets) {
            Map<String, List<String[]>> graph = new HashMap<>();
            for (String[] ticket : tickets) {
                graph.putIfAbsent(ticket[0], new ArrayList<>());
                graph.get(ticket[0]).add(ticket); // store edges in graph
            }

            // sort the tickets
            for (List<String[]> list : graph.values()) list.sort((a, b) -> a[1].compareTo(b[1]));

            List<String> itinerary = new ArrayList<>();
            dfs("JFK", graph, new HashSet<>(), tickets.length, itinerary);
            return itinerary;
        }

        /* Returns true if the itinerary is found, false otherwise */
        private boolean dfs(String origin, Map<String, List<String[]>> graph, Set<String[]> used,
                            int n, List<String> itinerary) {
            itinerary.add(origin); // add origin first to collect leaf node
            if (used.size() == n) return true;
            if (graph.containsKey(origin)) {
                for (String[] ticket : graph.get(origin)) {
                    if (used.contains(ticket)) continue;
                    String next = ticket[1];
                    used.add(ticket);
                    if (dfs(next, graph, used, n, itinerary)) return true;
                    used.remove(ticket);
                }
            }
            itinerary.remove(itinerary.size()-1);
            return false;
        }
    }

    /**
     * Solution 2: Recursive DFS
     *
     * This problem is essentially Eulerian path finding. An efficient algorithm is like this: Start from JFK and do
     * DFS until reaching a dead end. Then this node must be the end node of this Eulerian path because there is no
     * way out. We add this to the result, and rewind back to the second last node A. If there are still ways out,
     * explore them using DFS, and this time it must be a ring which eventually returns back to A, because otherwise
     * the graph will have two dead ends thus no Eulerian path that walks through all edges. Then we keep adding
     * nodes to the head of results as we rewind and march to branches if necessary, until there are no more nodes to
     * search.
     *
     * This algorithm is very like the post-order one used to do topological sort, except that we don't mark nodes
     * as visited, but remove edges instead.
     *
     * To retain the lexical order, we can use priority queue to store neighbors. Deleting edge is equivalent of
     * polling nodes out of the priority queue.
     *
     * Time complexity: O(E * log(E)) for heap sort, O(E) for DFS because we visit each edge for exactly once.
     * Overall time complexity is O(E * log(E)).
     *
     * Space complexity: O(E).
     */
    class Solution2 {
        public List<String> findItinerary(String[][] tickets) {
            Map<String, Queue<String>> graph = new HashMap<>();
            for (String[] ticket : tickets) {
                graph.putIfAbsent(ticket[0], new PriorityQueue<>());
                graph.get(ticket[0]).offer(ticket[1]);
            }
            List<String> itinerary = new LinkedList<>();
            visit("JFK", graph, itinerary);
            return itinerary;
        }

        private void visit(String from, Map<String, Queue<String>> graph, List<String> itinerary) {
            if (graph.containsKey(from)) {
                Queue<String> neighbors = graph.get(from);
                while (!neighbors.isEmpty()) visit(neighbors.poll(), graph, itinerary);
            }
            itinerary.add(0, from); // this line needs to be outside the if block to collect leaf node
        }
    }

    /**
     * Solution 3: Iterative DFS
     *
     * Same as Solution 2 except iterative with stack. Important note is that, when consuming edges (deleting
     * neighbor nodes), we don't want to do it all at once, polling out all nodes from the heap, because that way the
     * lower priority node with wrong lexical order will enter the stack and be added next to current node. Instead,
     * we only add (poll and push) lexicographically the next neighbor to the stack.
     */
    class Solution3 {
        public List<String> findItinerary(String[][] tickets) {
            Map<String, Queue<String>> graph = new HashMap<>();
            for (String[] ticket : tickets) {
                graph.putIfAbsent(ticket[0], new PriorityQueue<>());
                graph.get(ticket[0]).offer(ticket[1]);
            }
            List<String> itinerary = new LinkedList<>();
            Stack<String> stack = new Stack<>();
            stack.push("JFK");
            while (!stack.isEmpty()) {
                Queue<String> pq = graph.get(stack.peek());
                if (pq != null && !pq.isEmpty()) stack.push(pq.poll()); // don't to use a while loop to consume all
                                                                        // neighbors here, just take the first one
                else itinerary.add(0, stack.pop());
            }
            return itinerary;
        }
    }
}
