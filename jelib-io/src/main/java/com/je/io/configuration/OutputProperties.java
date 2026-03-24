package com.je.io.configuration;

import com.je.core.JeLib;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * {@link Properties} wrapper that can store properties to {@link OutputStream}
 * by just calling {@link #store(OutputStream)}.
 * @deprecated use Bundle instead.
 */
public class OutputProperties {
    /**
     * Holds properties.
     */
    private final Properties mProperties = new Properties();

    /**
     * Stores properties to {@link OutputStream}.
     * @param outputStream stream to store properties to.
     */
    public void store(OutputStream outputStream) {
        try {
            mProperties.store(outputStream, "Updated Properties");
        } catch (IOException e) {
            JeLib.console().exception(e);
        }
    }

    public OutputProperties put(String key, String value) {
        mProperties.setProperty(Objects.requireNonNull(key), Objects.requireNonNull(value));
        return this;
    }

    public OutputProperties put(String key, boolean value) {
        mProperties.setProperty(key, String.valueOf(value));
        return this;
    }

    public OutputProperties put(String key, int value) {
        mProperties.setProperty(key, String.valueOf(value));
        return this;
    }

    public OutputProperties put(String key, long value) {
        mProperties.setProperty(key, String.valueOf(value));
        return this;
    }

    public OutputProperties put(String key, float value) {
        mProperties.setProperty(key, String.valueOf(value));
        return this;
    }

    public OutputProperties put(String key, double value) {
        mProperties.setProperty(key, String.valueOf(value));
        return this;
    }
}
