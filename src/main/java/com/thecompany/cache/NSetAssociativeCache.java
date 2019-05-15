package com.thecompany.cache;

import java.util.Arrays;

/**
 * N-Set Associative Cache which stores mappings from a key to a value.
 * Entries in the cache are retrieved with get(Object key) and inserted with put(Object key, Object value).
 * Entries are stored in cache until they are evicted with a replacement algorithm or manually invalidated.
 */
public class NSetAssociativeCache implements Cache {

    private final CacheEntry[] cache;
    private final ReplacementAlgorithm replacementAlgorithm;
    private final int numberOfCacheSets;
    private final int numberOfSetBlocks;
    private Object masterKey;
    private Object masterValue;

    /**
     * Initialize NSetAssociativeCache Object with number of cache sets and number of
     * set entries. The default replacement algorithm is LRU.
     *
     * @param numberOfCacheSets
     * @param numberOfSetBlocks
     */
    public NSetAssociativeCache(int numberOfCacheSets, int numberOfSetBlocks) {
        this.numberOfCacheSets = numberOfCacheSets;
        this.numberOfSetBlocks = numberOfSetBlocks;
        this.cache = new CacheEntry[this.numberOfCacheSets * this.numberOfSetBlocks];
        this.replacementAlgorithm = new ReplacementAlgorithm(AlgorithmType.LRU);
        generateCache();
    }

    /**
     * Initialize NSetAssociativeCache Object with number of cache sets, number of
     * set entries, and the replacement algorithm (LRU, MRU, CUSTOM).
     *
     * @param numberOfCacheSets
     * @param numberOfSetBlocks
     * @param replacementAlgorithm
     */
    public NSetAssociativeCache(int numberOfCacheSets, int numberOfSetBlocks, ReplacementAlgorithm replacementAlgorithm) {
        this.numberOfCacheSets = numberOfCacheSets;
        this.numberOfSetBlocks = numberOfSetBlocks;
        this.cache = new CacheEntry[this.numberOfCacheSets * this.numberOfSetBlocks];
        this.replacementAlgorithm = replacementAlgorithm;
        generateCache();
    }

    @Override
    public void put(Object key, Object value) {
        validateKeyValue(key, value);

        int tag = generateTag(key);
        boolean cacheHit = false;
        int startBlockIndex = getStartBlockIndex(tag);
        int endBlockIndex = getEndBlockIndex(startBlockIndex);
        CacheEntry newEntry = new CacheEntry(tag, value, getCurrentTime(), false);

        for (int i = startBlockIndex; i <= endBlockIndex; i++) {
            if (cache[i].isEmpty() || cache[i].getTag() == tag) {
                cache[i] = newEntry;
                cacheHit = true;
                break;
            }
        }

        //If blocks are full replace at index from replacement algorithm.
        if (!cacheHit) {
            int replacementBlockIndex = replacementAlgorithm.getReplacementBlockIndex(cache, startBlockIndex, endBlockIndex);
            cache[replacementBlockIndex] = newEntry;
        }
    }

    @Override
    public Object get(Object key) {
        boolean cacheHit = false;
        int tag = generateTag(key);
        Object cacheEntryData = null;
        int startBlockIndex = getStartBlockIndex(tag);
        int endBlockIndex = getEndBlockIndex(startBlockIndex);

        for (int i = startBlockIndex; i <= endBlockIndex; i++) {
            if (cache[i].getTag() == tag) {
                cache[i].setLastHitTimestamp(getCurrentTime());
                cacheEntryData = cache[i].getData();
                cacheHit = true;
                break;
            }
        }

        if (!cacheHit) {
            return cacheMiss(tag);
        }

        return cacheEntryData;
    }

    @Override
    public long size() {
        long count = 0;
        for (CacheEntry aCacheArray : cache) {
            if (!aCacheArray.isEmpty()) {
                count++;
            }
        }

        return count;
    }

    @Override
    public void invalidate(Object key) {
        int tag = generateTag(key);
        for (int i = 0; i < cache.length; i++) {
            if (cache[i].getTag() == tag) {
                cache[i] = new CacheEntry();
                break;
            }
        }

        //Reset cache type if empty
        if (isEmpty()){
            masterKey = null;
            masterValue = null;
        }
    }

    @Override
    public void invalidateAll() {
        Arrays.setAll(cache, i -> new CacheEntry());

        //Reset cache type
        masterKey = null;
        masterValue = null;
    }

    @Override
    public boolean isEmpty() {
        return Arrays.stream(cache).allMatch(CacheEntry::isEmpty);
    }

    /**
     * Validate that the Key and/or Value is the same type that is present in this instance of
     * NSetAssociativeCache.
     *
     * @param key
     * @param value
     */
    private void validateKeyValue(Object key, Object value) {
        if (masterKey == null && masterValue == null) {
            masterKey = key;
            masterValue = value;
        } else if (masterKey.getClass() != key.getClass()
                || masterValue.getClass() != value.getClass()) {
            throw new IllegalArgumentException("This array contains types of key: " + masterKey.getClass().getSimpleName() +
                    " and value: " + masterValue.getClass().getSimpleName());
        }
    }

    /**
     * Returns cache miss result.
     * Default return is null, but can be overridden.
     *
     * @return Object
     */
    public Object cacheMiss(Object key) {
        return null;
    }

    /**
     * Generate a hash code integer from the key Object.
     *
     * @param key
     * @return hash code integer
     */
    public int generateTag(Object key) {
        return Math.abs(key.hashCode());
    }

    /**
     * Get the current JVM System time in nanoseconds.
     *
     * @return Current System time in nanoseconds
     */
    public long getCurrentTime() {
        return System.nanoTime();
    }

    /**
     * Generates empty CacheEntry Objects to be used as cache blocks.
     */
    private void generateCache() {
        Arrays.setAll(cache, i -> new CacheEntry());
    }

    /**
     * Retrieve the start block index of the cache set for cache entry placement.
     *
     * @param tag
     * @return start block index of cache set
     */
    private int getStartBlockIndex(int tag) {
        return Math.abs(tag % numberOfCacheSets);
    }

    /**
     * Retrieve the end block index of the cache set for cache entry placement.
     *
     * @param startBlockIndex
     * @return end block index of cache set
     */
    private int getEndBlockIndex(int startBlockIndex) {
        return startBlockIndex + numberOfSetBlocks - 1;
    }

}
