package com.je.game.bounds;

public enum Direction2 {
    NONE("game.utils.d2d.NONE"),
    UP("game.utils.d2d.UP"),
    DOWN("game.utils.d2d.DOWN"),
    LEFT("game.utils.d2d.LEFT"),
    RIGHT("game.utils.d2d.RIGHT");

    public final String ID;
    Direction2(String id) {
        ID = id;
    }
}