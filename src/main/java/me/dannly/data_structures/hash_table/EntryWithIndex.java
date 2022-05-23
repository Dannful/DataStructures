package me.dannly.data_structures.hash_table;

public class EntryWithIndex<K, V> {

    public Entry<K, V> entry;
    public int index;

    public EntryWithIndex(Entry<K, V> entry, int index) {
        this.entry = entry;
        this.index = index;
    }
}
