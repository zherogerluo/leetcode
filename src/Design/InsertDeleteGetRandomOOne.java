/**
 * LeetCode #380, medium
 *
 * Design a data structure that supports all following operations in average O(1) time.

 insert(val): Inserts an item val to the set if not already present.
 remove(val): Removes an item val from the set if present.
 getRandom: Returns a random element from current set of elements. Each element must have the same probability of
 being returned.

 Example:

 // Init an empty set.
 RandomizedSet randomSet = new RandomizedSet();

 // Inserts 1 to the set. Returns true as 1 was inserted successfully.
 randomSet.insert(1);

 // Returns false as 2 does not exist in the set.
 randomSet.remove(2);

 // Inserts 2 to the set, returns true. Set now contains [1,2].
 randomSet.insert(2);

 // getRandom should return either 1 or 2 randomly.
 randomSet.getRandom();

 // Removes 1 from the set, returns true. Set now contains [2].
 randomSet.remove(1);

 // 2 was already in the set, so return false.
 randomSet.insert(2);

 // Since 2 is the only number in the set, getRandom always return 2.
 randomSet.getRandom();
 */

package Design;

import java.util.*;

public class InsertDeleteGetRandomOOne {
    /**
     * Solution 1: Array list and hash map
     *
     * Use an array list to store values, and a hash map to store value : index map. Insertion is trivial; For
     * removal, to achieve O(1) performance, the trick is to swap the last value and the to-be-removed value, update
     * its index in the map, and finally remove the last value from both list and map. There is a subtle detail:
     * Put the removal code after updating index, otherwise if the last value is the one to be removed, it will be
     * re-added to the map. Random retrieval is trivial.
     *
     * Time complexity: O(1). Space complexity: O(n).
     */
    class RandomizedSet {
        Map<Integer, Integer> indexes;
        List<Integer> data;
        Random random;

        /** Initialize your data structure here. */
        public RandomizedSet() {
            indexes = new HashMap<>();
            data = new ArrayList<>();
            random = new Random();
        }

        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        public boolean insert(int val) {
            if (indexes.containsKey(val)) return false;
            indexes.put(val, data.size());
            data.add(val);
            return true;
        }

        /** Removes a value from the set. Returns true if the set contained the specified element. */
        public boolean remove(int val) {
            if (!indexes.containsKey(val)) return false;
            int index = indexes.get(val);
            // put last entry to this to-be-removed position, and update the index map
            int last = data.get(data.size() - 1);
            data.set(index, last);
            data.remove(data.size() - 1);
            indexes.put(last, index);
            indexes.remove(val); // this line has to come after the previous line.
            return true;
        }

        /** Get a random element from the set. */
        public int getRandom() {
            return data.get(random.nextInt(data.size()));
        }
    }
}
