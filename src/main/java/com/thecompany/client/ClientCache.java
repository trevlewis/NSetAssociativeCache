package com.thecompany.client;

import com.thecompany.cache.CacheEntry;
import com.thecompany.cache.NSetAssociativeCache;
import com.thecompany.cache.ReplacementAlgorithm;

/**
 * Mock of a potential client implementation of the cache.
 */
public class ClientCache extends NSetAssociativeCache {

    public ClientCache(int numberOfCacheSets, int numberOfSetEntries, ReplacementAlgorithm replacementAlgorithm) {
        super(numberOfCacheSets, numberOfSetEntries, replacementAlgorithm);
    }

    /**
     * Retrieve the cache entry object from main memory.
     *
     * @return object from main memory
     */
    @Override
    public Object cacheMiss(Object key) {
        //Mocked object returned from main memory.
        CacheEntry mainMemoryCacheEntry = new CacheEntry(generateTag(7), "Rufio", getCurrentTime(), false);

        if (key.equals(mainMemoryCacheEntry.getTag())){
            return mainMemoryCacheEntry.getData();
        }

        return null;
    }
}
