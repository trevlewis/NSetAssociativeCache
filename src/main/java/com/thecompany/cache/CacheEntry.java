package com.thecompany.cache;

/**
 * Object to be inserted into the cache.
 */
public class CacheEntry {

    private int tag;
    private Object data;
    private long lastHitTimestamp;
    private boolean isEmpty;

    /**
     * Default constructor to create empty CacheEntry Object for block in cache.
     */
    public CacheEntry() {
        this.tag = 0;
        this.data = null;
        this.lastHitTimestamp = 0;
        this.isEmpty = true;
    }

    /**
     * Constructor to create CacheEntry object with parameters.
     *
     * @param tag
     * @param data
     * @param lastHitTimestamp
     * @param isEmpty
     */
    public CacheEntry(int tag, Object data, long lastHitTimestamp, boolean isEmpty) {
        this.tag = tag;
        this.data = data;
        this.lastHitTimestamp = lastHitTimestamp;
        this.isEmpty = isEmpty;
    }

    // GETTERS
    public int getTag() {
        return tag;
    }

    public Object getData() {
        return data;
    }

    public long getLastHitTimestamp() {
        return lastHitTimestamp;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    // SETTERS
    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setLastHitTimestamp(long lastHitTimestamp) {
        this.lastHitTimestamp = lastHitTimestamp;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

}
