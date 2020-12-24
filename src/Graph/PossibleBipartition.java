/**
 * LeetCode #886, medium
 *
 * Given a set of N people (numbered 1, 2, ..., N), we would like to split everyone into two groups of any size.
 *
 * Each person may dislike some other people, and they should not go into the same group.
 *
 * Formally, if dislikes[i] = [a, b], it means it is not allowed to put the people numbered a and b into the same group.
 *
 * Return true if and only if it is possible to split everyone into two groups in this way.
 *
 * Example 1:
 *
 * Input: N = 4, dislikes = [[1,2],[1,3],[2,4]]
 * Output: true
 * Explanation: group1 [1,4], group2 [2,3]
 *
 * Example 2:
 *
 * Input: N = 3, dislikes = [[1,2],[1,3],[2,3]]
 * Output: false
 *
 * Example 3:
 *
 * Input: N = 5, dislikes = [[1,2],[2,3],[3,4],[4,5],[1,5]]
 * Output: false
 *
 * Constraints:
 *
 * 1 <= N <= 2000
 * 0 <= dislikes.length <= 10000
 * dislikes[i].length == 2
 * 1 <= dislikes[i][j] <= N
 * dislikes[i][0] < dislikes[i][1]
 * There does not exist i != j for which dislikes[i] == dislikes[j].
 */

package Graph;

import java.util.*;

public class PossibleBipartition {
    /**
     * Solution 1: Breadth-first search
     *
     * The BFS idea is that, for every node being visited, add all the haters to the other group and visit them next.
     * If any haters are already within the same group as the current node, return false.
     *
     * Note we need to deal with the case where the queue is empty halfway. The graph needs to be in-directed.
     */
    class Solution1 {
        public boolean possibleBipartition(int N, int[][] dislikes) {
            if (dislikes == null || dislikes.length == 0) {
                return true;
            }
            Map<Integer, List<Integer>> graph = new HashMap<>();
            for (int[] edge : dislikes) {
                if (!graph.containsKey(edge[0])) {
                    graph.put(edge[0], new ArrayList<>());
                }
                graph.get(edge[0]).add(edge[1]);
                if (!graph.containsKey(edge[1])) {
                    graph.put(edge[1], new ArrayList<>());
                }
                graph.get(edge[1]).add(edge[0]);
            }
            Set<Integer> cur_group = new HashSet<>(), other_group = new HashSet<>();
            Queue<Integer> to_visit = new ArrayDeque<>();
            to_visit.offer(dislikes[0][0]);
            int size = 1;
            while (!to_visit.isEmpty()) {
                int cur = to_visit.poll();
                // remove node to track unvisited nodes
                List<Integer> hates = graph.remove(cur);
                if (hates != null) {
                    for (int hate : hates) {
                        if (cur_group.contains(hate)) {
                            return false;
                        }
                        other_group.add(hate);
                        to_visit.offer(hate);
                    }
                }
                if (to_visit.isEmpty() && !graph.isEmpty()) {
                    // still nodes remaining
                    to_visit.offer(graph.keySet().iterator().next());
                }
                // swap group only when the current batch is finished
                --size;
                if (size == 0) {
                    size = to_visit.size();
                    Set<Integer> tmp = cur_group;
                    cur_group = other_group;
                    other_group = tmp;
                }
            }
            return true;
        }
    }

    /**
     * Solution 2: BFS
     *
     * Use more efficient data structures, use 1 and -1 to denote different groups instead of using sets. groups[i] == 0
     * means it has not been visited, therefore we can eliminate the need of removing from graph.
     */
    class Solution2 {
        public boolean possibleBipartition(int N, int[][] dislikes) {
            if (dislikes == null || dislikes.length == 0) {
                return true;
            }
            List<Integer>[] graph = new ArrayList[N+1];
            for (int[] edge : dislikes) {
                if (graph[edge[0]] == null) {
                    graph[edge[0]] = new ArrayList<>();
                }
                graph[edge[0]].add(edge[1]);
                if (graph[edge[1]] == null) {
                    graph[edge[1]] = new ArrayList<>();
                }
                graph[edge[1]].add(edge[0]);
            }
            int[] groups = new int[N+1];
            Queue<Integer> to_visit = new ArrayDeque<>();
            to_visit.offer(dislikes[0][0]);
            groups[dislikes[0][0]] = 1;
            while (!to_visit.isEmpty()) {
                int cur = to_visit.poll();
                int cur_group = groups[cur];
                List<Integer> hates = graph[cur];
                if (hates != null) {
                    for (int hate : hates) {
                        if (groups[hate] == cur_group) {
                            return false;
                        }
                        if (groups[hate] == 0) {
                            groups[hate] = -cur_group;
                            to_visit.add(hate);
                        }
                    }
                }
                if (to_visit.isEmpty()) {
                    for (int i = 1; i < N+1; ++i) {
                        if (groups[i] == 0) {
                            to_visit.add(i);
                            // need to assign group whenever we add to to_visit
                            groups[i] = 1;
                            break;
                        }
                    }
                }
            }
            return true;
        }
    }

    /**
     * Solution 3: Depth-first search
     *
     * DFS will also work, just imagine it as a different placing strategy: Place one, and its first hater, keep on
     * until cannot place, then go back and place the second hater and keep going.
     *
     * The important optimization is to return early as soon as we see a false. Do not wait till everything is done
     * (res &= helper(..) then return res)
     */
    class Solution3 {
        public boolean possibleBipartition(int N, int[][] dislikes) {
            if (dislikes == null || dislikes.length == 0) {
                return true;
            }
            List<Integer>[] graph = new ArrayList[N+1];
            for (int[] edge : dislikes) {
                if (graph[edge[0]] == null) {
                    graph[edge[0]] = new ArrayList<>();
                }
                graph[edge[0]].add(edge[1]);
                if (graph[edge[1]] == null) {
                    graph[edge[1]] = new ArrayList<>();
                }
                graph[edge[1]].add(edge[0]);
            }
            int[] groups = new int[N+1];
            for (int cur = 1; cur <= N; ++cur) {
                if (groups[cur] == 0) {
                    if (!helper(graph, cur, 1, groups)) {
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean helper(List<Integer>[] graph, int cur, int group, int[] groups) {
            if (groups[cur] != 0) {
                return groups[cur] == group;
            }
            if (graph[cur] == null) {
                return true;
            }
            groups[cur] = group;
            for (int hate : graph[cur]) {
                if (!helper(graph, hate, -group, groups)) {
                    return false;
                }
            }
            return true;
        }
    }
}
