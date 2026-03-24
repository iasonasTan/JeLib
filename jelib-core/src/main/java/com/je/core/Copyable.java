package com.je.core;

/**
 * Represents an object that can be copied.
 * @param <T> the type of the copyable object.
 */
public interface Copyable<T> {
    /**
     * Returns a copy of {@code this}.
     * It's recommended to use a copy constructor.
     */
    T copy();
}
