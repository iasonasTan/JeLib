package com.je.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used to build a {@link Map} with 
 * cleaner and more readable code.
 */
public final class MapBuilder<K, V> {
    /**
     * Implementation of map that holds the data.
     */
    private final Map<K, V> mMap;

    /**
     * No-arg constructor, initializes with {@link HashMap}.
     * @see MapBuilder(Map)
     */
    public MapBuilder() {
        this(new HashMap<>());
    }
    
    /**
     * Constructor that takes instance that will 
     * be used to store and returned.
     */
    public MapBuilder(Map<K, V> impl) {
        mMap = impl;
    }

    /**
     * Puts a value in map that's going to be returned.
     * @param k key to add
     * @param v value to add
     * @return returns this so chain calls are possible
     */
    public MapBuilder<K, V> put(K k, V v) {
        mMap.put(k, v);
        return this;
    }

    /**
     * Returns instance of the put data.
     * @return {@link Map} implementation with put values.
     * @see #put(K,V)
     */
    public Map<K, V> build() {
        return mMap;
    }
}

