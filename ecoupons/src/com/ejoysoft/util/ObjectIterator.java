package com.ejoysoft.util;

import java.util.Iterator;

/**
 * @author feiwj
 * @version $Revision: 1.0 $ $Date: 2004-08-15 11:31 $
 */
public class ObjectIterator implements Iterator {

    private long[] elements;
    private int currentIndex = -1;
    private Object nextElement = null;
    private int endIndex = -1;
    private ObjectFactory objectFactory;


    //��ҳ����
    public void setPageCtl(int pageIndex, int pageSize) {
        //���㿪ʼλ��
        currentIndex = (pageIndex - 1) * pageSize - 1;
        //�������λ��
        endIndex = currentIndex + 1 + pageSize;
        if (endIndex > elements.length) endIndex = elements.length;

    }


    //��Ҫ��д�˷���
    public ObjectIterator(int type, long[] elements,
                          final Object extraObject) {
        this.elements = elements;
        endIndex = elements.length;

        // Load the appropriate  factory depending on the type of object
        // that we're iterating through.
        switch (type) {
            case 0:
                // Create an objectFactoryImpl.
                this.objectFactory = new ObjectFactory() {
                    ObjectFactory factory = (ObjectFactory) extraObject;

                    public Object loadObject(long id) {
                        try {
                            Object object = factory.loadObject(id);
                            return object;
                        } catch (Exception e) {
                        }
                        return null;
                    }
                };
                break;
                //�˴������ڲ�������
            default:
                throw new IllegalArgumentException("Illegal type specified");
        }
    }


    //��Ҫ��д�˷���
    public ObjectIterator(long[] elements,
                          final ObjectFactory ObjectFactory) {
        this.elements = elements;
        endIndex = elements.length;
        //Load the appropriate  factory depending on the type of object
        //that we're iterating through.
        //Create an objectFactory to load forums.
        this.objectFactory = ObjectFactory;
    }

    public boolean hasNext() {

        if (currentIndex + 1 >= endIndex) {
            return false;
        }

        // If we are at the end of the list, there can't be any more elements
        // to iterate through.
        if (currentIndex + 1 >= elements.length && nextElement == null) {
            return false;
        }
        // Otherwise, see if nextElement is null. If so, try to load the next
        // element to make sure it exists.
        if (nextElement == null) {
            nextElement = getNextElement();
            if (nextElement == null) {
                return false;
            }
        }
        return true;
    }


    public Object next() throws java.util.NoSuchElementException {
        Object element = null;
        if (nextElement != null) {
            element = nextElement;
            nextElement = null;
        } else {
            element = getNextElement();
            if (element == null) {
                throw new java.util.NoSuchElementException();
            }
        }
        return element;
    }

    /**
     * Not supported for security reasons.
     */
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the next available element, or null if there are no more
     * elements to return.
     *
     * @return the next available element.
     */
    public Object getNextElement() {
        while (currentIndex + 1 < elements.length) {
            currentIndex++;
            Object element = objectFactory.loadObject(elements[currentIndex]);
            if (element != null) {
                return element;
            }
        }
        return null;
    }

}


