package com.je.game.bounds;

import com.je.core.Copyable;

/**
 * <b>Not</b> working in <i>hash collections</i>
 */
public final class Size2 implements Copyable<Size2> {
    public float width, height;

    public Size2() {
        this(0f, 0f);
    }

    public Size2(float width, float height) {
        this.width  = width;
        this.height = height;
    }

    public Size2(Size2 data) {
        width  = data.width;
        height = data.height;
    }

    @Override
    public String toString() {
        return String.format("Size{%f,%f}", width, height);
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(o == null) return false;
        if(o instanceof Size2 size) {
            return size.width == width && size.height == height;
        } else {
            return false;
        }
    }

    public int getWidth() {
        return (int)width;
    }

    public int getHeight() {
        return (int)height;
    }

    public void set(Size2 data) {
        width  = data.width;
        height = data.height;
    }

    @Override
    public Size2 copy() {
        return new Size2(this);
    }
}
