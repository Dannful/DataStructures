package me.dannly.data_structures.hash_table.separate_chaining;

import me.dannly.data_structures.hash_table.Entry;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class HashTableSeparateChaining<K, V> {

    private static final int DEFAULT_CAPACITY = 3;
    private static final float DEFAULT_LOAD_FACTOR = 0.7f;
    private final float loadFactor;
    private LinkedList<Entry<K, V>>[] buckets;
    private int capacity;
    private int bucketCount = 0;

    public HashTableSeparateChaining(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.buckets = new LinkedList[capacity];
    }

    public HashTableSeparateChaining(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public HashTableSeparateChaining(float loadFactor) {
        this(DEFAULT_CAPACITY, loadFactor);
    }

    public void add(K key, V value) {
        if (key == null || value == null)
            return;
        onLinkedList(key, (index, linkedList) -> {
            if (linkedList == null) {
                if (++bucketCount / (float) buckets.length > loadFactor) {
                    reallocBuckets();
                    index = normalizeIndex(key.hashCode());
                }
                linkedList = new LinkedList<>();
                buckets[index] = linkedList;
            }
            for (final Entry<K, V> entry : linkedList) {
                if (entry.getKey().equals(key)) {
                    entry.setValue(value);
                    return;
                }
            }
            linkedList.add(new Entry<>(key, value));
        });
    }

    private void reallocBuckets() {
        capacity *= 2;
        final List<Entry<K, V>> entryList = Arrays.stream(buckets).filter(Objects::nonNull).flatMap(LinkedList::stream).collect(Collectors.toList());
        Arrays.fill(buckets, null);
        buckets = Arrays.copyOf(buckets, capacity);
        bucketCount = 0;
        entryList.forEach(entry -> add(entry.getKey(), entry.getValue()));
    }

    public void remove(K key) {
        onLinkedList(key, (index, linkedList) -> {
            if (linkedList == null || linkedList.stream().noneMatch(entry -> entry.getKey().equals(key)))
                return;
            linkedList.remove(findEntry(key));
            if (!linkedList.isEmpty()) {
                buckets[index] = linkedList;
            } else {
                buckets[index] = null;
                bucketCount--;
            }
        });
    }

    public boolean hasKey(K key) {
        return findEntry(key) != null;
    }

    public List<Entry<K, V>> getEntries() {
        return Arrays.stream(buckets).flatMap(LinkedList::stream).collect(Collectors.toList());
    }

    private Entry<K, V> findEntry(K key) {
        return onLinkedList(key, (index, linkedList) -> {
            if (linkedList == null)
                return null;
            final Optional<Entry<K, V>> first = linkedList.stream().filter(e -> e.getKey().equals(key)).findFirst();
            return first.orElse(null);
        });
    }

    private void onLinkedList(K key, BiConsumer<Integer, LinkedList<Entry<K, V>>> action) {
        onLinkedList(key, (index, linkedList) -> {
            action.accept(index, linkedList);
            return null;
        });
    }

    private <R> R onLinkedList(K key, BiFunction<Integer, LinkedList<Entry<K, V>>, R> action) {
        final int index = normalizeIndex(key.hashCode());
        if (index >= buckets.length)
            return null;
        final LinkedList<Entry<K, V>> linkedList = buckets[index];
        return action.apply(index, linkedList);
    }

    private int normalizeIndex(int hashCode) {
        return Math.abs(hashCode) % capacity;
    }
}
