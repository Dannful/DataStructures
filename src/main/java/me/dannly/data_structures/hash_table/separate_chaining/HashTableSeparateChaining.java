package me.dannly.data_structures.hash_table.separate_chaining;

import me.dannly.data_structures.hash_table.Entry;
import me.dannly.data_structures.hash_table.HashTable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class HashTableSeparateChaining<K, V> extends HashTable<K, V> {

    private LinkedList<Entry<K, V>>[] buckets;

    public HashTableSeparateChaining(int capacity, float loadFactor) {
        super(capacity, loadFactor);
        this.buckets = new LinkedList[capacity];
    }

    public HashTableSeparateChaining(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    @Override
    protected int normalizeIndex(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    @Override
    public void add(K key, V value) {
        if (key == null || value == null)
            return;
        onLinkedList(key, (index, linkedList) -> {
            if (linkedList == null) {
                if (++bucketCount / (float) buckets.length > loadFactor) {
                    reallocBuckets();
                    index = normalizeIndex(key);
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

    @Override
    protected void reallocBuckets() {
        capacity *= 2;
        final List<Entry<K, V>> entryList = Arrays.stream(buckets).filter(Objects::nonNull).flatMap(LinkedList::stream).collect(Collectors.toList());
        Arrays.fill(buckets, null);
        buckets = Arrays.copyOf(buckets, capacity);
        bucketCount = 0;
        entryList.forEach(entry -> add(entry.getKey(), entry.getValue()));
    }

    @Override
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

    @Override
    public List<Entry<K, V>> getEntries() {
        return Arrays.stream(buckets).flatMap(LinkedList::stream).collect(Collectors.toList());
    }

    @Override
    protected Entry<K, V> findEntry(K key) {
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
        final int index = normalizeIndex(key);
        if (index >= buckets.length)
            return null;
        final LinkedList<Entry<K, V>> linkedList = buckets[index];
        return action.apply(index, linkedList);
    }
}
