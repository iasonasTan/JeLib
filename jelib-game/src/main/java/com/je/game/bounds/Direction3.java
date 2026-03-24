package com.je.game.bounds;

public enum Direction3 {
    NONE("game.utils.d3d.NONE"),
    UP("game.utils.d3d.UP"),
    DOWN("game.utils.d3d.DOWN"),
    LEFT("game.utils.d3d.LEFT"),
    RIGHT("game.utils.d3d.RIGHT"),
    FORWARD("game.utils.d3d.FORWARD"),
    BACKWARD("game.utils.d3d.BACKWARD");

    public final String ID;
    Direction3(String id) {
        ID = id;
    }
}
