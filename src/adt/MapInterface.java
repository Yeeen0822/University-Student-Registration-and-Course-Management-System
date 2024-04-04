/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;


/**
 *@author 
 * Name: Wong Yee En
 * RDS2Y2S2G3 
 * 22WMR13659
 */


public interface MapInterface<K, V> {
    public void put(K key, V value);

    public V get(K key);

    public V remove(K key);

    public ListInterface<K> keys();

    public ListInterface<V> values();

    public boolean containsKey(K key);

    public boolean containsValue(V value);

    public int size();

    public boolean isEmpty();

    public boolean isFull();

    public void clear();
    
}
