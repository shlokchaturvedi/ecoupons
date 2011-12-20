package com.ejoysoft.util;

import java.util.Map;
import java.util.HashMap;


/**
 * �ļ���ƣ�CacheFactory.java
 * @author feiwj
 * @version $Revision: 2.0 $ $Date: 2005-02-01 11:31 $
 */
public class CacheFactory {


    protected static Map utilCacheTable = new HashMap();
    protected Cache cache = null;

    public static Map getAllCache() {
        return utilCacheTable;
    }


    public static CacheFactory getInstance() {
        return new CacheFactory("default");
    }

    public static CacheFactory getInstance(String name) {
        return new CacheFactory(name);
    }


    private CacheFactory(String name) {
        synchronized (utilCacheTable) {
            if ((cache = (Cache) utilCacheTable.get(name.toLowerCase())) == null) {
                setPropertiesParams(name.toLowerCase());
                cache = new Cache(maxSize, expireTime);
                utilCacheTable.put(name.toLowerCase(), cache);
            }
        }
    }

    public Cacheable get(Object key) {
        return cache.get(key);
    }

    public void add(Object key, Cacheable value) {
        cache.add(key, value);
    }

    public void remove(Object key) {
        cache.remove(key);
    }


    /**
     * ���ƥ��� patter����
     * @param endth
     */
    public void removePatter(String endth) {
        Object[] o = cache.keys();
        if (o != null) {
            for (int j = 0; j < o.length; j++) {
                if (((String) o[j]).endsWith(endth))
                    cache.remove(o[j], "remove ends key{" + o[j] + "}  like" + endth);
            }
        }
    }

    public long size() {
        return cache.size;
    }

    public void clear() {
        cache.clear();
    }

    public boolean contain(Object key) {
        return cache.get(key) != null;
    }

    public Object[] values() {
        return cache.values();
    }

    protected void setPropertiesParams(String cacheName) {


    }


    /** The maximum number of elements in the cache.
     * If set to 0, there will be no limit on the number of elements in the cache.
     */

    protected long maxSize = 10 * 1024 * 1024;

    /** Specifies the amount of time since initial loading before an element will be reported as expired.
     * If set to 0, elements will never expire.
     */
    protected long expireTime = 10 * 1000 * 60;

    protected String description = "Specifies the amount of time since initial loading before";


}
