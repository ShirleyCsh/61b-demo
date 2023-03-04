package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Sandra Hui, Revised by Todd Yu for 2022,
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size;
    private double loadFactor;

    /* Constants */
    private static final int DEFAULT_INIT_SIZE = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_INIT_SIZE, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        size = 0;
        loadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<Node>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        buckets = createTable(DEFAULT_INIT_SIZE);
        size = 0;
    }

    /**
     * Returns the number of key-value mappings in this map.
     * @return number of entries in the map
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set<K> keySet() {
        Set<K> result = new HashSet<K>();
        for (Collection<Node> c : this.buckets) {
            //Alternate solution: c.forEach(node -> result.add(node.key));
            if (c == null) continue;
            for (Node n : c) {
                result.add(n.key);
            }
        }
        return result;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key key to search for
     * @return true if this map contains the specified key, false otherwise
     */
    @Override
    public boolean containsKey(K key) {
        Node node = getNode(key);
        return node != null;
    }

    /**
     * Returns an iterator over the stored keys
     *
     * @return an Iterator over the stored keys
     */
    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key key to search for
     * @return value associated with the key, null otherwise
     */
    @Override
    public V get(K key) {
        Node node = getNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    /**
     * Helper method to get an Entry associated with a key, or null
     * if the key is not stored
     *
     * @param key key to search for
     * @return Entry associated with the key
     */
    private Node getNode(K key) {
        int idx = findBucket(key);
        Collection<Node> bucketList = buckets[idx];
        if (bucketList != null) {
            for (Node node : bucketList) {
                if (node.key.equals(key)) {
                    return node;
                }
            }
        }
        return null;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     *
     * @param key key to store
     * @param value value to store
     */
    @Override
    public void put(K key, V value) {
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
            return;
        }

        if (((double) size) / buckets.length > loadFactor) {
            rebucket(buckets.length * 2);
        }
        size++;
        int idx = findBucket(key);
        Collection<Node> bucketList = buckets[idx];
        if (bucketList == null) {
            bucketList = createBucket();
            buckets[idx] = bucketList;
        }
        bucketList.add(createNode(key, value));
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key key to remove
     */
    @Override
    public V remove(K key) {
        return remove(key, null);
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value.
     *
     * @param key key to remove
     * @param value value previously associated with specified key, or null if not found
     */
    public V remove(K key, V value) {
        int idx = findBucket(key);
        Collection<Node> bucket = buckets[idx];
        if (bucket != null) {
            for (Node node : bucket) {
                if (node.key.equals(key) && (value == null || node.value.equals(value))) {
                    bucket.remove(node);
                    size--;
                    return node.value;
                }
            }
        }
        return null;
    }


    /**
     * Helper method to return the bucket for the key
     *
     * @param key key to hash
     * @return hashCode of key, bounded by the map's length
     */
    private int findBucket(K key) {
        return findBucket(key, buckets.length);
    }

    /**
     * Helper method to return the bucket for each key (this
     * is useful for rebucket())
     *
     * @param key key to hash
     * @param numBuckets max bucket index allowed
     * @return hashcode of passed key within given bounds
     */
    private int findBucket(K key, int numBuckets) {
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /**
     * Helper method to double the size of the backing array and
     * rebucket all stored Entries
     *
     * @param targetSize new size of the backing array
     */
    private void rebucket(int targetSize) {
        Collection<Node>[] newBuckets = createTable(targetSize);
        for (Collection<Node> c : this.buckets) {
            if (c == null) continue;
            for (Node n : c) {
                K key = n.key;
                int idx = findBucket(key, newBuckets.length);
                if (newBuckets[idx] == null) {
                    newBuckets[idx] = createBucket();
                }
                newBuckets[idx].add(getNode(key));

            }
        }
        buckets = newBuckets;
    }
}