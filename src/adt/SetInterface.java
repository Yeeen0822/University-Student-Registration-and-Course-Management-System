/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

import java.util.Iterator;

/**
 *
 * @author Name: Wong Yee En RDS2S2G3 22WMR13659
 */
public interface SetInterface<T> {

    /**
     * 
     * Boolean add (T newElement)
     *Precondition: The newElement does not exist in the current list
     *Post: The newElement is added to the current list
     *Return: true if added success, else false
     */
    public boolean add(T newElement);

    public boolean remove(T anElement);

    public boolean checkSubset(SetInterface anotherSet);

    public void union(SetInterface anotherSet);

    public SetInterface Intersection(SetInterface snotherSet);

    public boolean isEmpty();

    public Iterator<T> getIterator();

    /**
     * Task: Gets the number of entries in the list.
     *
     * @return the integer number of entries currently in the list
     */
    public int getNumberOfEntries();

    public T getEntry(int givenPosition);

     public boolean contains(T anEntry);
}
