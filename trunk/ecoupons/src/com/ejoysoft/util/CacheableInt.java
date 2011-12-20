/**
 * $RCSfile: CacheableInt.java,v $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/09/09 13:51:09 $
 *
 * New Jive  from Jdon.com.
 *
 * This software is the proprietary information of CoolServlets, Inc.
 * Use is subject to license terms.
 */

package com.ejoysoft.util;

/**
 * Wrapper for int values so they can be treated as Cacheable objects.
 * Integer is a final class, so it can't be extended.
 */
public class CacheableInt implements Cacheable {

    /**
     * Wrapped int value.
     */
    private int intValue;

    /**
     * Creates a new CacheableInt.
     *
     */
    public CacheableInt(int intValue) {
        this.intValue = intValue;
    }

    /**
     * Returns the underlying int value.
     *
     * @return the int value.
     */
    public int getInt() {
        return intValue;
    }

    //FROM THE CACHEABLE INTERFACE//

    public long getSize() {
        return CacheSizes.sizeOfObject() + CacheSizes.sizeOfInt();
    }
}