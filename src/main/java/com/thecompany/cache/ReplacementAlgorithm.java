package com.thecompany.cache;

/**
 * Contains the replacement algorithms for LRU, MRU, and the default CUSTOM algorithm.
 */
public class ReplacementAlgorithm {

    private AlgorithmType replacementAlgorithm;

    public ReplacementAlgorithm(AlgorithmType algorithmType) {
        this.replacementAlgorithm = algorithmType;
    }

    /**
     * Retrieve the replacement block index to be used to add new entry to a set that is full.
     *
     * @param cache
     * @param startBlockIndex
     * @param endBlockIndex
     * @return block index or IllegalArgumentException
     */
    protected int getReplacementBlockIndex(CacheEntry[] cache, int startBlockIndex, int endBlockIndex) {
        switch (this.replacementAlgorithm) {
            case LRU:
                return lruReplacementAlgorithm(cache, startBlockIndex, endBlockIndex);
            case MRU:
                return mruReplacementAlgorithm(cache, startBlockIndex, endBlockIndex);
            case CUSTOM:
                return customizedReplacementAlgorithm(cache, startBlockIndex, endBlockIndex);
            default:
                throw new IllegalArgumentException("Not a valid replacement algorithm. See AlgorithmType for options.");
        }
    }


    /**
     * Least recently used (LRU) replacement algorithm.
     *
     * @param startBlockIndex
     * @param endBlockIndex
     * @return LRU block index
     */
    private int lruReplacementAlgorithm(CacheEntry[] cache, int startBlockIndex, int endBlockIndex) {
        int lruBlockIndex = startBlockIndex;
        long lastHitTimestamp = cache[startBlockIndex].getLastHitTimestamp();
        for (int i = startBlockIndex; i <= endBlockIndex; i++) {
            long entryLastHitTimestamp = cache[i].getLastHitTimestamp();
            if (lastHitTimestamp > entryLastHitTimestamp) {
                lruBlockIndex = i;
                lastHitTimestamp = entryLastHitTimestamp;
            }
        }

        return lruBlockIndex;
    }

    /**
     * Most recently used (MRU) replacement algorithm.
     *
     * @param startBlockIndex
     * @param endBlockIndex
     * @return MRU block index
     */
    private int mruReplacementAlgorithm(CacheEntry[] cache, int startBlockIndex, int endBlockIndex) {
        int mruBlockIndex = startBlockIndex;
        long lastHitTimestamp = cache[startBlockIndex].getLastHitTimestamp();
        for (int i = startBlockIndex; i <= endBlockIndex; i++) {
            long entryLastHitTimestamp = cache[i].getLastHitTimestamp();
            if (lastHitTimestamp < entryLastHitTimestamp) {
                mruBlockIndex = i;
                lastHitTimestamp = entryLastHitTimestamp;
            }
        }

        return mruBlockIndex;
    }

    /**
     * Customized recently used (CUSTOM) replacement algorithm.
     *
     * @param startBlockIndex
     * @param endBlockIndex
     * @return Customized replacement algorithm block index.
     */
    public int customizedReplacementAlgorithm(CacheEntry[] cache, int startBlockIndex, int endBlockIndex) {
        return startBlockIndex;
    }

}
