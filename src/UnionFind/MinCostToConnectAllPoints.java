/**
 * LeetCode #1584, medium
 *
 * You are given an array points representing integer coordinates of some points on a 2D-plane, where points[i] =
 * [xi, yi].
 *
 * The cost of connecting two points [xi, yi] and [xj, yj] is the manhattan distance between them:
 * |xi - xj| + |yi - yj|, where |val| denotes the absolute value of val.
 *
 * Return the minimum cost to make all points connected. All points are connected if there is exactly one simple path
 * between any two points.
 *
 * Example 1:
 *
 * Input: points = [[0,0],[2,2],[3,10],[5,2],[7,0]]
 * Output: 20
 * Explanation:
 *
 * We can connect the points as shown above to get the minimum cost of 20.
 * Notice that there is a unique path between every pair of points.
 *
 * Example 2:
 *
 * Input: points = [[3,12],[-2,5],[-4,1]]
 * Output: 18
 *
 * Example 3:
 *
 * Input: points = [[0,0],[1,1],[1,0],[-1,1]]
 * Output: 4
 *
 * Example 4:
 *
 * Input: points = [[-1000000,-1000000],[1000000,1000000]]
 * Output: 4000000
 *
 * Example 5:
 *
 * Input: points = [[0,0]]
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= points.length <= 1000
 * -10^6 <= xi, yi <= 10^6
 * All pairs (xi, yi) are distinct.
 */

import java.util.*;

public class MinCostToConnectAllPoints {
    /**
     * Solution 1: Prim's algorithm
     *
     * Connect each pair of points with a weighted edge, the weight being the manhattan distance between those points.
     * The problem is now the cost of minimum spanning tree in graph with above edges. Use Prim's or Kruskal's algorithm
     * to solve the problem.
     */
    class Solution1 {
        public int minCostConnectPoints(int[][] points) {
            final int n = points.length;
            // int[] edge = {next vertex, weight}
            Queue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
            pq.offer(new int[]{ 0, 0 });
            boolean[] visited = new boolean[n];
            int res = 0;
            boolean done = false;
            while (!pq.isEmpty() && !done) {
                int[] edge = pq.poll();
                int curr = edge[0], weight = edge[1];
                if (visited[curr]) {
                    continue;
                }
                visited[curr] = true;
                res += weight;
                done = true;
                for (int i = 0; i < n; ++i) {
                    if (!visited[i]) {
                        pq.offer(new int[]{ i, dist(points, curr, i) });
                        done = false;
                    }
                }
            }
            return res;
        }

        private int dist(int[][] points, int i, int j) {
            return Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
        }
    }
}
