package com.je.game.bounds;

import lib.Copyable;

public final class Vector3 implements Copyable<Vector3> {
    public double x, y, z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 vec) {
        this();
        set(vec);
    }

    public Vector3() {
        x = 0;
        y = 0;
        z = 0;
    }

    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    public int getZ() {
        return (int)z;
    }

    @Override
    public String toString() {
        return String.format("Vector3{x:%f,y:%f,z:%f}", x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==this)return true;
        if(!(obj instanceof Vector3 otherVector)) return false;
        return otherVector.x==x&& otherVector.y==y&& otherVector.z==z;
    }

    @Override
    public Vector3 copy() {
        return new Vector3(this);
    }

    public void set(Vector3 data) {
        x = data.x;
        y = data.y;
        z = data.z;
    }
}
