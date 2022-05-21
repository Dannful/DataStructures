package me.dannly.data_structures.hash_table;

import java.util.List;

public abstract class HashTable<K, V> {

    protected static final int DEFAULT_CAPACITY = 3;
    protected static final float DEFAULT_LOAD_FACTOR = 0.7f;
    protected final float loadFactor;
    protected int capacity;
    protected int bucketCount = 0;

    public HashTable() {
        this.capacity = DEFAULT_CAPACITY;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    public HashTable(int capacity, float loadFactor) {
        this.loadFactor = loadFactor;
        this.capacity = capacity;
    }

    public HashTable(int capacity) {
        this.capacity = capacity;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    public boolean containsKey(K key) {
        return findEntry(key) != null;
    }

    public V get(K key) {
        return findEntry(key).getValue();
    }

    protected abstract int normalizeIndex(K key);

    protected abstract Entry<K, V> findEntry(K key);

    protected abstract void reallocBuckets();

    public abstract void add(K key, V value);

    public abstract void remove(K key);

    public abstract List<Entry<K, V>> getEntries();

}
