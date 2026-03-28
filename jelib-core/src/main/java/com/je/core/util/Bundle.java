package com.je.core.util;

import com.je.core.Copyable;

import java.util.HashMap;
import java.util.Map;

/**
 * A bundle that holds data like a {@link Map}.
 * This class uses a {@link Map} to store the
 * data and methods to get values and cast the data.
 */
public final class Bundle implements Copyable<Bundle> {
    /**
     * Returns a builder that generates a bundle.
     * @return {@link Builder} instance.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Map that holds data
     */
    private final Map<String, Object> mData;

    /**
     * Constructs a bundle with a {@link HashMap}.
     */
    public Bundle() {
        this(new HashMap<>());
    }

    /**
     * Constructs a bundle with a {@link HashMap} and puts given values.
     * @param data Data to put into map after construction.
     */
    public Bundle(Map<String, Object> data) {
        mData = new HashMap<>(data);
    }

    /**
     * Constructs a bundle and copies values from given one 
     * @param bundle Bundle to copy data from.
     */
    public Bundle(Bundle bundle) {
        this(new HashMap<>(bundle.mData));
    }

    /**
     * Returns the object of the given key.
     * @param key Key of object to return.
     * @return Value of given key as {@link Object}.
     */
    public Object get(String key) {
        return mData.get(key);
    }

    /**
     * Returns the string of the given key.
     * @param key Key of object to return.
     * @return Value of given key as {@link String}
     */
    public String getString(String key) throws ClassCastException {
        return (String)mData.get(key);
    }

    /**
     * Returns the integer of the given key.
     * @param key Key of object to return.
     * @return Value of given key as integer.
     */
    public int getInteger(String key, int defaultValue) throws ClassCastException {
        Object obj = mData.get(key);
        if(obj==null)
            return defaultValue;
        return (Integer)obj;
    }

    /**
     * Returns the double of the given key.
     * @param key Key of object to return.
     * @return Value of given key as double.
     */
    public double getDouble(String key, double defaultValue) throws ClassCastException {
        Object obj = mData.get(key);
        if(obj==null)
            return defaultValue;
        return (Double)obj;
    }

    /**
     * Returns the value of the given key.
     * @param key          key of the object to return.
     * @param defaultValue value to return in case required value is not found.
     * @return Value of the given key as boolean.
     */
    public boolean getBoolean(String key, boolean defaultValue) throws ClassCastException {
        Object obj = mData.get(key);
        if(obj==null)
            return defaultValue;
        return (Boolean)obj;
    }

    /**
     * Returns the object of the given key.
     * @param key Key of object to return.
     * @return Value of given key as param T.
     * @param <T> Type to cast object to.
     */
    public <T> T getObject(String key, Class<T> clazz) {
        Object obj = mData.get(key);
        return clazz.cast(obj);
    }

    /**
     * Puts given key and object to bundle.
     * @param key Key of object to put.
     * @param value Value to put to bundle.
     */
    public void put(String key, Object value) {
        mData.put(key, value);
    }

    /**
     * Checks if bundle contains given keys.
     * @param keys Keys to check if they exist.
     * @return true if given key is contained in bundle; false otherwise.
     */
    public boolean contains(String... keys) {
        for(String key: keys) {
            if(!mData.containsKey(key))
                return false;
        }
        return true;
    }

    /**
     * Returns a representation of the bundle.
     * @return returns a string that represents the bundle.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()+mData;
    }

    /**
     * Returns a copy of {@code this}.
     * It's recommended to use a copy constructor.
     */
    @Override
    public Bundle copy() {
        return new Bundle(this);
    }

    /**
     * Returns a raw copy of the bundle's data.
     * @return a raw copy of the bundle's data as {@link HashMap}.
     */
    public Map<String, Object> getRawData() {
        return new HashMap<>(mData);
    }

    /**
     * Builds a bundle and supplies method {@link Bundle#put(String,Object)}
     */
    public static final class Builder {
        /**
         * Building bundle.
         */
        private final Bundle mBundle = new Bundle();

        /**
         * Constructs a builder.
         */
        private Builder(){
        }

        /**
         * Puts given key and object to the building bundle.
         * @param id ID of object to put.
         * @param obj Object to put.
         * @return Returns this so chain calls are possible.
         */
        public Builder put(String id, Object obj) {
            mBundle.put(id, obj);
            return this;
        }
    
        /**
         * Returns instance of building bundle.
         */
        public Bundle build() {
            return mBundle;
        }
    }
}
