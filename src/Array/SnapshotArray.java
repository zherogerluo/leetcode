/**
 * LeetCode #1146, medium
 *
 * Implement a SnapshotArray that supports the following interface:
 *
 * SnapshotArray(int length) initializes an array-like data structure with the given length.  Initially, each element
 * equals 0.
 * void set(index, val) sets the element at the given index to be equal to val.
 * int snap() takes a snapshot of the array and returns the snap_id: the total number of times we called snap() minus 1.
 * int get(index, snap_id) returns the value at the given index, at the time we took the snapshot with the given snap_id
 *
 * Example 1:
 *
 * Input: ["SnapshotArray","set","snap","set","get"]
 * [[3],[0,5],[],[0,6],[0,0]]
 * Output: [null,null,0,null,5]
 * Explanation:
 * SnapshotArray snapshotArr = new SnapshotArray(3); // set the length to be 3
 * snapshotArr.set(0,5);  // Set array[0] = 5
 * snapshotArr.snap();  // Take a snapshot, return snap_id = 0
 * snapshotArr.set(0,6);
 * snapshotArr.get(0,0);  // Get the value of array[0] with snap_id = 0, return 5
 *
 * Constraints:
 *
 * 1 <= length <= 50000
 * At most 50000 calls will be made to set, snap, and get.
 * 0 <= index < length
 * 0 <= snap_id < (the total number of times we call snap())
 * 0 <= val <= 10^9
 */

/* Need to discuss with interviewer about which function is called most frequently that needs to be optimized */

package Array;

import java.util.*;

public class SnapshotArray {
    /**
     * Solution 1:
     *
     * Use list to persist snapshotted data per index. Snapshot takes O(n) time but get() takes O(1) time.
     */
    class SnapshotArray1 {
        List<Integer>[] storage;
        int id;

        public SnapshotArray1(int length) {
            storage = new ArrayList[length];
            for (int i = 0; i < length; ++i) {
                storage[i] = new ArrayList<>();
                storage[i].add(0);
            }
            id = 0;
        }

        public void set(int index, int val) {
            List<Integer> list = storage[index];
            list.set(list.size()-1, val);
        }

        public int snap() {
            for (List<Integer> list : storage) {
                list.add(list.get(list.size()-1));
            }
            return id++;
        }

        public int get(int index, int snap_id) {
            return storage[index].get(snap_id);
        }
    }

    /**
     * Solution 2:
     *
     * Only update data when set() is called. get() would require a binary search to locate the requested snap_id.
     * Optimization can be made to avoid log(n) search if get() frequently queries current snapshot. set() takes O(1)
     * time as we only need to bump the id.
     */
    class SnapshotArray2 {
        class SnapVal implements Comparable<SnapVal> {
            int id;
            int val;

            public SnapVal(int id, int val) {
                this.id = id;
                this.val = val;
            }

            public int compareTo(SnapVal other) {
                return Integer.compare(id, other.id);
            }
        }

        List<SnapVal>[] storage;
        int id;

        public SnapshotArray2(int length) {
            id = 0;
            storage = new ArrayList[length];
            for (int i = 0; i < length; ++i) {
                storage[i] = new ArrayList<>();
                storage[i].add(new SnapVal(id, 0));
            }
        }

        public void set(int index, int val) {
            List<SnapVal> list = storage[index];
            SnapVal last = list.get(list.size() - 1);
            if (last.id != id) {
                list.add(new SnapVal(id, val));
            } else {
                last.val = val;
            }
        }

        public int snap() {
            return id++;
        }

        public int get(int index, int snap_id) {
            List<SnapVal> list = storage[index];
            if (snap_id == id) {
                return list.get(list.size()-1).val;
            }
            if (snap_id == 0) {
                return list.get(0).val;
            }
            int lower = Collections.binarySearch(list, new SnapVal(snap_id, 0));
            if (lower < 0) {
                // - upper - 1
                lower = -lower - 2;
            }
            return list.get(lower).val;
        }
    }
}
