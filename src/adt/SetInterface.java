/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

import java.util.Iterator;

/**
 *
 * @author Wong Yee En
 */
public interface SetInterface<T> {
    
    public boolean add(T newElement);
    public boolean remove(T anElement);
    public boolean checkSubset(SetInterface anotherSet);
    public void union(SetInterface anotherSet);
    public SetInterface Intersection(SetInterface snotherSet);
    public boolean isEmpty();
    public Iterator<T> getIterator();
    
    
}
