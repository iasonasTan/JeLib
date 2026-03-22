package com.je.core;

import java.util.HashMap;
import java.util.Map;

public final class MapBuilder<K, V> {
    private final Map<K, V> mMap=new HashMap<>();

    public MapBuilder() {
    }

    public MapBuilder<K, V> put(K k, V v) {
        mMap.put(k, v);
        return this;
    }

    public Map<K, V> build() {
        return mMap;
    }
}

