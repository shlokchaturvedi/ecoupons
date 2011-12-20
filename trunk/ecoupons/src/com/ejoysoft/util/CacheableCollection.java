package com.ejoysoft.util;

/**
 * @author feiwj
 * @version $Revision: 1.0 $ $Date: 2004-8-18 13:31 $
 */
public class CacheableCollection implements Cacheable {

    private java.util.Collection collection = null;

    public CacheableCollection(java.util.Collection c) {
        collection = c;
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }


    public boolean add(Object o) {
        return collection.add(o);
    }

    public java.util.Iterator iterator() {
        return collection.iterator();
    }

    public boolean iterator(java.util.Collection c) {
        return collection.addAll(c);
    }

    public void clear() {
        collection.clear();
    }

    public boolean removeAll(java.util.Collection c) {
        return collection.removeAll(c);
    }

    public boolean containsAll(java.util.Collection c) {
        return collection.containsAll(c);
    }

    public boolean contains(Object o) {
        return collection.contains(o);
    }

    public boolean equals(Object o) {
        return collection.equals(o);
    }

    public Object[] toArray(Object[] o) {
        return collection.toArray(o);
    }


    public boolean remove(Object o) {
        return collection.remove(o);
    }

    public Object[] toArray() {
        return collection.toArray();
    }

    public int size() {
        return collection.size();
    }

    public long getSize() {
        return CacheSizes.sizeOfObject() * size() + 1;
    }
}
