/**
 * LeetCode #347, medium
 *
 * Given a non-empty array of integers, return the k most frequent elements.
 *
 * Example 1:
 *
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 *
 * Example 2:
 *
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 * Note:
 *
 * You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 * Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 */

package HashTable;

import java.util.*;

public class TopKFrequentElements {
    /**
     * Solution 1: Hash map and heap
     *
     * Use a hash map to count frequency, and a k size heap to find the most frequent ones. The heap should evict the
     * smallest one if size increment to k+1, so it should be a min frequency heap.
     *
     * Time complexity: O(n * log(k)). Space complexity: O(n).
     */
    class Solution1 {
        public List<Integer> topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> freq = new HashMap<>();
            for (int num : nums) {
                freq.put(num, freq.getOrDefault(num, 0) + 1);
            }
            Queue<Integer> pq = new PriorityQueue<>((a, b) -> freq.get(a) - freq.get(b));
            for (int num : freq.keySet()) {
                pq.offer(num);
                if (pq.size() > k) pq.poll();
            }
            List<Integer> res = new LinkedList<>();
            while (!pq.isEmpty()) res.add(0, pq.poll());
            return res;
        }
    }

    /**
     * Solution 2: Bucket sort
     *
     * Instead of using a heap, we can use bucket sort to find largest k frequency. The number might be big but its
     * frequency will not exceed n. Tricky part is to allocate size n+1 for the buckets - the frequency (index) can
     * be up to n.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    class Solution2 {
        public List<Integer> topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> freq = new HashMap<>();
            for (int num : nums) {
                freq.put(num, freq.getOrDefault(num, 0) + 1);
            }
            List<Integer>[] buckets = new List[nums.length + 1]; // note the size here
            for (int num : freq.keySet()) {
                int count = freq.get(num);
                if (buckets[count] == null) buckets[count] = new ArrayList<>();
                buckets[count].add(num);
            }
            List<Integer> res = new ArrayList<>();
            for (int i = nums.length; i >= 0; i--) { // note the initial index value here
                if (buckets[i] != null) {
                    for (int num : buckets[i]) {
                        res.add(num);
                        if (res.size() == k) return res;
                    }
                }
            }
            return null;
        }
    }
}
