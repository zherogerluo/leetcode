/**
 * LeetCode #968, hard
 *
 * Given a binary tree, we install cameras on the nodes of the tree.
 *
 * Each camera at a node can monitor its parent, itself, and its immediate children.
 *
 * Calculate the minimum number of cameras needed to monitor all nodes of the tree.
 *
 * Example 1:
 *      0
 *     /
 *   cam
 *  /  \
 * 0    0
 * Input: [0,0,null,0,0]
 * Output: 1
 * Explanation: One camera is enough to monitor all nodes if placed as shown.
 *
 * Example 2:
 *        0
 *       /
 *     cam
 *     /
 *    0
 *   /
 * cam
 *   \
 *    0
 * Input: [0,0,null,0,null,0,null,null,0]
 * Output: 2
 * Explanation: At least two cameras are needed to monitor all nodes of the tree. The above image shows one of the
 * valid configurations of camera placement.
 *
 * Note:
 *
 * The number of nodes in the given tree will be in the range [1, 1000].
 * Every node has value 0.
 */

package Tree;

public class BinaryTreeCameras {
    /**
     * Solution 1: Depth-first search
     *
     * There are a few cases to consider, such as has cam/no cam, and covered/not covered by parent. The DFS function
     * returns the min cams in all cases, and the caller (parent node) construct its return values based on those from
     * children.
     *
     * Time complexity: O(n). Space complexity: O(log(n)) average stack depth
     */
    class Solution1 {
        public int minCameraCover(TreeNode root) {
            int[] arr = helper(root);
            // root is not covered, not taking arr[1]
            return Math.min(arr[0], arr[2]);
        }

        // returns min camara when { root has cam, root has no cam but covered, root has no cam and not covered }
        private int[] helper(TreeNode root) {
            if (root == null) {
                return new int[]{ 10000, 0, 0 };
            }
            int[] left_res = helper(root.left), right_res = helper(root.right);
            int left_cam = left_res[0], left_cover = left_res[1], left = left_res[2];
            int right_cam = right_res[0], right_cover = right_res[1], right = right_res[2];
            // case 1: this node has cam, children have coverage, and can either have cams or not
            int res_cam = 10000;
            res_cam = Math.min(res_cam, left_cover + right_cover);
            res_cam = Math.min(res_cam, left_cam + right_cover);
            res_cam = Math.min(res_cam, left_cover + right_cam);
            res_cam = Math.min(res_cam, left_cam + right_cam);
            // count the camera on self
            res_cam += 1;
            //case 2: this node has no cam, at least one child needs to have cam except...
            int res = 10000;
            res = Math.min(res, left_cam + right);
            res = Math.min(res, left + right_cam);
            res = Math.min(res, left_cam + right_cam);
            // case 3: if it is covered, children can have no cameras
            int res_cover = res;
            res_cover = Math.min(res_cover, left + right);
            // finalize
            int[] arr = new int[]{ res_cam, res_cover, res };
            return arr;
        }
    }

    /**
     * Solution 2: Greedy
     *
     * Credit to lee215, brilliant algorithm
     *
     * An important observation is that, for a leaf node, it is always better to put camera on its parent rather than
     * the leaf itself, because a camera on leaf covers parent + leaf but a camera on parent covers parent + leaf +
     * grandparent. The greedy algorithm puts cameras on all leaves' parent nodes, removes the covered nodes, and
     * repeats until no nodes left.
     *
     * In practice, the "removal" action can be achieved by returning the same "state" as a null node would, i.e.
     * pretending it is a null node. The enumeration of all the possible states are:
     *
     * case 0: A leaf node which does not have a camera
     * case 1: A parent node of a leaf which has a camera
     * case 2: A null node, or a node without a camera but covered by children (null node after the "removal")
     */
    class Solution2 {
        int res = 0;

        public int minCameraCover(TreeNode root) {
            return (dfs(root) < 1 ? 1 : 0) + res;
        }

        // return code meaning as shown above
        public int dfs(TreeNode root) {
            if (root == null) return 2; // null node
            int left = dfs(root.left), right = dfs(root.right);
            if (left == 0 || right == 0) {
                // parent of leaf, greedily put a camera on it
                res++;
                return 1;
            }
            // if any of its children has camera, it can be "removed" and treated a null node
            // otherwise both children returned 2, then it must be a leaf (or a new leaf after "removal")
            return left == 1 || right == 1 ? 2 : 0;
        }
    }
}
