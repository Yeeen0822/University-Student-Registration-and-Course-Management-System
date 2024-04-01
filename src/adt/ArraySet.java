/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * @author Wong Yee En
 */
public class ArraySet<T> implements SetInterface<T>, Serializable {

    T[] array;
    int numberOfEntries;

    public ArraySet() {
        numberOfEntries = 0;
        array = (T[]) new Object[5];

    }

    public void DoubleUpArraySize() {
        T[] newArraySize = (T[]) new Object[2 * numberOfEntries];
        System.arraycopy(array, 0, newArraySize, 0, numberOfEntries);
        array = newArraySize;
    }

    private void makeRoom(int newPosition) {
        int newIndex = newPosition - 1;
        int lastIndex = numberOfEntries - 1;

        // move each entry to next higher index, starting at end of
        // array and continuing until the entry at newIndex is moved
        for (int index = lastIndex; index >= newIndex; index--) {
            array[index + 1] = array[index];
        }
    }

    /**
     * Task: Shifts entries that are beyond the entry to be removed to the next
     * lower position. Precondition: array is not empty; 1 <= givenPosition <
     * numberOfEntries; numberOfEntries is array's numberOfEntries before
     * removal.
     */
    private void removeGap(int givenPosition) {
        // move each entry to next lower position starting at entry after the
        // one removed and continuing until end of array
        int removedIndex = givenPosition - 1;
        int lastIndex = numberOfEntries - 1;

        for (int index = removedIndex; index < lastIndex; index++) {
            array[index] = array[index + 1];
        }
    }

    @Override
    public boolean add(T newElement) {
        for (int x = 0; x < numberOfEntries; x++) {
            if (array[x].equals(newElement)) {
                return false;
            }
        }
        if (numberOfEntries == array.length) {
            DoubleUpArraySize();
        }
        array[numberOfEntries] = newElement;
        numberOfEntries++;
        return true;

    }

    @Override
    public boolean remove(T anElement) {
        boolean result = false;

        for (int x = 0; x < array.length && !result; x++) {
            if (array[x].equals(anElement)) {
                result = true;
                removeGap(x + 1);
                numberOfEntries--;
            }
        }

        return result;
    }

    @Override
    public boolean checkSubset(SetInterface anotherSet) {
        if (anotherSet instanceof ArraySet) {
            ArraySet anotherSets = (ArraySet) anotherSet;
            for (int x = 0; x < anotherSets.numberOfEntries; x++) {
                boolean result = false;
                for (int y = 0; y < numberOfEntries && !result; y++) {
                    if (array[y].equals(anotherSets.array[x])) {
                        result = true;
                    }
                }
                if (!result) {
                    return false;
                }
            }
            return true;

        }
        return false;
    }

    @Override
    public void union(SetInterface anotherSet) {
        if (anotherSet instanceof ArraySet) {
            ArraySet arraySet = (ArraySet) anotherSet;
            for (int x = 0; x < arraySet.numberOfEntries; x++) {
                this.add((T) arraySet.array[x]);

            }
        }

    }

    @Override
    public SetInterface Intersection(SetInterface anotherSet) {

        if (anotherSet instanceof ArraySet) {
            SetInterface tempArraySet = new ArraySet();

            ArraySet anotherArraySet = (ArraySet) anotherSet;
            for (int x = 0; x < anotherArraySet.numberOfEntries; x++) {
                for (int y = 0; y < numberOfEntries; y++) {
                    if (anotherArraySet.array[x].equals(array[y])) {
                        tempArraySet.add(array[y]);
                    }
                }
            }

            return tempArraySet;
        }
        return null;

    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(int x = 0; x < numberOfEntries;x++){
            builder.append(array[x] + ",");
        }
        if(builder.length() > 0){
            builder.deleteCharAt(builder.length() -1);
        }
        
        return builder.toString();
    }

    @Override
    public Iterator<T> getIterator() {
       return new getArraySetIterator();
    }
    
    
    private class getArraySetIterator implements Iterator<T>{
        private int nextIndex;
        
        public getArraySetIterator(){
            nextIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < numberOfEntries;
        }

        @Override
        public T next() {
            if(hasNext()){
                
                T item = array[nextIndex];
                nextIndex++;
                return item;
            }
            
            
            return null;
        }
        
        
    }
    
    @Override
    public int getNumberOfEntries() {
        return numberOfEntries;
    }

}


