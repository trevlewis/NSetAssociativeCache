package com.thecompany.client;

import com.thecompany.cache.AlgorithmType;
import com.thecompany.cache.ReplacementAlgorithm;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ClientCacheTest {

    @Test
    public void cacheMissTest() {
        ClientCache cache = new ClientCache(4, 2, new ClientCustomAlgorithm(AlgorithmType.CUSTOM));
        cache.put(4, "Jon");
        cache.put(8, "Frank");
        cache.put(16, "Mark");
        cache.put(14, "Bill");

        assertEquals(cache.get(7), "Rufio");
    }

    @Test
    public void customizedReplacementAlgorithmTest() {
        ClientCache cache = new ClientCache(4, 2,new ClientCustomAlgorithm(AlgorithmType.CUSTOM));
        cache.put(4, "Jon");
        cache.put(8, "Frank");
        cache.put(16, "Mark");
        cache.put(14, "Bill");

        assertNull(cache.get(8));
        assertEquals(cache.get(4), "Jon");
        assertEquals(cache.get(16), "Mark");
        assertEquals(cache.get(14), "Bill");
    }

    @Test
    public void defulatCustomizedReplacementAlgorithmTest() {
        ClientCache cache = new ClientCache(4, 2,new ReplacementAlgorithm(AlgorithmType.CUSTOM));
        cache.put(4, "Jon");
        cache.put(8, "Frank");
        cache.put(16, "Mark");
        cache.put(14, "Bill");

        assertNull(cache.get(4));
        assertEquals(cache.get(8), "Frank");
        assertEquals(cache.get(16), "Mark");
        assertEquals(cache.get(14), "Bill");
    }

    @Test
    public void lruReplacementAlgorithmTest() {
        ClientCache cache = new ClientCache(4, 2, new ReplacementAlgorithm(AlgorithmType.LRU));
        cache.put(4, "Jon");
        cache.put(8, "Frank");
        cache.put(16, "Mark");
        cache.put(14, "Bill");

        assertNull(cache.get(4));
        assertEquals(cache.get(8), "Frank");
        assertEquals(cache.get(16), "Mark");
        assertEquals(cache.get(14), "Bill");
    }

    @Test
    public void mruReplacementAlgorithmTest() {
        ClientCache cache = new ClientCache(4, 2, new ReplacementAlgorithm(AlgorithmType.MRU));
        cache.put(4, "Jon");
        cache.put(8, "Frank");
        cache.put(16, "Mark");
        cache.put(14, "Bill");

        assertNull(cache.get(8));
        assertEquals(cache.get(4), "Jon");
        assertEquals(cache.get(16), "Mark");
        assertEquals(cache.get(14), "Bill");
    }
}
