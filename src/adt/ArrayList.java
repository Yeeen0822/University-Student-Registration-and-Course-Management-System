package adt;

/**
 * @author Name: Wong Yee En RDS2Y2S2G3 22WMR13659
 * @version 2.0
 */
import java.io.Serializable;
import java.util.Iterator;

public class ArrayList<T> implements ListInterface<T>, Serializable {

    private T[] array;
    private int size;
    private static final int DEFAULT_CAPACITY = 15;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        size = 0;
        array = (T[]) new Object[initialCapacity];
    }

    @Override
    public boolean add(T newEntry) {
        if (isArrayFull()) {
            doubleArray();
        }

        array[size] = newEntry;
        size++;
        return true;
    }

    @Override
    public boolean add(int index, T newEntry) {
        boolean success  = true;

        if ((index >= 1) && (index <= size + 1)) {
            if (isArrayFull()) {
                doubleArray();
            }
            makeRoom(index);
            array[index - 1] = newEntry;
            size++;
        } else {
            success  = false;
        }

        return success;
    }


    @Override
    public T remove(int index) {
        T result = null;

        if ((index >= 1) && (index <= size)) {
            result = array[index - 1];

            if (index < size) {
                closeGap(index);
            }

            size--;
        }

        return result;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public boolean replace(int index, T newEntry) {
        boolean isSuccessful = true;

        if ((index >= 1) && (index <= size)) {
            array[index - 1] = newEntry;
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public T getEntry(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= size)) {
            result = array[givenPosition - 1];
        }

        return result;
    }

    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        for (int index = 0; !found && (index < size); index++) {
            if (anEntry.equals(array[index])) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public int getNumberOfEntries() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    private boolean isArrayFull() {
        return size == array.length;
    }

    private void doubleArray() {
        T[] oldArray = array;
        array = (T[]) new Object[oldArray.length * 2];
        for (int i = 0; i < oldArray.length; i++) {
            array[i] = oldArray[i];
        }
    }

    @Override
    public String toString() {
        String outputStr = "";
        for (int index = 0; index < size; ++index) {
            outputStr += array[index] + "\n";
        }

        return outputStr;
    }

    /**
     * Task: Makes room for a new entry at newPosition. Precondition: 1 <=
     * newPosition <= numberOfEntries + 1; numberOfEntries is array's
     * numberOfEntries before addition.
     */
    private void makeRoom(int newPosition) {
        int newIndex = newPosition - 1;
        int lastIndex = size - 1;

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
    private void closeGap(int givenPosition) {
        // move each entry to next lower position starting at entry after the
        // one removed and continuing until end of array
        int removedIndex = givenPosition - 1;
        int lastIndex = size - 1;

        for (int index = removedIndex; index < lastIndex; index++) {
            array[index] = array[index + 1];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                return array[currentIndex++];
            }
        };
    }

}
