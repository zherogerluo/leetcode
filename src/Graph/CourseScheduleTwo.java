/**
 * LeetCode #210, medium
 *
 * There are a total of n courses you have to take, labeled from 0 to n-1.

 Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is
 expressed as a pair: [0,1]

 Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take
 to finish all courses.

 There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all
 courses, return an empty array.

 Example 1:

 Input: 2, [[1,0]]
 Output: [0,1]
 Explanation: There are a total of 2 courses to take. To take course 1 you should have finished
 course 0. So the correct course order is [0,1] .
 Example 2:

 Input: 4, [[1,0],[2,0],[3,1],[3,2]]
 Output: [0,1,2,3] or [0,2,1,3]
 Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both
 courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
 So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3] .
 Note:

 The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a
 graph is represented.
 You may assume that there are no duplicate edges in the input prerequisites.
 */

package Graph;

import java.util.*;

public class CourseScheduleTwo {
    /**
     * Solution 1: Recursive DFS
     *
     * Same as Solution 1 in Course Schedule (Problem 207). Add a node to topological sorted order if it has no
     * incoming edges (indegree[i] == 0).
     *
     * This is Kahn's algorithm.
     */
    class Solution1 {
        int[] order;
        int index;

        public int[] findOrder(int numCourses, int[][] prerequisites) {
            order = new int[numCourses];
            index = 0;
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
                if (!visit) return new int[0];
            }
            return order;
        }

        private void dfs(List<Integer>[] graph, int course, int[] indegrees, boolean[] visited) {
            if (visited[course]) return;
            indegrees[course]--; // decrement indegree every time we reach this node
            if (indegrees[course] > 0) return; // however, only visit it when indegree becomes zero
            visited[course] = true;
            order[index++] = course;
            if (graph[course] != null) {
                for (int nextCourse : graph[course]) dfs(graph, nextCourse, indegrees, visited);
            }
        }
    }

    /**
     * Solution 2: Iterative BFS
     *
     * Same as Solution 2 in Course Schedule (Problem 207). Kahn's algorithm.
     */
    class Solution2 {
        public int[] findOrder(int numCourses, int[][] prerequisites) {
            int[] order = new int[numCourses];
            int index = 0;
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
                order[index++] = course;
                if (graph[course] != null) {
                    for (int next : graph[course]) queue.offer(next);
                }
            }
            for (boolean visit : visited) {
                if (!visit) return new int[0];
            }
            return order;
        }
    }

    /**
     * Solution 3: Recursive DFS
     *
     * Instead of Kahn's algorithm (which is easier to understand), we can also utilize post-order traversal, and the
     * reversed post-order is a topological sort.
     *
     * Tricky part is we need to use another boolean array to mark visited nodes in this DFS path to detect cycles.
     */
    class Solution3 {
        int[] order;
        int index;

        public int[] findOrder(int numCourses, int[][] prerequisites) {
            order = new int[numCourses];
            index = numCourses - 1;
            List<Integer>[] graph = new List[numCourses];
            for (int[] edge : prerequisites) {
                int from = edge[1], to = edge[0];
                if (graph[from] == null) graph[from] = new ArrayList<>();
                graph[from].add(to);
            }
            boolean[] visited = new boolean[numCourses];
            boolean[] mark = new boolean[numCourses];
            for (int i = 0; i < numCourses; i++) {
                if (!visited[i]) {
                    if (!dfs(graph, i, visited, mark)) return new int[0];
                }
            }
            return order;
        }

        /* Returns false if DFS detects a cycle. */
        private boolean dfs(List<Integer>[] graph, int course, boolean[] visited, boolean[] mark) {
            if (visited[course]) return true;
            if (mark[course]) return false; // detects a cycle
            mark[course] = true; // mark node temporarily, for cycle detection
            if (graph[course] != null) {
                for (int nextCourse : graph[course]) {
                    if (!dfs(graph, nextCourse, visited, mark)) return false;
                }
            }
            mark[course] = false;
            visited[course] = true; // mark node permanently
            order[index--] = course; // post-order, record from back to front
            return true;
        }
    }
}
