package com.thecompany.client;

import com.thecompany.cache.AlgorithmType;
import com.thecompany.cache.CacheEntry;
import com.thecompany.cache.ReplacementAlgorithm;

/**
 * Mock of a potential client implementation of the ReplacementAlgorithm customizedReplacementAlgorithm().
 */
public class ClientCustomAlgorithm extends ReplacementAlgorithm {

    public ClientCustomAlgorithm(AlgorithmType algorithmType) {
        super(algorithmType);
    }

    @Override
    public int customizedReplacementAlgorithm(CacheEntry[] cache, int startBlockIndex, int endBlockIndex) {
        return startBlockIndex + 1;
    }
}
