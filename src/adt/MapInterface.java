/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 * An interface for the ADT Map, which stores key-value pairs.
 * 
 * @param <K> The type of keys in the map.
 * @param <V> The type of values in the map.
 * 
 * @author 
 * Name: Wong Yee En, Yam Jason
 * RDS2S2G3
 * 22WMR13659, 22WMR13662
 */
public interface MapInterface<K, V> {
    
    /**
     * Task: Adds a new key-value pair to the map. If the key already exists, the
     * corresponding value is overwritten.
     * 
     * @param key The key to be added.
     * @param value The value to be associated with the key.
     */
    public void put(K key, V value);

    /**
     * Task: Retrieves the value associated with the specified key.
     * 
     * @param key The key whose associated value is to be retrieved.
     * @return The value associated with the specified key, or null if the key
     *         is not found.
     */
    public V get(K key);

    /**
     * Task: Removes the key-value pair associated with the specified key from the
     * map.
     * 
     * @param key The key of the key-value pair to be removed.
     * @return The value associated with the removed key, or null if the key is
     *         not found.
     */
    public V remove(K key);

    /**
     * Task: Retrieves a list of all keys in the map.
     * 
     * @return A list containing all keys in the map.
     */
    public ListInterface<K> keys();

    /**
     * Task: Retrieves a list of all values in the map.
     * 
     * @return A list containing all values in the map.
     */
    public ListInterface<V> values();

    /**
     * Task: Checks whether the map contains the specified key.
     * 
     * @param key The key to be checked for existence.
     * @return true if the map contains the specified key, false otherwise.
     */
    public boolean containsKey(K key);

    /**
     * Task: Checks whether the map contains the specified value.
     * 
     * @param value The value to be checked for existence.
     * @return true if the map contains the specified value, false otherwise.
     */
    public boolean containsValue(V value);

    /**
     * Task: Retrieves the number of key-value pairs in the map.
     * 
     * @return The number of key-value pairs in the map.
     */
    public int size();

    /**
     * Task: Checks whether the map is empty.
     * 
     * @return true if the map is empty, false otherwise.
     */
    public boolean isEmpty();

    /**
     * Task: Checks whether the map is full. This method may not be applicable for
     * all implementations of the Map ADT.
     * 
     * @return true if the map is full, false otherwise.
     */
    public boolean isFull();

    /**
     * Task: Removes all key-value pairs from the map, resulting in an empty map.
     */
    public void clear();
    
}
