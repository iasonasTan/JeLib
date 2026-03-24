package com.je.io.configuration;

import com.je.core.JeLib;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * {@link Properties} wrapper that can load properties from {@link InputStream}
 * by just calling {@link #load(InputStream)}.
 * @deprecated use Bundle instead.
 */
@Deprecated
public class InputProperties {
    /**
     * Holds properties.
     */
    private Properties mProperties = null;

    /**
     * Loads properties from {@link InputStream}.
     * @param inputStream stream to load properties from.
     */
    public void load(InputStream inputStream) {
        try {
            mProperties = new Properties();
            mProperties.load(inputStream);
        } catch (IOException e) {
            JeLib.console().exception(e);
        }
    }

    public String getString(String key) {
        if(mProperties == null)
            throw new IllegalStateException("No data loaded.");
        return mProperties.getProperty(key);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key);
        if(value == null)
            return defaultValue;
        return Boolean.parseBoolean(getString(key));
    }

    public int getInteger(String key, int defaultValue) throws NumberFormatException {
        String value = getString(key);
        if(value == null)
            return defaultValue;
        return Integer.parseInt(getString(key));
    }

    public long getLong(String key, long defaultValue) throws NumberFormatException {
        String value = getString(key);
        if(value==null)
            return defaultValue;
        return Long.parseLong(value);
    }

    public float getFloat(String key, float defaultValue) throws NumberFormatException {
        String value = getString(key);
        if(value == null)
            return defaultValue;
        return Float.parseFloat(getString(key));
    }

    public double getDouble(String key, double defaultValue) throws NumberFormatException {
        String value = getString(key);
        if(value == null)
            return defaultValue;
        return Double.parseDouble(value);
    }
}
