package com.je.game.bounds;

public class Hitbox2 {
    public final Vector2 pos;
    public final Size2 size;

    public Hitbox2(Vector2 pos, Size2 size) {
        this.pos = pos;
        this.size = size;
    }

    public Hitbox2() {
        pos = new Vector2();
        size = new Size2();
    }

    public Hitbox2(Hitbox2 hitbox) {
        pos = new Vector2(hitbox.pos);
        size = new Size2(hitbox.size);
    }

    @Override
    public String toString() {
        return "Hitbox{"+pos+", "+size+"}";
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(o == null) return false;
        if(o instanceof Hitbox2 hb) {
            return hb.pos.equals(pos)&&hb.size.equals(size);
        } else {
            return false;
        }
    }

    public final boolean intersects(Hitbox2 hb) {
        // this
        double tx = pos.x, ty = pos.y;
        double tw = size.width, th = size.height;
        // other
        double ox = hb.pos.x, oy = hb.pos.y;
        double ow = hb.size.width, oh = hb.size.height;

        return tx < ox+ow && tx+tw > ox &&
                ty < oy+oh && ty+th > oy;
    }

}
