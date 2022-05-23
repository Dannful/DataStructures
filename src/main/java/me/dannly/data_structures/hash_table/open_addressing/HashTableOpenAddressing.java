package me.dannly.data_structures.hash_table.open_addressing;

import me.dannly.data_structures.hash_table.Entry;
import me.dannly.data_structures.hash_table.EntryWithIndex;
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

    private int applyProbingFunction(K key, int x) {
        switch (probingMethod) {
            case LINEAR_PROBING: {
                int a = 3;
                while (maxCommonDenominator(a, capacity) != 1)
                    a++;
                return normalizeIndex(key.hashCode() + a * x);
            }
            case QUADRATIC_PROBING:
                return normalizeIndex(key.hashCode() + (x * x + x) / 2) % capacity;
            case DOUBLE_HASHING: {
                int hashCode = key.hashCode();
                return normalizeIndex(hashCode + x * (7 - (hashCode % 7)));
            }
        }
        return -1;
    }

    @Override
    protected EntryWithIndex<K, V> findEntry(K key) {
        final int index = findIndex(key, true);
        final Entry<K, V> bucket = buckets[index];
        return bucket != null && bucket.getKey() != null ? new EntryWithIndex<>(buckets[index], index) : null;
    }

    private int findIndex(K key, boolean stopOnNullOrTombstone) {
        int x = 0;
        int index;
        int tombstone = -1;
        while (true) {
            index = applyProbingFunction(key, x++);
            final Entry<K, V> entry = buckets[index];
            if (entry != null && entry.getKey() == null) {
                tombstone = index;
                if (stopOnNullOrTombstone)
                    break;
            }
            if (stopOnNullOrTombstone && entry == null)
                break;
            if (entry != null && entry.getKey().equals(key)) {
                if (tombstone != -1) {
                    buckets[tombstone] = entry;
                    buckets[index] = null;
                    index = tombstone;
                }
                break;
            }
        }
        return index;
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
        if (++bucketCount / (float) capacity > loadFactor)
            reallocBuckets();
        final int foundIndex = findIndex(key, true);
        final Entry<K, V> bucket = buckets[foundIndex];
        if (bucket != null) {
            bucket.setValue(value);
            return;
        }
        buckets[foundIndex] = new Entry<>(key, value);
    }

    @Override
    public void remove(K key) {
        final int foundIndex = findIndex(key, false);
        final Entry<K, V> bucket = buckets[foundIndex];
        if (bucket == null)
            return;
        buckets[foundIndex].setKey(null);
        bucketCount--;
    }

    @Override
    public List<Entry<K, V>> getEntries() {
        return Arrays.asList(buckets);
    }
}
