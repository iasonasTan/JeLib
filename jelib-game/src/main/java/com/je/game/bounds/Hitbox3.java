package com.je.game.bounds;

import lib.Copyable;

public final class Hitbox3 implements Copyable<Hitbox3> {
    public double x, y, z;
    public double width, height, length;
    public String name;

    public Hitbox3(Hitbox3 data) {
        name   = data.name;
        x      = data.x;
        y      = data.y;
        z      = data.z;
        width  = data.width;
        height = data.height;
        length = data.length;
    }

    public Hitbox3(String name, double x, double y, double w, double h) {
        this.name   = name;
        this.x      = x;
        this.y      = y;
        this.width  = w;
        this.height = h;
    }

    public Hitbox3(double x, double y, double w, double h) {
        this("", x, y, w, h);
        name = "Hitbox:"+hashCode();
    }

    public boolean intersects(Hitbox3 other) {
        return (other.x +other.width >= x &&
                other.x <= x + width &&
                other.y +other.height >= y &&
                other.y <= y + height);
    }

    @Override
    public String toString() {
        return String.format("%s{x:%f,y:%f,z:%f,w:%f,h:%f,l:%f}",getClass().getSimpleName(), x, y, z, width, height, length);
    }

    @Override
    public boolean equals(Object other) {
        if(this==other)return true;
        if(!(other instanceof Hitbox3 otherHitbox)) return false;
        return otherHitbox.x == x && otherHitbox.y == y&&otherHitbox.z==z&&
                otherHitbox.width==width&&otherHitbox.height==height&&otherHitbox.length==length;
    }

    @Override
    public Hitbox3 copy() {
        return new Hitbox3(this);
    }
}
