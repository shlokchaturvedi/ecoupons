package com.ejoysoft.util;

/**
 * @author feiwj
 * @version $Revision: 1.0 $ $Date: 2004-8-11 11:31 $
 */
public class LongList implements Cacheable {

    long[] elements;
    int capacity;
    int size;

    /**
     * Creates a new list of long values with a default capacity of 50.
     */
    public LongList() {
        this(50);
    }

    /**
     * Creates a new list of long values with a specified initial capacity.
     *
     * @param initialCapacity a capacity to initialize the list with.
     */
    public LongList(int initialCapacity) {
        size = 0;
        capacity = initialCapacity;
        elements = new long[capacity];
    }

    /**
     * Adds a new LongOfString value to the end of the list.
     */
    public void add(String value) {
        add(Long.parseLong(value));
    }

    /**
     * Adds a new long value to the end of the list.
     */
    public void add(long value) {
        elements[size] = value;
        size++;
        if (size == capacity) {
            capacity = capacity * 2;
            long[] newElements = new long[capacity];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
    }

    /**
     * Returns the long value at the specified index. If the index is not
     * valid, an IndexOutOfBoundException will be thrown.
     *
     * @param index the index of the value to return.
     * @return the value at the specified index.
     */
    public long get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " not valid.");
        }
        return elements[index];
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list.
     */
    public int size() {
        return size;
    }

    /**
     * Returns a new array containing the list elements.
     *
     * @return an array of the list elements.
     */
    public long[] toArray() {
        int size = this.size;
        long[] newElements = new long[size];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        return newElements;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < this.size; i++) {
            buf.append(elements[i]).append(",");
        }

        //ɾ�����һ��ָ��
        buf.deleteCharAt(buf.lastIndexOf(","));

        return buf.toString();
    }

    public long getSize() {
        return CacheSizes.sizeOfObject() + CacheSizes.sizeOfInt() * 2 + CacheSizes.sizeOfLong() * size;
    }
}
