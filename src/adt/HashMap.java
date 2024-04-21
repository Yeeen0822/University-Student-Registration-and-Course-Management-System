/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import java.io.Serializable;

/**
 * Array implementation of HashMap using Open Addressing with Double Hashing.
 * 
 * @param <K> The type of keys in the map.
 * @param <V> The type of values in the map.
 * @author 
 * Name: Wong Yee En, Yam Jason
 * RDS2Y2S2G3
 * 22WMR13659, 22WMR13662
 * 
 */


public class HashMap<K, V> implements MapInterface<K, V>, Serializable {

    private class Entry<K, V> implements Serializable {

        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry<K, V>[] entries;
    private int numberOfEntries;
    private double loadFactor = 0.75;
    private int primeNumber;
    private static final int DEFAULT_CAPACITY = 20;

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    public HashMap(int capacity) {
        entries = new Entry[capacity];
        numberOfEntries = 0;
        primeNumber = getPrimeNumber();
    }

    public HashMap(int capacity, double loadFactor) {
        this(capacity);
        this.loadFactor = loadFactor;
    }

    @Override
    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value cannot be null");
        }

        // Update Value if key exists
        int index = getIndexForExistingEntries(key);
        if (index != -1) {
            entries[index].value = value;
            return;
        }

        // Add new entry
        if (isHashMapTooFull()) {
            rehash();
        }

        index = getIndexForNullEntries(key);

        // Find next available index
        while (index == -1) {
            rehash();
            index = getIndexForNullEntries(key);
        }

        entries[index] = new Entry<>(key, value);
        numberOfEntries++;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = getIndexForExistingEntries(key);
        if (index != -1) {
            return entries[index].value;
        }
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        V removedValue = null;
        int index = getIndexForExistingEntries(key);
        if (index != -1) {
            removedValue = entries[index].value;
            entries[index] = null;
            numberOfEntries--;
        }
        return removedValue;
    }

    @Override
    public ListInterface<K> keys() {
        ListInterface<K> keys = new ArrayList<>();
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] != null) {
                keys.add(entries[i].key);
            }
        }
        return keys;
    }

    @Override
    public ListInterface<V> values() {
        ListInterface<V> values = new ArrayList<>();
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] != null) {
                values.add(entries[i].value);
            }
        }
        return values;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }

        return getIndexForExistingEntries(key) != -1;
    }

    @Override
    public boolean containsValue(V value) {
        if (value == null) {
            return false;
        }

        for (int i = 0; i < entries.length; i++) {
            if (entries[i] != null && entries[i].value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public boolean isFull() {
        return entries.length == numberOfEntries;
    }

    @Override
    public void clear() {
        for (int i = 0; i < entries.length; i++) {
            entries[i] = null;
        }
        numberOfEntries = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HashMap: {");
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] != null) {
                sb.append(entries[i].key).append("=").append(entries[i].value).append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

     // Check if HashMap needs rehashing
    private boolean isHashMapTooFull() {
        return numberOfEntries >= loadFactor * entries.length;
    }

    // Get the prime number that is closest and lesser to the size of the array
    private int getPrimeNumber() {
        for (int i = entries.length - 1; i >= 1; i--) {
            int count = 0;
            for (int j = 2; j * j <= i; j++) {
                if (i % j == 0) {
                    count++;
                    break;
                }
            }
            if (count == 0) {
                return i;
            }
        }
        return 3;
    }

    // Rehash the HashMap
    private void rehash() {
        Entry<K, V>[] oldEntries = entries;
        entries = new Entry[oldEntries.length * 2];

        for (int i = 0; i < oldEntries.length; i++) {
            if (oldEntries[i] != null) {
                int index = getIndexForNullEntries(oldEntries[i].key);
                entries[index] = oldEntries[i];
            }
        }
    }

    // Get index for null entries
    private int getIndexForNullEntries(K key) {
        int steps = 0;

        while (steps < entries.length) {
            int index = index(key, steps++);
            Entry<K, V> entry = entries[index];
            if (entry == null) {
                return index;
            }
        }
        return -1;
    }

    // Get index for existing entries
    private int getIndexForExistingEntries(K key) {
        int steps = 0;

        while (steps < entries.length) {
            int index = index(key, steps++);
            Entry<K, V> entry = entries[index];
            if (entry != null && entry.key.equals(key)) {
                return index;
            }
        }
        return -1;
    }

    // Calculate index based on hash values and number of steps
    private int index(K key, int i) {
        int hash1 = hash1(key);
        int hash2 = hash2(key);
        return (hash1 + i * hash2) % entries.length;
    }

     // Calculate the first hash value
    private int hash1(K key) {
        int hashIndex1 = key.hashCode() % entries.length;
        if (hashIndex1 < 0) {
            hashIndex1 += entries.length;
        }
        return hashIndex1;
    }

    // Calculate the second hash value
    private int hash2(K key) {
        int hashIndex2 = primeNumber - (key.hashCode() % primeNumber);
        if (hashIndex2 < 0) {
            hashIndex2 += primeNumber;
        }
        return hashIndex2;
    }

    protected int getIndex(K key, int step) {
        return index(key, step);
    }

}
