/**
 * LeetCode #207, medium
 *
 * There are a total of n courses you have to take, labeled from 0 to n-1.

 Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is
 expressed as a pair: [0,1]

 Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?

 Example 1:

 Input: 2, [[1,0]]
 Output: true
 Explanation: There are a total of 2 courses to take.
 To take course 1 you should have finished course 0. So it is possible.
 Example 2:

 Input: 2, [[1,0],[0,1]]
 Output: false
 Explanation: There are a total of 2 courses to take.
 To take course 1 you should have finished course 0, and to take course 0 you should
 also have finished course 1. So it is impossible.
 Note:

 The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a
 graph is represented.
 You may assume that there are no duplicate edges in the input prerequisites.
 */

package Graph;

import java.util.*;

public class CourseSchedule {
    /**
     * Solution 1: Recursive DFS
     *
     * This problem is actually a topological sort problem, which can be approached using DFS. However, comparing
     * with a standard DFS, there are a few tricky things to note:
     *
     * 1) Node can't be visited unless all edges pointing to it has been visited (course cannot be taken unless all
     * prerequisites have been fulfilled). We can keep an array that stores the number of edges coming to the ith
     * node. Whenever we reach the node, decrement this number, and visit it only if it becomes zero.
     *
     * 2) We have to start the DFS from a node that has no incoming edges (no prerequisites). Imagine a case where
     * all courses are dependent on something else which forms a cycle, we cannot finish these courses. However if we
     * start DFS from any of the node, we can still traverse the graph, which is not correct. In this case, there
     * should be no nodes we can start our traversal. To achieve this, we just need to start DFS from a node who has
     * no incoming edges (indegree[i] == 0).
     *
     * Time complexity: O(V + E). Space complexity: O(E).
     */
    class Solution1 {
        public boolean canFinish(int numCourses, int[][] prerequisites) {
            List<Integer>[] graph = new List[numCourses];
            int[] indegrees = new int[numCourses];
            for (int[] edge : prerequisites) {
                int from = edge[1], to = edge[0];
                if (graph[from] == null) graph[from] = new ArrayList<>();
                graph[from].add(to);
                indegrees[to]++; // keep track of number of incoming edges
            }
            boolean[] visited = new boolean[numCourses];
            for (int i = 0; i < numCourses; i++) {
                if (indegrees[i] == 0) { // we shall only start from courses with no prerequisites
                    dfs(graph, i, indegrees, visited);
                }
            }
            for (boolean visit : visited) {
                if (!visit) return false;
            }
            return true;
        }

        private void dfs(List<Integer>[] graph, int course, int[] indegrees, boolean[] visited) {
            if (visited[course]) return;
            indegrees[course]--; // decrement indegree every time we reach this node
            if (indegrees[course] > 0) return; // however, only visit it when indegree becomes zero
            visited[course] = true;
            if (graph[course] != null) {
                for (int nextCourse : graph[course]) dfs(graph, nextCourse, indegrees, visited);
            }
        }
    }

    /**
     * Solution 2: Iterative BFS
     *
     * Same idea as Solution 1, except using queue-based BFS.
     */
    class Solution2 {
        public boolean canFinish(int numCourses, int[][] prerequisites) {
            List<Integer>[] graph = new List[numCourses];
            int[] indegrees = new int[numCourses];
            for (int[] edge : prerequisites) {
                int from = edge[1], to = edge[0];
                if (graph[from] == null) graph[from] = new ArrayList<>();
                graph[from].add(to);
                indegrees[to]++;
            }
            boolean[] visited = new boolean[numCourses];
            Queue<Integer> queue = new LinkedList<>();
            for (int i = 0; i < numCourses; i++) {
                if (indegrees[i] == 0) queue.offer(i);
            }
            while (!queue.isEmpty()) {
                int course = queue.poll();
                if (visited[course] || --indegrees[course] > 0) continue;
                visited[course] = true;
                if (graph[course] != null) {
                    for (int next : graph[course]) queue.offer(next);
                }
            }
            for (boolean visit : visited) {
                if (!visit) return false;
            }
            return true;
        }
    }
}
