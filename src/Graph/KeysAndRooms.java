/**
 * LeetCode #841, medium
 *
 * There are N rooms and you start in room 0.  Each room has a distinct number in 0, 1, 2, ..., N-1, and each room
 * may have some keys to access the next room.
 *
 * Formally, each room i has a list of keys rooms[i], and each key rooms[i][j] is an integer in [0, 1, ..., N-1]
 * where N = rooms.length.  A key rooms[i][j] = v opens the room with number v.
 *
 * Initially, all the rooms start locked (except for room 0).
 *
 * You can walk back and forth between rooms freely.
 *
 * Return true if and only if you can enter every room.
 *
 * Example 1:
 *
 * Input: [[1],[2],[3],[]]
 * Output: true
 * Explanation:
 * We start in room 0, and pick up key 1.
 * We then go to room 1, and pick up key 2.
 * We then go to room 2, and pick up key 3.
 * We then go to room 3.  Since we were able to go to every room, we return true.
 * Example 2:
 *
 * Input: [[1,3],[3,0,1],[2],[0]]
 * Output: false
 * Explanation: We can't enter the room with number 2.
 * Note:
 *
 * 1 <= rooms.length <= 1000
 * 0 <= rooms[i].length <= 1000
 * The number of keys in all rooms combined is at most 3000.
 */

package Graph;

import java.util.*;

public class KeysAndRooms {
    /**
     * Solution 1: DFS
     *
     * Typical DFS. Mark room as we DFS, then check the marks after DFS is done.
     *
     * Time complexity: O(E + V). Space complexity: O(V).
     */
    class Solution1 {
        public boolean canVisitAllRooms(List<List<Integer>> rooms) {
            boolean[] visited = new boolean[rooms.size()];
            visit(rooms.toArray(new List[rooms.size()]), 0, visited);
            for (boolean mark : visited) {
                if (!mark) return false;
            }
            return true;
        }

        private void visit(List<Integer>[] graph, int node, boolean[] visited) {
            if (visited[node]) return;
            visited[node] = true;
            for (int next : graph[node]) visit(graph, next, visited);
        }
    }

    /**
     * Solution 2: BFS
     *
     * Typical DFS. Note that we need to mark node as we pull node into the queue, otherwise the queue will have
     * replicates which can degrade performance and even cause bugs. Rule of thumb: Mark node as visited as soon as
     * we visit this node and it is not marked yet. True for both DFS and BFS.
     *
     * Time complexity: O(E + V). Space complexity: O(V).
     */
    class Solution2 {
        public boolean canVisitAllRooms(List<List<Integer>> rooms) {
            boolean[] visited = new boolean[rooms.size()];
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(0);
            int count = 1; // count of room 0
            visited[0] = true; // mark room 0
            while (!queue.isEmpty()) {
                int room = queue.poll();
                for (int next : rooms.get(room)) {
                    if (!visited[next]) {
                        visited[next] = true; // need to mark this node
                        queue.offer(next); // as we put it into the queue
                        count++;
                    }
                }
            }
            return count == rooms.size();
        }
    }
}
