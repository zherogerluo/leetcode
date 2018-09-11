/**
 * LeetCode #399, medium
 *
 * Equations are given in the format A / B = k, where A and B are variables represented as strings, and k is a real
 * number (floating point number). Given some queries, return the answers. If the answer does not exist, return -1.0.
 *
 * Example:
 * Given a / b = 2.0, b / c = 3.0.
 * queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
 * return [6.0, 0.5, -1.0, 1.0, -1.0 ].
 *
 * The input is: vector<pair<string, string>> equations, vector<double>& values, vector<pair<string, string>> queries
 * , where equations.size() == values.size(), and the values are positive. This represents the equations. Return
 * vector<double>.
 *
 * According to the example above:
 *
 * equations = [ ["a", "b"], ["b", "c"] ],
 * values = [2.0, 3.0],
 * queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].
 */

package Graph;

import java.util.*;

public class EvaluateDivision {
    /**
     * Solution 1: BFS
     *
     * The equations form a graph where src -> dst has weight of w and dst -> src has weight of 1/w. Solving query A
     * to B is equivalent of finding a path from A to B and multiply all weights together. BFS can be used for this
     * purpose. Note that we don't need to find a shortest path, because any path will lead to same result as inputs
     * are always valid.
     *
     * Time complexity: O(V + n * E) where n is the number of queries. A single query takes O(E) time.
     * Space complexity: O(V + E), O(E) for graph and O(V) for BFS queue.
     */
    class Solution1 {
        public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
            Map<String, List<Integer>> graph = new HashMap<>(); // node-index map
            for (int i = 0; i < equations.length; i++) {
                String[] eq = equations[i];
                if (!graph.containsKey(eq[0])) graph.put(eq[0], new ArrayList<>());
                if (!graph.containsKey(eq[1])) graph.put(eq[1], new ArrayList<>());
                graph.get(eq[0]).add(i);
                graph.get(eq[1]).add(i);
            }
            double[] res = new double[queries.length];
            for (int i = 0; i < queries.length; i++) {
                res[i] = eval(graph, equations, values, queries[i][0], queries[i][1]);
            }
            return res;
        }

        private double eval(Map<String, List<Integer>> graph, String[][] equations, double[] values,
                            String start, String end) {
            if (!graph.containsKey(start) || !graph.containsKey(end)) return -1;
            Map<String, Double> map = new HashMap<>();
            map.put(start, 1.);
            Queue<String> queue = new LinkedList<>();
            queue.offer(start);
            while (!queue.isEmpty()) {
                String cur = queue.poll();
                double curVal = map.get(cur);
                for (int i : graph.get(cur)) {
                    String next = equations[i][1];
                    double nextVal = values[i];
                    if (next.equals(cur)) {
                        next = equations[i][0];
                        nextVal = 1. / nextVal;
                    }
                    nextVal *= curVal;
                    if (next.equals(end)) return nextVal;
                    if (!map.containsKey(next)) {
                        map.put(next, nextVal);
                        queue.offer(next); // this line must come inside the if block, no need to recalculate if it
                                           // is already in the result map
                    }
                }
            }
            return -1;
        }
    }

    /**
     * Solution 2: Dynamic programming, Floyd-Warshall
     *
     * Floyd-Warshall algorithm solves shortest path problem for all nodes. The core idea is this formula:
     *
     * shortestPath(i, j) = min(shortestPath(i, j), shortestPath(i, k) + shortestPath(k, j)) where k is some
     * intermediate stop. The implementation is three nested loops for k, i and j.
     *
     * The nice thing of this algorithm is that, all the path finding can be done in pre-processing steps and each
     * query takes constant time, which is advantageous if the number of queries is huge.
     *
     * Time complexity: O(V^3 + n) where n is the number of queries. Each query takes O(1) time.
     * Space complexity: O(V^2).
     */
    class Solution2 {
        public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
            // build var-index map
            Map<String, Integer> map = new HashMap<>();
            int index = 0;
            for (String[] eq : equations) {
                for (String var : eq) {
                    if (!map.containsKey(var)) map.put(var, index++);
                }
            }
            // pre-processing using Floyd-Warshall
            final int n = index;
            double[][] data = new double[n][n];
            for (int i = 0; i < equations.length; i++) {
                String[] eq = equations[i];
                int p = map.get(eq[0]), q = map.get(eq[1]);
                data[p][q] = values[i];
                data[q][p] = 1. / values[i];
            }
            for (int i = 0; i < n; i++) data[i][i] = 1.;
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        // since we don't need to calculate shortest path in this problem (in fact all paths should
                        // be equal assuming the inputs are all valid), we can simply update the array entry only
                        // when it is zero (meaning can't reach from i -> j).
                        if (data[i][j] == 0) data[i][j] = data[i][k] * data[k][j];
                    }
                }
            }
            // eval queries
            double[] res = new double[queries.length];
            for (int i = 0; i < queries.length; i++) {
                res[i] = eval(map, data, queries[i][0], queries[i][1]);
            }
            return res;
        }

        private double eval(Map<String, Integer> map, double[][] data, String start, String end) {
            if (map.containsKey(start) && map.containsKey(end)) {
                int i = map.get(start), j = map.get(end);
                return data[i][j] == 0 ? -1 : data[i][j]; // don't forget to convert to -1
            }
            return -1;
        }
    }
}
