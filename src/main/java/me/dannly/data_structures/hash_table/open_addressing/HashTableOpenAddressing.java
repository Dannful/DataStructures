package me.dannly.data_structures.hash_table.open_addressing;

import me.dannly.data_structures.hash_table.Entry;
import me.dannly.data_structures.hash_table.HashTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class HashTableOpenAddressing<K, V> extends HashTable<K, V> {

    private final ProbingMethod probingMethod;
    private Entry<K, V>[] buckets;

    public HashTableOpenAddressing(ProbingMethod probingMethod) {
        this(probingMethod, probingMethod == ProbingMethod.QUADRATIC_PROBING ? 8 : 7, DEFAULT_LOAD_FACTOR);
    }

    private HashTableOpenAddressing(ProbingMethod probingMethod, int capacity, float loadFactor) {
        super(capacity, loadFactor);
        buckets = new Entry[capacity];
        this.probingMethod = probingMethod;
    }

    private int maxCommonDenominator(int a, int b) {
        final List<Integer> factorialsA = new ArrayList<>();
        final List<Integer> factorialsB = new ArrayList<>();
        for (int i = a; i >= 1; i--) {
            if (a % i == 0) {
                factorialsA.add(i);
            }
        }
        for (int i = b; i >= 1; i--) {
            if (b % i == 0) {
                factorialsB.add(i);
            }
        }
        return factorialsA.stream().filter(factorialsB::contains)
                .max(Integer::compare).orElse(-1);
    }

    @Override
    protected int normalizeIndex(K key) {
        int x = 0;
        int index;
        while (true) {
            final Entry<K, V> bucket = buckets[index = applyProbingFunction(key, x)];
            if (bucket == null || bucket.getKey() == null) break;
            x++;
        }
        return index;
    }

    private int applyProbingFunction(K key, int x) {
        switch (probingMethod) {
            case LINEAR_PROBING: {
                int a = 3;
                while (maxCommonDenominator(a, capacity) != 1)
                    a++;
                return (key.hashCode() & Integer.MAX_VALUE + a * x) % capacity;
            }
            case QUADRATIC_PROBING:
                return (key.hashCode() & Integer.MAX_VALUE + (x * x + x) / 2) % capacity;
            case DOUBLE_HASHING: {
                int hashCode = key.hashCode() & Integer.MAX_VALUE;
                return (hashCode + x * (7 - (hashCode % 7))) % capacity;
            }
        }
        return -1;
    }

    @Override
    protected Entry<K, V> findEntry(K key) {
        final KeyWithXAndIndex index = findIndex(key);
        return index != null ? buckets[index.index] : null;
    }

    private KeyWithXAndIndex findIndex(K key) {
        int x = 0;
        while (true) {
            int index = applyProbingFunction(key, x);
            final Entry<K, V> entry = buckets[index];
            if (entry == null)
                return null;
            if (entry.getKey() != null && entry.getKey().equals(key))
                return new KeyWithXAndIndex(index, x);
            x++;
        }
    }

    @Override
    protected void reallocBuckets() {
        capacity *= 2;
        final Stream<Entry<K, V>> entryStream = Arrays.stream(buckets.clone()).filter(Objects::nonNull);
        Arrays.fill(buckets, null);
        buckets = Arrays.copyOf(buckets, capacity);
        bucketCount = 0;
        entryStream.forEach(entry -> add(entry.getKey(), entry.getValue()));
    }

    @Override
    public void add(K key, V value) {
        final Entry<K, V> foundEntry = findEntry(key);
        if (foundEntry != null) {
            foundEntry.setValue(value);
            return;
        }
        if (++bucketCount / (float) capacity > loadFactor)
            reallocBuckets();
        final int index = normalizeIndex(key);
        if (index == -1)
            return;
        buckets[index] = new Entry<>(key, value);
    }

    @Override
    public void remove(K key) {
        final KeyWithXAndIndex foundIndex = findIndex(key);
        if (foundIndex == null)
            return;
        buckets[foundIndex.index].setKey(null);
        bucketCount--;
    }

    @Override
    public List<Entry<K, V>> getEntries() {
        return Arrays.asList(buckets);
    }

    private void reallocAfterRemoval(int originalIndex, K key, int x) {
        if (buckets[originalIndex] == null || buckets[originalIndex].getKey() != null)
            return;
        buckets[originalIndex] = null;
        while (true) {
            int i = applyProbingFunction(key, x + 1);
            final Entry<K, V> bucket = buckets[i];
            if (i == originalIndex || bucket == null)
                break;
            buckets[originalIndex] = bucket;
            buckets[i] = null;
            x++;
        }
    }

    private static class KeyWithXAndIndex {

        int index;
        int x;

        public KeyWithXAndIndex(int index, int x) {
            this.index = index;
            this.x = x;
        }
    }
}
