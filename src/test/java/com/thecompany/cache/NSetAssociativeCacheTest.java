package com.thecompany.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NSetAssociativeCacheTest {

    @Test
    public void putTest() {
        NSetAssociativeCache cache = new NSetAssociativeCache(4, 2);
        cache.put("First", "Mark");
        cache.put("Second", "Bill");

        assertEquals(cache.get("First"), "Mark");
        assertEquals(cache.get("Second"), "Bill");
    }

    @Test
    public void getTest() {
        NSetAssociativeCache cache = new NSetAssociativeCache(4, 2);
        cache.put(15, "Mark");
        cache.put(16, "Bill");

        assertEquals(cache.get(15), "Mark");
        assertEquals(cache.get(16), "Bill");
    }

    @Test
    public void sizeTest() {
        NSetAssociativeCache cache = new NSetAssociativeCache(6, 2);
        cache.put(5, "Jon");
        cache.put(26, "Frank");
        cache.put(15, "Mark");
        cache.put(6, "Bill");

        assertEquals(cache.size(), 4);
    }

    @Test
    public void invalidateTest() {
        NSetAssociativeCache cache = new NSetAssociativeCache(4, 2);
        cache.put(5, "Jon");
        cache.put(26, "Frank");
        cache.put(15, "Mark");
        cache.put(6, "Bill");

        cache.invalidate(26);

        assertEquals(cache.get(5), "Jon");
        assertEquals(cache.get(15), "Mark");
        assertEquals(cache.get(6), "Bill");

        assertEquals(cache.size(), 3);
    }

    @Test
    public void invalidateAllTest() {
        NSetAssociativeCache cache = new NSetAssociativeCache(4, 2);
        cache.put(5, "Jon");
        cache.put(26, "Frank");
        cache.put(15, "Mark");
        cache.put(6, "Bill");

        cache.invalidateAll();

        assertEquals(cache.size(), 0);
    }

    @Test
    public void isEmptyTest() {
        NSetAssociativeCache cache = new NSetAssociativeCache(4, 2);
        cache.put(5, "Jon");
        cache.put(26, "Frank");
        cache.put(15, "Mark");
        cache.put(6, "Bill");

        cache.invalidateAll();

        assertTrue(cache.isEmpty());
    }

}
