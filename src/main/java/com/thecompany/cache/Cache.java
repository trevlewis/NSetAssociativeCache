package com.thecompany.cache;

/**
 * Cache which stores mappings from a key to a value.
 * Entries in the cache are retrieved with get(Object key) and inserted with put(Object key, Object value).
 * Entries are stored in cache until they are evicted with a replacement algorithm or manually invalidated.
 */
public interface Cache {

    /**
     * Insert key and mapped value into cache.
     *
     * @param key
     * @param value
     */
    void put(Object key, Object value);

    /**
     * Retrieve the value that is mapped to key.
     *
     * @param key
     * @return
     */
    Object get(Object key);

    /**
     * Retrieve the number of cache blocks that are not empty.
     *
     * @return
     */
    long size();

    /**
     * Invalidate cache block mapped to key.
     *
     * @param key
     */
    void invalidate(Object key);

    /**
     * Invalidate all blocks in the cache.
     */
    void invalidateAll();

    /**
     * Determine if the cache is empty.
     *
     * @return
     */
    boolean isEmpty();

}
