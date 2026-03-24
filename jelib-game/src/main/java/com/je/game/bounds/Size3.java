package com.je.game.bounds;

import com.je.core.Copyable;

public final class Size3 implements Copyable<Size3> {
    public double width, height, length;

    public Size3(double size) {
        this(size, size, size);
    }

    public Size3(Size3 size) {
        width  = size.width;
        height = size.height;
        length = size.length;
    }

    public Size3() {
        width  = 0;
        height = 0;
        length = 0;
    }

    public Size3(double w, double h, double l) {
        width  = w;
        height = h;
        length = l;
    }

    public int getWidth() {
        return (int)width;
    }

    public int getHeight() {
        return (int)height;
    }

    public int getLength() {
        return (int)length;
    }

    @Override
    public String toString() {
        return String.format("%s{w:%f,h:%f,l:%f}", getClass().getSimpleName(), width, height, length);
    }

    @Override
    public boolean equals(Object other) {
        if(this==other)return true;
        if(!(other instanceof Size3 otherSize)) return false;
        return otherSize.width == width && otherSize.height == height&&otherSize.length==length;
    }

    @Override
    public Size3 copy() {
        return new Size3(this);
    }
}
